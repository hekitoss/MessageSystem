import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static server.commands.Commands.AUTHORIZATION;

public class Client {

    private final Socket clientSocket;
    private final PrintWriter out;
    private final BufferedReader in;
    private final BufferedReader reader;

    public Client(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    private void startConnection() throws IOException {
        try {
            authorization();
            while (true) {
                String message = reader.readLine();
                out.println(message);
                System.out.println(in.readLine());
            }
        } finally {
            this.stopConnection();
        }
    }

    private void authorization() throws IOException {
        System.out.println("Please enter your name:");
        String name = reader.readLine();
        out.println(AUTHORIZATION.getName() + " " + name);
        System.out.println(in.readLine());
    }

    private void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    public static void main(String[] args) {
        try {
            Client clients = new Client("127.0.0.1", 5678);
            clients.startConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
