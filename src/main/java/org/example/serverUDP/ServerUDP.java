package org.example.serverUDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ServerUDP {
    private static final int PORT = 8080;
    private DatagramSocket socket;
    private boolean running = true;
    private HashMap<String, String> clientRooms;  // Хранит информацию о том, в какой комнате находится каждый клиент
    private HashMap<String, String> clientYCoordinates;

    public ServerUDP() {
        try {
            socket = new DatagramSocket(PORT);
            clientRooms = new HashMap<>();
            clientYCoordinates = new HashMap<>();
            System.out.println("Server is running on port " + PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startServer() {
            try {
            while (running) {
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

//                String message = new String(packet.getData(), 0, packet.getLength());
//                handlePacket(packet.getAddress().toString(), packet.getPort(), message);
                Runnable clientHandler = new ClientHandler(packet, packet.getAddress(), packet.getPort(), socket, clientRooms, clientYCoordinates);
                new Thread(clientHandler).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }

    public void stop() {
        running = false;
    }

    private static class ClientHandler implements Runnable {
        private DatagramPacket packet;
        private InetAddress address;
        private int port;
        private DatagramSocket socket;
        private HashMap<String, String> clientRooms;
        private HashMap<String, String> clientYCoordinates;

        public ClientHandler(DatagramPacket packet, InetAddress address, int port, DatagramSocket socket, HashMap<String, String> clientRooms, HashMap<String, String> clientYCoordinates) {
            this.packet = packet;
            this.address = address;
            this.port = port;
            this.socket = socket;
            this.clientRooms = clientRooms;
            this.clientYCoordinates = clientYCoordinates;
        }

        @Override
        public void run() {
            String message = new String(packet.getData(), 0, packet.getLength());

            try {
                handlePacket(message);
//                socket.send(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void handlePacket(String message) throws IOException {
            // Парсинг сообщения от клиента (пример: "nickname:y_coordinate")
            String[] parts = message.split(":");
            String nickname;
            int yCoordinate = 0;
            int roomNumber = 0;
            if (parts.length == 1){
                nickname = parts[0];
            } else {
                nickname = parts[0];
                yCoordinate = Integer.parseInt(parts[1]);
                roomNumber = Integer.parseInt(parts[2]);
            }

            if (parts[0].equals("@init")) {
                String answer;
                ArrayList<String> rooms = new ArrayList<>();
                if (rooms.isEmpty()){
                    answer = "@init:" + "No rooms";
                } else {
                    HashSet<String> uniqueValues = new HashSet<>(clientRooms.values());
                    for (String value : uniqueValues) {
                        rooms.add(value);
                    }
                    answer = "@init:" + String.join(":", rooms);
                }

                byte[] sendData = answer.getBytes();

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, port);
                socket.send(sendPacket);

            } else {
                updateClientInformation(nickname, roomNumber, yCoordinate);

                sendResponseToClient(roomNumber, nickname, yCoordinate);
            }
        }

        private void updateClientInformation(String nickname, int roomNumber, int yCoordinate) {
            clientRooms.put(nickname, String.valueOf(roomNumber));
            clientYCoordinates.put(nickname, String.valueOf(yCoordinate));
        }

        private void sendResponseToClient(int roomNumber, String nickname, int yCoordinate) throws IOException{
//            String response = nickname + ":" + yCoordinate;
//            byte[] sendData = response.getBytes();
//            System.out.println("bytes are ready");

//            String response = "enemyNickname" + ":" + yCoordinate;
//            byte[] sendData = response.getBytes();
//            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, port);
//            socket.send(sendPacket);

            for (Map.Entry<String, String> entry : clientRooms.entrySet()) {
//                System.out.println(entry);
                if (entry.getValue().equals(String.valueOf(roomNumber))) {
                    String enemyNickname = entry.getKey();
                    String enemyYCoordinate = "";

                    for (Map.Entry<String, String> enemyEntry : clientYCoordinates.entrySet()){
                        if (enemyEntry.getKey().equals(enemyNickname)){
                            enemyYCoordinate = String.valueOf(enemyEntry.getValue());
                        }
                    }
//                    String enemyYCoordinate = String.valueOf(yCoordinate);

                    String response = enemyNickname + ":" + enemyYCoordinate;
                    byte[] sendData = response.getBytes();
//                    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
//                    System.out.println(nickname);
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, port);
                    socket.send(sendPacket);
                }
            }
        }
    }

    public static void main(String[] args) {
        ServerUDP server = new ServerUDP();
        server.startServer();
    }
}
