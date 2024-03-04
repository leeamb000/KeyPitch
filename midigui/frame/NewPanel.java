
package midigui.frame;

import javax.swing.*;
import java.awt.*;

public class NewPanel extends JPanel {
    public NewPanel() {
        Dimension dim = getPreferredSize();
        dim.width = 15;
        setPreferredSize(dim);

        setLayout(new FlowLayout(FlowLayout.LEADING));
        setBorder(BorderFactory.createLineBorder(new Color(1,1,1,1)));
        setBackground(new Color(3, 84, 87, 120));
    }
}
