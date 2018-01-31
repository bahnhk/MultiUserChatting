package Client;

import java.net.Socket;

public class Client {

	public static void main(String[] args) {

		String ip = "70.12.111.141";
		int port = 8888;
		Socket socket;
		ClientChat chat = null;
		chat = new ClientChat(ip, port);

	}

}
