package CN;

import java.io.*;
import java.net.*;
import java.util.Locale;

class CalculatorTask implements Runnable {
    private final Socket connection;

    CalculatorTask(Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
             PrintWriter writer = new PrintWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"), true)) {

            writer.println("OK READY"); 

            String line;
            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();
                if (trimmed.isEmpty()) {
                    writer.println("ERR MALFORMED");
                    continue;
                }
                if ("QUIT".equalsIgnoreCase(trimmed)) {
                    writer.println("OK BYE");
                    break;
                }

                String[] parts = trimmed.split("\\s+");
                if (parts.length != 3) {
                    writer.println("ERR BAD_ARITY");
                    continue;
                }

                String op = parts[0].toUpperCase(Locale.ROOT);
                double a, b;
                try {
                    a = Double.parseDouble(parts[1]);
                    b = Double.parseDouble(parts[2]);
                } catch (NumberFormatException e) {
                    writer.println("ERR BAD_NUMBER");
                    continue;
                }

                Double result = evaluate(op, a, b);
                if (result == null) {
                    if ("DIV".equals(op) && b == 0.0) {
                        writer.println("ERR DIV_BY_ZERO");
                    } else if (!isSupported(op)) {
                        writer.println("ERR BAD_OPERATOR");
                    } else {
                        writer.println("ERR MALFORMED");
                    }
                } else {
                    double val = (result == -0.0) ? 0.0 : result;
                    writer.println("OK " + val);
                }
            }
        } catch (IOException e) {
            System.err.println("[Worker] IO error: " + e.getMessage());
        } finally {
            try { connection.close(); } catch (IOException ignore) {}
        }
    }

    private boolean isSupported(String op) {
        return "ADD".equals(op) || "SUB".equals(op) || "MUL".equals(op) || "DIV".equals(op);
    }

    private Double evaluate(String op, double a, double b) {
        switch (op) {
            case "ADD": return a + b;
            case "SUB": return a - b;
            case "MUL": return a * b;
            case "DIV": return (b == 0.0) ? null : a / b;
            default:    return null;
        }
    }
}