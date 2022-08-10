import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    public static final List<ClientHandler> clients = new ArrayList<>();
    private final static int port = 5678;

    public void start(int port) {
        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Server successfully started, waiting message...");
            while (true) {
                clients.add(new ClientHandler(server.accept()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Server successfully stopped");
        }
    }

    public static void main(String[] args) {
        new Server().start(port);
    }
}
