package sockets.socketsServer;

import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.JSONObject;

import Singleton.SessionEnemigo;

class SocketSesion extends Thread {

    private Socket socket;
    private boolean isRun;
    private SocketSesion instance;

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
            System.out.println("Error al iniciarlizar request and response");
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
                // System.out.println("error en hilo socket-session");
                isRun = false;
                onClose();
            }
        }
    }

    public void onClose() {
        System.out.println("Session cerrada:::" + socket.getInetAddress());
        observed.firePropertyChange("socketSession", "close", "msns");
    }

    public void onOpen() {
        System.out.println("Nueva session iniciada con exito:::");
        System.out.println("IP:" + socket.getInetAddress());
        System.out.println("PORT:" + socket.getPort());
        observed.firePropertyChange("socketSession", "open", "msns");

    }

    public void onMensaje(String line) {
        // JSONObject data = new JSONObject(line);
        System.out.println("NuevoMensaje:::" + socket.getInetAddress() + ":::" + line);
        observed.firePropertyChange("socketSession", "mensaje", line);
    }
}
