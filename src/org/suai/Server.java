package org.suai;

import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server {

    public static void main(String[] args) throws Exception{
        DatagramSocket clientSocket = new DatagramSocket(9877);
        InetAddress IPAddress = InetAddress.getByName("localhost");
        GUI gui = new GUI(clientSocket, IPAddress, 9876, "Server");
        Thread wr = new Thread(new WorkerReader(gui, clientSocket));
        wr.start();
        System.out.println("Github commit test c:");
    }
}