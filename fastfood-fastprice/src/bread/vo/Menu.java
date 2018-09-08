package bread.vo;

import java.io.Serializable;

/**
 * DB의 Table 컬럼과 동일 구성.
 * 메뉴 VO클래스.
 * 기본 단품 메뉴, 가격, 주재료(beef, chicken, shrimp, else)  카테고리(burger, hotsnack, coldsnack, drink).
 * @author 김기홍
 * */
public class Menu implements Serializable {
	private String menu;
	private int price;
	private String mainRecipe;
	private String category;
	
	public Menu(String menu, int price, String mainRecipe, String category){
		this.menu = menu;
		this.price = price;
		this.mainRecipe = mainRecipe;
		this.category = category;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getMainRecipe() {
		return mainRecipe;
	}

	public void setMainRecipe(String mainRecipe) {
		this.mainRecipe = mainRecipe;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	@Override
	public String toString() {
		return String.format("메뉴명 : %s, 가격 : %d, 주재료 : %s, 카테고리 : %s", menu, price, mainRecipe, category);
	}
}
