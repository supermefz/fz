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
		// TODO �Զ����ɵķ������
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
//	// TODO �Զ����ɵķ������
//		Login l = new Login();
//	}

	public Client(String name) {
		// TODO �Զ����ɵĹ��캯�����
		username = name;
	}


	public void init(){
		JFrame jf = new JFrame("�ͻ���");
		jf.setLocation(600, 400);
		jf.setSize(600, 500);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setResizable(false);
		
		jf.setLayout(new BorderLayout());
		//���ڹرռ���
		jf.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					s.close();
					dos.close();
					dis.close();
					
					
				} catch (IOException e1) {
					// TODO �Զ����ɵ� catch ��
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
		JLabel bq = new JLabel("���");
		bq.setFont(new   java.awt.Font("Dialog",   1,   18));
		JButton an = new JButton("����");
		an.setFont(new   java.awt.Font("Dialog",   1,   18));
		//���Ͱ�ť�����¼�����
		an.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
			
				String str = username + ':'+tf.getText().trim();
				//ta.setText(str);
				tf.setText("");
				try {
					dos.writeUTF(str);
					dos.flush();//ǿ�ƽ����������������
					
				} catch (IOException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
			}
			
		});
		jp.add(bq);
		jp.add(tf);
		jp.add(an);
		pack();//�Ѵ�������Ϊ�ʺ������С�������Լ����õĴ�С
		jf.setVisible(true);
		
		connect();
		
		//r.start();
			
	}
	public void connect(){
		try {
			s = new Socket("127.0.0.1",5000);//���ӵ������������˿ں�Ϊ5000
			beconnected = true;
			System.out.println("�ÿͻ����Ѿ����ӣ�");
			dos = new DataOutputStream(s.getOutputStream());
			dis = new DataInputStream(s.getInputStream());
		} catch (UnknownHostException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		
	}	
}
