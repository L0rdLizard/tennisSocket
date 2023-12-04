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
    private static ArrayList<Connection> connections = new ArrayList<>();
    private static ArrayList<Room> rooms = new ArrayList<>();


    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java Server <port>");
            return;
        }

        int port = Integer.parseInt(args[0]);
        InetAddress tempAdr = InetAddress.getByName("0.0.0.0");
        ServerSocket serverSocket = new ServerSocket(port);
//        DatagramSocket serverSocket = new DatagramSocket(port, tempAdr);
//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//        PrintWriter out = new PrintWriter(System.out, true);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            Connection connection = new Connection(clientSocket);
            connections.add(connection);
            connection.start();
//            connection.out.println("Choose room\nWrite number of room or write 'new'");
//            for (Room room : rooms) {
//                System.out.println(room.number);
//                System.out.println("\n");
//            }
        }
    }

    private static class Connection extends Thread {
        private BufferedReader in;
        private PrintWriter out;
        private Socket clientSocket;
        private String roomNumber;


        public Connection(Socket clientSocket) throws IOException {
            this.clientSocket = clientSocket;
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        }

        @Override
        public void run() {
            try {
                out.println("Choose room\nWrite number of room to join or write another number to create new room");
                for (Room room : rooms) {
                    System.out.println(room.number);
                    System.out.println("\n");
                }

                boolean k = true;
                while (k) {
                    roomNumber = in.readLine();

                    if (roomNumber.matches("[0-9]+")) {
                        if (isRoomExist()) {
                            for (Room room : rooms) {
                                if (room.getNumber() == Integer.parseInt(roomNumber)){
                                    room.setRightPlayerConnection(this);
                                }
                            }
                        } else {
                            System.out.println("Строка содержит только числа");
                            Room room = new Room(Integer.parseInt(roomNumber), this);
                            rooms.add(room);
                            k = false;
                        }
                    } else {
                        System.out.println("Строка содержит другие символы помимо чисел");
                    }
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

        public boolean isRoomExist(){
            boolean result = false;
            for (Room room : rooms) {
                if (room.getNumber() == Integer.parseInt(roomNumber)){
                    result = true;
                    return result;
                }
            }
            return result;
        }
    }

    private static class Room extends Thread{
        private int number;
        private Connection leftPlayerConnection;
        private Connection rightPlayerConnection;

        public Room(int number, Connection connection){
            this.number = number;
            this.leftPlayerConnection = connection;
        }

        @Override
        public void run() {
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            while (true) {
//                socket.receive(packet);
//                InetAddress clientAddress = packet.getAddress();
//                int clientPort = packet.getPort();
//
//                if (!clients.contains(clientAddress)) {
//                    clients.add(clientAddress);
//                }
//
//                String received = new String(packet.getData(), 0, packet.getLength());
//                int clientY = Integer.parseInt(received);
//
//                // TODO: calculate enemy y coordinate and send it to the client
//                String enemyY = "100";
//                byte[] sendData = enemyY.getBytes();
//
//                for (InetAddress client : clients) {
//                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, client, clientPort);
//                    socket.send(sendPacket);
                }
            }
        }

        public int getNumber(){
            return number;
        }

        public void setRightPlayerConnection(Connection connection){
            this.rightPlayerConnection = connection;
        }
    }
}