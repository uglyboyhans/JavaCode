package object;


import java.awt.Color;
import java.awt.Dimension;

import draw.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.WindowConstants;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

public class Client extends JPanel {
	private  Socket socket = null;
	private String username;
	private String IP;
	private JTextArea textArea;//input
	private JButton btnStart;
	private JTextArea textArea_1;//output
	private JPanel painter;//painter
	private BufferedReader reader;
	private PrintWriter printWriter;
	
	public Client() {
		setPreferredSize(new Dimension(750, 450));
		this.setBackground(new Color(1000000399));
		textArea = new JTextArea();
		textArea.setColumns(10);

		JButton btnSend = new JButton("Send");
		btnSend.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String str = (new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).format(new Date());
				printWriter.println(username+"  "+str);
				printWriter.println(textArea.getText());
				printWriter.flush();
				textArea.setText("");
			}
		});

		btnStart = new JButton("Start");
		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				IP = JOptionPane.showInputDialog("Please enter the IP").trim();
				username = JOptionPane.showInputDialog("Please enter your name").trim();
				try {
					this.connectServer();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			private void connectServer() throws IOException, IOException {
				String str=textArea.getText();
				socket = new Socket(IP,Server.port);
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				printWriter = new PrintWriter(socket.getOutputStream(),true);
				Thread thread = new Thread(){
					public void run() {
						while (socket != null) {
							//painter
							//painter
							//painter
							//painter
							//painter
							//painter
							//painter
							String msg;
							try {
								while ((msg = reader.readLine()) != null) {
									textArea_1.append(msg+"\r\n");
									textArea_1.setCaretPosition(textArea_1.getText().length());
								}
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
				}//end run
			};//end thread
			thread.start();
			}
			
		});

		
		textArea_1 = new JTextArea();
		textArea_1.setEditable(false);
		textArea_1.setColumns(10);
		painter = new JPanel();
		painter.setBackground(Color.white);

		JComboBox comboBox = new JComboBox();
		
		SpringLayout springLayout = new SpringLayout();
		springLayout.putConstraint(SpringLayout.WEST, textArea, 27, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, textArea, -524, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.NORTH, btnSend, 0, SpringLayout.NORTH, textArea);
		springLayout.putConstraint(SpringLayout.WEST, btnSend, 6, SpringLayout.EAST, textArea);
		springLayout.putConstraint(SpringLayout.EAST, comboBox, -10, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.NORTH, comboBox, 53, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.SOUTH, painter, 372,
				SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.EAST, painter, 653,
				SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.WEST, comboBox, 671,
				SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.NORTH, painter, 51,
				SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, painter, 259,
				SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.NORTH, btnStart, 10,
				SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, btnStart, 27,
				SpringLayout.WEST, this);
		setLayout(springLayout);

		JScrollPane scrollPane = new JScrollPane(textArea_1);
		springLayout.putConstraint(SpringLayout.NORTH, textArea, 26, SpringLayout.SOUTH, scrollPane);
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 18, SpringLayout.SOUTH, btnStart);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, 0, SpringLayout.SOUTH, painter);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 0,
				SpringLayout.WEST, btnStart);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, 226,
				SpringLayout.WEST, this);

		add(scrollPane);
		add(btnStart);
		add(textArea);
		add(btnSend);
		add(painter);
		add(comboBox);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Client of WhiteBoard");
		frame.setSize(750,450);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().add(new Client());
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}
}
