package midigui.frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.URL;

import midigui.midi.*;
//import frame.*;

/**
 * the MainFrame class adds all the components to the frame window to customize the gui
 */
public class MainFrame  extends JFrame {
    private ToolBar toolBar;

    private TextPanel textPanel; //adds strings to the text panel when buttons are clicked
    private NewPanel panel_1, panel_2, panel_3;
    private MidiPanel midiPanel;
    private JFileChooser fileChooser;

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    private int height;
    private int width;

    /**
     * MainFrame constructor
     * initializes a layout, a toolbar, a textPanel
     */
    public MainFrame(){
        super("MIDI PLAYER");

        setLayout(new BorderLayout());//set layout design BorderLayout
        panel_1 = new NewPanel();
        panel_2 = new NewPanel();
        panel_3 = new NewPanel();
        fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new WAVFileFilter());
        midiPanel =new MidiPanel();


        toolBar= new ToolBar(); //setting up a toolbar on top of the application
        /*
        add a listener interface
        change the component when connecting
         */
        toolBar.setCustomListener(new frame.CustomListener() {
            @Override
            public void emmited(String text) {
                textPanel.appendText(text);
            }
        });

        add(panel_1, BorderLayout.WEST);
        add(panel_2, BorderLayout.EAST);
        add(panel_3, BorderLayout.SOUTH);
        add(toolBar, BorderLayout.NORTH); //add a toolbar at the top of the screen
        add(midiPanel, BorderLayout.CENTER); //midi graphics panel

        setJMenuBar(createMenuBar());
        setIconImage(createIcon("..//images//icons8-piano-pastel-glyph-96.png").getImage());

        
// set the frame height and width
        height = screenSize.height * 2 / 3;
        width = screenSize.width * 2 / 3;
//        setPreferredSize(new Dimension(width, height));

        setSize(new Dimension(810, 600));//sets the size of the application frame/
//        setMinimumSize(new Dimension(900,600));
        
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //close application
        setVisible(true); //set visibility
    }

    /**
     * creates the menubar to contain file management
     * @return menubar
     */
    private JMenuBar createMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        //   JMenu settings = new JMenu("Settings");

        JMenuItem openFile = new JMenuItem("Open...");
        JMenuItem saveAll = new JMenuItem("Save All");
        JMenuItem exportFile = new JMenuItem("Export File");
        JMenuItem exit = new JMenuItem("Exit");

        fileMenu.add(openFile);
        fileMenu.add(saveAll);
        fileMenu.add(exportFile);
        fileMenu.addSeparator();
        fileMenu.add(exit);

        menuBar.add(fileMenu);
//    menuBar.add(settings);

        /* ALT + F to access the file menu */
        fileMenu.setMnemonic(KeyEvent.VK_F);
        /* ALT + X to exit the application */
        exit.setMnemonic(KeyEvent.VK_X);

        /*add accelerators to access the menu fields CTRL + O to open, CTRL + S to save ...*/

        saveAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        exportFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
//        openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_0, ActionEvent.CTRL_MASK));

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int action = JOptionPane.showConfirmDialog(MainFrame.this,
                        "Do you really want to exit the application?", "Confirm",
                        JOptionPane.OK_CANCEL_OPTION);

                if (action == JOptionPane.OK_OPTION ){
                    System.exit(0);
                }
            }
        });

        openFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION)
                    //System.out.println(fileChooser.getSelectedFile());//change this code to connect to application
                    profile.ProfileController.setCustomFile(fileChooser.getSelectedFile());
            }
        });

        exportFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION)
                    System.out.println(fileChooser.getSelectedFile());//change this code to connect to application
            }
        });

        saveAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MainFrame.this, "File saved");
            }
        });

        return menuBar;
    }

    /**
     * checks is the icon image is in the correct path
     * @param path
     * @return
     */
    private ImageIcon createIcon(String path){
        URL url = getClass().getResource(path);

        if(url == null){
            System.err.println("Unable to load the image: " + path);
        }
        ImageIcon icon = new ImageIcon(url);
        return icon;
    }

}

