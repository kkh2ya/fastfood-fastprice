package bread.menuserver;

import java.util.ArrayList;

import javax.swing.plaf.synth.SynthSeparatorUI;

import bread.db.DBManager;
import bread.vo.Menu;
import bread.vo.SaleMenu;
import bread.vo.User;

/**
 * Server쪽 Manager클래스.
 * Client쪽 Manager클래스의 메소드들과  1:1 동일 목적 관계
 * */
public class ServerManager {
	DBManager dbm = new DBManager();
	static ArrayList<User> userAll = new ArrayList<>();
	static User userNow = new User(); 
	static ArrayList<Menu> menuAll = new ArrayList<>();
	static ArrayList<Menu> userMenu = new ArrayList<>();
	static ArrayList<SaleMenu> saleMenuAll = new ArrayList<>();
	
	public ServerManager(){
		
	}
	
	/**
	 * 유저 등록 메소드
	 * @author 정동영
	 */
	public void userRegistration(User user) {
		dbm.insertUserDBWithUser(user);
	}
	
	/**
	 * 유저 수정 메소드
	 * 유저의 수정사항을 반영하여 업데이트
	 * @author 정동영
	 */
	public void userRegistration2(User user) {
		dbm.updateUserDBWithUser(user);
	}
	
	/**
	 * USER 사용 내역 저장 메소드.
	 * DBManager클래스의 동명의 메소드 호출.
	 * @author 김기홍
	 * */
	public void userFinalChoice(String s){
		dbm.insertUserFinalChoiceDB(userNow.getId(), s);
	}
	
	/**
	 * 선택된 User객체를 멤버변수 userAll의 ArrayList에서 가져와 반환.
	 * @author 김기홍
	 * */	
	public User selectUser(String id, String password){
		User result = null;
		for(User u : userAll){
			if(u.getId().equals(id) && u.getPassword().equals(password)){
				userNow = u;
				result = u;
				break;
			}
		}
		return result;
	}
	
	/**
	 * 선택된 Menu객체를 멤버변수 menuAll의 ArrayList에서 가져와 반환.
	 * @author 김기홍
	 * */
	public Menu selectMenu(String menuName){
		Menu result = null;
		for(Menu m : menuAll){
			if(m.getMenu().equals(menuName)){
				userMenu.add(m);
				System.out.println(userMenu);
				result = m;
				System.out.println(m);
			}
		}
		return result;
	}
	
	/**
	 * 해당 Menu객체를 사용자가 선택한 메뉴의 리스트에서 제거하고 정상제거 됬으면 true반환.
	 * @author 김기홍
	 * */
	public boolean selectMenuCancel(String menuName) {
		boolean result = false;
		for(int i = 0; i<userMenu.size(); i++){
			if(userMenu.get(i).getMenu().equals(menuName)){
				userMenu.remove(userMenu.get(i));
				result = true;
			}
		}
		return result;
	}
	
	/**
	 * 현재 접속 User의 보유할인종류(ArrayList)를 멤버변수 userAll(모든 User DB정보)에서 가져와
	 * 멤버변수 saleAll(모든 SaleMenu DB정보)에서 해당 보유할인종류와 일치하는 SaleMenu객체를 가져온다.
	 * Merge메소드에서 SaleMenu생성시 사용.
	 * @author 정동영, 김기홍
	 * */
	public ArrayList<SaleMenu> bringSale(){
		ArrayList<SaleMenu> result = new ArrayList<>();
		//현재 유저가 사용 가능한 세일 방법을 가져와서 saleList에 저장
		ArrayList<String> saleList = this.userNow.getSaleList();
		
		// 첫번쨰 for문에서는 유저가 사용가능한 세일 방법을 돌림
		for (int i = 0; i < saleList.size(); i++) {
			// 두번째 for문에서는 모든 세일 방법 리스트 안에서 getSaleWay에 적혀있는 세일 방법과 일치하는 세일 메뉴를 가져와서 결과값에 담음
			for (int j = 0; j < saleMenuAll.size(); j++) {
				if(saleList.get(i).equals((saleMenuAll.get(j).getSaleIndex()))) {
					result.add(saleMenuAll.get(j));
				}
			}
		}
		return result;
	}
	
	/**
	 * USER가 선택한 메뉴 내에서의 가격순 조합 발굴하여 반환.
	 * totalMerge()메소드가 사용하는 메소드.
	 * bringSale()메소드로 할인 정보를 가져와 sameMenuComPrice()메소드로 할인 종류별 적용시 가격비교, reduceMenu()메소드로 서브메뉴 제거한 조합 발굴.
	 * @author 김기홍
	 * */
	public ArrayList<ArrayList<SaleMenu>> basicMerge(ArrayList<Menu> userMenu){
		ArrayList<ArrayList<SaleMenu>> result = new ArrayList<>();
		ArrayList<SaleMenu> userSale = bringSale();
		ArrayList<SaleMenu> rePrMenu = sameMenuComPrice(userMenu, userSale);
		result.add(rePrMenu);
		ArrayList<ArrayList<SaleMenu>> reDuMenu = reduceMenu(userMenu, userSale);
		for(ArrayList<SaleMenu> as : reDuMenu){
			result.add(as);
		}
		return result;
	}
	
	/**
	 * USER가 선택한 메뉴의 완세트 최저가 조합 발굴하여 반환.
	 * basicMerge()메소드가 사용하는 메소드.
	 * priceMin()메소드 사용하여 완세트에 대한 할인 종류별 적용시 가격비교하여 최저가 완세트 조합 1개만 추출.
	 * @author 김기홍, 정동영
	 * */
	public ArrayList<SaleMenu> sameMenuComPrice(ArrayList<Menu> userMenu, ArrayList<SaleMenu> userSale){
		ArrayList<SaleMenu> result = new ArrayList<>();

		ArrayList<Menu> tempUserMenu = userMenu;
		ArrayList<SaleMenu> tempUserSale = userSale;
		
		// tempUserMenu에서 유저가 고른 메뉴를 스트링화해서 넣을 리스트 하나 생성
		ArrayList<String> selectedMenuStringList = new ArrayList<>();
		
		// 세일된 메뉴를 잘라서 저장할 리스트 생성
		ArrayList<String> userSaleMenuStringList = new ArrayList<>();
		
		// 유저가 고른거랑 세일중인 메뉴가 일치하는것을 담을 리스트
		ArrayList<SaleMenu> saleNominatedList = new ArrayList<>();
		
		// 노미네이트 리스트를 담을 리스트
		ArrayList<ArrayList<SaleMenu>> listOfSaleNomintatedList = new ArrayList<>();
		
		// 유저가 선택한 완세트 3개 조합 발굴
		//for(int totalRotation = 0; totalRotation<3; totalRotation++){
			// tempUserMenu에서 유저가 고른 메뉴를 스트링화해서 selectedMenuStringList에 저장
			for (int i = 0; i < tempUserMenu.size(); i++) {
				Menu tempMenu = tempUserMenu.get(i);
				String savingMenu =  tempMenu.getMenu();
				selectedMenuStringList.add(savingMenu);
			}
			
			// 유저가 할인 받을 수 있는 세일 리스트를 여기 포문에서 돌림
			// 거기서 어떤 단품메뉴항목인지 스트링으로 저장
			// +로 메뉴가 구분된 스트링 값은 split으로 잘라서 어레이에 저장
			// +는 그냥 스플릿으로 나눌 수 없음. "\\+"로만 가능
			for (int j = 0; j < tempUserSale.size(); j++) {
				
				String getsaleMenu = tempUserSale.get(j).getSaleMenu();
				String[] splitedSaleArray = getsaleMenu.split("\\+");
				
				// 여기 포문은 잘라져서 어레이에 저장된 각 메뉴를 saleMenuStringList에 저장
				// .trim()으로 혹시 모를 빈칸을 정리
				for (int k = 0; k < splitedSaleArray.length; k++) {
					userSaleMenuStringList.add(splitedSaleArray[k].trim());
				}
				
				//유저가 고른 메뉴의 스트링값 전부와 세일 메뉴 스트링 값 전부를 비교해서 유저>세일메뉴 면 아래 if문 실행
				if(selectedMenuStringList.containsAll(userSaleMenuStringList)) {
					// 사용자가 고른 메뉴가 전부 세일 메뉴 스트링 리스트에 있다면 해당 세일 메뉴를 비교할 리스트인 saleNominatedList에 넣기
					// 그리고 여기 포문은 saleNominatedList에 넣은 사용자 메뉴는 빼기
					saleNominatedList.add(tempUserSale.get(j));
					tempUserSale.set(j, null);
					
					for (int l = 0; l < userSaleMenuStringList.size(); l++) {
						for (int m = 0; m < selectedMenuStringList.size(); m++) {
							if ((userSaleMenuStringList.get(l)).equals(selectedMenuStringList.get(m))) {
								// 스트링 리스트를 지우면서 첨에 유저 셀렉트한 메뉴 객체있는 리스트도 같은 메뉴면 지워줘야됨
								selectedMenuStringList.remove(m);
							}
							break;
						}
					}
					userSaleMenuStringList.clear(); 
				} else if(tempUserSale.contains("영수증")){
					// 영수증 할인
					for(int a = 0; a<selectedMenuStringList.size(); a++){
						for(int b = 0; b<tempUserMenu.size(); b++){
							if(selectedMenuStringList.get(a).equals(tempUserMenu.get(b).getMenu())){
								if(tempUserMenu.get(b).getCategory().equals("burger")){
									saleNominatedList.add(new SaleMenu("1.1", "영수증 세트 업그레이드 쿠폰", selectedMenuStringList.get(a)+"+ 프렌치프라이 + 콜라", tempUserMenu.get(b).getPrice(), "17/01/01","17/12/31", "000000", "240000"));
									tempUserSale.set(j, null);
									selectedMenuStringList.remove(selectedMenuStringList.get(a));			
								}
							}
						}
					}
				} else if(userSaleMenuStringList.contains("올마이쇼핑카드")){
					System.out.println("올마이쇼핑카드 들어옴");
					for(int a = 0; a<selectedMenuStringList.size(); a++){
						for(int b = 0; b<tempUserMenu.size(); b++){
							if(selectedMenuStringList.get(a).equals(tempUserMenu.get(b).getMenu())){
								saleNominatedList.add(new SaleMenu("2.1", "올마이쇼핑카드(점심)", selectedMenuStringList.get(a), tempUserMenu.get(b).getPrice()*0.9, "17/01/01","17/12/31", "120000", "240000"));
								tempUserSale.set(j, null);
								selectedMenuStringList.remove(selectedMenuStringList.get(a));	
							}
						}
					}
				} else if(userSaleMenuStringList.contains("현대카드ZERO")){
					System.out.println("현대카드ZERO 들어옴");
					for(int a = 0; a<selectedMenuStringList.size(); a++){
						for(int b = 0; b<tempUserMenu.size(); b++){
							if(selectedMenuStringList.get(a).equals(tempUserMenu.get(b).getMenu())){
								saleNominatedList.add(new SaleMenu("2.2", "현대카드ZERO", selectedMenuStringList.get(a), tempUserMenu.get(b).getPrice()*0.988, "17/01/01","17/12/31", "000000", "240000"));
								tempUserSale.set(j, null);
								selectedMenuStringList.remove(selectedMenuStringList.get(a));	
							}
						}
					}
				} else if(userSaleMenuStringList.contains("콜라") && tempUserMenu.size() > 1 && selectedMenuStringList.size() == 1){
					System.out.println("콜라 드러옴");
					saleNominatedList.add(new SaleMenu("4.4", "(공식) 버거킹 BURGERKINGKOREA", "콜라", 0, "17/05/15","17/05/31", "100000", "220000"));
					tempUserSale.set(j, null);
					selectedMenuStringList.clear();
				} else if(j == tempUserSale.size()-1){
					for(int a = 0; a<selectedMenuStringList.size(); a++){
						for(int b = 0; b<tempUserMenu.size(); b++){
							if(selectedMenuStringList.get(a).equals(tempUserMenu.get(b).getMenu())){
								saleNominatedList.add(new SaleMenu(a+"", "적용 할인 없음", selectedMenuStringList.get(a), tempUserMenu.get(b).getPrice(), "17/01/01","17/12/31", "000000", "240000"));
							}
						}
					}
					selectedMenuStringList.clear();
				}
				userSaleMenuStringList.clear();
			}
			listOfSaleNomintatedList.add(saleNominatedList);
			selectedMenuStringList.clear();
			
		result = priceMin(listOfSaleNomintatedList);
		return result;
	}
	
	/**
	 * USER가 선택한 메뉴의 완세트에서 사이드 메뉴를 최대 3개까지 뺀 메뉴의 최저가 조합 발굴하여 반환.
	 * basicMerge()메소드가 사용하는 메소드.
	 * 사이드 메뉴를 최대 3개까지 뺀 메뉴를 각각 sameMenuComPrice()메소드에 대입하여, 사이드 메뉴가 제거된 새로운 메뉴에 대한 최저가 조합 발굴하여 반환.
	 * 이때, 카테고리가 burger인 메뉴는 제거하지 않고 항상 포함되도록 조건식 설정.
	 * @author 김기홍
	 * */
	public ArrayList<ArrayList<SaleMenu>> reduceMenu(ArrayList<Menu> userMenu, ArrayList<SaleMenu> userSale){
		ArrayList<ArrayList<SaleMenu>> result = new ArrayList<>();
	
		for(int reduceOne = 0; reduceOne < userMenu.size(); reduceOne++){
			ArrayList<Menu> userMenuReduceElse1 = userMenu;
			if(!(userMenuReduceElse1.get(reduceOne).getCategory().equals("burger"))){
				userMenuReduceElse1.set(reduceOne, new Menu("", 0, "", ""));
				result.add(sameMenuComPrice(userMenuReduceElse1, userSale));
				for(int reduceTwo = 0; reduceTwo < userMenuReduceElse1.size(); reduceTwo++){
					ArrayList<Menu> userMenuReduceElse2 = userMenuReduceElse1;
					if(!(userMenuReduceElse2.get(reduceTwo).getCategory().equals("burger"))){
						userMenuReduceElse2.set(reduceTwo, new Menu("", 0, "", ""));
						result.add(sameMenuComPrice(userMenuReduceElse2, userSale));
						for(int reduceThree = 0; reduceThree < userMenuReduceElse2.size(); reduceThree++){
							ArrayList<Menu> userMenuReduceElse3 = userMenuReduceElse2;
							if(!(userMenuReduceElse3.get(reduceThree).getCategory().equals("burger"))){
								userMenuReduceElse3.set(reduceThree, new Menu("", 0, "", ""));
								result.add(sameMenuComPrice(userMenuReduceElse3, userSale));
						    }
						}
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * USER가 선택한 메뉴의 대안이 되는 최저가 유사 조합 발굴하여 반환.
	 * totalMerge()메소드가 사용하는 메소드.
	 * 같은 카테고리, 같은 주재료, 유사 가격(이하 500원까지) 기준으로 다른 메뉴 조합 2개(해당 메소드 호출을 2회 반복) 발굴.
	 * @author 김기홍
	 * */
	public ArrayList<ArrayList<SaleMenu>> altMerge(ArrayList<Menu> userMenu){
		ArrayList<Menu> menuAllTemp = menuAll;
		ArrayList<Menu> difMenu = new ArrayList<>();
		ArrayList<ArrayList<SaleMenu>> result = new ArrayList<>();
		for(Menu m : userMenu){
			System.out.println(m.getMenu());
			for(Menu ma : menuAllTemp){
				System.out.println(ma.getMenu());
				if(ma.getCategory().equals(m.getCategory()) && ma.getMainRecipe().equals(m.getMainRecipe()) 
						&& (m.getPrice() >= ma.getPrice() && ma.getPrice() >= m.getPrice()-500) 
						&& !(m.getMenu().equals(ma.getMenu()))){
					System.out.println(ma);
					if(difMenu.add(ma)){
						menuAllTemp.remove(ma);
						break;
					}
				}
			}
		}
		result = basicMerge(difMenu);
		System.out.println(result);
		System.out.println(difMenu);
		difMenu.clear();
		System.out.println(difMenu);
		return result;
	}
	
	/**
	 * 종합 최저가 조합 메뉴 발굴
	 * UI의 Menu()메소드 창에서 USER가 선택한 단품 메뉴들이 MenuSelected()메소드 창에 뜬걸 USER가 해당 조합을 구매하기로 결정을 누르면 호출.
	 * USER가 선택한 조합을 기초로하여 Manager클래스의 basicMerge(), altMerge()메소드를 호출하여 각각의 리스트를 반환 받음.
	 * 이를 priceSort()메소드 활용하여 가격순으로 6순위까지 종합한 최종 최저가 조합 생성.
	 * @author 김기홍
	 * */
	public ArrayList<ArrayList<SaleMenu>> totalMerge(){
		ArrayList<ArrayList<SaleMenu>> result = new ArrayList<>();
		System.out.println(userMenu);
		ArrayList<ArrayList<SaleMenu>> bsa = this.basicMerge(userMenu);
		
		for(ArrayList<SaleMenu> bs : bsa){
			result.add(bs);
		}
		
		for(int i=0; i<2; i++){
		ArrayList<ArrayList<SaleMenu>> asa = this.altMerge(userMenu);
		
			for(ArrayList<SaleMenu> as : asa){
				result.add(as);
			}
		}
		result = priceSort(result);
		return result;
	}
	
	/**
	 * 가격순 정렬 메소드.
	 * '선택정렬' 방식 활용.
	 * @author 김기홍
	 * */
	public ArrayList<ArrayList<SaleMenu>> priceSort(ArrayList<ArrayList<SaleMenu>> result){
		int salePrice1 = 0;
		int salePrice2 = 0;
		ArrayList<SaleMenu> temp = new ArrayList<>();
		ArrayList<ArrayList<SaleMenu>> result2 = new ArrayList<>();
		for(int i = 0; i<result.size()-1; i++){
			for(SaleMenu s1 : result.get(i)){
				salePrice1 += s1.getSalePrice();
			}
			for(int j = i+1; j<result.size(); j++){
				for(SaleMenu s2 : result.get(j)){
					salePrice2 += s2.getSalePrice();
				}
				
				if(salePrice1 > salePrice2){
					temp = result.get(i);
					result.set(i, result.get(j));
					result.set(j, temp);
				}
				salePrice2 = 0;
			}
			salePrice1 = 0;
		}
		if(result.size() >= 6){
			for(int i = 0; i < 6; i++){
				result2.add(result.get(i));
			}
		}else{
			for(int i = 0; i < result.size(); i++){
				result2.add(result.get(i));
			}
		}
		return result2;
	}
	
	/**
	 * 최저가 조합 발굴 메소드.
	 * @author 김기홍
	 * */
	public ArrayList<SaleMenu> priceMin(ArrayList<ArrayList<SaleMenu>> result){
		int paraPrice = 999999999;
		int minPrice = 0;
		for(int i = 0; i<result.size(); i++){ 
			for(int j = 0; j<result.get(i).size(); j++){
				minPrice += result.get(i).get(j).getSalePrice();
			}
			if(minPrice < paraPrice){
				paraPrice = minPrice;
				result.add(0, result.get(i));
				result.remove(result.get(i+1));
			}
		}
		return result.get(0);
	}
	
	/**
	 * DBManager클래스의 selectUserAllDB(), selectMenuAllDB(), selectSaleAllDB()메소드를 호출하여, DB에 저장된 모든 User, Menu, Sale 정보를 가져와 member변수에 대입하고, 
	 * 그 중에 모든 Menu, Sale 정보를 Client쪽으로 반환하는 메소드들.
	 * @author 김기홍
	 * */
	public void selectUserAll(){
		userAll = dbm.selectUserAllDB();
	}
	
	public ArrayList<Menu> selectMenuAll(){
		ArrayList<Menu> result = new ArrayList<>();
		menuAll = dbm.selectMenuAllDB();
		result = menuAll;
		return result;
	}
	
	public ArrayList<SaleMenu> selectSaleAll(){
		ArrayList<SaleMenu> result = new ArrayList<>();
		saleMenuAll = dbm.selectSaleAllDB();
		result = saleMenuAll;
		return result;
	}
	
}
