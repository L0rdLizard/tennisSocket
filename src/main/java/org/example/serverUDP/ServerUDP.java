package org.example.serverUDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ServerUDP {

    private static final int PORT = 9876;

    private DatagramSocket socket;
    private HashMap<String, String> clientRooms;  // Хранит информацию о том, в какой комнате находится каждый клиент

    public ServerUDP() {
        try {
            socket = new DatagramSocket(PORT);
            clientRooms = new HashMap<>();
            System.out.println("Server is running on port " + PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startServer() {
        try {
            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String message = new String(packet.getData(), 0, packet.getLength());
                handlePacket(packet.getAddress().toString(), packet.getPort(),message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }

    private void handlePacket(String clientAddress, int clientPort, String message) throws IOException {
        // Парсинг сообщения от клиента (пример: "nickname:y_coordinate")
        String[] parts = message.split(":");

        String nickname = parts[0];
        int yCoordinate = Integer.parseInt(parts[1]);
        int roomNumber = Integer.parseInt(parts[2]);

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

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName(clientAddress), clientPort);
            socket.send(sendPacket);

        } else {

            // Обработка сообщения и обновление информации о клиенте в комнатах
            updateClientInformation(clientAddress, nickname, yCoordinate);

            // Отправка обновленной информации обратно клиенту
            sendResponseToClient(clientAddress, clientPort);
        }
    }

    private void updateClientInformation(String clientAddress, String nickname, int yCoordinate) {
        // Обновление информации о клиенте в комнатах
        clientRooms.put(clientAddress, "Room1");  // Пример: всегда помещаем клиентов в комнату "Room1"
        // Дополнительная логика по обработке координат и других данных


    }

    private void sendResponseToClient(String clientAddress, int clientPort) {
        // Отправка ответа клиенту (если необходимо)
        // В данном примере, сервер не отправляет ответ клиенту, но вы можете добавить логику ответа
    }

    public static void main(String[] args) {
        ServerUDP server = new ServerUDP();
        server.startServer();
    }
}
