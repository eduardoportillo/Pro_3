package sockets.socketsServer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {

    private Server INSTANCE;
    private int puerto;
    private ServerSocket serverSocket;
    private boolean isRun;

    private PropertyChangeSupport observed;

    public Server(int puerto) {
        this.INSTANCE = this;
        this.puerto = puerto;
        observed = new PropertyChangeSupport(this);
        System.out.println("Intentando inicar server socket en puerto: " + puerto);

        try {
            this.serverSocket = new ServerSocket(puerto);
            this.start();
            System.out.println("Server iniciado esperando conexiones...");

        } catch (Exception e) {
            System.out.println("error al iniciar socket-server");

        }

    }

    @Override
    public void run() {
        isRun = true;
        while (isRun) {
            try {
                Socket socket_new_conexion = INSTANCE.serverSocket.accept();
                new SocketSesion(socket_new_conexion, observed);
            } catch (Exception e) {
                System.out.println("error en hilo socket-server");
            }
        }
    }

    public void addObserver(PropertyChangeListener panel) {
        observed.addPropertyChangeListener(panel);
    }

}
