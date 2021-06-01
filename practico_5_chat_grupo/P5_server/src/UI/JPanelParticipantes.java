package UI;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;

import serversocket.ServerSock;
import singleton.Sesion;

public class JPanelParticipantes extends JPanel implements PropertyChangeListener {

    JList listaParticipantes;
    DefaultListModel modelo;
    ServerSock server;

    public JPanelParticipantes() {
        initPanelParticipantes();
    }

    private void initPanelParticipantes() {
        this.setVisible(true);
        this.setBounds(380, 100, 200, 450);
        server = ServerSock.getInstanceServer();
        server.addObserver(this);

        listaParticipantes = new JList(/* nombres */);
        listaParticipantes.setSelectionMode(1);
        this.add(listaParticipantes);
        listaParticipantes.setVisible(true);

        this.setBackground(new Color(135, 158, 255));

    }

    public void agregarParticipanteLista() {
        modelo = new DefaultListModel();
        for (int i = 0; i < Sesion.getListasessiones().getTamano(); i++) {
            modelo.addElement("SESSION " + i);
        }
        listaParticipantes.setModel(modelo);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        agregarParticipanteLista();
        this.repaint();
        this.validate();
    }

}
