package bread.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import bread.menuserver.ServerManager;
import bread.vo.Menu;
import bread.vo.SaleMenu;
import bread.vo.User;

/**
 * Client쪽 Manager클래스.
 * */
public class ClientManager {
	ServerManager sm = new ServerManager();
	ArrayList<Menu> menuAll = new ArrayList<>();
	ArrayList<SaleMenu> saleAll = new ArrayList<>();
	
	/**
	 * Data 전송용 Socket 생성
	 * @author 정동영.
	 */
	private static final int PORT = 1004;
	private static final String HOST = "localhost";
	
	private Socket c_DataSocket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	public ClientManager(){
		try {
			c_DataSocket = new Socket(HOST, PORT);
			out = new ObjectOutputStream(c_DataSocket.getOutputStream());
			in = new ObjectInputStream(c_DataSocket.getInputStream());
			
		} catch (IOException e) {
			e.printStackTrace();
			closeStreams();
			System.exit(0);
		}
	}
	
	/**
	 * 유저 레지스트레이션 메소드.
	 * @author 정동영
	 */
	public void userRegistration(User user) {
		Object[] request = { "userRegistration", user };
		sendRequest(request);
	}
	
	/**
	 * 유저 레지스트레이션2 메소드.
	 * 접속하고 나서 정보를 수정할 때
	 * @author 정동영
	 */
	
	public void userRegistration2(User user) {
		System.out.println("유저 리스트 좀 보여주세요" + user.getSaleList());
		Object[] request = { "userRegistration2", user };
		sendRequest(request);
	}
	
	/**
	 * USER 사용 내역 저장 메소드.
	 * UI에서 USER가 finalChoice()메소드에서 최종 선택시 호출됨.
	 * ServerManager클래스의 동명의 메소드를 호출.
	 * 이때 , 현재 USER가 선택한 메뉴의 해당 할인 사항까지 같이 매개변수로 보냄.
	 * @author 김기홍
	 * */	
	public void userFinalChoice(String s){
		Object[] request = { "userFinalChoice", s };
		sendRequest(request);
	}
	
	/**
	 * UI에서 USER가 logIN()메소드에서 로그인시 호출됨.
	 * ServerManager클래스의 동명의 메소드를 호출하여 해당 User객체를 받아서 반환.
	 * UI에 현재 로그인 중인 사용자의 id를 간단하게 표시.
	 * @author 김기홍
	 * */	
	public User selectUser(String id, String password){
		User result = null;
		Object[] request = { "selectUser", id, password };
		result = (User) sendRequest(request);
		return result;
	}
	
	/**
	 * UI에서 USER가 Menu()메소드에서 메뉴 선택시 호출됨.
	 * ServerManager클래스의 동명의 메소드를 호출하여 해당 Menu객체를 받아서 반환.
	 * UI의 MenuSelected()메소드 창에 현재 선택된 메뉴들을 표시.
	 * @author 김기홍
	 * */
	public Menu selectMenu(String menuName){
		Menu result = null;
		Object[] request = { "selectMenu", menuName };
		result = (Menu) sendRequest(request);
		return result;
	}
	
	/**
	 * UI의 Menu()메소드 창에서 USER가 선택한 단품 메뉴들이 MenuSelected()메소드 창에 뜬걸 USER가 어느 하나의 단품을 선택하고 취소 버튼을 누르면 호출.
	 * ServerManager클래스의 동명의 메소드를 호출하여 해당 Menu객체를 사용자가 선택한 메뉴의 리스트에서 제거하고 정상제거 됬으면 true반환.
	 * UI의 MenuSelected()메소드 창에서 취소한 메뉴 삭제.
	 * @author 김기홍
	 * */
	public boolean selectedMenuCancel(String menuName){
		boolean result = false;
		Object[] request = { "selectedMenuCancel", menuName };
		result = (boolean) sendRequest(request);
		return result;
	}
	
	/**
	 * 종합 최저가 조합 메뉴 발굴.
	 * UI의 Menu()메소드 창에서 USER가 선택한 단품 메뉴들이 MenuSelected()메소드 창에 뜬걸 USER가 해당 조합을 구매하기로 결정을 누르면 호출.
	 * ServerManager의 동명 메소드를 호출하여 최저가 조합을 산출한 후 그 리스트(SaleMenu)를 반환 받음.
	 * 이를 가격순으로 6순위까지 종합한 최종 최저가 조합 생성.
	 * @author 정동영
	 * */
	public ArrayList<ArrayList<SaleMenu>> totalMerge(){
		ArrayList<ArrayList<SaleMenu>> result = new ArrayList<>();
		Object[] request = { "totalMerge" };
		result = (ArrayList<ArrayList<SaleMenu>>) sendRequest(request);
		System.out.println("토탈머지 보내는 리절트" + result);
		return result;
	}
	
	/**
	 * ServerManager클래스의 동명의 메소드를 호출하여 DB에 저장된 모든 Menu, Sale 정보를 가져오는 메소드들.
	 * UI에서 USER가 logIN()메소드에서 로그인시 호출되어 ServerManager의 동명 메소드 호출.
	 * menu()메소드 창에 전체 단품 메뉴 출력하도록 모든Menu정보 반환, recommendBoard()메소드 창에 현재 할인 정보 출력하도록 모든 Sale정보 반환.
	 * @author 김기홍
	 * */
	public void selectUserAll(){
		Object[] request = { "selectUserAll" };
		sendRequest(request);
	}
	
	/**
	 * 모든 메뉴 가져오기
	 * @author 김기홍
	 * */
	public ArrayList<Menu> selectMenuAll(){
		ArrayList<Menu> result = new ArrayList<>();
		
		Object[] request = { "selectMenuAll" };
		result = (ArrayList<Menu>) sendRequest(request);
		return result;
	}
	
	/**
	 * 모든 세일메뉴 가져오기
	 * @author 김기홍
	 * */
	public ArrayList<SaleMenu> selectSaleAll(){
		ArrayList<SaleMenu> result = new ArrayList<>();
		
		Object[] request = { "selectSaleAll" };
		result = (ArrayList<SaleMenu>) sendRequest(request);
		return result;
	}
	
	
	public Object sendRequest(Object[] request) {
		Object response = null;
		
		try {
			out.writeObject(request);
			out.flush();
			response = in.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 열어 놓은 스트림들을 모두 닫는다.
	 */
	public void closeStreams() {
		try{
			if (in != null) in.close();
			if (out != null) out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
