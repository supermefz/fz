package com.hyw.SDS;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;




public class Server {
	boolean started = false;
	ServerSocket ss = null;
	ArrayList<aclient> clients = new ArrayList<aclient>();
	//enum type {master,slave};
	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		new Server();
	}
	public Server(){
		File f = new File("serverdb.txt");
		Map<String, SDS> mapdb= new HashMap<>();
		try {
			ss = new ServerSocket(5000);
			started = true;
		} catch (BindException e) {
			// TODO: handle exception
			System.out.println("��ʹ�øö˿�");
			System.exit(0);
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}

		try{

			while (started) {
				Socket s = ss.accept();//����ʽ ֱ����һ���û����󣬷������û�ͨ�ŵ�socket����
				aclient c = new aclient(s);//newһ���̣߳�ר��������û�����ͨ��
				new Thread(c).start();
				clients.add(c);
			}

		} catch (IOException e) {

			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}finally {
			try {
				ss.close();
			} catch (IOException e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
	}
	class aclient implements Runnable{
		boolean beconnected = false;
		DataOutputStream dos = null;
		DataInputStream dis = null;
		Socket thes = null;
		@Override
		public void run() {
			aclient c = null;
			// TODO �Զ����ɵķ������
			try{
				//�յ�ÿһ���û�����������Ϣ��ת����ÿһ���û�
				while(beconnected){
					String str = dis.readUTF();
					System.out.println(str);
					for(int a = 0;a<clients.size();a++){
						c = clients.get(a);
						c.dos.writeUTF(str);
					}
				}
			} catch (EOFException e) {
				// TODO: handle exception
				System.out.println("�ͻ����Ѿ��ر�");	
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			} finally {
				try {
					if (dis!=null) dis.close();
					if (dos!=null) dos.close();
					if (thes!=null) thes.close();
				} catch (IOException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
				if (c!=null) clients.remove(c);
			}
		}
		public aclient(Socket s){
			this.thes= s;
			beconnected = true;
			System.out.println("һ���ͻ���������");
			try {
				dos = new DataOutputStream(thes.getOutputStream());
				dis = new DataInputStream(thes.getInputStream());
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}

		}
	}	 

}
