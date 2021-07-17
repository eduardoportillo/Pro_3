package sockets.socketsServer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Server extends Thread {

    private static final Logger log = LogManager.getRootLogger();

    private Server INSTANCE;
    private int puerto;
    private ServerSocket serverSocket;
    private boolean isRun;

    private PropertyChangeSupport observed;

    public Server(int puerto) {
        this.INSTANCE = this;
        this.puerto = puerto;
        observed = new PropertyChangeSupport(this);
        log.info("Intentando inicar server socket en puerto: " + puerto);

        try {
            this.serverSocket = new ServerSocket(puerto);
            this.start();
            log.info("Server iniciado esperando conexiones...");

        } catch (Exception e) {
            log.error("error al iniciar socket-server");

        }

    }

    @Override
    public void run() {
        isRun = true;
        while (isRun) {
            try {
                Socket sockeNewConexion = INSTANCE.serverSocket.accept();
                new SocketSesion(sockeNewConexion, observed);
            } catch (Exception e) {
                log.error("error en hilo socket-server");
            }
        }
    }

    public void addObserver(PropertyChangeListener panel) {
        observed.addPropertyChangeListener(panel);
    }

}
