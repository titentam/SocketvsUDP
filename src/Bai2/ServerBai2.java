package Bai2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;

public class ServerBai2 {
    public static void main(String[] args) {
        try {
            DatagramSocket server = new DatagramSocket(7788);
            System.out.println("Server is listening");
            byte[] receiveData = new byte[1024];
            byte[] sendData = new byte[1024];
            while (true){
                DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
                server.receive(receivePacket);

                String request = new String(receivePacket.getData()).trim();
                System.out.println(request);

                String res ="";
                res += request.toLowerCase() +"\n";
                res += request.toUpperCase() +"\n";

                sendData = res.getBytes();

                InetAddress ipAdress = receivePacket.getAddress();
                int port = receivePacket.getPort();
                DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,ipAdress,port);
                server.send(sendPacket);
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
