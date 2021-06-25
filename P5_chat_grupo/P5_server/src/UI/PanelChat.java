package UI;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

public class PanelChat extends JPanel {
    JPanelMensajes panelMensaje;

    private JScrollPane scrollFrame;

    private int width = 350;
    private int height = 450;
    private int oldSize = 0;

    public PanelChat() {
        initPanelParticipantes();
    }

    public void resize() {
        panelMensaje.setPreferredSize(new Dimension(width - 35, panelMensaje.getScrollHeight()));
        this.repaint();
        this.validate();
    }

    private void initPanelParticipantes() {
        this.setBounds(10, 99, width, height);

        panelMensaje = new JPanelMensajes(this);
        panelMensaje.setPreferredSize(new Dimension(width - 35, panelMensaje.getScrollHeight()));

        scrollFrame = new JScrollPane(panelMensaje);
        scrollFrame.setPreferredSize(new Dimension(width, 450));

        scrollFrame.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                int size = e.getAdjustable().getMaximum();
                if (size != oldSize) {
                    oldSize = size;
                    e.getAdjustable().setValue(size);
                }
            }
        });

        this.add(scrollFrame);
        this.setVisible(true);

        scrollFrame.setVisible(true);
        this.repaint();
        this.validate();
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {

        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
