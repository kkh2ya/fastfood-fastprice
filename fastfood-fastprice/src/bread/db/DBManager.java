package bread.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import javax.swing.plaf.synth.SynthSeparatorUI;

import bread.vo.User;
import bread.vo.Menu;
import bread.vo.SaleMenu;

/**
 * DB Manager클래스.
 * Command창에서 직접 SQL파일 복사 붙여넣기, 직접 수정하는 방법 외에,
 * 
 * 현재 클래스의 콘솔 UI로 DB를 수정할 수 있음.
 * 현재 콘솔 UI는 미사용중.
 * 
 * 현재 insertUserDBWithUser, updateUserDBWithUse, insertUserFinalChoiceDB, selectUserAllDB, selectMenuAllDB, selectSaleAllDB 메소드만 사용중.
 * 
 * 콘솔 UI를 사용하려면 ServerMain클래스에서 dbManager()호출 주석 제거.
 * 만약 실행하면, 콘솔에 뜨는 ----DBA메뉴 중 9.0 선택완료를 해주어야 로그인 단계로 넘어가는 것이 가능. 
 * @author 김기홍
 * */
public class DBManager {
	Scanner sc = new Scanner(System.in);
	ArrayList<User> userAll = new ArrayList<>();
	ArrayList<Menu> menuAll = new ArrayList<>();
	ArrayList<SaleMenu>	saleMenuAll = new ArrayList<>();
	/**
	 * VO와 연동.
	 * SQL 사용하여 User, Menu, Sale 테이블에 create, insert, delete, update문 시행.
	 * */
	/**
	 * @author 김기홍
	 * */
	public void dbManager(){
		boolean result = true;
		while(result){
			System.out.println("----DBA메뉴");
			System.out.println("1.1 Sale Table CREATE");
			System.out.println("1.2 Menu Table CREATE");
			System.out.println("1.3 User Table CREATE");
			System.out.println("2.1 Sale INSERT");
			System.out.println("2.2 Sale DELETE");
			System.out.println("2.3 Sale UPDATE");
			System.out.println("3.1 Menu INSERT");
			System.out.println("3.2 Menu DELETE");
			System.out.println("3.3 Menu UPDATE");
			System.out.println("4.1 User INSERT");
			System.out.println("4.2 User DELETE");
			System.out.println("4.3 User UPDATE");
			System.out.println("8.0 User 사용 내역 기록 Table Create");
			System.out.println("9.0 선택완료");
			System.out.print("메뉴 번호 선택 => ");
			String DBMenu = sc.nextDouble() + "";
			switch (DBMenu) {
			case "1.1" :
				if(createSaleDB()) System.out.println("Sale Created");
				break;
			case "1.2" :
				if(createMenuDB()) System.out.println("Menu Created");
				break;
			case "1.3" :
				if(createUserDB()) System.out.println("User Table Created");
				break;
			case "2.1" :
				if(insertSaleDB()) System.out.println("Sale Inserted");
				break;
			case "2.2" :
				if(deleteSaleDB()) System.out.println("Sale Deleted");
				break;
			case "2.3" :
				if(updateSaleDB()) System.out.println("Sale Updated");
				break;
			case "3.1" :
				if(insertMenuDB()) System.out.println("Menu Inserted");;
				break;
			case "3.2" :
				if(deleteMenuDB()) System.out.println("Menu Deleted");
				break;
			case "3.3" :
				if(updateMenuDB()) System.out.println("Menu Updated");
				break;
			case "4.1" :
				if(insertUserDB()) System.out.println("User Inserted");
				break;
			case "4.2" :
				if(deleteUserDB()) System.out.println("User Deleted");
				break;
			case "4.3" :
				if(updateUserDB()) System.out.println("User Updated");;
				break;
			case "8.0" :
				createUserFinalChoiceDB();
			case "9.0" : result = false;
				break;
			default: System.out.println("잘못된 선택");
				break;
			}
		}
	}
	
	public DBManager(){}
	
	/**
	 * DB에서 해당 User 정보 수정.
	 * selectUserDB()메소드 사용하여 검색.
	 * SQL 사용하여 Users 테이블에 update문 시행.
	 * @author 김기홍
	 * */
	private boolean updateUserDB() {
		boolean result = false;
		System.out.println("----사용자 수정(ID 불변)");
		User user = makeUserVO();
		
		String sql1 = "update users set name = ?, birthday = ?, password = ? where id = ?";
		String sql0 = "select userindex from users where id = ?";
		String sql2 = "delete from usersalelist where userindex = ?";
		String sql3 = "insert into usersalelist values(?, ?)";
		Connection conn = DBConnection.getConnection();
		try {
			PreparedStatement ps1 = conn.prepareStatement(sql1);
			ps1.setString(1, user.getName());
			ps1.setString(2, user.getBirthday());
			ps1.setString(3, user.getPassword());
			ps1.setString(4, user.getId());
			int exe1 = ps1.executeUpdate();
			if(exe1 != 0){
				PreparedStatement ps0 = conn.prepareStatement(sql0);
				ps0.setString(1, user.getId());
				ResultSet rs0 = ps0.executeQuery();
				int userindex = 0;
				while(rs0.next()){
					userindex = rs0.getInt(1);
				}
				PreparedStatement ps2 = conn.prepareStatement(sql2);
				ps2.setInt(1, userindex);
				int exe2 = ps2.executeUpdate();
				if(exe2 !=0){
					PreparedStatement ps3 = conn.prepareStatement(sql3);
					for(String s : user.getSaleList()){
						ps3.setInt(1, userindex);
						ps3.setString(2, s);
						int exe3 = ps3.executeUpdate();
						if(exe3 != 0){
							result = true;
						}else{
							result = false;
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * UI 회원정보 수정시 사용되는 메소드.
	 * DB에서 해당 User 정보 수정.
	 * User 정보를 넘겨받아 사용.
	 * SQL 사용하여 Users 테이블에 update문 시행.
	 * @author 김기홍, 정동영
	 * */
	public boolean updateUserDBWithUser(User usr) {
		// TODO Auto-generated method stub
		boolean result = false;
		System.out.println("----유저 수정을 통한 사용자 수정(ID 불변)");
		User user = usr;
		
		String sql1 = "update users set name = ?, birthday = ?, password = ? where id = ?";
		String sql0 = "select userindex from users where id = ?";
		String sql2 = "delete from usersalelist where userindex = ?";
		String sql3 = "insert into usersalelist values(?, ?)";
		Connection conn = DBConnection.getConnection();
		try {
			PreparedStatement ps1 = conn.prepareStatement(sql1);
			ps1.setString(1, user.getName());
			ps1.setString(2, user.getBirthday());
			ps1.setString(3, user.getPassword());
			ps1.setString(4, user.getId());
			int exe1 = ps1.executeUpdate();
			if(exe1 != 0){
				PreparedStatement ps0 = conn.prepareStatement(sql0);
				ps0.setString(1, user.getId());
				ResultSet rs0 = ps0.executeQuery();
				int userindex = 0;
				while(rs0.next()){
					userindex = rs0.getInt(1);
				}
				PreparedStatement ps2 = conn.prepareStatement(sql2);
				ps2.setInt(1, userindex);
				int exe2 = ps2.executeUpdate();
				if(exe2 !=0){
					PreparedStatement ps3 = conn.prepareStatement(sql3);
					for(String s : user.getSaleList()){
						ps3.setInt(1, userindex);
						ps3.setString(2, s);
						System.out.println(s);
						int exe3 = ps3.executeUpdate();
						if(exe3 != 0){
							result = true;
						}else{
							result = false;
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * DB에서 해당 User 정보 삭제.
	 * selectUserDB()메소드 사용하여 검색.
	 * SQL 사용하여 Users 테이블에 delete문 시행.
	 * @author 김기홍
	 * */
	private boolean deleteUserDB() {
		boolean result = false;
		System.out.println("----사용자 삭제");
		System.out.print("삭제할 ");
		User user = selectUserDB();
		String sql = "delete from users where id = ? and password = ?";
		Connection conn = DBConnection.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, user.getId());
			ps.setString(2, user.getPassword());
			int exe = ps.executeUpdate();
			if(exe != 0){
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * DB에서 해당 User 정보 검색.
	 * 아이디, 비번으로 검색.
	 * SQL 사용하여 Users 테이블에 select문 시행.
	 * @author 김기홍
	 * */
	private User selectUserDB(){
		User result = null;
		System.out.println("----사용자 검색");
		System.out.print("ID : ");
		String userID = sc.next();
		System.out.print("비밀번호 : ");
		String userPW = sc.next();
		String sql1 = "select * from users where id = ? and password = ?";
		String sql2 = "select * from usersalelist where userindex = ?";
		Connection conn = DBConnection.getConnection();
		try {
			PreparedStatement ps1 = conn.prepareStatement(sql1);
			ps1.setString(1, userID);
			ps1.setString(2, userPW);
			ResultSet rs1 = ps1.executeQuery();
			while(rs1.next()){
				String name = rs1.getString(1);
				String birthday = rs1.getString(2);
				String id = rs1.getString(3);
				String password = rs1.getString(4);
				int userIndex = rs1.getInt(5);
				
				PreparedStatement ps2 = conn.prepareStatement(sql2);
				ps2.setInt(1, userIndex);
				ResultSet rs2 = ps2.executeQuery();
				ArrayList<String> userSaleList = new ArrayList<>();
				while(rs2.next()){
					userSaleList.add(rs2.getString(2));
				}
				result = new User(name, birthday, id, password, userSaleList);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * DB에 해당 User 정보 입력.
	 * SQL 사용하여 Users 테이블에 insert문 시행.
	 * 아이디로 중복 검사 시행 -> DB SQL create시 unique값 설정.
	 * @author 김기홍
	 * */
	private boolean insertUserDB(){
		boolean result = false;
		System.out.println("----사용자 등록");
		User user = makeUserVO();
		String sql1 = "insert into users values(?, ?, ?, ?, user_userindex_seq.nextval)";
		String sql2 = "insert into usersalelist values(user_userindex_seq.currval, ?)";
		Connection conn = DBConnection.getConnection();
		try {
			PreparedStatement ps1 = conn.prepareStatement(sql1);
			ps1.setString(1, user.getName());
			ps1.setString(2, user.getBirthday());
			ps1.setString(3, user.getId());
			ps1.setString(4, user.getPassword());
			System.out.println(user.getName());
			System.out.println(user.getBirthday());
			System.out.println(user.getId());
			System.out.println(user.getPassword());
			int exe1 = ps1.executeUpdate();
			if(exe1 != 0){
				PreparedStatement ps2 = conn.prepareStatement(sql2);
				for(String s : user.getSaleList()){
					ps2.setString(1, s);
					int exe2 = ps2.executeUpdate();
					if(exe2 != 0){
						result = true;
					}else{
						result = false;
					}
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * UI 회원가입시 사용되는 메소드.
	 * DB에 해당 User 정보 입력.
	 * SQL 사용하여 Users 테이블에 insert문 시행.
	 * 아이디로 중복 검사 시행 -> DB SQL create시 unique값 설정.
	 * @author 김기홍, 정동영
	 * */
	public boolean insertUserDBWithUser(User usr){
		boolean result = false;
		System.out.println("----사용자 등록");
		User user = usr;
		String sql1 = "insert into users values(?, ?, ?, ?, user_userindex_seq.nextval)";
		String sql2 = "insert into usersalelist values(user_userindex_seq.currval, ?)";
		Connection conn = DBConnection.getConnection();
		try {
			PreparedStatement ps1 = conn.prepareStatement(sql1);
			ps1.setString(1, user.getName());
			ps1.setString(2, user.getBirthday());
			ps1.setString(3, user.getId());
			ps1.setString(4, user.getPassword());
			System.out.println(user.getName());
			System.out.println(user.getBirthday());
			System.out.println(user.getId());
			System.out.println(user.getPassword());
			int exe1 = ps1.executeUpdate();
			if(exe1 != 0){
				PreparedStatement ps2 = conn.prepareStatement(sql2);
				for(String s : user.getSaleList()){
					ps2.setString(1, s);
					int exe2 = ps2.executeUpdate();
					if(exe2 != 0){
						result = true;
					}else{
						result = false;
					}
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * DB등록 및 수정용 User VO클래스 객체 생성.
	 * @author 김기홍
	 * */
	String name;
	String birthday;
	String id;
	String password;
	private User makeUserVO() {
		System.out.print("이름 : ");
		name = sc.next();
		System.out.print("생년월일(yyyy/mm/dd) : ");
		birthday = sc.next();
		System.out.print("ID(10자리 이하) : ");
		id = sc.next();
		System.out.print("비밀번호(문자, 숫자 조합 10자리 이하) : ");
		password = sc.next();
		ArrayList<String> saleList = makeUserVOsaleList();
		
		return new User(name, birthday, id, password, saleList);
	}
	
	/**
	 * DB등록용 User VO클래스 객체 중 saleList 생성.
	 * @author 김기홍
	 * */
	private ArrayList<String> makeUserVOsaleList() {
		System.out.println("USER 보유 할인 목록----");
		ArrayList<String> saleList = saleList();
		for(String s : saleList){
			System.out.println(s);
		}
		ArrayList<String> saleListChoice = saleListChoice();
		return saleListChoice;
	}

	/**
	 * DB에 User VO클래스와 1:1관계인 테이블 작성.
	 * 테이블명 "Users"
	 * SQL 사용하여 create문 시행. unique값 설정.
	 * @author 김기홍
	 * */
	private boolean createUserDB() {
		boolean result = false;
		boolean re0 = false;
		boolean re1	= false;
		boolean re2 = false;
		String sql = "create table users("
				+ "name varchar2(25) not null,"
				+ "birthday varchar2(15) not null,"
				+ "id varchar2(25) unique not null,"
				+ "password varchar2(10) not null,"
				+ "userindex number primary key"
				+ ")";
		
		String sqlSeq = "create sequence user_userindex_seq "
				+ "start with 1"
				+ "increment by 1";
		
		Connection conn = DBConnection.getConnection();
		try {
			PreparedStatement ps1 = conn.prepareStatement(sql);
			int exe = ps1.executeUpdate();
			if(exe != 0){
				re0 = true;
			}
			PreparedStatement ps2 = conn.prepareStatement(sqlSeq);
			int exe1 = ps2.executeUpdate();
			if(exe1 != 0){
				re1 = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(createUserSaleListDB()) re2 = true;
		
		if(re0 && re1 && re2) result = true;
		
		createUserFinalChoiceDB();
		
		return result;
	}
	
	/**
	 * DB의 Users테이블의 userindex컬럼을 참조하는 usersalelist테이블 작성. cascade관계 설정.
	 * saleMenu VO(테이블)의 saleIndex과 1:1관계로 각각의 sale종류 인덱스와 참조관계인 테이블.
	 * SQL 사용하여 create문 시행.
	 * @author 김기홍
	 * */
	private boolean createUserSaleListDB() {
		boolean result = false;
		String sql = "create table usersalelist("
				+ "userindex number references users(userindex) on delete cascade not null,"
				+ "usersalelistindex varchar2(4) references salemenu(saleindex)"
				+ ")";
		Connection conn = DBConnection.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			int exe = ps.executeUpdate();
			if(exe != 0){
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	/**
	 * DB에서 해당 Menu 정보 수정.
	 * selectMenuDB()메소드 사용하여 검색.
	 * SQL 사용하여 Menu 테이블에 update문 시행.
	 * @author 김기홍
	 * */
	private boolean updateMenuDB() {
		boolean result = false;
		System.out.println("----메뉴 수정(메뉴 이름 불변)");
		Menu menu = makeMenuVO();
		String sql = "update menu set price = ?, mainRecipe = ?, category = ? where menu = ?";
		Connection conn = DBConnection.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, menu.getPrice());
			ps.setString(2, menu.getMainRecipe());
			ps.setString(3, menu.getCategory());
			ps.setString(4, menu.getMenu());
			int exe = ps.executeUpdate();
			if(exe != 0){
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * DB에서 해당 Menu 정보 삭제.
	 * selectMenuDB()메소드 사용하여 검색.
	 * SQL 사용하여 Menu 테이블에 delete문 시행.
	 * @author 김기홍
	 * */
	private boolean deleteMenuDB() {
		boolean result = false;
		System.out.println("----메뉴 삭제");
		System.out.print("삭제할 메뉴 : ");
		String deleteMenu = sc.next();
		String sql = "delete from menu where menu = ?";
		Connection conn = DBConnection.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, deleteMenu);
			int exe = ps.executeUpdate();
			if(exe !=0){
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * DB에서 해당 Menu 정보 검색.
	 * SQL 사용하여 Menu 테이블에 select문 시행.
	 * @author 김기홍
	 * */
	private Menu selectMenuDB() {
		Menu result = null;
		System.out.println("----메뉴 검색");
		System.out.print("메뉴 이름: ");
		String selectMenu = sc.next();
		String sql = "select * from menu where menu = ?";
		Connection conn = DBConnection.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, selectMenu);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				String menu = rs.getString(1);
				int price = rs.getInt(2);
				String mainRecipe = rs.getString(3);
				String category = rs.getString(4);
				result = new Menu(menu, price, mainRecipe, category);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * DB에 해당 Menu 정보 입력.
	 * SQL 사용하여 Menu 테이블에 insert문 시행.
	 * 중복 검사 시행.
	 * @author 김기홍
	 * */
	private boolean insertMenuDB() {
		boolean result = false;
		System.out.println("----메뉴등록");
		Menu menu = makeMenuVO();
		String sql = "insert into Menu values(?, ?, ?, ?)";
		Connection conn = DBConnection.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, menu.getMenu());
			ps.setInt(2, menu.getPrice());
			ps.setString(3, menu.getMainRecipe());
			ps.setString(4, menu.getCategory());
			int exe = ps.executeUpdate();
			if(exe != 0){
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * DB등록 및 수정용 Menu VO클래스 객체 생성.
	 * @author 김기홍
	 * */
	private Menu makeMenuVO() {
		Menu result = null;
		System.out.print("메뉴 이름 : ");
		String menu = sc.next();
		System.out.print("가격(원) : ");
		int price = sc.nextInt();
		System.out.print("주재료(beef/chicken/shrimp/else) : ");
		String mainRecipe = sc.next();
		System.out.print("분류(burger/hotsnack/coldsnack/drink) : ");
		String category = sc.next();
		
		return new Menu(menu, price, mainRecipe, category);
	}
	
	/**
	 * DB에 MenuVO클래스와 1:1관계인 테이블 작성.
	 * 테이블명 "Menu"
	 * SQL 사용하여 create문 시행.
	 * @author 김기홍
	 * */
	private boolean createMenuDB() {
		boolean result = false;
		String sql = "create table menu("
				+ "menu varchar2(80) unique not null,"
				+ "price number(10) not null,"
				+ "mainRecipe varchar2 (15) not null,"
				+ "category varchar2 (15) not null"
				+ ")";
		Connection conn = DBConnection.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			int exe = ps.executeUpdate();
			if(exe != 0){
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * DB에서 해당 Sale 정보 수정.
	 * selectSaleDB()메소드 사용하여 검색.
	 * SQL 사용하여 Sale 테이블에 update문 시행.
	 * @author 김기홍
	 * */
	private boolean updateSaleDB() {
		boolean result = false;
		System.out.println("----할인 목록 수정");
		System.out.print("할인 목록 번호 : ");
		String saleIndex = sc.next();
		System.out.print("할인 방법(영수증 세트 업그레이드 쿠폰/올마이쇼핑카드(점심)/현대카드ZERO/시럽월렛_버거킹/(공식) 버거킹 BURGERKINGKOREA/버거킹이 드리는 감사의 달 선물) : ");
		String saleWay = sc.next();
		System.out.print("할인 메뉴(ex) 와퍼 + 콜라): ");
		String saleMenu = sc.next();
		System.out.print("할인 가격 : ");
		double salePrice = sc.nextDouble();
		System.out.print("할인 날짜 시작(ex) 2017/07/01) : ");
		String saleFrm = sc.next();
		System.out.print("할인 날짜 종료(ex) 2017/10/03) : ");
		String saleDue = sc.next();
		System.out.print("할인 시간 시작(ex) 오전 10시 -> 100000) : ");
		String saleTimeSrt = sc.next();
		System.out.print("할인 시간 종료(ex) 오후 2시 -> 140000) : ");
		String saleTimeEnd = sc.next();
		
		Calendar calendar = Calendar.getInstance();
		ArrayList<String> sfas = new ArrayList<>();
		for(String s : saleFrm.split("/")){
			sfas.add(s);
		}
		calendar.set(Integer.parseInt(sfas.get(0)), Integer.parseInt(sfas.get(1)), Integer.parseInt(sfas.get(2)));
        Date dateFrom = calendar.getTime();
        String saleFrom = new SimpleDateFormat("yyyy-MM-dd").format(dateFrom);
        
        ArrayList<String> sdas = new ArrayList<>();
		for(String s : saleFrm.split("/")){
			sdas.add(s);
		}
		calendar.set(Integer.parseInt(sdas.get(0)), Integer.parseInt(sdas.get(1)), Integer.parseInt(sdas.get(2)));
        Date dateDue = calendar.getTime();
        String saleDued = new SimpleDateFormat("yyyy-MM-dd").format(dateDue);
		
		String sql = "update salemenu set saleway = ?, salemenu = ?, saleprice = ?, salefrm = ?, saledue = ?, saletimesrt = ?, saletimeend = ? where saleindex = ?";
		Connection conn = DBConnection.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, saleWay);
			ps.setString(2, saleMenu);
			ps.setDouble(3, salePrice);
			ps.setDate(4, java.sql.Date.valueOf(saleFrom));
			ps.setDate(5, java.sql.Date.valueOf(saleDued));
			ps.setString(6, saleTimeSrt);
			ps.setString(7, saleTimeEnd);
			ps.setString(8, saleIndex);
			int exe = ps.executeUpdate();
			if(exe != 0){
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * DB에서 해당 Sale 정보 삭제.
	 * selectSaleDB()메소드 사용하여 검색.
	 * SQL 사용하여 Sale 테이블에 delete문 시행.
	 * @author 김기홍
	 * */
	private boolean deleteSaleDB() {
		boolean result = false;
		System.out.println("----할인 목록 삭제");
		System.out.print("삭제할 할인 목록 번호 : ");
		String saleIndex = sc.next();
		String sql = "delete from salemenu where saleindex = ?";
		Connection conn = DBConnection.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, saleIndex);
			int exe = ps.executeUpdate();
			if(exe != 0){
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * DB에서 해당 Sale 정보 검색.
	 * SQL 사용하여 Sale 테이블에 select문 시행.
	 * @author 김기홍
	 * */
	private SaleMenu selectSaleDB() {
		SaleMenu result = null;
		System.out.println("----할인 목록 검색");
		System.out.print("목록 번호 : ");
		String saleMenuIndex = sc.next();
		String sql = "select * from salemenu where saleindex = ?";
		Connection conn = DBConnection.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, saleMenuIndex);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				String saleIndex = rs.getString(1);
				String saleWay = rs.getString(2);
				String saleMenu = rs.getString(3);
				double salePrice = rs.getDouble(4);
				String saleFrm = rs.getString(5);
				String saleDue = rs.getString(6);
				String saleTimeSrt = rs.getString(7);
				String saleTimeEnd = rs.getString(8);
				result = new SaleMenu(saleIndex, saleWay, saleMenu, salePrice, saleFrm, saleDue, saleTimeSrt, saleTimeEnd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * DB에 해당 Sale 정보 입력.
	 * SQL 사용하여 Sale 테이블에 insert문 시행.
	 * 중복 검사 시행.
	 * @author 김기홍
	 * */
	private boolean insertSaleDB() {
		boolean result = false;
		System.out.println("----할인 목록 등록");
		ArrayList<String> saleList = saleList();
		String sql = "insert into salemenu values(?, ?, ?, ?, ?, ?, ?, ?)";
		Connection conn = DBConnection.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			int i = 0;
			int j = 0;
			for(String s1 : saleList){
				System.out.println(s1);
				for(String s2 : s1.split(" \\| ")){
					i++;
					System.out.println(i);
					System.out.println(s2);
					if(i == 4){
						ps.setDouble(i, Double.parseDouble(s2));
					}else if(i == 5 || i == 6){
						Calendar calendar = Calendar.getInstance();
						ArrayList<String> sfas = new ArrayList<>();
						for(String s : s2.split("/")){
							sfas.add(s);
						}
						calendar.set(Integer.parseInt(sfas.get(0)), Integer.parseInt(sfas.get(1)), Integer.parseInt(sfas.get(2)));
				        Date dateFrom = calendar.getTime();
				        String saleFrom = new SimpleDateFormat("yyyy-MM-dd").format(dateFrom);
						
						ps.setDate(i, java.sql.Date.valueOf(saleFrom));
					}else{
						ps.setString(i, s2);
					}
				}
				int exe = ps.executeUpdate();
				ps.clearParameters();
				if(exe != 0){
					result = true;
				}else{
					result = false;
				}
				i = 0;
				j++;
				if(j == saleList.size()-1) break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * DB등록 및 수정용 Sale VO클래스 객체 생성용 String 리스트.
	 * @author 김기홍
	 * */
	private ArrayList<String> saleList() {
		ArrayList<String> saleList = new ArrayList<>();
		saleList.add("1.1 | 영수증 세트 업그레이드 쿠폰 | 단품 구매 시 세트 업그레이드 쿠폰 | 0 | 2017/01/01 | 2017/12/31 | 000000 | 240000");
		saleList.add("2.1 | 올마이쇼핑카드(점심) | 점심시간 음식점(버거킹 포함) 10% 청구할인 | 0 | 2017/01/01 | 2017/12/31 | 120000 | 140000");
		saleList.add("2.2 | 현대카드ZERO | 외식 : 모든 외식업종(버거킹 포함) 이용금액 1.2% 할인 | 0 | 2017/01/01 | 2017/12/31 | 000000 | 240000");
		saleList.add("3.1 | 시럽월렛 | 와퍼 + 콜라 | 4500 | 2017/05/01 | 2017/05/31 | 100000 | 220000");
		saleList.add("3.2 | 시럽월렛 | 치즈와퍼 + 콜라 | 5000 | 2017/05/01 | 2017/05/31 | 100000 | 220000");
		saleList.add("3.3 | 시럽월렛 | 와퍼주니어 + 와퍼주니어 + 콜라 + 콜라 | 6000 | 2017/05/01 | 2017/05/31 | 100000 | 220000");
		saleList.add("3.4 | 시럽월렛 | 콰트로치즈와퍼 + BLT롱치킨버거 + 콜라 + 콜라 | 10000 | 2017/05/01 | 2017/05/31 | 100000 | 220000");
		saleList.add("3.5 | 시럽월렛 | 갈릭스테이크버거 + 콜라 | 5500 | 2017/05/01 | 2017/05/31 | 100000 | 220000");
		saleList.add("3.6 | 시럽월렛 | 치즈프라이 | 2000 | 2017/05/01 | 2017/05/31 | 100000 | 220000");
		saleList.add("3.7 | 시럽월렛 | 크런치치킨버거 + 콜라 | 3900 | 2017/05/01 | 2017/05/31 | 100000 | 220000");
		saleList.add("3.8 | 시럽월렛 | 아이스아메리카노 | 1000 | 2017/05/01 | 2017/05/31 | 100000 | 220000");
		saleList.add("3.9 | 시럽월렛 | 컵아이스크림 | 500 | 2017/05/01 | 2017/05/31 | 100000 | 220000");
		saleList.add("3.10 | 시럽월렛 | 아메리카노(Hot) | 1000 | 2017/05/01 | 2017/05/31 | 100000 | 220000");
		saleList.add("4.1 | (공식) 버거킹 BURGERKINGKOREA | 불고기버거 | 1000 | 2017/05/15 | 2017/05/31 | 100000 | 220000");
		saleList.add("4.2 | (공식) 버거킹 BURGERKINGKOREA | 와퍼주니어 | 2000 | 2017/05/15 | 2017/05/31 | 100000 | 220000");
		saleList.add("4.3 | (공식) 버거킹 BURGERKINGKOREA | 와퍼 + 프렌치프라이 + 콜라 | 4900 | 2017/05/15 | 2017/05/31 | 100000 | 220000");
		saleList.add("4.4 | (공식) 버거킹 BURGERKINGKOREA | 콜라 | 0 | 2017/05/15 | 2017/05/31 | 100000 | 220000");
		saleList.add("4.5 | (공식) 버거킹 BURGERKINGKOREA | 너겟킹10조각 | 1500 | 2017/05/15 | 2017/05/31 | 100000 | 220000");
		saleList.add("4.6 | (공식) 버거킹 BURGERKINGKOREA | 콰트로치즈와퍼주니어 + 프렌치프라이 + 콜라 | 4500 | 2017/05/15 | 2017/05/31 | 100000 | 220000");
		saleList.add("4.7 | (공식) 버거킹 BURGERKINGKOREA | 갈릭스테이크버거 + 망고젤리에이드 | 5500 | 2017/05/15 | 2017/05/31 | 100000 | 220000");
		saleList.add("4.8 | (공식) 버거킹 BURGERKINGKOREA | 콰트로치즈와퍼 + 통새우와퍼주니어 + 콜라 + 콜라 + 프렌치프라이 | 10000 | 2017/05/15 | 2017/05/31 | 100000 | 220000");
		saleList.add("4.9 | (공식) 버거킹 BURGERKINGKOREA | 프렌치프라이 + 바닐라선데 | 1500 | 2017/05/15 | 2017/05/31 | 100000 | 220000");
		saleList.add("4.10 | (공식) 버거킹 BURGERKINGKOREA | 아이스아메리카노 | 1000 | 2017/05/15 | 2017/05/31 | 100000 | 220000");
		saleList.add("5.1 | 버거킹이 드리는 감사의 달 선물 | 콰트로치즈와퍼 + 치즈와퍼 + 콜라  + 콜라 | 10000 | 2017/05/15 | 2017/06/18 | 100000 | 220000");
		saleList.add("5.2 | 버거킹이 드리는 감사의 달 선물 | 갈릭스테이크버거 + 치즈와퍼 + 콜라  + 콜라 | 10000 | 2017/05/15 | 2017/06/18 | 100000 | 220000");
		saleList.add("5.3 | 버거킹이 드리는 감사의 달 선물 | 콰트로치즈와퍼 + BLT롱치킨버거 + 콜라 + 콜라 | 10000 | 2017/05/15 | 2017/06/18 | 100000 | 220000");
		saleList.add("5.4 | 버거킹이 드리는 감사의 달 선물 | X-TRA크런치치킨 + 콜라 | 4500 | 2017/05/15 | 2017/06/18 | 100000 | 220000");
		saleList.add("5.5 | 버거킹이 드리는 감사의 달 선물 | 치킨프라이 + 쉬림프치킨프라이 | 3000 | 2017/05/15 | 2017/06/18 | 100000 | 220000");
		saleList.add("5.6 | 버거킹이 드리는 감사의 달 선물 | 와퍼주니어 + 콜라 | 3900 | 2017/05/15 | 2017/06/18 | 100000 | 220000");
		saleList.add("5.7 | 버거킹이 드리는 감사의 달 선물 | 불고기버거 | 1500 | 2017/05/15 | 2017/06/18 | 100000 | 220000");
		saleList.add("5.8 | 버거킹이 드리는 감사의 달 선물 | 아메리카노 | 1000 | 2017/05/15 | 2017/06/18 | 100000 | 220000");
		saleList.add("5.9 | 버거킹이 드리는 감사의 달 선물 | 컵아이스크림 | 500 | 2017/05/15 | 2017/06/18 | 100000 | 220000");
		saleList.add("9.0 | 선택완료");
		return saleList;
	}
	
	private ArrayList<String> saleListChoice() {
		ArrayList<String> saleListChoice = new ArrayList<>();
		int i = 0;
		while(true){
			i++;
			System.out.print(i+"번째 번호 선택(ex) 5.8 ) -> ");
			String saleWay = sc.nextDouble() + "";
			switch (saleWay) {
			case "1.1" : saleListChoice.add("1.1");
				break;
			case "2.1" : saleListChoice.add("2.1");
				break;
			case "2.2" : saleListChoice.add("2.2");
				break;
			case "3.1" : saleListChoice.add("3.1");
				break;
			case "3.2" : saleListChoice.add("3.2");
				break;
			case "3.3" : saleListChoice.add("3.3");
				break;
			case "3.4" : saleListChoice.add("3.4");
				break;
			case "3.5" : saleListChoice.add("3.5");
				break;
			case "3.6" : saleListChoice.add("3.6");
				break;
			case "3.7" : saleListChoice.add("3.7");
				break;
			case "3.8" : saleListChoice.add("3.8");
				break;
			case "3.9" : saleListChoice.add("3.9");
				break;
			case "3.10" : saleListChoice.add("3.10");
				break;
			case "4.1" : saleListChoice.add("4.1");
				break;
			case "4.2" : saleListChoice.add("4.2");
				break;
			case "4.3" : saleListChoice.add("4.3");
				break;
			case "4.4" : saleListChoice.add("4.4");
				break;
			case "4.5" : saleListChoice.add("4.5");
				break;
			case "4.6" : saleListChoice.add("4.6");
				break;
			case "4.7" : saleListChoice.add("4.7");
				break;
			case "4.8" : saleListChoice.add("4.8");
				break;
			case "4.9" : saleListChoice.add("4.9");
				break;
			case "4.10" : saleListChoice.add("4.10");
				break;
			case "5.1" : saleListChoice.add("5.1");
				break;
			case "5.2" : saleListChoice.add("5.2");
				break;
			case "5.3" : saleListChoice.add("5.3");
				break;
			case "5.4" : saleListChoice.add("5.4");
				break;
			case "5.5" : saleListChoice.add("5.5");
				break;
			case "5.6" : saleListChoice.add("5.6");
				break;
			case "5.7" : saleListChoice.add("5.7");
				break;
			case "5.8" : saleListChoice.add("5.8");
				break;
			case "5.9" : saleListChoice.add("5.9");
				break;
			case "9.0" : return saleListChoice;
			default: System.out.println("잘못된 선택");
				break;
			}
		}
	}
	
	/**
	 * DB에 Sale VO클래스와 1:1관계인 테이블 작성.
	 * 테이블명 "Sale"
	 * SQL 사용하여 create문 시행.
	 * @author 김기홍
	 * */
	private boolean createSaleDB() {
		boolean result = false;
		String sql = "create table salemenu("
				+ "saleindex varchar2(4) primary key,"
				+ "saleway varchar2(200),"
				+ "salemenu varchar2(200),"
				+ "saleprice number(8),"
				+ "salefrm date,"
				+ "saledue date,"
				+ "saletimesrt varchar2(6),"
				+ "saletimeend varchar2(6)"
				+ ")";
		Connection conn = DBConnection.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			int exe = ps.executeUpdate();
			if(exe != 0){
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}


	/**
	 * DB에 User 별 사용 내역을 기록하는 테이블 작성.
	 * 테이블명 "UsersfinalChoice". 
	 * createUserDB()메소드내에서 실행.
	 * SQL 사용하여 create문 시행.
	 * @author 김기홍
	 * */
	public void createUserFinalChoiceDB(){
		String sql = "create table usersfinalchoice("
				+ "userid varchar2(25),"
				+ "finalchoice varchar2(200)"
				+ ")";
		Connection conn = DBConnection.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * DB에 User 별 사용 내역을 기록.
	 * SQL 사용하여 UsersfinalChoice 테이블에 insert문 시행.
	 * @author 김기홍
	 * */
	public void insertUserFinalChoiceDB(String user, String menu){
		String sql = "insert into usersfinalchoice values(?, ?)";
		Connection conn = DBConnection.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, user);
			ps.setString(2, menu);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * DB에 저장된 모든 User정보를 가져오는 메소드들.
	 * SQL 사용하여 User테이블에 select문 시행.
	 * @author 김기홍
	 * */
	public ArrayList<User> selectUserAllDB(){
		ArrayList<User> result = new ArrayList<>();
		String sql1 = "select * from users";
		String sql2 = "select * from usersalelist where userindex = ?";
		Connection conn = DBConnection.getConnection();
		try {
			PreparedStatement ps1 = conn.prepareStatement(sql1);
			ResultSet rs1 = ps1.executeQuery();
			while(rs1.next()){
				String name = rs1.getString(1);
				String birthday = rs1.getString(2);
				String id = rs1.getString(3);
				String password = rs1.getString(4);
				int userIndex = rs1.getInt(5);
				
				PreparedStatement ps2 = conn.prepareStatement(sql2);
				ps2.setInt(1, userIndex);
				ResultSet rs2 = ps2.executeQuery();
				ArrayList<String> userSaleList = new ArrayList<>();
				if(rs2.next()){
					userSaleList.add(rs2.getString(2));
				}
				result.add(new User(name, birthday, id, password, userSaleList));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * DB에 저장된 모든 Menu정보를 가져오는 메소드들.
	 * SQL 사용하여 Menu테이블에 select문 시행.
	 * @author 김기홍
	 * */
	public ArrayList<Menu> selectMenuAllDB(){  
		ArrayList<Menu> result = new ArrayList<>();
		String sql = "select * from menu";
		Connection conn = DBConnection.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				String menu = rs.getString(1);
				int price = rs.getInt(2);
				String mainRecipe = rs.getString(3);
				String category = rs.getString(4);
				result.add(new Menu(menu, price, mainRecipe, category));
			}
			System.out.println(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * DB에 저장된 모든 Sale 정보를 가져오는 메소드들.
	 * SQL 사용하여 Sale 테이블에 select문 시행.
	 * @author 김기홍
	 * */
	public ArrayList<SaleMenu> selectSaleAllDB(){
		ArrayList<SaleMenu> result = new ArrayList<>();
		Connection connd = DBConnection.getConnection();
		try {
			String sql = "select * from salemenu";
			Connection conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				String saleIndex = rs.getString(1);
				String saleWay = rs.getString(2);
				String saleMenu = rs.getString(3);
				double salePrice = rs.getDouble(4);
				String saleFrm = rs.getString(5);
				String saleDue = rs.getString(6);
				String saleTimeSrt = rs.getString(7);
				String saleTimeEnd = rs.getString(8);
				System.out.println(saleIndex);
				result.add(new SaleMenu(saleIndex, saleWay, saleMenu, salePrice, saleFrm, saleDue, saleTimeSrt, saleTimeEnd));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
