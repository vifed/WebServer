import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Viola Federico
 * 
 * @category Academic Project / Web Server
 * @version 1.0
 */

public class Main {
    ServerSocket serverSocket;

    public static void main(String[] args) throws IOException {
        new Main().runServer();
    }
    public void runServer() throws IOException {
        System.out.println("Server is running");
        serverSocket = new ServerSocket(4950);
        acceptRequests();
    }
    private void acceptRequests() throws IOException {
        while(true){
            Socket s = serverSocket.accept();
            ConnectionHandler ch = new ConnectionHandler(s);
            ch.start();
        }
    }
}
