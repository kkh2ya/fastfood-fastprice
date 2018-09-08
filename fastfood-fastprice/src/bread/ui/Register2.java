package bread.ui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import bread.client.ClientManager;
import bread.vo.User;

/**
 * 회원정보수정 UI 클래스.
 * @author 정동영
 * */
public class Register2 extends JFrame {
	

	private JPanel contentPane;
	private JTextField textFieldName;
	private JTextField textFieldBirthday;
	private JTextField textFieldID;
	private JPasswordField passwordField;
	
	private ClientManager cm = new ClientManager();
	
	private JCheckBox cb0;
	private JCheckBox cb1;
	private JCheckBox cb2;
	private JCheckBox cb3;
	private JCheckBox cb4;
	private JCheckBox cb5;
	private JCheckBox cb6;
	private JCheckBox cb7;
	private JCheckBox cb8;
	private JCheckBox cb9;
	private JCheckBox cb10;
	private JCheckBox cb11;
	private JCheckBox cb12;
	private JCheckBox cb13;
	private JCheckBox cb14;
	private JCheckBox cb15;
	private JCheckBox cb16;
	private JCheckBox cb17;
	private JCheckBox cb18;
	private JCheckBox cb19;
	private JCheckBox cb20;
	private JCheckBox cb21;
	private JCheckBox cb22;
	private JCheckBox cb23;
	private JCheckBox cb24;
	private JCheckBox cb25;
	private JCheckBox cb26;
	private JCheckBox cb27;
	private JCheckBox cb28;
	private JCheckBox cb29;
	private JCheckBox cb30;
	private JCheckBox cb31;
	private JCheckBox cb32;
	private JCheckBox cb33;
	private JPanel panel_10;
	private JButton confirm;
	private JButton setClear;
	
	private User register2User;


	/**
	 * Create the frame.
	 */
	public Register2(User user) {
		register2User = user;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel mainPanel = new JPanel();
		contentPane.add(mainPanel);
		mainPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel basicInfoPanel = new JPanel();
		mainPanel.add(basicInfoPanel);
		basicInfoPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel namePanel = new JPanel();
		basicInfoPanel.add(namePanel);
		namePanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panel = new JPanel();
		namePanel.add(panel);
		
		JLabel nameLabel = new JLabel("이름");
		panel.add(nameLabel);
		
		JPanel panel_4 = new JPanel();
		namePanel.add(panel_4);
		
		textFieldName = new JTextField();
		textFieldName.setText(register2User.getName());
		panel_4.add(textFieldName);
		textFieldName.setColumns(10);
		
		JPanel birthdayPanel = new JPanel();
		basicInfoPanel.add(birthdayPanel);
		birthdayPanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panel_1 = new JPanel();
		birthdayPanel.add(panel_1);
		
		JLabel lblNewLabel = new JLabel("생일 (ex:1990/01/01)");
		panel_1.add(lblNewLabel);
		
		JPanel panel_5 = new JPanel();
		birthdayPanel.add(panel_5);
		
		textFieldBirthday = new JTextField();
		textFieldBirthday.setText(register2User.getBirthday());
		panel_5.add(textFieldBirthday);
		textFieldBirthday.setColumns(10);
		
		JPanel idPanel = new JPanel();
		basicInfoPanel.add(idPanel);
		idPanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panel_2 = new JPanel();
		idPanel.add(panel_2);
		
		JLabel idLabel = new JLabel("아이디");
		panel_2.add(idLabel);
		
		JPanel panel_6 = new JPanel();
		idPanel.add(panel_6);
		
		textFieldID = new JTextField();
		textFieldID.setText(register2User.getId());
		textFieldID.setEditable(false);
		panel_6.add(textFieldID);
		textFieldID.setColumns(10);
		
		JPanel pwdPanel = new JPanel();
		basicInfoPanel.add(pwdPanel);
		pwdPanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panel_3 = new JPanel();
		pwdPanel.add(panel_3);
		
		JLabel pwdLabel = new JLabel("패스워드");
		panel_3.add(pwdLabel);
		
		JPanel panel_7 = new JPanel();
		pwdPanel.add(panel_7);
		
		passwordField = new JPasswordField();
		passwordField.setColumns(10);
		panel_7.add(passwordField);
		
		JPanel salePartPanel = new JPanel();
		mainPanel.add(salePartPanel);
		salePartPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_8 = new JPanel();
		salePartPanel.add(panel_8);
		panel_8.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_9 = new JPanel();
		panel_8.add(panel_9);
		panel_9.setLayout(new BoxLayout(panel_9, BoxLayout.X_AXIS));
		
		JPanel panel_12 = new JPanel();
		panel_9.add(panel_12);
		panel_12.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel saleLable = new JLabel("세일항목");
		saleLable.setHorizontalAlignment(SwingConstants.CENTER);
		saleLable.setVerticalAlignment(SwingConstants.TOP);
		panel_12.add(saleLable);
		
		panel_10 = new JPanel();
		panel_12.add(panel_10);
		panel_10.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		confirm = new JButton("확인");
		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				registerUser();
				dispose();
			}
		});
		panel_10.add(confirm);
		
		setClear = new JButton("초기화");
		setClear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				textFieldName.setText("");
				textFieldBirthday.setText("");
				passwordField.setText("");
			}
		});
		
		panel_10.add(setClear);
		
		JPanel jpanel = new JPanel();
		jpanel.setPreferredSize(new Dimension(100, 850));
		cb0 = new JCheckBox("1.1 | 영수증 세트 업그레이드 쿠폰 | 단품 구매 시 세트 업그레이드 쿠폰 | 0");
		cb1 = new JCheckBox("2.1 | 올마이쇼핑카드(점심) | 점심시간 음식점(버거킹 포함) 10% 청구할인 | 0");
		cb2 = new JCheckBox("2.2 | 현대카드ZERO | 외식 : 모든 외식업종(버거킹 포함) 이용금액 1.2% 할인 | 0");
		cb3 = new JCheckBox("3.1 | 시럽월렛 | 와퍼 + 콜라 | 4500");
		cb4 = new JCheckBox("3.2 | 시럽월렛 | 치즈와퍼 + 콜라 | 5000");
		cb5 = new JCheckBox("3.3 | 시럽월렛 | 와퍼주니어 + 와퍼주니어 + 콜라 + 콜라 | 6000");
		cb6 = new JCheckBox("3.4 | 시럽월렛 | 콰트로치즈와퍼 + BLT롱치킨버거 + 콜라 + 콜라 | 10000");
		cb7 = new JCheckBox("3.5 | 시럽월렛 | 갈릭스테이크버거 + 콜라 | 5500");
		cb8 = new JCheckBox("3.6 | 시럽월렛 | 치즈프라이 | 2000");
		cb9 = new JCheckBox("3.7 | 시럽월렛 | 크런치치킨버거 + 콜라 | 3900");
		cb10 = new JCheckBox("3.8 | 시럽월렛 | 아이스아메리카노 | 1000");
		cb11 = new JCheckBox("3.9 | 시럽월렛 | 컵아이스크림 | 500");
		cb12 = new JCheckBox("3.10 | 시럽월렛 | 아메리카노(Hot) | 1000");
		cb13 = new JCheckBox("4.1 | (공식) 버거킹 BURGERKINGKOREA | 불고기버거 | 1000");
		cb14 = new JCheckBox("4.2 | (공식) 버거킹 BURGERKINGKOREA | 와퍼주니어 | 2000");
		cb15 = new JCheckBox("4.3 | (공식) 버거킹 BURGERKINGKOREA | 와퍼 + 프렌치프라이 + 콜라 | 4900");
		cb16 = new JCheckBox("4.4 | (공식) 버거킹 BURGERKINGKOREA | 콜라 | 0");
		cb17 = new JCheckBox("4.5 | (공식) 버거킹 BURGERKINGKOREA | 너겟킹 + 너겟킹 + 너겟킹 + 너겟킹 + 너겟킹 + 너겟킹 + 너겟킹 + 너겟킹 + 너겟킹 + 너겟킹 | 1500");
		cb18 = new JCheckBox("4.6 | (공식) 버거킹 BURGERKINGKOREA | 콰트로치즈와퍼주니어 + 프렌치프라이 + 콜라 | 4500");
		cb19 = new JCheckBox("4.7 | (공식) 버거킹 BURGERKINGKOREA | 갈릭스테이크버거 + 망고젤리에이드 | 5500");
		cb20 = new JCheckBox("4.8 | (공식) 버거킹 BURGERKINGKOREA | 콰트로치즈와퍼 + 통새우와퍼주니어 + 콜라 + 콜라 + 프렌치프라이 | 10000");
		cb21 = new JCheckBox("4.9 | (공식) 버거킹 BURGERKINGKOREA | 프렌치프라이 + 바닐라선데 | 1500");
		cb22 = new JCheckBox("4.10 | (공식) 버거킹 BURGERKINGKOREA | 아이스아메리카노 | 1000");
		cb23 = new JCheckBox("5.1 | 버거킹이 드리는 감사의 달 선물 | 콰트로치즈와퍼 + 치즈와퍼 + 콜라  + 콜라 | 10000");
		cb24 = new JCheckBox("5.2 | 버거킹이 드리는 감사의 달 선물 | 갈릭스테이크버거 + 치즈와퍼 + 콜라  + 콜라 | 10000");
		cb25 = new JCheckBox("5.3 | 버거킹이 드리는 감사의 달 선물 | 콰트로치즈와퍼 + BLT롱치킨버거 + 콜라 + 콜라 | 10000");
		cb26 = new JCheckBox("5.4 | 버거킹이 드리는 감사의 달 선물 | X-TRA크런치치킨 + 콜라 | 4500");
		cb27 = new JCheckBox("5.5 | 버거킹이 드리는 감사의 달 선물 | 치킨프라이 + 쉬림프치킨프라이 | 3000");
		cb28 = new JCheckBox("5.6 | 버거킹이 드리는 감사의 달 선물 | 와퍼주니어 + 콜라 | 3900");
		cb29 = new JCheckBox("5.7 | 버거킹이 드리는 감사의 달 선물 | 불고기버거 | 1500");
		cb30 = new JCheckBox("5.8 | 버거킹이 드리는 감사의 달 선물 | 아메리카노 | 1000");
		cb31 = new JCheckBox("5.9 | 버거킹이 드리는 감사의 달 선물 | 컵아이스크림 | 500");
 
		JScrollPane scrollPane = new JScrollPane(jpanel);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		jpanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		jpanel.add(cb0);
		jpanel.add(cb1);
		jpanel.add(cb2);
		jpanel.add(cb3);
		jpanel.add(cb4);
		jpanel.add(cb5);
		jpanel.add(cb6);
		jpanel.add(cb7);
		jpanel.add(cb8);
		jpanel.add(cb9);
		jpanel.add(cb10);
		jpanel.add(cb11);
		jpanel.add(cb12);
		jpanel.add(cb13);
		jpanel.add(cb14);
		jpanel.add(cb15);
		jpanel.add(cb16);
		jpanel.add(cb17);
		jpanel.add(cb18);
		jpanel.add(cb19);
		jpanel.add(cb20);
		jpanel.add(cb21);
		jpanel.add(cb22);
		jpanel.add(cb23);
		jpanel.add(cb24);
		jpanel.add(cb25);
		jpanel.add(cb26);
		jpanel.add(cb27);
		jpanel.add(cb28);
		jpanel.add(cb29);
		jpanel.add(cb30);
		jpanel.add(cb31);
		salePartPanel.add(scrollPane);
		
		setVisible(true);
	}

	public void registerUser() {
		String usrName = textFieldName.getText();
		String birthday = textFieldBirthday.getText();
		String id = textFieldID.getText();
		String password = "";
		char tempPwd[] = passwordField.getPassword();
		for (int i = 0; i < tempPwd.length; i++) {
			password += "" + tempPwd[i]; 
		}
		ArrayList<String> saleRegister = new ArrayList<>();
		if (cb0.isSelected()) {
			String check0 = refineNumber(cb0.getText());
			saleRegister.add(check0);
		}
		if (cb1.isSelected()) {
			String check1 = refineNumber(cb1.getText());
			saleRegister.add(check1);
			
		}
		if (cb2.isSelected()) {
			String check2 = refineNumber(cb2.getText());
			saleRegister.add(check2);
		}
		if (cb3.isSelected()) {
			String check3 = refineNumber(cb3.getText());
			saleRegister.add(check3);
		}
		if (cb4.isSelected()) {
			String check4 = refineNumber(cb4.getText());
			saleRegister.add(check4);
		}
		if (cb5.isSelected()) {
			String check5 = refineNumber(cb5.getText());
			saleRegister.add(check5);
		}
		if (cb6.isSelected()) {
			String check6 = refineNumber(cb6.getText());
			saleRegister.add(check6);
		}
		if (cb7.isSelected()) {
			String check7 = refineNumber(cb7.getText());
			saleRegister.add(check7);
		}
		if (cb8.isSelected()) {
			String check8 = refineNumber(cb8.getText());
			saleRegister.add(check8);
		}
		if (cb9.isSelected()) {
			String check9 = refineNumber(cb9.getText());
			saleRegister.add(check9);
		}
		if (cb10.isSelected()) {
			String check10 = refineNumber(cb10.getText());
			saleRegister.add(check10);
		}
		if (cb11.isSelected()) {
			String check11 = refineNumber(cb11.getText());
			saleRegister.add(check11);
		}
		if (cb12.isSelected()) {
			String check12 = refineNumber(cb12.getText());
			saleRegister.add(check12);
		}
		if (cb13.isSelected()) {
			String check13 = refineNumber(cb13.getText());
			saleRegister.add(check13);
		}
		if (cb14.isSelected()) {
			String check14 = refineNumber(cb14.getText());
			saleRegister.add(check14);
		}
		if (cb15.isSelected()) {
			String check15 = refineNumber(cb15.getText());
			saleRegister.add(check15);
		}
		if (cb16.isSelected()) {
			String check16 = refineNumber(cb16.getText());
			saleRegister.add(check16);
		}
		if (cb17.isSelected()) {
			String check17 = refineNumber(cb17.getText());
			saleRegister.add(check17);
		}
		if (cb18.isSelected()) {
			String check18 = refineNumber(cb18.getText());
			saleRegister.add(check18);
		}
		if (cb19.isSelected()) {
			String check19 = refineNumber(cb19.getText());
			saleRegister.add(check19);
		}
		if (cb20.isSelected()) {
			String check20 = refineNumber(cb20.getText());
			saleRegister.add(check20);
		}
		if (cb21.isSelected()) {
			String check21 = refineNumber(cb21.getText());
			saleRegister.add(check21);
		}
		if (cb22.isSelected()) {
			String check22 = refineNumber(cb22.getText());
			saleRegister.add(check22);
		}
		if (cb23.isSelected()) {
			String check23 = refineNumber(cb23.getText());
			saleRegister.add(check23);
		}
		if (cb24.isSelected()) {
			String check24 = refineNumber(cb24.getText());
			saleRegister.add(check24);
		}
		if (cb25.isSelected()) {
			String check25 = refineNumber(cb25.getText());
			saleRegister.add(check25);
		}
		if (cb26.isSelected()) {
			String check26 = refineNumber(cb26.getText());
			saleRegister.add(check26);
		}
		if (cb27.isSelected()) {
			String check27 = refineNumber(cb27.getText());
			saleRegister.add(check27);
		}
		if (cb28.isSelected()) {
			String check28 = refineNumber(cb28.getText());
			saleRegister.add(check28);
		}
		if (cb29.isSelected()) {
			String check29 = refineNumber(cb29.getText());
			saleRegister.add(check29);
		}
		if (cb30.isSelected()) {
			String check30 = refineNumber(cb30.getText());
			saleRegister.add(check30);
		}
		if (cb31.isSelected()) {
			String check31 = refineNumber(cb31.getText());
			saleRegister.add(check31);
		}
		
		User usr = new User(usrName, birthday, id, password, saleRegister);
		
		cm.userRegistration2(usr);
	}
	
	public String refineNumber(String cbText) {
		String refinedNumber = "";
		String[] splited = cbText.split(" | ");
		refinedNumber = splited[0];
		return refinedNumber;
	}

}
