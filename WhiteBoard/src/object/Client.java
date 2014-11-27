package object;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import draw.Draw;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.WindowConstants;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.awt.event.ActionListener;

public class Client extends JPanel {
	private  Socket socket = null;
	private String username;
	private String IP;
	private JTextArea textArea;//input
	private JButton btnStart;
	private JTextArea textArea_1;//output
	private Draw painter;//painter
	private static BufferedReader reader;
	private static PrintWriter printWriter;
	private static  int mark=0;
	private JButton btnClear;
	
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

		painter = new Draw();
		painter.setBackground(Color.white);
		painter.setMark(0);
		painter.setSize(394, 321);
		painter.setLocation(259, 51);
		painter.setVisible(true);
		
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
							String msg;
							try {
								while ((msg = reader.readLine()) != null) {
									String[] str=msg.split(" ");
									if (str[0].equalsIgnoreCase("mark=")) 
									{                   //   0      1    2    3      4     
										               //("mark= "+mark+" 1 "+X1+" "+Y1);   
										if(!painter.isMyselfSend){
											
											mark = Integer.valueOf(str[1]).intValue();
											if(Integer.valueOf(str[2]).intValue() ==1){
												painter.X1 = Integer.valueOf(str[3]).intValue();
												painter.Y1 = Integer.valueOf(str[4]).intValue();
											}else if(Integer.valueOf(str[2]).intValue() ==2){
												painter.X2 = Integer.valueOf(str[3]).intValue();
												painter.Y2 = Integer.valueOf(str[4]).intValue();
											}
											if (Integer.valueOf(str[1]).intValue()==1) 
											{
												if(Integer.valueOf(str[2]).intValue() ==2){
													painter.lineStart.add(new Point(painter.X1,painter.Y1));
													painter.lineEnd.add(new Point(painter.X2,painter.Y2));
												}		
											}else if (Integer.valueOf(str[1]).intValue()==2) 
											{
												if(Integer.valueOf(str[2]).intValue() ==2){
													painter.rectStart.add(new Point(painter.X1,painter.Y1));
													painter.rectEnd.add(new Point(painter.X2,painter.Y2));
												}
											}else if (Integer.valueOf(str[1]).intValue()==3) 
											{
												if(Integer.valueOf(str[2]).intValue() ==2){
													painter.circleStart.add(new Point(painter.X1,painter.Y1));
													painter.circleEnd.add(new Point(painter.X2,painter.Y2));
												}
											}else if (Integer.valueOf(str[1]).intValue()==4) 
											{
												if(Integer.valueOf(str[2]).intValue() ==2){
													painter.points.add(new Point(painter.X2,painter.Y2));
												}else if(Integer.valueOf(str[2]).intValue() ==3){
													painter.points.add(new Point(painter.X2,painter.Y2));
													painter.points.add(new Point(painter.X2,painter.Y2));
												}
											}

											painter.repaintAll();
										}
										painter.isMyselfSend = false;		
									}else if(str[0].equalsIgnoreCase("clear") ){
										if(!painter.isMyselfSend)
											painter.clear();
									}else{
									textArea_1.append(msg+"\r\n");
									textArea_1.setCaretPosition(textArea_1.getText().length());
									}
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
		

		String[] s={"no draw","line","rect","circle","random"};
		JComboBox comboBox = new JComboBox(s);
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 if (e.getSource() == comboBox) {
					   int index = comboBox.getSelectedIndex();
					   switch (index) {
					   case 0:
						   painter.setMark(0);
						   Client.mark=0;
					    break;
					   case 1:
						   painter.setMark(1);
						   Client.mark=1;
					    break;
					   case 2:
						   painter.setMark(2);
						   Client.mark=2;
					    break;
					   case 3:
						   painter.setMark(3);
						   Client.mark=3;
						   break;
					   case 4:
						   painter.setMark(4);
						   Client.mark=4;
					    break;
					   }
					  }
			}
		});
		
		
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
		
		btnClear = new JButton("Clear");
		btnClear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				painter.clear();
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnClear, 25, SpringLayout.SOUTH, comboBox);
		springLayout.putConstraint(SpringLayout.WEST, btnClear, 0, SpringLayout.WEST, comboBox);
		add(btnClear);
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

	public static void setMark(String string) {
		if(mark ==0 ) return;
		printWriter.println(string);
		printWriter.flush();
		
	}
}
