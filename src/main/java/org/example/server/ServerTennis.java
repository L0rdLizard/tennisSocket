package org.example.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServerTennis {
    private static InetAddress clientAddress;
    private static int clientPort;
    private static String chatMateName = "";

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Usage: java ServerTennis.java <port>");
            return;
        }

        int port = Integer.parseInt(args[0]);
        InetAddress tempAdr = InetAddress.getByName("0.0.0.0");
        DatagramSocket serverSocket = new DatagramSocket(port, tempAdr);
        byte[] receiveData = new byte[1024];

        System.out.println("Server is running on port " + port);

        // поток приёма от клиента
        Thread receiveThread = new Thread(() -> {
            while (true) {
                try {
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    serverSocket.receive(receivePacket);


                    String receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                    if (receivedMessage.startsWith("<HELLO>")){
                        clientPort = receivePacket.getPort();
                        clientAddress = receivePacket.getAddress();
                    }
                    else if (receivedMessage.startsWith("<NAME>")){
                        chatMateName = receivedMessage.substring(5);
                    }
                    else if (receivedMessage.startsWith("<MSG>")){
                        String userMessage = receivedMessage.substring(5);
                        if (chatMateName.isEmpty()) {
                            System.out.println("Received: " + userMessage);
                        }
                        else {
                            System.out.println(chatMateName + ": " + userMessage);
                        }
                        byte[] sendEchoData = receivedMessage.getBytes();

                        DatagramPacket sendPacket = new DatagramPacket(sendEchoData, sendEchoData.length, clientAddress, clientPort);
                        serverSocket.send(sendPacket);
                    }
                    else {
                        System.out.println("Invalid packet");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        receiveThread.start();

        // поток для отправки клиенту
        Thread sendThread = new Thread(() -> {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

                while (true) {
                    String message = reader.readLine();

                    if (message.equals("@quit")) {
                        // reader.close();
                        // serverSocket.close();
                        System.exit(0);
                    } else if (message.startsWith("@name ")) {

                        // Установка имени пользователя
                        String username = message.substring(6);
                        String tempMessage = "<NAME>" + username;
                        byte[] sendData = tempMessage.getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                        serverSocket.send(sendPacket);
                    } else {
                        message = "<MSG>" + message;

                        byte[] sendData = message.getBytes();

                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                        serverSocket.send(sendPacket);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        sendThread.start();
    }
}