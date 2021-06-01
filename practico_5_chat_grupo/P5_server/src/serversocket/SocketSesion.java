package serversocket;

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

import singleton.ListaMensajes;
import singleton.Sesion;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Comunication.Mensaje;

public class SocketSesion extends Thread {
    private Socket socket;
    private boolean isRun;
    private SocketSesion instance;
    private String key;
    private static final Logger log = LogManager.getRootLogger();

    private BufferedReader request;
    private ObjectInputStream requestObject;

    private PrintWriter response;
    private PropertyChangeSupport observed;

    public SocketSesion(Socket socketP, PropertyChangeSupport observed) {
        this.observed = observed;
        this.instance = this;
        this.socket = socketP;
        Sesion.getListasessiones().insertar(this);
        String adr = socketP.getRemoteSocketAddress().toString();
        key = adr;
        this.start();
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
                log.error("error en hilo socket-session");
                isRun = false;
                onClose();
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public String getKey() {
        return key;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void onClose() {
        log.info("Session cerrada:::" + socket.getInetAddress());
        Sesion.removerSesion();
        observed.firePropertyChange("socketSession", "close", "msns");
    }

    public void onOpen() {
        log.info("Nueva session iniciada con exito:::");
        log.info("IP:" + socket.getInetAddress());
        log.info("PORT:" + socket.getPort());
        observed.firePropertyChange("socketSession", "open", "msns");

    }

    @Override
    public String toString() {

        return "SocketSesion:  IP: " + socket.getInetAddress() + "  Port: " + socket.getPort();
    }

    public void onMensaje(String line) {
        try {
            byte[] data = Base64.getDecoder().decode(line);
            ObjectInputStream ois;
            ois = new ObjectInputStream(new ByteArrayInputStream(data));
            Mensaje msn = (Mensaje) ois.readObject();
            ois.close();
            ListaMensajes.setMensaje(msn);
            observed.firePropertyChange("socketSession", "mensaje", msn.getMensaje());

            for (int i = 0; i < Sesion.getListasessiones().getTamano(); i++) {
                SocketSesion ss = (SocketSesion) Sesion.getListasessiones().get(i).getContenido();

                if (!(ss.getKey().equals(this.getKey()))) {
                    Sesion.getListasessiones().get(i).getContenido().sendObject(msn);
                }
                ;
            }

        } catch (IOException e) {
            log.error("No se pudo parsear a MENSAJE");
        } catch (ClassNotFoundException e) {
            log.error("No se pudo parsear a MENSAJE");
        }

    }

    public void sendObject(Object obj) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            oos.close();
            String b64 = Base64.getEncoder().encodeToString(baos.toByteArray());
            response.println(b64);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
