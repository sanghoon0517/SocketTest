package kr.co.jsh.test.socket03;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;


public class ClientFile {
	//서버에 연결하는 것이니 Socket만 생성
	Socket socket;
	BufferedWriter bw;
	
	//키보드로 입력해서 전송할 데이터 생성
	BufferedReader keyboard; //헷갈리지 않기 위해
	
	//서버로부터 들어온 데이터를 읽어 들임
	BufferedReader br;
	
	public ClientFile() {
		System.out.println("1. 클라이언트 소켓 시작-------------------------");
		try {
			socket = new Socket("127.0.0.1", 10000); //해당 라인 실행 시, 서버소켓의 accept()메서드가 호출됨
			
			System.out.println("2. 버퍼 연결 완료(write)");
			bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())); 
			
			//키보드 연결
			System.out.println("3. 키보드를 입력장치로 한 스트림 + 버퍼(read)연결완료");
			keyboard = new BufferedReader(new InputStreamReader(System.in));
			
			//새로운 스레드(글읽기)
			br = new BufferedReader(new InputStreamReader(socket.getInputStream())); //서버로부터 흘러들어온 데이터를 읽음
			ReaderThread target = new ReaderThread();
			Thread t1 = new Thread(target);
			t1.start();
			
			//통신을 계속 이어가기 위해서는 while문을 돌려야 한다. 그래야 프로그램이 계속 돌기 때문에.(Server - Client 모두)
			//소켓과 버퍼는 한번만 만들어지면 되기 때문에, while문 안에서 계속 만들어줄 필요가 없다.
			while(true) {
				System.out.println("4. 키보드 입력 대기중...----------------------------------");
				String keyboardMsg = keyboard.readLine();
				//통신의 규칙 : 메시지를 전송하기 위해 메시지의 끝을 "\n"(라인피드)를 통해 프로그램에게 메시지의 끝임을 알려줘야 한다.
				bw.write(keyboardMsg+"\n");
				bw.flush();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("클라이언트소켓 에러 발생 : "+e.getMessage());
		}
	}
	
	class ReaderThread implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true) {
				try {
					String msg = br.readLine();
					System.out.println("서버로 부터 받은 메시지 : "+msg);
				}catch(Exception e) {
					System.out.println("클라이언트 소켓에서 서버로부터 메시지를 입력받는 도중 오류 발생 : "+e.getMessage());
				}
			}
		}
		
	}
	
	public static void main(String[] args) {
		new ClientFile();
	}

}
