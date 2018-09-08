package bread.chatserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ServerSocket 연결용 클래스.
 * 
 * @author 정동영
 */
public class ChatSocketManager {

	// 서버에서 사용할 PORT선언 1005
	private static final int PORT = 1005;
	// 서버소켓과 소켓을 미리 전역변수로 생성
	private ServerSocket serverSocket;
	private Socket clientSocket;

	// 소켓을 통해 들어온 클라이언트를 동기화 하기 위한 싱크로나이즈드맵
	// 자바 1.5부터 ConcurrentUtil 시리즈를 사용함
	private ConcurrentHashMap<String, ObjectOutputStream> onAirClients = new ConcurrentHashMap<>();
	private String message = "";

	// 서버 소켓 매니저가 실행되면 먼저 쓰레드를 실행
	public ChatSocketManager() {
		System.out.println("SERVER : Generating client socket to chatting");

		try {
			System.out.println(">1");
			serverSocket = new ServerSocket(PORT);

			while (true) {
				System.out.println(">>1");
				clientSocket = serverSocket.accept();

				ChatServerThread st = new ChatServerThread(clientSocket);
				Thread startThread = new Thread(st);
				startThread.start();
				
				System.out.println("SERVER : Here comes new challenger!");
			}

		} catch (IOException e) {
		//	e.printStackTrace();
		}
	}

	class ChatServerThread implements Runnable {

		private Socket clientSocket;
		private String clientIP;
		private ObjectInputStream ois;
		private ObjectOutputStream oos;

		public ChatServerThread(Socket clientSocket) {
			System.out.println(">>>1");
			this.clientSocket = clientSocket;
			this.clientIP = clientSocket.getInetAddress().toString();

			System.out.println("SERVER : Getting intput/output streams");
			System.out.println(">2");

			try {
				ois = new ObjectInputStream(clientSocket.getInputStream());
				System.out.println(">>2");
				oos = new ObjectOutputStream(clientSocket.getOutputStream());

				System.out.println(">>>2");
				System.out.println("SERVER : Connection has been established");

				addClient(clientIP, oos);
				System.out.println(onAirClients.keySet());

			} catch (IOException e) {
			//	e.printStackTrace();
			}
		}

		@Override
		public void run() {
			try {
				while (!clientSocket.isClosed()) {

					message = ois.readUTF();
					sendMessage(message);
				}
			} catch (IOException e) {
			//	e.printStackTrace();
				System.out.println("SERVER : " + "/" + clientIP + "/" + "is quitting now");
			} finally {
				try {
					
					if (oos != null)
						oos.close();

					if (ois != null)
						ois.close();

					if (clientSocket != null)
						clientSocket.close();

					removeClient(clientIP);
				} catch (IOException e) {
				//	e.printStackTrace();
				}
			}
		}
	}
	
	public void addClient(String IP, ObjectOutputStream oos) {
		
		onAirClients.put(IP, oos);
		
		sendMessage("\n");
		sendMessage("채팅방에 오신것을 환영합니다\n");
		sendMessage("바르고 고운 말을 써주세요 \n");
	}
	
	public void removeClient(String clientNickName) {
		onAirClients.remove(clientNickName);
		sendMessage("SERVER : " + clientNickName + " exit");
	}

	public void sendMessage(String message) {
		System.out.println(message);
		Iterator<String> iterator = (onAirClients.keySet()).iterator();
		String key;
		
		while (iterator.hasNext()) {
			key = iterator.next();
			try {
				(onAirClients.get(key)).writeUTF(message);
				(onAirClients.get(key)).flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
