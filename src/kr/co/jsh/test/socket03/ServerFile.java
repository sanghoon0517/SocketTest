package kr.co.jsh.test.socket03;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerFile {
	//서버를 위한 소켓은 2개가 필요하다. : ServerSocket, Socket
	
	//클라이언트와의 연결을 받는 소켓 - 연결하자마자 실제 통신을 위한 Socket을 연다.
	ServerSocket serverSocket;
	
	//실제로 데이터를 통신하는 소켓
	Socket socket;
	BufferedReader br;
	
	//새로운 스레드 필요.(Client에게 데이터 전송을 위해)
	BufferedWriter bw; 
	BufferedReader keyboard;
	
	public ServerFile() {
		System.out.println("1. 서버소켓 시작------------------------------------");
		
		try {
			serverSocket = new ServerSocket(10000); //포트를 할당한 채로 서버소켓 통신 시작
			System.out.println("2. 서버소켓 생성완료 : 클라이언트 접속 대기중-------------");
			socket = serverSocket.accept(); //클라이언트의 접속을 대기중... 접속이  되면 socket객체에 할당
			System.out.println("3. 클라이언트 연결 완료 - Buffer연결 완료(read)");
			
			br = new BufferedReader(new InputStreamReader(socket.getInputStream())); //클라이언트로부터 흘러들어온 데이터를 읽음
			//BufferedReader를 통해 읽어 들어온 버퍼 사이즈의 숫자를 읽어 들여서, InputStreamReader를 통해 문자로 부호화(변환)한다.
			
			//Write스레드에서 돌아갈 버퍼들 (글쓰기)
			keyboard = new BufferedReader(new InputStreamReader(System.in)); // 키보드로 입력
			bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			WriteThread target = new WriteThread();
			Thread t1 = new Thread(target);
			t1.start();
			
			//main스레드의 역할 (글읽기)
			//통신을 계속 이어가기 위해서는 while문을 돌려야 한다. 그래야 프로그램이 계속 돌기 때문에.(Server - Client 모두)
			//소켓과 버퍼는 한번만 만들어지면 되기 때문에, while문 안에서 계속 만들어줄 필요가 없다.
			while(true) {
				String msg = br.readLine();
				System.out.println("클라이언트로부터 받은 메시지 : "+msg);
			}
			
		} catch(Exception e) {
			System.out.println("서버소켓 에러 발생 : "+e.getMessage());
		}
	}
	
	class WriteThread implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true) {
				try {
					System.out.println("4. 키보드 입력 대기중...----------------------------------");
					String keyboardMsg = keyboard.readLine();
					bw.write(keyboardMsg+"\n"); // \n을 적어서 끝을 알리는 표시
					bw.flush();
				}catch(Exception e) {
					System.out.println("서버소켓에서 키보드로 입력받는 도중 에러 발생 : "+e.getMessage());
				}
			}
		}
		
	}

	public static void main(String[] args) {
		new ServerFile();
	}

}
