package bread.menuserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import bread.vo.Menu;
import bread.vo.SaleMenu;
import bread.vo.User;


/**
 * Client와 자료를 송수신하는 ServerSocketManager클래스.
 * @author 정동영
 * */
public class ServerSocketManager implements Runnable {

	private ServerManager sm = new ServerManager();
	private Socket s;
	private ObjectInputStream nois;
	private ObjectOutputStream noos;
	private boolean exit = false;

	public ServerSocketManager(ObjectInputStream nois, ObjectOutputStream noos, Socket s) {
		this.nois = nois;
		this.noos = noos;
		this.s = s;
	}

	@Override
	public void run() {
		try {
			while (!s.getKeepAlive()) {
				Object readObjects[] = (Object[]) nois.readObject();

				String caseString = (String) readObjects[0];

				switch (caseString) {
				case "selectUserAll":
					sm.selectUserAll();
					noos.writeObject(null);
					noos.flush();
					break;

				case "selectUser":
					User result = sm.selectUser((String) readObjects[1], (String) readObjects[2]);
					noos.writeObject(result);
					noos.flush();
					break;

				case "selectMenu":
					Menu selectMenu = sm.selectMenu((String) readObjects[1]);
					noos.writeObject(selectMenu);
					noos.flush();
					break;

				case "selectedMenuCancel":
					boolean isCanceled = sm.selectMenuCancel((String) readObjects[1]);
					noos.writeObject(isCanceled);
					noos.flush();
					break;

				case "totalMerge":
					ArrayList<ArrayList<SaleMenu>> totalMerge = sm.totalMerge();
					noos.writeObject(totalMerge);
					noos.flush();
					break;

				case "selectMenuAll":
					ArrayList<Menu> selectMenuAll = sm.selectMenuAll();
					noos.writeObject(selectMenuAll);
					noos.flush();
					break;

				case "selectSaleAll":
					ArrayList<SaleMenu> selectSaleAll = sm.selectSaleAll();
					noos.writeObject(selectSaleAll);
					noos.flush();
					break;

				case "userFinalChoice":
					sm.userFinalChoice((String) readObjects[1]);
					noos.writeObject(null);
					noos.flush();
					break;
					
				case "userRegistration" :
					sm.userRegistration((User) readObjects[1]);
					noos.writeObject(null);
					noos.flush();
					break;
					
				case "userRegistration2" :
					sm.userRegistration2((User) readObjects[1]);
					noos.writeObject(null);
					noos.flush();
					break;

				default:
					System.out.println("제대로 입력하세요");
				}
			}
		} catch (IOException e) {
			// e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// e.printStackTrace();
		} finally {
			try {
				if (noos != null)
					noos.close();
				if (nois != null)
					nois.close();
			} catch (IOException e) {
				// e.printStackTrace();
			}

		}

	}

}
