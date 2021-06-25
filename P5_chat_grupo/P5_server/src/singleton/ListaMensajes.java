package singleton;

import lista.Lista;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import Comunication.Mensaje;

public class ListaMensajes {

    private static Lista<Mensaje> listaMensajes;
    private PropertyChangeSupport observed = new PropertyChangeSupport(this);

    public static Lista<Mensaje> getListasessiones() {
        if (listaMensajes == null) {
            listaMensajes = new Lista<Mensaje>();
        }
        return listaMensajes;

    }

    public static void setMensaje(Mensaje msm) {
        ListaMensajes.getListasessiones().insertar(msm);
    }

    public void addObserver(PropertyChangeListener panel) {
        observed.addPropertyChangeListener(panel);
    }

}
