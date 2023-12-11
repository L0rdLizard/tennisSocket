package dev.Maksim.client;

import dev.Maksim.client.contoller.Controller;
import dev.Maksim.client.model.BallModel;
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
    private static boolean gameStart = false;

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

            System.out.print("Enter your nickname: ");

            Scanner scanner = new Scanner(System.in);
            nickname = scanner.nextLine();

            String initMessage = "@init";
            sendPacket(socket, initMessage, InetAddress.getByName(serverAddress), serverPort);

            receivePacket(socket);

            System.out.print("Enter room number: ");

            while(scanner.hasNextLine()){
                roomNumber = Integer.parseInt(scanner.nextLine());
                break;
            }

            String initMessage2 = "@init2" + ":" + "0" + ":" + roomNumber;
            sendPacket(socket, initMessage2, InetAddress.getByName(serverAddress), serverPort);

            receivePacket(socket);

            GameModel gameModel = new GameModel();
            GameView gameView = new GameView(gameModel);
            Controller controller = new Controller(gameView.getGamePanelView());

            tennisCourtModel = gameModel.getTennisCourt();

            Runnable sendPacketThread = new SendPacketThread(socket, InetAddress.getByName(serverAddress), serverPort, nickname, String.valueOf(roomNumber), tennisCourtModel);
            new Thread(sendPacketThread).start();

            Runnable receivePacketThread = new ReceivePacketThread(socket);
            new Thread(receivePacketThread).start();
//            while (true) {
//                int yCoordinate = tennisCourtModel.getRacketLeft().getY();
//
//                String message = nickname + ":" + yCoordinate + ":" + roomNumber;
////                System.out.println(nickname + " " + yCoordinate + " " + roomNumber);
//
//                sendPacket(socket, message, InetAddress.getByName(serverAddress), serverPort);
//
//                receivePacket(socket);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class SendPacketThread implements Runnable {
        private DatagramSocket socket;
        private InetAddress serverAddress;
        private int serverPort;
        private String nickname;
        private String roomNumber;
        private TennisCourtModel tennisCourtModel;

        public SendPacketThread(DatagramSocket socket, InetAddress serverAddress, int serverPort, String nickname, String roomNumber, TennisCourtModel tennisCourtModel){
            this.socket = socket;
            this.serverAddress = serverAddress;
            this.serverPort = serverPort;
            this.nickname = nickname;
            this.roomNumber = roomNumber;
            this.tennisCourtModel = tennisCourtModel;
        }
        @Override
        public void run() {
            while (true){
                try {
                    sendPacket(socket, serverAddress, serverPort, nickname, roomNumber, tennisCourtModel);
                    Thread.sleep(5);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        private void sendPacket(DatagramSocket socket, InetAddress serverAddress, int serverPort, String nickname, String roomNumber, TennisCourtModel tennisCourtModel) throws Exception{
            int yCoordinate = tennisCourtModel.getRacketLeft().getY();
            String message = nickname + ":" + yCoordinate + ":" + roomNumber;
            byte[] sendData = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
            socket.send(sendPacket);
        }
    }

    private static class ReceivePacketThread implements Runnable {

        DatagramSocket socket;

        public ReceivePacketThread(DatagramSocket socket){
            this.socket = socket;
        }
        @Override
        public void run() {
            while (true){
                try {
                    receivePacket(socket);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        private static void receivePacket(DatagramSocket socket) throws Exception {
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);

            String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
            String[] parts = message.split(":");

            if (parts[0].equals("@init")){
                for (String part : parts){
                    if (part.equals("@init")) continue;
                    System.out.println("\n" + part);
                }
//                System.out.println("\n" + parts[1]);
            } else if (parts[0].equals("@kick")) {
                System.out.println("This room is full");
                System.exit(0);
            }
            else if (!parts[0].equals(nickname)) {
                if (gameStart == false){
                    tennisCourtModel.getGameModel().changePlayingWithPause(true);
                    gameStart = true;
                }

//            System.out.println(parts[0] + " " + nickname);
                tennisCourtModel.setRacketPosRight(Integer.parseInt(parts[1]) + 45);
            }
//        return new String(receivePacket.getData(), 0, receivePacket.getLength());
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

//        System.out.println(parts[0]);

        if (parts[0].equals("@init")){
            System.out.println("Available rooms:");
            for (String part : parts){
                if (part.equals("@init")) continue;
                System.out.println(part + "\n");
            }
        } else if (parts[0].equals("@kick")) {
            System.out.println("This room is full");
            System.exit(0);
        } else if (parts[0].equals("@init2")){
//            if (parts[1].equals("0")){
//                for (BallModel ball : tennisCourtModel.getBalls()) {
//                    ball.setx
//                }
//            }
            return;
        }
        else if (!parts[0].equals(nickname)) {
//            System.out.println(parts[0] + " " + nickname);
            tennisCourtModel.setRacketPosRight(Integer.parseInt(parts[1]));
        }
//        return new String(receivePacket.getData(), 0, receivePacket.getLength());
    }
}

// cd C:\code\tennisSocket
//  .\gradlew :ClientTennis:run --args="localhost 8080"
// java -jar .\dev.Maksim-0.1.0.jar localhost 8080