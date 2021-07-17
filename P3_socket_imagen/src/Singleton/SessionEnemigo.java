package Singleton;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class SessionEnemigo {

    private static Socket sessionActual;

    public static void setSession(Socket SESSION) {
        sessionActual = SESSION;
    }

    public static void send(String mensaje) {
        try {
            PrintWriter response = new PrintWriter(sessionActual.getOutputStream(), true);
            response.write(mensaje + "\n");
            response.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
