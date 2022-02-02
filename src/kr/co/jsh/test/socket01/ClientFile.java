package kr.co.jsh.test.socket01;

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
	BufferedReader br;
	
	public ClientFile() {
		System.out.println("1. 클라이언트 소켓 시작-------------------------");
		try {
			socket = new Socket("127.0.0.1", 10000); //해당 라인 실행 시, 서버소켓의 accept()메서드가 호출됨
			
			System.out.println("2. 버퍼 연결 완료(write)");
			bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())); 
			
			//키보드 연결
			System.out.println("3. 키보드를 입력장치로 한 스트림 + 버퍼(read)연결완료");
			br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("4. 키보드 입력 대기중...----------------------------------");
			String keyboardMsg = br.readLine();
			//통신의 규칙 : 메시지를 전송하기 위해 메시지의 끝을 "\n"(라인피드)를 통해 프로그램에게 메시지의 끝임을 알려줘야 한다.
			bw.write(keyboardMsg+"\n");
			bw.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("클라이언트소켓 에러 발생 : "+e.getMessage());
		}
	}
	public static void main(String[] args) {
		new ClientFile();
	}

}
