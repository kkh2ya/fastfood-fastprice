package bread.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import bread.ui.UI;

/**
 * Socket 연결용 클래스.
 * @author 정동영.
 * */
public class ClientSocketManager implements Runnable {
	
	private static final int PORT = 1005;
//	private static final String HOST = "10.10.10.244";
	private static final String HOST = "localhost";
	
	private Socket clientSocket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	// 생성자에 UI를 담아서 실행
	private UI mainUI = null;
	
	private String clientNickName;
	
	public ClientSocketManager(UI mainUI) {
		try {
			System.out.println("CLIENT : Verifying connection...");
			System.out.println(">");
			this.mainUI = mainUI;
			System.out.println(">>");
			clientSocket = new Socket(HOST, PORT);
			System.out.println(">>>");
			
			System.out.println("CLIENT : Verifying streams... ");
			System.out.println(">");
			oos = new ObjectOutputStream(clientSocket.getOutputStream());
			System.out.println(">>");
			ois = new ObjectInputStream(clientSocket.getInputStream());
			System.out.println(">>>");
			
			System.out.println("CLIENT : Branching thread...");
			System.out.println(">");
			Thread t = new Thread(this);
			System.out.println(">>");
			t.start();
			System.out.println(">>>");
			
			System.out.println("CLIENT : CLIENT has been connected");
			
		} catch (IOException e) {
//			e.printStackTrace();
		} 
	}

	@Override
	public void run() {
		try {
			while(!clientSocket.getKeepAlive()) {
				String message = ois.readUTF();
				mainUI.updateChatLog("\n" + message);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setNickName(String newNickName) {
		this.clientNickName = newNickName;
	}
	
	public boolean isConnected() {
		return clientSocket.isClosed();
	}
	
	public void sendMessage(String message) {
		try {
			oos.writeUTF(clientNickName +"님이 말합니다 : " + message);
			oos.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
