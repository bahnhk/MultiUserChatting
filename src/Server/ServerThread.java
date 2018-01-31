package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread{
	 //멤버변수로 선언
	 private Socket socket;
	 private BufferedReader br = null;
	 private PrintWriter pw = null;
	 private String userIP = socket.getInetAddress().toString();
	 
	 ServerThread(Socket s){
	  this.socket = s;
	 }
	 //오버라이딩일 경우 throw 불가. 
	 public void run(){
	  try{
	   service();
	  }catch(IOException e){
	   System.out.println("Client: "+userIP+"님 접속 종료.");
	  }finally{
	   try {
	    closeAll();
	   } catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	   }
	  }
	 }
	 
	 private void service()throws IOException{
	  br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	  pw = new PrintWriter(socket.getOutputStream(), true);
	  String str = null;
	  while(true){
	   str = br.readLine();
	   if(str.equals("q")){
	    System.out.println(userIP+"님이 연결을 종료했습니다.");
	    break;
	   }
	   System.out.println(userIP+"님: "+str);
	   pw.println(str);
	  }
	 }
	 public void closeAll()throws IOException{
	  if (pw != null)
	   pw.close();
	  if (br != null)
	   br.close();
	  if (socket != null)
	   socket.close();
	 }
	}

