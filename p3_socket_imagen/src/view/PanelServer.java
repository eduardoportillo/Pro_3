package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.json.JSONObject;

import Limagen.Imagen;
import Limagen.PanelImagen;
import Singleton.ImagenSelect;
import sockets.socketsServer.Server;

public class PanelServer extends JPanel implements PropertyChangeListener {

    private JLabel etiqueta;
    private Server sesion;

    boolean beConexion;

    private FrameServer frameServer;

    public PanelServer(int puerto, FrameServer frameServer) {
        this.frameServer = frameServer;
        initPanel();
        sesion = new Server(puerto);
        sesion.addObserver(this);
        etiqueta = new JLabel();
        inintJLabel();
    }

    private void initPanel() {
        this.setBounds(0, 0, 480, 40);
    }

    private void inintJLabel() {
        etiqueta.setBounds(0, 0, 240, 40);
        this.add(etiqueta);
        etiqueta.setVisible(true);
        etiqueta.setForeground(Color.RED);
        etiqueta.setFont(new Font("Roma", Font.PLAIN, 18));
        etiqueta.setText("Esperando conexion...");
    }

    private void responsiveBeConexion() {
        etiqueta.setVisible(false);
        JOptionPane.showMessageDialog(null, "Alguien se conecto");
        frameServer.setBounds(0, 0, ((frameServer.getPanelImagen().getWidth() * 2) + 80),
                (frameServer.getPanelImagen().getHeight() + 100));
        frameServer.repaint();
        frameServer.validate();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
     
        if (evt.getPropertyName().equals("socketSession")) {
            switch (evt.getOldValue() + "") {
            case "open":
                etiqueta.setVisible(false);
                responsiveBeConexion();
                break;
            case "mensaje":
                JSONObject obj = new JSONObject(evt.getNewValue() + "");
                if (obj.has("type")) {
                    switch (obj.getString("type")) {
                    case "gano_enemigo":
                        JOptionPane.showMessageDialog(null, "GANASTE :)");
                        break;
                    case "click":
                        frameServer.getPanelImagen().getImg().iniciarPintarParecidos(obj.getInt("x"), obj.getInt("y"));
                        break;
                    }
                }
                break;
            }

        }

    }

}
