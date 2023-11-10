package Bai3HomeWork;

import Common.ClientBase;
import Common.ClientDetail;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;

public class Client extends ClientBase implements Runnable {
    private DatagramSocket client;

    private DatagramPacket receivePacket;
    private DatagramPacket sendPacket;
    private byte[] receiveData;
    private byte[] sendData;

    private InetAddress ipServer;
    private int portServer;

    public Client(){
        try {
            client = new DatagramSocket();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        receiveData = new byte[1024];
        sendData = new byte[1024];

        this.btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread t = new Thread(Client.this);
                t.start();

            }
        });

        //setVisible(true);
    }

    @Override
    public void run() {
        try {
            this.nickName = this.txtName.getText();
            this.ipServer = InetAddress.getByName(txtIP.getText());
            this.portServer =Integer.parseInt(this.txtPort.getText());

            // send nick name
            this.sendData = this.nickName.getBytes();
            System.out.println(nickName);

            sendPacket = new DatagramPacket(sendData,sendData.length,ipServer,portServer);
            client.send(sendPacket);

            Form2 f2 = new Form2(nickName);
            f2.show();
            Thread threadF2 = new Thread(f2);
            threadF2.start();
            dispose();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private class Form2 extends ClientDetail implements Runnable{

        public Form2(String nickName) {
            super(nickName);

            this.btnSend.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String message = txtMessage.getText();
                    Thread t = new Thread(() -> {
                        SendData(message);
                        txtMessage.setText("");
                    });
                    t.start();
                }
            });
            this.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    // code để xử lý sự kiện đóng form
                    System.out.println("Đóng form!");
                    client.close();
                }
            });
        }
        private void SendData(String message){
            try {
                sendData = message.getBytes();
                sendPacket = new DatagramPacket(sendData,sendData.length,ipServer,portServer);
                client.send(sendPacket);
                this.txtMessage.setText("");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        private void ReceiveData(){
            try {
                receivePacket = new DatagramPacket(receiveData,receiveData.length);
                client.receive(receivePacket);
                String message = new String(receivePacket.getData()).trim();
                txtaMessages.append(message+"\n");
                Arrays.fill( receiveData, (byte) 0 );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        @Override
        public void run() {
            while(true){
                ReceiveData();
            }
        }

    }

    public static void main(String[] args) {
        new Client();
    }
}
