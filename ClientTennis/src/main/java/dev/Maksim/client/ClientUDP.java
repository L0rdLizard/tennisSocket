package dev.Maksim.client;

import dev.Maksim.client.contoller.Controller;
import dev.Maksim.client.model.GameModel;
import dev.Maksim.client.model.TennisCourtModel;
import dev.Maksim.client.view.GameView;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class ClientUDP {
//    private static final int SERVER_PORT = 9876;
    private static String nickname;
    private static int roomNumber;
    private static TennisCourtModel tennisCourtModel;

//    public ClientUDP(TennisCourtModel tennisCourtModel){
//        ClientUDP.tennisCourtModel = tennisCourtModel;
//    }

    public static void main(String[] args) {
        try {
            if (args.length != 2) {
                System.out.println("Usage: java Client <server_address> <server_port>");
                return;
            }

            String serverAddress = args[0];
            int serverPort = Integer.parseInt(args[1]);
            DatagramSocket socket = new DatagramSocket();

//            DatagramSocket socket = new DatagramSocket();
//            InetAddress serverAddress = InetAddress.getByName("localhost");

            // Initialize the client with a unique nickname
            System.out.print("Enter your nickname: ");
            Scanner scanner = new Scanner(System.in);
            while(!scanner.hasNextLine()){
                System.out.println("abbbbbbb");
                nickname=scanner.nextLine();
            }
//            nickname = scanner.nextLine();

            // Send an initialization message to the server
            String initMessage = "@init";
            sendPacket(socket, initMessage, InetAddress.getByName(serverAddress), serverPort);

            // Receive and print the server's response
//            String initResponse = receivePacket(socket);
            receivePacket(socket);
//            System.out.println("Server response: " + initResponse);

            System.out.print("Enter room number: ");
//            roomNumber = Integer.parseInt(scanner.nextLine());
            while(!scanner.hasNextLine()){
                roomNumber= Integer.parseInt(scanner.nextLine());
            }

            GameModel gameModel = new GameModel();
            GameView gameView = new GameView(gameModel);
            Controller controller = new Controller(gameView.getGamePanelView());

            tennisCourtModel = gameModel.getTennisCourt();
            // Start a loop to send and receive game updates
            while (true) {
//                System.out.print("Enter your Y-coordinate: ");
//                int yCoordinate = scanner.nextInt();
                int yCoordinate = tennisCourtModel.getRacketLeft().getY();

                // Construct the message to be sent to the server
                String message = nickname + ":" + yCoordinate + ":" + roomNumber;

                // Send the message to the server
                sendPacket(socket, message, InetAddress.getByName(serverAddress), serverPort);

                // Receive and print the server's response
//                String serverResponse = receivePacket(socket);
                receivePacket(socket);
//                System.out.println("Server response: " + serverResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sendPacket(DatagramSocket socket, String message, InetAddress serverAddress, int serverPort) throws Exception {
        byte[] sendData = message.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
        socket.send(sendPacket);
    }

    private static void receivePacket(DatagramSocket socket) throws Exception {
        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        socket.receive(receivePacket);

        String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
        String[] parts = message.split(":");

        if (parts[0].equals("@init")){
            System.out.println("\n" + parts[1]);
        }

        if (!parts[0].equals(nickname)) {
            tennisCourtModel.setRacketPosRight(Integer.parseInt(parts[1]));
        }
//        return new String(receivePacket.getData(), 0, receivePacket.getLength());
    }
}