package Frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Limagen.Imagen;
import Limagen.PanelImagen;
import Singleton.ImagenSelect;
import paneles.PanelServer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FrameServer extends JFrame {

    private JButton btnServer = new JButton("Esperar Conexion");
    private PanelServer panel;

    private PanelImagen paImagen;
    private PanelImagen paEnemigo;

    private FrameServer instance;

    private static final Logger log = LogManager.getRootLogger();

    public FrameServer() {
        instance = this;
        initFrameServer();
        crearBtn();

    }

    private void initFrameServer() {
        this.setTitle("Server");
        this.setVisible(true);
        this.setBounds(0, 0, 480, 80);
        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    private void crearBtn() {
        instance.add(btnServer);
        btnServer.setBounds(5, 5, 450, 30);

        btnServer.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int puerto = Integer.parseInt(JOptionPane.showInputDialog(null, "Introduzca el puerto"));
                    btnServer.setVisible(false);
                    panel = new PanelServer(puerto, instance);
                    btnServer.setVisible(false);
                    instance.add(panel);
                    panel.setVisible(true);
                    instance.repaint();
                    instance.validate();
                    cargarImagen();
                    CargarImagenEnemigo();
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(null, "Digite un numero entero");
                    log.error(exc);
                }

            }
        });
    }

    private void cargarImagen() {

        paImagen = new PanelImagen(false);
        instance.add(paImagen);
        paImagen.setImg(ImagenSelect.getInstance());
        instance.repaint();
        instance.validate();
    }

    private void CargarImagenEnemigo() {
        paEnemigo = new PanelImagen(true);
        Imagen imagenEnemigo = new Imagen(1, 1, 1, ImagenSelect.getInstance().getWidth(),
                ImagenSelect.getInstance().getHeight());
        instance.add(paEnemigo);
        paEnemigo.setImg(imagenEnemigo, 500, 50);
        instance.repaint();
        instance.validate();
    }

    public PanelImagen getPanelImagen() {
        return paImagen;
    }
}
