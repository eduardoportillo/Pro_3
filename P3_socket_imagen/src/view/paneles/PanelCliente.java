package paneles;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.json.JSONObject;

import Frames.FrameCliente;
import Limagen.Imagen;
import Limagen.PanelImagen;
import Singleton.ImagenSelect;
import sockets.SocketsCliente.SessionClienteSocket;

public class PanelCliente extends JPanel implements PropertyChangeListener {

    private JTextField inputIP;
    private JTextField inputPort;

    private JButton conectarse;

    private JLabel IPLabel;
    private JLabel PortLabel;

    private PanelImagen paImagen;
    private PanelImagen paEnemigo;

    private PanelCliente instance;

    private FrameCliente frame;

    public PanelCliente(FrameCliente frame) {
        instance = this;
        this.frame = frame;
        initPanelCliente();
        conectarse();
    }

    private void initPanelCliente() {
        this.setLayout(null);
        this.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        inputIP = new JTextField();
        inputPort = new JTextField();

        IPLabel = new JLabel("IP: ");
        this.add(IPLabel);
        IPLabel.setBounds(10, 10, 30, 30);
        IPLabel.setForeground(Color.RED);
        IPLabel.setVisible(true);

        PortLabel = new JLabel("PORT: ");
        this.add(PortLabel);
        PortLabel.setBounds(150, 10, 100, 30);
        PortLabel.setForeground(Color.RED);
        PortLabel.setVisible(true);

        this.add(inputIP);
        inputIP.setBounds(30, 10, 100, 30);
        inputIP.setVisible(true);

        this.add(inputPort);
        inputPort.setBounds(200, 10, 100, 30);
        inputPort.setVisible(true);
    }

    private void conectarse() {
        conectarse = new JButton("Conectarse");
        instance.add(conectarse);
        conectarse.setBounds(320, 10, 100, 30);

        conectarse.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    int puerto = Integer.parseInt(inputPort.getText());
                    String ip = inputIP.getText();
                    Pattern pt = Pattern.compile(
                            "\\b(?:(?:2(?:[0-4][0-9]|5[0-5])|[0-1]?[0-9]?[0-9])\\.){3}(?:(?:2([0-4][0-9]|5[0-5])|[0-1]?[0-9]?[0-9]))\\b");
                    Matcher mt = pt.matcher(ip);
                    if (!mt.find()) {
                        JOptionPane.showMessageDialog(null, "Digite una ip valida.");
                        return;
                    }
                    SessionClienteSocket ass = new SessionClienteSocket(ip, puerto);
                    ass.addObserver(instance);
                    int cant = 0;
                    while (!ass.isRun()) {
                        Thread.sleep(1000);
                        cant++;
                        if(cant>=5){
                            break;
                        }
                    }
                    if (!ass.isRun()) {
                        JOptionPane.showMessageDialog(null, "Servidor no encontrado");
                        return;
                    }
                    quitarInputs();
                    cargarImagen();
                    CargarImagenEnemigo();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Digite un numero entero.");
                }

            }

        });
    }

    public void quitarInputs() {
        inputIP.setVisible(false);
        inputPort.setVisible(false);
        IPLabel.setVisible(false);
        PortLabel.setVisible(false);
        conectarse.setVisible(false);

        this.repaint();
        this.validate();
    }

    private void cargarImagen() {

        paImagen = new PanelImagen(false);
        instance.add(paImagen);
        paImagen.setImg(ImagenSelect.getInstance());
        instance.repaint();
        instance.validate();
        frame.setBounds(0, 0, ((paImagen.getWidth() * 2) + 80), (paImagen.getHeight() + 100));
        this.setBounds(0, 0, ((paImagen.getWidth() * 2) + 80), (paImagen.getHeight() + 100));
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

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ((evt.getPropertyName() + "").equals("perdiste")) {
            JOptionPane.showMessageDialog(null, "PERDISTE :(");
            return;
        }
        if (evt.getPropertyName().equals("socketSession")) {
            switch (evt.getOldValue() + "") {
            case "mensaje":
                JSONObject obj = new JSONObject(evt.getNewValue() + "");
                if (obj.has("type")) {
                    switch (obj.getString("type")) {
                    case "gano_enemigo":
                        JOptionPane.showMessageDialog(null, "GANASTE");
                        break;
                    case "click":
                        paImagen.getImg().iniciarPintarParecidos(obj.getInt("x"), obj.getInt("y"));
                        break;
                    }
                }
            }
        }

    }

}
