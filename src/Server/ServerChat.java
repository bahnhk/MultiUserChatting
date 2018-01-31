package Server;

import java.io.*;
import java.net.*;
import java.util.*;

public class ServerChat {
	ServerSocket serverSocket;
	Socket socket;
	ArrayList<DataOutputStream> list;

	String userip ;
	public ServerChat() {
	}

	public ServerChat(int port) {
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Ready Server");
			list = new ArrayList<>();
			start();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void start() throws IOException {
		while (true) {
			socket = serverSocket.accept();
			System.out.println(socket.getInetAddress()+" Connected..");
			Receiver receiver = new Receiver(socket);
			InetAddress ip =socket.getInetAddress();
			userip = ip.toString();
			receiver.start();
		}
	}

	// Message Send to All
	class Sender implements Runnable {

		String msg;

		public Sender() {
		}

		public void setSendMsg(String msg) {
			this.msg = msg;
		}

		@Override
		public void run() {
			try {
				if (list.size() != 0) {
					for (DataOutputStream dout : list) {
						dout.writeUTF(msg);
					}
				}
			} catch (IOException e) {
			}
		}

	}

	public void sendToAll(String msg) throws IOException {
		Sender sender = new Sender();
		Thread t = new Thread(sender);
		InetAddress ip =socket.getInetAddress();
		String userip = ip.toString();
		sender.setSendMsg(userip +" : "+ msg);
		t.start();
	}

	// Message Receiver
	class Receiver extends Thread {
		DataInputStream din;
		DataOutputStream dout;
		InputStream in;
		OutputStream out;
		
		public Receiver(Socket socket) throws IOException {
			din = new DataInputStream(socket.getInputStream());
			dout = new DataOutputStream(socket.getOutputStream());
			in = socket.getInputStream();
			out = socket.getOutputStream();
			list.add(dout);
			System.out.println("접속자 수 : " + list.size());
		}

		public void close() throws IOException {
			in.close();
			din.close();
		}

		@Override
		public void run() {
			while (true) {
				String msg;
				try {

					msg = din.readUTF();
					System.out.println(userip +" : "+ msg);
					sendToAll(msg);
				} catch (IOException e) {
					System.out.println("Exit Server User");
					break;
				}
			}
		}
	}
}