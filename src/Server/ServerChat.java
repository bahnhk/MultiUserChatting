package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerChat{
 /**
  * �ټ��� Ŭ���̾�Ʈ�� �����Ͽ� ��� ����ϴ� ���� ���α׷�
  * step
  * 1. Server Socket
  * 2. while loop���� accept()�Ͽ� ���� ��ȯ�޴´�.
  * 3. ������ �Ҵ��ϴ� ServerThread�� ����
  * 4. start()
  */
 public void go() throws IOException{
  ServerSocket serverSocket = null;
  Socket s = null;
  
  try{
   serverSocket = new ServerSocket(8888);
   System.out.println("========���� ����=========");
   //�ټ��� Ŭ���̾�Ʈ�� ����ϱ� ���� loop
   while(true){
    s = serverSocket.accept(); //Ŭ���̾�Ʈ ���ӽ� ���ο� ������ ����
    ServerThread st = new ServerThread(s);
    st.start(); 
    System.out.println(s.getInetAddress()+"�� ����");
   }
  }finally{
   if (s != null)
    s.close();
   if (serverSocket != null)
	   serverSocket.close();
   System.out.println("=======���� ����=======");   
  }
 }
}
