package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
        
public class PanelSeleccionSC extends JPanel {

    JButton btnBeServer;
    JButton btnBeClient;

    private FrameServer frameServer;
    private FrameCliente frameCliente;

    public PanelSeleccionSC() {
        initPanel();
    }

    private void initPanel() {
        this.setBounds(0, 0, 250, 150);
        this.setLayout(null);
        crearBtnBeServer();
        crearBtnBeClient();
    }

    public void crearBtnBeServer() {
        btnBeServer = new JButton("Server");
        btnBeServer.setBounds(10, 10, 100, 100);
        this.add(btnBeServer);
        btnBeServer.setVisible(true);

        btnBeServer.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frameServer = new FrameServer();
            }

        });
    }

    public void crearBtnBeClient() {
        btnBeClient = new JButton("Client");
        btnBeClient.setBounds(120, 10, 100, 100);
        this.add(btnBeClient);
        btnBeClient.setVisible(true);

        btnBeClient.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frameCliente = new FrameCliente();
            }
        });
    }
}
