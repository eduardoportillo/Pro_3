package clientsocket;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Base64;

import Comunication.Mensaje;
import singleton.ListaMensajes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SessionClienteSocket extends Thread {

    private static final Logger log = LogManager.getRootLogger();

    private static SessionClienteSocket INSTANCE;

    public static SessionClienteSocket getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SessionClienteSocket("127.0.0.1", 5000);
        }
        return INSTANCE;

    }

    private boolean isRun;
    private BufferedReader request;
    private PrintWriter response;
    private PropertyChangeSupport observed;
    // private ObjectOutputStream responseOutput;

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
            System.exit(0);
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
                log.error("error en hilo socket-session");
                isRun = false;
                System.exit(0);
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
        // log.info("NuevoMensaje:::" + socket.getInetAddress() + ":::" + line);
        try {
            byte[] data = Base64.getDecoder().decode(line);
            ObjectInputStream ois;
            ois = new ObjectInputStream(new ByteArrayInputStream(data));
            Mensaje msn = (Mensaje) ois.readObject();
            ois.close();
            ListaMensajes.setMensaje(msn);
            observed.firePropertyChange("socketSession", "mensaje", msn.getMensaje());

        } catch (IOException e) {
            log.error("No se pudo parsear a MENSAJE");
        } catch (ClassNotFoundException e) {
            log.error("No se pudo parsear a MENSAJE");
        }
    }

    public void send(String line) {
        response.println(line);
        response.flush();
    }

    public void sendObject(Object obj) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            oos.close();
            String b64 = Base64.getEncoder().encodeToString(baos.toByteArray());
            response.println(b64);
            observed.firePropertyChange("socketSession", "mensaje", obj.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
