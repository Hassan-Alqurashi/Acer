import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class AcerServer {
    private static final int PORT = 6379;
    private static final ConcurrentHashMap<String, String> store = new ConcurrentHashMap<>();

    public static void main(String[] args) throws IOException {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Acer listening on port " + PORT + "...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                pool.execute(() -> handleClient(clientSocket));
            }
        }
    }

    private static void handleClient(Socket socket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            String line;
            while ((line = in.readLine()) != null) {
                String[] parts = line.trim().split("\\s+", 3);
                String cmd = parts[0].toUpperCase();

                switch (cmd) {
                    case "SET":
                        if (parts.length < 3) { out.println("-ERR wrong number of arguments"); break; }
                        store.put(parts[1], parts[2]);
                        out.println("+OK");
                        break;
                    case "GET":
                        if (parts.length < 2) { out.println("-ERR wrong number of arguments"); break; }
                        String val = store.get(parts[1]);
                        out.println(val != null ? "$" + val : "$-1 (nil)");
                        break;
                    case "DEL":
                        if (parts.length < 2) { out.println("-ERR wrong number of arguments"); break; }
                        out.println(store.remove(parts[1]) != null ? ":1" : ":0");
                        break;
                    default:
                        out.println("-ERR unknown command");
                }
            }
        } catch (IOException e) {
            System.err.println("Client disconnected.");
        }
    }
}
