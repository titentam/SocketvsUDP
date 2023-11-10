package Bai2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

public class ClientBai2 {
    public static void main(String[] args) {
        try {
            DatagramSocket client = new DatagramSocket();
            InetAddress ipAdress = InetAddress.getByName("localhost");

            byte[] receiveData = new byte[1024];
            byte[] sendData = new byte[1024];

            System.out.println("Nhap: ");
            Scanner scanner = new Scanner(System.in);
            String request = scanner.nextLine();
            sendData = request.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,ipAdress,7788);
            client.send(sendPacket);

            DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);

            client.receive(receivePacket);
            String str = new String(receivePacket.getData()).trim();
            System.out.println(str);
            client.close();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
