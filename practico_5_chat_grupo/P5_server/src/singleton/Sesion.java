package singleton;

import lista.Lista;
import serversocket.SocketSesion;

public class Sesion {

    private static Lista<SocketSesion> listasessiones;

    public static Lista<SocketSesion> getListasessiones() {
        if (listasessiones == null) {
            listasessiones = new Lista<SocketSesion>();
        }
        return listasessiones;

    }

    public static void removerSesion() {
        for (int i = 0; i < Sesion.getListasessiones().getTamano(); i++) {
            if (Sesion.getListasessiones().getRaiz().getContenido().getSocket().getPort() == Sesion.getListasessiones()
                    .iterator().next().getSocket().getPort()) {
                Sesion.getListasessiones().removerPorPosicion(i);
            }

        }
    }
}
