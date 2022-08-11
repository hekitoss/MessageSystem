package server;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Server {

    public static final List<ClientHandler> clients = new ArrayList<>();
    private final static int port = 5678;

    public void start(int port) {
        try (ServerSocket server = new ServerSocket(port)) {
            log.info("Server successfully started, waiting message...");
            while (true) {
                clients.add(new ClientHandler(server.accept()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            log.info("Server successfully stopped");
        }
    }

    public static void main(String[] args) {
        new Server().start(port);
    }
}
