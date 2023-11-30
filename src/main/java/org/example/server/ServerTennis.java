package org.example.server;

import org.example.client.model.GameModel;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

//public class ServerTennis {
//    public static void main(String[] args) throws IOException {
//        ServerSocket serverSocket = new ServerSocket(12345);
//        while (true) {
//            Socket clientSocket = serverSocket.accept();
//            new Thread(new ClientHandler(clientSocket)).start();
//        }
//    }
//}
//
//class ClientHandler implements Runnable {
//    private Socket clientSocket;
//
//    public ClientHandler(Socket clientSocket) {
//        this.clientSocket = clientSocket;
//    }
//
//    public void run() {
//        try {
//            InputStream inputStream = clientSocket.getInputStream();
//            OutputStream outputStream = clientSocket.getOutputStream();
//
//            // Чтение данных из inputStream и запись данных в outputStream
//
//            clientSocket.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}

public class ServerTennis {
    private static List<Connection> connections = new ArrayList<>();
    private static List<Room> rooms = new ArrayList<>();


    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java Server <port>");
            return;
        }

        int port = Integer.parseInt(args[0]);
        ServerSocket serverSocket = new ServerSocket(port);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//        PrintWriter out = new PrintWriter(System.out, true);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            Connection connection = new Connection(clientSocket);
            connections.add(connection);
            connection.start();
        }
    }

    private static class Connection extends Thread {
        private BufferedReader in;
        private PrintWriter out;
        private Socket clientSocket;
        private String name;


        public Connection(Socket clientSocket) throws IOException {
            this.clientSocket = clientSocket;
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        }

        @Override
        public void run() {
            try {
                name = in.readLine();
                for (Connection connection : connections) {
                    connection.out.println(name + " joined");
                }

                while (true) {
                    String message = in.readLine();
                    if (message.equals("@exit")) {
                        break;

                    } else if (message.startsWith("@ls ")) {
                        String username = message.substring(4);
                        String tempMessage = in.readLine();
                        for (Connection connection : connections) {
                            if (connection.name.equals(username)) {
                                connection.out.println(name + ": " + tempMessage);
                            }
                        }
                    } else {
                        for (Connection connection : connections) {
                            if (!(connection.name.equals(name))) {
                                connection.out.println(name + ": " + message);
                            }
                        }
                    }
                }

                for (Connection connection : connections) {
                    connection.out.println(name + " left");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                    in.close();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}