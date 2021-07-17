package Frames;

import javax.swing.JFrame;

import paneles.PanelCliente;

public class FrameCliente extends JFrame {

    private PanelCliente panelCli;

    public FrameCliente() {
        initFrameServer();
        initPanelCliente();

    }

    private void initFrameServer() {
        this.setTitle("Cliente");
        this.setVisible(true);
        this.setBounds(0, 0, 500, 100);
        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);

    }

    private void initPanelCliente() {
        panelCli = new PanelCliente(this);
        this.add(panelCli);
        panelCli.setVisible(true);
        panelCli.repaint();
        panelCli.validate();
    }

}
