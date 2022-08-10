import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private BufferedReader reader;

    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            reader = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String message = reader.readLine();
                out.println(message);
                System.out.println(in.readLine());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            this.stopConnection();
        }
    }

    public void stopConnection() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client clients = new Client();
        clients.startConnection("127.0.0.1", 5678);
    }
}
