package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {

		ServerSocket serverSocket;
		Socket socket;
		ServerChat serverChat = null;
		serverChat = new ServerChat();
		try {
			serverChat.go();
		} catch (IOException e) {
			System.out.println("오류입니다.");
			e.printStackTrace();
		}

	}

}
