package sockets.SocketsCliente;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.json.JSONObject;

import Singleton.SessionEnemigo;

public class SessionClienteSocket extends Thread {

    private static final Logger log = LogManager.getRootLogger();

    private boolean isRun;
    private BufferedReader request;
    private PrintWriter response;
    private PropertyChangeSupport observed;
    Socket socket;

    public SessionClienteSocket(String ip, int puerto) {
        observed = new PropertyChangeSupport(this);
        log.info("Intentando conectar con el servidor socket ::: IP: " + ip + ":" + puerto);
        try {
            socket = new Socket(ip, puerto);
        } catch (Exception e) {
            log.error("cliente no inicia");
        }
        this.start();

    }

    public void addObserver(PropertyChangeListener panel) {
        observed.addPropertyChangeListener(panel);
    }

    @Override
    public void run() {
        try {
            request = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            response = new PrintWriter(socket.getOutputStream(), true);

        } catch (Exception e1) {
            log.error("Error al iniciarlizar request and response");
            return;
        }
        isRun = true;
        onOpen();
        SessionEnemigo.setSession(socket);
        String line;
        while (isRun) {
            try {
                line = request.readLine();
                if ((line == null) || (line.equalsIgnoreCase("QUIT"))) {
                    onClose();
                    isRun = false;
                } else {
                    onMensaje(line);
                }
            } catch (Exception e) {
                log.error("error en hilo socket-session");
            }
        }
    }

    public boolean isRun() {
        return isRun;
    }

    public void onClose() {
        log.info("Session cerrada:::" + socket.getInetAddress());
    }

    public void onOpen() {
        log.info("Nueva session iniciada con exito:::");
        log.info("IP:" + socket.getInetAddress());
        log.info("PORT:" + socket.getPort());

    }

    public void onMensaje(String line) {
        log.info("NuevoMensaje:::" + socket.getInetAddress() + ":::" + line);
        observed.firePropertyChange("socketSession", "mensaje", line);
    }
}
