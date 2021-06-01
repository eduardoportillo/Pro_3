package Singleton;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class SessionEnemigo {

    private static Socket SESSION_ACTUAL;

    public static void setSession(Socket SESSION) {
        SESSION_ACTUAL = SESSION;
    }

    public static void send(String mensaje) {
        try {
            PrintWriter response = new PrintWriter(SESSION_ACTUAL.getOutputStream(), true);
            response.write(mensaje+"\n");
            response.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
