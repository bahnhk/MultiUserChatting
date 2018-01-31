package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ServerChat {
	ServerSocket serverSocket;
	Socket socket;
	Scanner scanner;// socket이 연결이되면 만든다.
	ArrayList<DataOutputStream> doulist = null;

	public ServerChat() {
	}

	public ServerChat(int port) {
		try {
			doulist = new ArrayList<>();
			serverSocket = new ServerSocket(port);
			System.out.println("Ready Server...");
			start();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
			
			}
		}
	}

	private void start() throws IOException {
		socket = serverSocket.accept();
		scanner = new Scanner(System.in);
		
		Receiver receiver = new Receiver();
		receiver.start();
		
		while (true) {
			Sender sender= new Sender();
			Thread t = new Thread(sender);
			
			System.out.println("Input Server Message.....");
			String msg = scanner.nextLine();
			if (msg.equals("q")) {
				scanner.close();
				break;
			}
			sender.setSendMsg(msg);
			t.start();
		}
		System.out.println("Exit ServerChat.....");
	}
	
	// Message Sender...........................................
	class Sender implements Runnable {
		OutputStream out;
		DataOutputStream dout;
		String msg;

		public Sender() throws IOException {// 객체생성
			out = socket.getOutputStream();
			while(true) {
			dout = new DataOutputStream(out);
			
			
			}
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
					System.out.println("========대화가 끝났습니다=========");
					break;
					
				}

			}

		}

	}

}
