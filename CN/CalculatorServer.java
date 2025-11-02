package CN;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class CalculatorServer {
    private static final int PORT = 1234;

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(20); // ThreadPool
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("[Server] Listening on " + PORT);
            while (true) {
                Socket connection = server.accept();
                pool.submit(new CalculatorTask(connection));
            }
        } catch (IOException e) {
            System.err.println("[Server] Error: " + e.getMessage());
        } finally {
            pool.shutdown();
        }
    }
}
