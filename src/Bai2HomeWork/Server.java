package Bai2HomeWork;

import Common.ClientHandlerBase;
import Common.ServerBase;
import Common.Service;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Server extends ServerBase implements Runnable {
    private DatagramSocket server;
    private byte[] receiveData;
    private byte[] sendData;
    private boolean isOpen;
    private ArrayList<ClientHandler> listClient;

    public Server() throws UnknownHostException {

        receiveData = new byte[1024];
        sendData = new byte[1024];
        listClient = new ArrayList<>();

        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isOpen = true;
                btnOK.setEnabled(false);
                txtIP.setEditable(false);
                txtPort.setEditable(false);
                Thread t = new Thread(Server.this);
                t.start();
            }
        });
    }

    private ClientHandler CheckClientExist(InetAddress ipClient, int portClient){
        for (var client :
                listClient) {
            if(ipClient.equals(client.ipClient)&& portClient == client.portClient){
                return client;
            }
        }
        return null;
    }
    @Override
    public void run() {
        try {
            server = new DatagramSocket(Integer.parseInt(txtPort.getText()));
            this.textArea.append("Server in running\n");
            while (this.isOpen){
                DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
                server.receive(receivePacket);

                InetAddress ipClient = receivePacket.getAddress();
                int portClient = receivePacket.getPort();

                // if exist
                var client = CheckClientExist(ipClient,portClient);
                if(client==null){
                    String nickName = new String(receivePacket.getData()).trim();
                    System.out.println(nickName);
                    listClient.add(new ClientHandler(ipClient,portClient,nickName));
                    textArea.append(nickName+ " connected"+"\n");
                }
                else{

                    String clientMessage = new String(receivePacket.getData()).trim();
                    textArea.append(client.nickName+ " request: "+ clientMessage+"\n");

                    String responeMessage = "Server respone " +client.nickName+ ": ";
                    // send client
                    try {
                        int res = Service.Balan.evaluate(clientMessage);
                        responeMessage = responeMessage + res +"\n";
                    } catch (Exception e) {
                        responeMessage = responeMessage+ e.getMessage()+"\n";
                    }

                    textArea.append(responeMessage);

                    sendData = responeMessage.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,ipClient,portClient);
                    server.send(sendPacket);
                }
                Arrays.fill( receiveData, (byte) 0 );
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private class ClientHandler extends ClientHandlerBase{
        private String nickName;
        public ClientHandler(InetAddress ipClient, int portClient, String nickName) {
            super(ipClient, portClient);
            this.nickName = nickName;
        }

    }

    public static void main(String[] args) {
        try {
            new Server();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
