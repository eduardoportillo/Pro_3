import UI.FrameInit;
import clientsocket.SessionClienteSocket;

public class App {
    public static void main(String[] args) throws Exception {
        SessionClienteSocket.getInstance();
        new FrameInit();
    }
}
