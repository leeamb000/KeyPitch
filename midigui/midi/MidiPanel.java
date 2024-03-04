package midigui.midi;

import javax.swing.*;
import java.awt.*;

public class MidiPanel extends JPanel {

    private midi.MidiGraphics midi;
    public MidiPanel(){
        setLayout(new BorderLayout());
        midi = new midi.MidiGraphics();
        midi.setPreferredSize(new Dimension(300,300));
        add(midi);
//        midi.paintComponent();

    }


}
