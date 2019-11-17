package org.suai;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import static java.lang.System.exit;

public class GUI extends JFrame {
    private JPanel container = new JPanel();
    private JTextArea ta = new JTextArea("Welcome to chat!");
    private JButton button = new JButton(" > ");
    private JTextArea tai = new JTextArea("Input your text here...");
    private DatagramSocket serverSocket;
    private InetAddress IPAddress;
    private int port;
    private boolean firstPress = true;
    private String str;
    private String name;
    private String quit = "@quit";
    private byte[] sendData = new byte[1024];

    public GUI(DatagramSocket ss, InetAddress ia, int p, String n){
        super("Chat");
        serverSocket = ss;
        IPAddress = ia;
        port = p;
        name = n;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        container.setLayout(null);
        ta.setSize(494, 400);
        ta.setLocation(3,3);
        container.add(ta);
        button.setSize(50, 50);
        button.setLocation(436, 406);
        button.addActionListener(new ListenerAction());
        container.add(button);
        tai.setSize(435, 50);
        tai.setLocation(3,406);
        tai.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == '\n'){
                    sendMessage();
                }
                else{
                    if (firstPress){
                        tai.setText("");
                        firstPress = false;
                    }
                }
            }
        });
        container.add(tai);
        this.setContentPane(container);
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }

    class ListenerAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            sendMessage();
        }
    }

    public void sendMessage(){
        try {
            str = tai.getText();
            tai.setText("");
            if (str.equals(quit)){
                exit(0);
            }
            if (str.startsWith("@name")){
                name = str.substring(6);
                return;
            }
            str = name + ": " + str;
            sendData = str.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress,
                    port);
            serverSocket.send(sendPacket);
            printNewMessage("[You] " + str);
            firstPress = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void printNewMessage(String message){
        String strings[] = ta.getText().split("\n");
        if (strings.length > 24) {
            String newstr = "";
            for (int i = 1; i < 25; i++) {
                newstr = newstr + strings[i] + "\n";
            }
            newstr += message;
            ta.setText(newstr);
            return;
        }
        else {
            ta.append("\n" + message);
        }
    }
}