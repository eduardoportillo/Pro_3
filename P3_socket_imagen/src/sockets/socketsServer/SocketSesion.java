package sockets.socketsServer;

import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.JSONObject;

import Singleton.SessionEnemigo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class SocketSesion extends Thread {

    private Socket socket;
    private boolean isRun;
    private SocketSesion instance;

    private static final Logger log = LogManager.getRootLogger();

    private BufferedReader request;
    private PrintWriter response;
    private PropertyChangeSupport observed;

    public SocketSesion(Socket socketP, PropertyChangeSupport observed) {
        this.observed = observed;
        this.instance = this;
        this.socket = socketP;
        this.start();
        SessionEnemigo.setSession(socketP);
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
                // log.error("error en hilo socket-session");
                isRun = false;
                onClose();
            }
        }
    }

    public void onClose() {
        log.info("Session cerrada:::" + socket.getInetAddress());
        observed.firePropertyChange("socketSession", "close", "msns");
    }

    public void onOpen() {
        log.info("Nueva session iniciada con exito:::");
        log.info("IP:" + socket.getInetAddress());
        log.info("PORT:" + socket.getPort());
        observed.firePropertyChange("socketSession", "open", "msns");

    }

    public void onMensaje(String line) {
        // JSONObject data = new JSONObject(line);
        log.info("NuevoMensaje:::" + socket.getInetAddress() + ":::" + line);
        observed.firePropertyChange("socketSession", "mensaje", line);
    }
}
