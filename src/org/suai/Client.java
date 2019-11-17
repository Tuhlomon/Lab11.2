package org.suai;

import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {

    public static void main(String[] args) throws Exception{
        DatagramSocket clientSocket = new DatagramSocket(9876);
        InetAddress IPAddress = InetAddress.getByName("localhost");
        GUI gui = new GUI(clientSocket, IPAddress, 9877, "Client");
        Thread wr = new Thread(new WorkerReader(gui, clientSocket));
        wr.start();
    }
}
