package com.hyw.SDS;

import java.awt.BorderLayout;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.TabableView;



public class Client extends JFrame {

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		new Client("kk").init();;
	}
	JFrame jf;
	JPanel jp;
	TextField tf = new TextField(30);
	TextArea ta = new TextArea();
	
	JLabel bq;
	JButton an;
	
	Socket s = null;
	DataOutputStream dos = null;
	DataInputStream dis = null;
	boolean beconnected = false;
	String username = null;
	//getmassage gm = new getmassage();
	//Thread r   = new Thread(gm);
//	public static void main(String[] args) {
//	// TODO 自动生成的方法存根
//		Login l = new Login();
//	}

	public Client(String name) {
		// TODO 自动生成的构造函数存根
		username = name;
	}


	public void init(){
		JFrame jf = new JFrame("客户端");
		jf.setLocation(600, 400);
		jf.setSize(600, 500);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setResizable(false);
		
		jf.setLayout(new BorderLayout());
		//窗口关闭监听
		jf.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					s.close();
					dos.close();
					dis.close();
					
					
				} catch (IOException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
				System.exit(0);
			}
		});
		
		//TextArea ta = new TextArea();
		jf.add(ta,BorderLayout.CENTER);
		ta.setEditable(false);
		ta.setFont(new   java.awt.Font("Dialog",   1,   15));
		JPanel jp = new JPanel();
		jp.setFont(new   java.awt.Font("Dialog",   1,   20));
		jf.add(jp,BorderLayout.SOUTH);
		//TextField tf = new TextField(40);
		JLabel bq = new JLabel("命令：");
		bq.setFont(new   java.awt.Font("Dialog",   1,   18));
		JButton an = new JButton("发送");
		an.setFont(new   java.awt.Font("Dialog",   1,   18));
		//发送按钮增加事件监听
		an.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
			
				String str = username + ':'+tf.getText().trim();
				//ta.setText(str);
				tf.setText("");
				try {
					dos.writeUTF(str);
					dos.flush();//强制将缓冲区的数据输出
					
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
			
		});
		jp.add(bq);
		jp.add(tf);
		jp.add(an);
		pack();//把窗口设置为适合组件大小，不是自己设置的大小
		jf.setVisible(true);
		
		connect();
		
		//r.start();
			
	}
	public void connect(){
		try {
			s = new Socket("127.0.0.1",5000);//连接到本地主机，端口号为5000
			beconnected = true;
			System.out.println("该客户端已经连接！");
			dos = new DataOutputStream(s.getOutputStream());
			dis = new DataInputStream(s.getInputStream());
		} catch (UnknownHostException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	}	
}
