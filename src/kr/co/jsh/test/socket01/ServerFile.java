package kr.co.jsh.test.socket01;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerFile {
	//서버를 위한 소켓은 2개가 필요하다. : ServerSocket, Socket
	
	//클라이언트와의 연결을 받는 소켓 - 연결하자마자 실제 통신을 위한 Socket을 연다.
	ServerSocket serverSocket;
	
	//실제로 데이터를 통신하는 소켓
	Socket socket;
	BufferedReader br;
	
	public ServerFile() {
		System.out.println("1. 서버소켓 시작------------------------------------");
		
		try {
			serverSocket = new ServerSocket(10000); //포트를 할당한 채로 서버소켓 통신 시작
			System.out.println("2. 서버소켓 생성완료 : 클라이언트 접속 대기중-------------");
			socket = serverSocket.accept(); //클라이언트의 접속을 대기중... 접속이  되면 socket객체에 할당
			System.out.println("3. 클라이언트 연결 완료 - Buffer연결 완료(read)");
			
			br = new BufferedReader(new InputStreamReader(socket.getInputStream())); //클라이언트로부터 흘러들어온 데이터를 읽음
			//BufferedReader를 통해 읽어 들어온 버퍼 사이즈의 숫자를 읽어 들여서, InputStreamReader를 통해 문자로 부호화(변환)한다.
			String msg = br.readLine();
			System.out.println("클라이언트로부터 받은 메시지 : "+msg);
			
		} catch(Exception e) {
			System.out.println("서버소켓 에러 발생 : "+e.getMessage());
		}
	}

	public static void main(String[] args) {
		new ServerFile();
	}

}
