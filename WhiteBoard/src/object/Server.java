package object;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.WindowConstants;


public class Server extends JFrame{
	class MyThread implements Runnable{
		private BufferedReader reader;
		private PrintWriter printWriter;
		private Socket socket;
		
		public MyThread(Socket s) throws IOException {
			super();
			socket = s;
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}
		
		public void run() {
			try {
				while ((msg = reader.readLine()) != null) {
					for (int i = 0; i < threads.size(); i++) {
						printWriter = new PrintWriter(threads.get(i).getOutputStream(),true);
						printWriter.println(msg);
					}
				}
				printWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
				
		}
	}
	
	public static int port = 8888;
	private static ServerSocket serverSocket = null;
	private Vector<Socket> threads = new Vector<Socket>();
	private String msg;
	private int number=0;
	public Server() {
		setPreferredSize(new Dimension(450,300));
		setSize(450, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		pack();
		
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		JTextArea textArea=new JTextArea();
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -61, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, 396, SpringLayout.WEST, this);
		getContentPane().add(scrollPane);
		
		JLabel lblServerInformation = new JLabel("Server Information");
		springLayout.putConstraint(SpringLayout.NORTH, lblServerInformation, 0, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblServerInformation, 23, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, lblServerInformation, -232, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblServerInformation, -287, SpringLayout.EAST, getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 6, SpringLayout.SOUTH, lblServerInformation);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 0, SpringLayout.WEST, lblServerInformation);
		getContentPane().add(lblServerInformation);
		this.setVisible(true);
		try {
			serverSocket = new ServerSocket(port);
			while (true) 
			{
				textArea.append("Server is running\nIP:"+InetAddress.getLocalHost()+"\n");
				Socket socket = serverSocket.accept();
				number++;
				textArea.append("linked "+number+" user(s)\n");
				threads.add(socket);
				Thread thread = new Thread(new MyThread(socket));
				thread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		try {
			serverSocket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setVisible(true);
	}


	public static void main(String[] args) {
		Server frame=new Server();
		
		frame.setVisible(true);
	}
}
