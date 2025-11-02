package CN;

import java.io.*;
import java.net.*;
import java.util.Properties;

public class CalculatorClient {
    public static void main(String[] args) {
        Properties config = new Properties();
        try (FileInputStream fis = new FileInputStream("server_info.dat")) {
            config.load(fis);
        } catch (IOException ignore) {  }

        String serverAddress = config.getProperty("server.address", "localhost");
        int serverPort;
        try { serverPort = Integer.parseInt(config.getProperty("server.port", "1234")); }
        catch (Exception e) { serverPort = 1234; }

        System.out.println("[Client] Connecting to " + serverAddress + ":" + serverPort);

        try (Socket socket = new Socket(serverAddress, serverPort);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
             PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
             BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in, "UTF-8"))) {

            String greet = in.readLine();
            if (greet != null) System.out.println("[Server] " + greet);

            System.out.println("Type: ADD a b | SUB a b | MUL a b | DIV a b | QUIT");
            String line;
            while (true) {
                System.out.print("> ");
                line = stdin.readLine();
                if (line == null) break;
                out.println(line);                
                String resp = in.readLine();       
                if (resp == null) {
                    System.out.println("[Client] Server closed.");
                    break;
                }
                System.out.println("[Server] " + resp);

                if ("QUIT".equalsIgnoreCase(line.trim())) break;
            }
        } catch (IOException e) {
            System.err.println("[Client] Error: " + e.getMessage());
        }
    }
}
