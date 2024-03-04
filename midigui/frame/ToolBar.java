package midigui.frame;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.ButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class ToolBar extends JPanel implements ActionListener {
    private JButton undo;
    private JButton forward;
    private JButton record;
    private JButton play;

    private JButton synth;
    private JButton piano;
    private JButton custom;

    private frame.CustomListener textListener;
    Dimension d;

    public ToolBar(){
        undo = new JButton("undo");
        forward = new JButton("forward");
        record = new JButton("record");
        play = new JButton("play");

        synth = new JButton("Synth");
        piano = new JButton("Piano");
        custom = new JButton("Custom");

        d = getPreferredSize();
        d.height = 120;//height of the toolbar
        setPreferredSize(d);//set the height of the toolbar

        setLayout(new FlowLayout(FlowLayout.CENTER)); //move all the buttons to the left
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "KeyPitch Midi Player", TitledBorder.RIGHT, TitledBorder.BOTTOM));
        setBackground(new Color(3, 84, 87, 120));

//        add(undo);
//        add(forward);
        //add(play);
        //add(record);

        add(synth);
        add(piano);
        add(custom);

        undo.setToolTipText("undo");
        forward.setToolTipText("forward");
        play.setToolTipText("play");
        record.setToolTipText("record");

        synth.setToolTipText("synthesizer profile");
        piano.setToolTipText("piano profile");
        custom.setToolTipText("custom profile");

        play.setIcon(createIcon("..//images//icons8-play-button-circled-16.png"));
        record.setIcon(createIcon("..//images//icons8-voice-recorder-16.png"));

        undo.setFocusable(false);
        forward.setFocusable(false);
        play.setFocusable(false);
        record.setFocusable(false);

        synth.setFocusable(false);
        piano.setFocusable(false);
        custom.setFocusable(false);

        undo.setFont(new Font("Times New Roman", Font.BOLD, 15));
        forward.setFont(new Font("Times New Roman", Font.BOLD, 15));
        play.setFont(new Font("Times New Roman", Font.BOLD, 15));
        record.setFont(new Font("Times New Roman", Font.BOLD, 15));

        synth.setFont(new Font("Times New Roman", Font.BOLD, 15));
        piano.setFont(new Font("Times New Roman", Font.BOLD, 15));
        custom.setFont(new Font("Times New Roman", Font.BOLD, 15));

        undo.addActionListener(this); //respond to an action
        forward.addActionListener(this);
        record.addActionListener(this);
        play.addActionListener(this);

        synth.addActionListener(this);
        piano.addActionListener(this);
        custom.addActionListener(this);
    }

    /**
     * checks the icon image path
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

    /**
     * add action listeners to each button
     * @param customListener
     */
    public void setCustomListener(frame.CustomListener customListener){
        this.textListener = customListener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();

        if(clicked == undo){
            //to be changed for connection
//            if(textListener != null){
//                textListener.emmited("undo\n");
//            }

        }
        if(clicked == forward){
            //to be changed for connection
//               if(textListener != null){
//                   textListener.emmited("forward\n");
//            }
        }
        if(clicked == play){
            //to be changed for connection
//            if(textListener != null){
//                textListener.emmited("play\n");
//            }
        }
        if(clicked == record){
            //to be changed for connection
//            if(textListener != null){
//                textListener.emmited("record\n");
//            }
        }
        if(clicked == synth){
            //to be changed for connection
//            if(textListener != null){
//                textListener.emmited("record\n");
//            }
            profile.ProfileController.setProfileSynth();
        }
        if(clicked == piano){
            //to be changed for connection
//            if(textListener != null){
//                textListener.emmited("record\n");
//            }
            profile.ProfileController.setProfilePiano();
        }
        if(clicked == custom){
            //to be changed for connection
//            if(textListener != null){
//                textListener.emmited("record\n");
//            }
            profile.ProfileController.setProfileCustom();
        }
    }
}
