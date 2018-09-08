package bread.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import bread.db.DBManager;
import bread.menuserver.ServerSocketManager;

/**
 * Server쪽 main메소드 사용 클래스. ServerManager생성자 호출.
 * @author 정동영
 */
public class ServerMain {
	
	public static void main(String[] args) {
//		new ServerManager();
//		DBManager dbm = new DBManager();
//		dbm.dbManager();
		
		int PORT = 1004;

		try {
			ServerSocket ssocket = new ServerSocket(PORT);

			while (true) {
				Socket socket = ssocket.accept();

				ObjectInputStream nois = new ObjectInputStream(socket.getInputStream());
				ObjectOutputStream noos = new ObjectOutputStream(socket.getOutputStream());

				ServerSocketManager dst = new ServerSocketManager(nois, noos, socket);
				Thread t = new Thread(dst);
				t.start();
			}
		} catch (IOException e) {
//			e.printStackTrace();
		}
	}
}
