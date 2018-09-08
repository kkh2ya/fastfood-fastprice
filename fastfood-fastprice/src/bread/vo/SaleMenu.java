package bread.vo;

import java.io.Serializable;

/**
 * DB의 Table 컬럼과 동일 구성.
 * 할인 VO클래스.
 * 할인 종류, 세일 메뉴, 할인 가격, 할인 기간.
 * 할인 종류로는 일반세트할인, 공식행사할인, 카드사1할인, 카드사2할인, 쿠폰할인, 영주증할인이 있음.
 * @author 김기홍
 * */
public class SaleMenu implements Serializable {
	private String saleIndex;
	private String saleWay;
	private String saleMenu;
	private double salePrice;
	private String saleFrm;
	private String saleDue;
	private String saleTimeSrt;
	private String saleTimeEnd;
	
	public SaleMenu(String saleIndex, String saleWay, String saleMenu, double salePrice, String saleFrm, String saleDue, String saleTimeSrt, String saleTimeEnd){
		this.saleIndex = saleIndex;
		this.saleWay = saleWay;
		this.saleMenu = saleMenu;
		this.salePrice = salePrice;
		this.saleFrm = saleFrm;
		this.saleDue = saleDue;
		this.saleTimeSrt = saleTimeSrt;
		this.saleTimeEnd = saleTimeEnd;
	}
	
	public String getSaleIndex() {
		return saleIndex;
	}

	public void setSaleIndex(String saleIndex) {
		this.saleIndex = saleIndex;
	}

	public String getSaleWay() {
		return saleWay;
	}

	public void setSaleWay(String saleWay) {
		this.saleWay = saleWay;
	}

	public String getSaleMenu() {
		return saleMenu;
	}

	public void setSaleMenu(String saleMenu) {
		this.saleMenu = saleMenu;
	}

	public double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(int salePrice) {
		this.salePrice = salePrice;
	}
	

	public String getSaleFrm() {
		return saleFrm;
	}

	public void setSaleFrm(String saleFrm) {
		this.saleFrm = saleFrm;
	}

	public String getSaleDue() {
		return saleDue;
	}

	public void setSaleDue(String saleDue) {
		this.saleDue = saleDue;
	}

		
	public String getSaleTimeSrt() {
		return saleTimeSrt;
	}

	public void setSaleTimeSrt(String saleTimeSrt) {
		this.saleTimeSrt = saleTimeSrt;
	}

	public String getSaleTimeEnd() {
		return saleTimeEnd;
	}

	public void setSaleTimeEnd(String saleTimeEnd) {
		this.saleTimeEnd = saleTimeEnd;
	}

	@Override
	public String toString() {
		return String.format("할인 번호 : %s, 할인 종류 : %s, 할인 메뉴 : %s, 할인 가격 : %f, 할인 시작일 : %s, 할인 종료일 : %s, 할인 시작시간 : %s, 할인 종료시간 : %s", saleIndex, saleWay, saleMenu, salePrice, saleFrm, saleDue, saleTimeSrt, saleTimeEnd);
	}
}
