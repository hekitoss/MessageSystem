package server;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static server.commands.Commands.AUTHORIZATION;
import static server.commands.Commands.STOP;

@Setter
@Getter
@Slf4j
public class ClientHandler extends Thread {

    private String username;

    private final Socket clientSocket;
    private final PrintWriter out;
    private final BufferedReader in;

    public ClientHandler(Socket socket) throws IOException {
        this.clientSocket = socket;
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = in.readLine();
                if (message.startsWith(AUTHORIZATION.getName())) {
                    setUsername(message.replace(AUTHORIZATION.getName() + " ", ""));
                    log.info(username + " connected to chat");
                    send("Hello " + username + ", welcome to chat!");
                } else if (message.startsWith(STOP.getName())) {
                    log.info(username + " left the chat");
                    break;
                } else {
                    for (ClientHandler client : Server.clients) {
                        log.info(username + " sends message: " + message);
                        client.send(username + ": " + message);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.close();
        }
    }

    private void close() {
        try {
            in.close();
            clientSocket.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void send(String message) {
        out.println(message);
    }
}
