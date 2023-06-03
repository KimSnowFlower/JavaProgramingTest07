import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;


public class WordClient extends JFrame{
	static JTextField field;
	static JLabel wordOutput;
	static BufferedReader in = null;
	static BufferedWriter out = null;
	static Socket socket = null;
	static String inputMessage;
	
	WordClient(){
		setSize(400, 100);
		setTitle("스펠체크 클라이언트");
		setLayout(new FlowLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel wordInput = new JLabel("단어 입력");
		field = new JTextField(10);
		field.addKeyListener(new MyKeyListener());
		wordOutput=new JLabel("단어 뜨는 곳");
		
		add(wordInput);
		add(field);
		add(wordOutput);
		
		setVisible(true);
	}
	
	class MyKeyListener extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				try {
					String outputMessage = field.getText();
					out.write(outputMessage+"\n");
					out.flush();
					
				}catch(IOException e1) {
					System.out.println(e1.getMessage());
				}finally {
					try {
						if(socket == null)socket.close();
					}catch(IOException e1) {
						System.out.println("서버와 채팅 중 오류가 발생했습니다.");
					}
				}				
			}
		}
	}

	public static void main(String[] args) {
		try {
			socket=new Socket("218.155.224.21",9100);
			in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			new WordClient();
			
			while(true) {
				inputMessage=in.readLine();
				wordOutput.setText(field.getText() + "는" + inputMessage);
			}
			
		}catch(IOException e1) {
			System.out.println(e1.getMessage());
		}	
	}
}