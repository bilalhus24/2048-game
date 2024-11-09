package proj2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Tiles extends JPanel {
    private int value;
    private JLabel label;
    
    Tiles(int value) {
        setValue(value);
        setPreferredSize(new Dimension(100, 100));
        setLayout(new BorderLayout());
    }

    public void setValue(int value) {
        this.value = value;
        setBackground(getTileColor(value));
        removeAll();
        
        label = new JLabel(value > 0 ? String.valueOf(value) : "");
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);
        setBorder(BorderFactory.createLineBorder(Color.gray, 3));

        revalidate();
        repaint();
    }
    
    public void added() {
//        if (value == 0) {
//            return; // No animation for tiles with value 0
//        }
//        
//        final Color startColor = getBackground();
//        final Color endColor = getTileColor(value);
//        final int startFontSize = 24;
//        final int endFontSize = 36;
//        
//        Timer timer = new Timer(30, null);
//        timer.addActionListener(new ActionListener() {
//            double progress = 0;
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                progress += 0.05; // Increase progress for animation
//                if (progress >= 1) {
//                    progress = 1;
//                    timer.stop();
//                }
//
//                int red = (int) (startColor.getRed() + progress * (endColor.getRed() - startColor.getRed()));
//                int green = (int) (startColor.getGreen() + progress * (endColor.getGreen() - startColor.getGreen()));
//                int blue = (int) (startColor.getBlue() + progress * (endColor.getBlue() - startColor.getBlue()));
//                setBackground(new Color(red, green, blue));
//                
//                int fontSize = (int) (startFontSize + progress * (endFontSize - startFontSize));
//                label.setFont(new Font("Arial", Font.BOLD, fontSize));
//                
//                revalidate();
//                repaint();
//            }
//        });
//        
//        timer.start();
    }

    public int getValue() {
        return this.value;
    }

    Color getTileColor(int value) {
        switch (value) {
            case 2:
                return new Color(255, 255, 224); // Light Yellow
            case 4:
                return new Color(255, 255, 192); // Light Gold
            case 8:
                return new Color(255, 223, 186); // Light Coral
            case 16:
                return new Color(255, 192, 203); // Pink
            case 32:
                return new Color(255, 160, 122); // Light Salmon
            case 64:
                return new Color(255, 127, 80);  // Coral
            case 128:
                return new Color(255, 99, 71);   // Tomato
            case 256:
                return new Color(255, 69, 0);    // Orange Red
            case 512:
                return new Color(255, 0, 0);     // Red
            case 1024:
                return new Color(255, 140, 0);   // Dark Orange
            case 2048:
                return new Color(255, 215, 0);   // Gold
            default:
                return new Color(205, 193, 180); // Default tile color (Light Gray)
        }
    }

}
