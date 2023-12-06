package org.example.client.contoller;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class ClientUDP {
    private static final int SERVER_PORT = 9876;

    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName("localhost");

            // Initialize the client with a unique nickname
            System.out.print("Enter your nickname: ");
            Scanner scanner = new Scanner(System.in);
            String nickname = scanner.nextLine();
            int roomNumber = 1;  // You can change this based on your game logic

            // Send an initialization message to the server
            String initMessage = "@init";
            sendPacket(socket, initMessage, serverAddress);

            // Receive and print the server's response
            String initResponse = receivePacket(socket);
            System.out.println("Server response: " + initResponse);

            // Start a loop to send and receive game updates
            while (true) {
                System.out.print("Enter your Y-coordinate: ");
                int yCoordinate = scanner.nextInt();

                // Construct the message to be sent to the server
                String message = nickname + ":" + yCoordinate + ":" + roomNumber;

                // Send the message to the server
                sendPacket(socket, message, serverAddress);

                // Receive and print the server's response
                String serverResponse = receivePacket(socket);
                System.out.println("Server response: " + serverResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sendPacket(DatagramSocket socket, String message, InetAddress serverAddress) throws Exception {
        byte[] sendData = message.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, SERVER_PORT);
        socket.send(sendPacket);
    }

    private static String receivePacket(DatagramSocket socket) throws Exception {
        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        socket.receive(receivePacket);
        return new String(receivePacket.getData(), 0, receivePacket.getLength());
    }
}