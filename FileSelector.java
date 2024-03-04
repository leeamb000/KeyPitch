import java.awt.FlowLayout;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileSelector extends JFrame implements ActionListener
{
    JButton button;

    FileSelector() //constructor
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());

        button = new JButton("Select File");
        button.addActionListener(this);

        this.add(button);
        this.pack();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {

        if(e.getSource()==button)
        {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(  //file chooser only shows WAV files
                    "WAV Files", "wav");
            fileChooser.setFileFilter(filter);

            int returnVal = fileChooser.showOpenDialog(null); //select file to open

            if(returnVal == JFileChooser.APPROVE_OPTION) //if file is selected
            {
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                System.out.println(file); //replace or delete print line when no longer wanted

            }
        }
    }
}
