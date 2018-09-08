package bread.vo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * DB의 Table 컬럼과 동일 구성.
 * 사용자 VO클래스.
 * 이름, 생년월일, 아이디, 비밀번호, 보유할인종류(ArrayList).
 * 보유할인종류로는 일반세트할인, 공식행사할인, 카드사1할인, 카드사2할인, 쿠폰할인, 영주증할인이 있음.
 * @author 김기홍
 * */
public class User implements Serializable {
	private String name;
	private String birthday;
	private String id;
	private String password;
	private ArrayList<String> saleList = new ArrayList<>();
	
	
	public User() {
		
	}
	
	public User(String name, String birthday, String id, String password, ArrayList<String> saleList){
		this.name = name;
		this.birthday = birthday;
		this.id = id;
		this.password = password;
		this.saleList = saleList;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ArrayList<String> getSaleList() {
		return saleList;
	}

	public void setSaleList(ArrayList<String> saleList) {
		this.saleList = saleList;
	}

	@Override
	public String toString() {
		return String.format("이름 : %s, 생일 : %s, 아이디: %s, 비밀번호 : %s, 보유할인종류 : %s", name, birthday, id, password, saleList);
	}
}
