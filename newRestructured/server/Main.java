package newRestructured.server;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.Headers;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws Exception {
        // Create an HTTP server on port 4112
        HttpServer server = HttpServer.create(new InetSocketAddress(4112), 0);

        // health check
        server.createContext("/health", new AliveHandler());
        server.createContext("/signup", new SignUpHandler());
        server.createContext("/validateUser", new ValidateUserHandler());

        server.setExecutor(null); // default executor
        server.start();

        System.out.println("Server running on http://localhost:4112");
    }

    static class AliveHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "Endpoint is alive";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class ValidateUserHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(405, -1); // 405 Method Not Allowed, no body
                return;
            }

            String contentType = exchange.getRequestHeaders().getFirst("Content-Type");
            if (contentType == null || !contentType.equals("application/json")) {
                exchange.sendResponseHeaders(400, -1); // 400 Bad Request
                return;
            }

            // Excpected JSON format is {username:[username]}
            StringBuilder body = new StringBuilder();
            try (InputStream is = exchange.getRequestBody();
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(is, StandardCharsets.UTF_8))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    body.append(line);
                }
            }

            String json = body.toString();

            String usernameToCheck = json.substring(json.indexOf(":") + 2, json.lastIndexOf("\""));
            usernameToCheck = usernameToCheck.trim();
            String salt = "";
            boolean userExists = false;
            System.out.println("connection recieved, checking if " + usernameToCheck + " exists");

            // checking if username exists in accountData, if it does, send associated salt.
            try {
                String jsonString = new String(Files.readAllBytes(
                        Paths.get("C:\\school\\Semester 1\\CSE 205\\pro\\newRestructured\\server\\accountData.txt")));

                // Parse the JSON data as a JSONObject
                JSONObject jsonObject = new JSONObject(jsonString);

                // Extract the "accounts" JSONArray
                JSONArray accounts = jsonObject.getJSONArray("accounts");
                userExists = false;

                for (int i = 0; i < accounts.length(); i++) {
                    JSONObject account = accounts.getJSONObject(i);
                    String username = account.getString("username");
                    if (username.equals(usernameToCheck)) {
                        salt = account.getString("salt");
                        System.out.println(usernameToCheck + " found");
                        userExists = true;
                        break;
                    }
                }

                if (!userExists) {
                    System.out.println(usernameToCheck + " not found");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // it if it dosen't, send false
            String response = userExists ? salt : "false";

            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class SignUpHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(405, -1); // 405 Method Not Allowed, no body
                return;
            }

            System.out.println("connection recieved");

            Headers headers = exchange.getRequestHeaders();
            String contentType = headers.getFirst("Content-Type");
            if (contentType == null || !contentType.contains("application/json")) {
                String msg = "Content-Type must be application/json";
                exchange.sendResponseHeaders(400, msg.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(msg.getBytes(StandardCharsets.UTF_8));
                }
                return;
            }

            // 3. Read JSON body as a String
            StringBuilder body = new StringBuilder();
            try (InputStream is = exchange.getRequestBody();
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(is, StandardCharsets.UTF_8))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    body.append(line);
                }
            }

            String json = body.toString();
            System.out.println("Received JSON: " + json);

            // 4. Send response
            String response = "Here is your api key -> sasjdao@#!@3jwojs";
            exchange.getResponseHeaders().add("Content-Type", "text/plain; charset=utf-8");
            exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes(StandardCharsets.UTF_8));
            }
        }
    }
}
