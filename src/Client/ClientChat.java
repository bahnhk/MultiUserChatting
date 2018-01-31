package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;


public class ClientChat {
	String ip;
	int port;
	Socket socket;
	Scanner scanner;

	public ClientChat() {

	}

	public ClientChat(String ip, int port) {
		this.ip = ip;
		this.port = port;
		try {
			socket = new Socket(ip, port);
			System.out.println("Connected Server....");
			start();
		} catch (IOException e) {
			System.out.println("Connection Refused....");
			e.printStackTrace();

		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void start() throws IOException {
		scanner = new Scanner(System.in);
		
		Receiver receiver = new Receiver();
		receiver.start();
		
		while (true) {
			Sender sender = new Sender();
			Thread t = new Thread(sender);
			System.out.println("Input Client Message....");
			String msg = scanner.nextLine();
			if (msg.equals("q")) {
				scanner.close();// while문 끝나기 전에 닫아줘야함.
				sender.close();
				break;
			}
			sender.setSendMsg(msg);
			t.start();
		}
		System.out.println("Exit Chatting....");
	}

	// 둘이 같으나 thread로 생성해서 start하는 방식이 다름

	// Message Sender.....................................
	class Sender implements Runnable {
		OutputStream out;
		DataOutputStream dout;
		String msg;

		public Sender() throws IOException {// 객체생성
			out = socket.getOutputStream();
			dout = new DataOutputStream(out);
		}

		public void setSendMsg(String msg) {// 문자넣어주고
			this.msg = msg;
		}

		public void close() throws IOException {
			dout.close();
			out.close();
		}

		@Override
		public void run() {// start
			try {
				if (dout != null) {
					dout.writeUTF(msg);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	class Receiver extends Thread {
		InputStream in;
		DataInputStream din;

		public Receiver() throws IOException {
			in = socket.getInputStream();
			din = new DataInputStream(in);
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
					System.out.println(msg);
				} catch (IOException e) {
					System.out.println("Server User가 로그아웃하였습니다.");
					break;
				}

			}

		}

	}

}