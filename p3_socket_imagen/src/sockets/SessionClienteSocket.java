package sockets.SocketsCliente;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.JSONObject;

import Singleton.SessionEnemigo;

public class SessionClienteSocket extends Thread {

    private boolean isRun;
    private BufferedReader request;
    private PrintWriter response;
    private PropertyChangeSupport observed;
    Socket socket;

    public SessionClienteSocket(String ip, int puerto) {
        observed = new PropertyChangeSupport(this);
        System.out.println("Intentando conectar con el servidor socket ::: IP: " + ip + ":" + puerto);
        try {
            socket = new Socket(ip, puerto);
        } catch (Exception e) {
            System.out.println("cliente no inicia");
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
            System.out.println("Error al iniciarlizar request and response");
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
                System.out.println("error en hilo socket-session");
            }
        }
    }

    public boolean isRun() {
        return isRun;
    }
    public void onClose() {
        System.out.println("Session cerrada:::" + socket.getInetAddress());
    }

    public void onOpen() {
        System.out.println("Nueva session iniciada con exito:::");
        System.out.println("IP:" + socket.getInetAddress());
        System.out.println("PORT:" + socket.getPort());

    }

    public void onMensaje(String line) {

        // JSONObject data = new JSONObject(line);
        System.out.println("NuevoMensaje:::" + socket.getInetAddress() + ":::" +line);
        observed.firePropertyChange("socketSession", "mensaje", line);
    }
}
