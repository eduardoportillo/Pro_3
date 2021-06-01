package UI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.util.Base64;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import Comunication.Mensaje;
import clientsocket.SessionClienteSocket;
import singleton.ListaMensajes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PanelEnviarMensaje extends JPanel {

    private static final Logger log = LogManager.getRootLogger();

    private JTextField TextImput;
    private JButton BtnEnviarTexto;
    private JButton BtnEnviarimg;
    private PanelEnviarMensaje instance;

    public PanelEnviarMensaje() {
        instance = this;
        this.setBounds(0, 500, 560, 80);
        this.setBackground(Color.ORANGE);
        this.setLayout(null);
        this.setVisible(true);
        initTextInput();
        initButonText();
        initButonImg();

    }

    public void initTextInput() {
        TextImput = new JTextField();
        TextImput.setBounds(30, 25, 300, 30);
        TextImput.setVisible(true);
        this.add(TextImput);
    }

    public void initButonText() {
        BtnEnviarTexto = new JButton("Enviar");
        BtnEnviarTexto.setBounds(340, 25, 80, 30);
        BtnEnviarTexto.setVisible(true);
        this.add(BtnEnviarTexto);

        BtnEnviarTexto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mensaje = TextImput.getText();
                Mensaje msn = new Mensaje(mensaje);
                ListaMensajes.setMensaje(msn);
                SessionClienteSocket.getInstance().sendObject(msn);
                TextImput.setText("");
                TextImput.requestFocus();
                // msn.
            }
        });
    }

    public void initButonImg() {
        BtnEnviarimg = new JButton("Enviar Imagen");
        BtnEnviarimg.setBounds(430, 25, 120, 30);
        BtnEnviarimg.setVisible(true);
        this.add(BtnEnviarimg);

        BtnEnviarimg.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "jpeg", "png");
                fileChooser.addChoosableFileFilter(filter);
                fileChooser.setFileFilter(filter);
                int seleccion = fileChooser.showOpenDialog(instance);

                File abreimg = fileChooser.getSelectedFile();
                try {
                    byte[] filecont = Files.readAllBytes(abreimg.toPath());
                    String b64 = Base64.getEncoder().encodeToString(filecont);
                    Mensaje msn = new Mensaje("@", b64);
                    ListaMensajes.setMensaje(msn);
                    SessionClienteSocket.getInstance().sendObject(msn);
                } catch (Exception Exception) {
                    log.error(Exception);
                }
            }

        });

    }

}
