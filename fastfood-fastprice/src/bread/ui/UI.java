package bread.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.synth.SynthSeparatorUI;

import bread.client.ClientManager;
import bread.client.ClientSocketManager;
import bread.vo.Menu;
import bread.vo.SaleMenu;
import bread.vo.User;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.FlowLayout;

/**
 * 메인 UI 클래스.
 * @author 정동영
 * */
public class UI extends JFrame {

	private JPanel contentPane;
	private JTextArea tf_recommend;
	
	// 메인패널
	private	JPanel mainPanel;
	
	// 채팅관련
	private JPanel allChatPanel;
	private JTextArea ta_printChatTextArea;
	private JTextField ta_InputChatTextArea;
	private JPanel printChatPanel;
	private JScrollPane scrollInputChatPanel;
	private JPanel sendBtnPanel;
	private JButton sendBtn;
	
	// 매니저 호출
	private ClientSocketManager csm;
	private ClientManager cm = new ClientManager();
	
	// 메인메뉴 패널
	private JScrollPane mainMenuScrollPane;
	private JPanel panelInmmsp;

	// 메뉴 카테고리 별 버튼리스트
	private ArrayList<JButton> burgerMenuBtnList = new ArrayList<>();
	private ArrayList<JButton> drinkMenuBtnList = new ArrayList<>();
	private ArrayList<JButton> hotsnackMenuBtnList = new ArrayList<>();
	private ArrayList<JButton> coldsnackMenuBtnList = new ArrayList<>();
	
	private ArrayList<JButton> allMenuBtnList = new ArrayList<>();
	private ArrayList<JButton> selectedMenuBtnList = new ArrayList<>();
	
	// 기타 어레이리스트
	private ArrayList<Menu> menuAll = new ArrayList<>();
	private ArrayList<SaleMenu> saleAll = new ArrayList<>();
	private ArrayList<String> menuSelected = new ArrayList<>();
	private ArrayList<String> menuTotal1 = new ArrayList<>();
	private ArrayList<ArrayList<String>> menuTotal2 = new ArrayList<>();
	
	// 선택메뉴 패널
	private JPanel selectMenuPanel;
	private JButton modifyInfo;
	
	// 유저
	private User uiUser;
	
	// 메뉴들 변수 선언

	/**
	 * Create the frame.
	 */
	public UI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(SystemColor.activeCaption);
		setContentPane(contentPane);
		setTitle("버거킹 메뉴 선택 프로그램에 오신것을 환영합니다");
		
		mainPanel = new JPanel();
		mainPanel.setBackground(SystemColor.activeCaption);
		
		allChatPanel = new JPanel();
		allChatPanel.setBackground(SystemColor.activeCaption);
		
		printChatPanel = new JPanel();
		JScrollPane scrollPrintChatPanel = new JScrollPane(printChatPanel);
		scrollPrintChatPanel.setHorizontalScrollBarPolicy(scrollPrintChatPanel.HORIZONTAL_SCROLLBAR_NEVER);
		printChatPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		ta_printChatTextArea = new JTextArea();
		ta_printChatTextArea.setFont(new Font("HY나무L", Font.PLAIN, 20));
		ta_printChatTextArea.setEditable(false);
		ta_printChatTextArea.setLineWrap(true);
		printChatPanel.add(ta_printChatTextArea);
		
		ta_InputChatTextArea = new JTextField();
		ta_InputChatTextArea.setFont(new Font("HY나무L", Font.PLAIN, 20));
		scrollInputChatPanel = new JScrollPane(ta_InputChatTextArea);
		scrollInputChatPanel.setHorizontalScrollBarPolicy(scrollInputChatPanel.HORIZONTAL_SCROLLBAR_NEVER);
		
		sendBtnPanel = new JPanel();
		sendBtnPanel.setLayout(new GridLayout(0, 1, 0, 0));
		GroupLayout gl_allChatPanel = new GroupLayout(allChatPanel);
		gl_allChatPanel.setHorizontalGroup(
			gl_allChatPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_allChatPanel.createSequentialGroup()
					.addGroup(gl_allChatPanel.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(sendBtnPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(scrollInputChatPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(scrollPrintChatPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE))
					.addContainerGap(0, Short.MAX_VALUE))
		);
		gl_allChatPanel.setVerticalGroup(
			gl_allChatPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_allChatPanel.createSequentialGroup()
					.addComponent(scrollPrintChatPanel, GroupLayout.PREFERRED_SIZE, 518, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollInputChatPanel, GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(sendBtnPanel, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addGap(0))
		);
		
		sendBtn = new JButton("SEND");
		sendBtn.setContentAreaFilled(false);
		sendBtn.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		sendBtnPanel.add(sendBtn);
		
		// 프로그램 실행하면 채팅 소켓 만들고 실행
		csm = new ClientSocketManager(this);
		sendBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String message = ta_InputChatTextArea.getText();
				
				if (message.isEmpty()) {
					return;
				}
				csm.sendMessage(message);
				ta_InputChatTextArea.setText("");
			}
		});
		
		// 채팅창에서 엔터키 눌러도 SEND버튼 사용 
		ta_InputChatTextArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					sendBtn.doClick();
				}
			}
		});
		
		scrollPrintChatPanel.setHorizontalScrollBarPolicy( scrollPrintChatPanel.HORIZONTAL_SCROLLBAR_NEVER);
		
		allChatPanel.setLayout(gl_allChatPanel);
		
		JPanel allSelectionPanel = new JPanel();
		allSelectionPanel.setBackground(SystemColor.activeCaption);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(allSelectionPanel, GroupLayout.DEFAULT_SIZE, 957, Short.MAX_VALUE)
						.addComponent(mainPanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 957, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(allChatPanel, GroupLayout.PREFERRED_SIZE, 287, GroupLayout.PREFERRED_SIZE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(allChatPanel, 0, 0, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(mainPanel, GroupLayout.PREFERRED_SIZE, 517, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(allSelectionPanel, GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGap(1))
		);
		
		tf_recommend = new JTextArea();
		tf_recommend.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 15));
		JScrollPane recommendPanel = new JScrollPane(tf_recommend);
		
		// 여기에 할인 내역 추가
		recommendBoard();
		tf_recommend.setEditable(false);
		tf_recommend.setColumns(10);
		
		JPanel mainMenu = new JPanel();
		mainMenu.setBackground(SystemColor.activeCaption);
		
		GroupLayout gl_mainPanel = new GroupLayout(mainPanel);
		gl_mainPanel.setHorizontalGroup(
			gl_mainPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(recommendPanel, GroupLayout.DEFAULT_SIZE, 957, Short.MAX_VALUE)
				.addComponent(mainMenu, GroupLayout.PREFERRED_SIZE, 957, Short.MAX_VALUE)
		);
		gl_mainPanel.setVerticalGroup(
			gl_mainPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_mainPanel.createSequentialGroup()
					.addComponent(recommendPanel, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(mainMenu, GroupLayout.PREFERRED_SIZE, 476, GroupLayout.PREFERRED_SIZE)
					.addGap(1))
		);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(SystemColor.controlHighlight);
		panel_3.setLayout(new GridLayout(0, 1, 0, 0));
		JLabel labelTitleMenu = new JLabel(" 전체 메뉴");
		labelTitleMenu.setFont(new Font("굵은안상수체", Font.PLAIN, 25));
		panel_3.add(labelTitleMenu);
		
		panelInmmsp = new JPanel();
		// 자꾸 옆으로 벌어지는거 방지
		panelInmmsp.setPreferredSize(new Dimension(800, 3000));
		mainMenuScrollPane = new JScrollPane(panelInmmsp);
		panelInmmsp.setLayout(new GridLayout(20, 0, 0, 0));
		
		//메뉴 불러오자
		menu();
		
		GroupLayout gl_mainMenu = new GroupLayout(mainMenu);
		gl_mainMenu.setHorizontalGroup(
			gl_mainMenu.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_mainMenu.createSequentialGroup()
					.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(mainMenuScrollPane, GroupLayout.DEFAULT_SIZE, 865, Short.MAX_VALUE))
		);
		gl_mainMenu.setVerticalGroup(
			gl_mainMenu.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_mainMenu.createSequentialGroup()
					.addGroup(gl_mainMenu.createParallelGroup(Alignment.TRAILING)
						.addComponent(mainMenuScrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
						.addComponent(panel_3, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 476, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		mainMenu.setLayout(gl_mainMenu);
		mainPanel.setLayout(gl_mainPanel);
		
		JPanel selectionPanel = new JPanel();
		selectionPanel.setBackground(SystemColor.activeCaption);
		
		JPanel finalDecisionPanel = new JPanel();
		finalDecisionPanel.setBackground(SystemColor.activeCaption);
		GroupLayout gl_allSelectionPanel = new GroupLayout(allSelectionPanel);
		gl_allSelectionPanel.setHorizontalGroup(
			gl_allSelectionPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_allSelectionPanel.createSequentialGroup()
					.addComponent(selectionPanel, GroupLayout.PREFERRED_SIZE, 755, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(finalDecisionPanel, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE))
		);
		gl_allSelectionPanel.setVerticalGroup(
			gl_allSelectionPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(finalDecisionPanel, GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
				.addComponent(selectionPanel, GroupLayout.PREFERRED_SIZE, 213, Short.MAX_VALUE)
		);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.controlHighlight);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		JLabel labelTitle = new JLabel("  고른 메뉴");
		labelTitle.setFont(new Font("굵은안상수체", Font.PLAIN, 25));
		panel_1.add(labelTitle);
		
		selectMenuPanel = new JPanel();
		selectMenuPanel.setBackground(SystemColor.control);
		
		JScrollPane selectMenuScrollPane = new JScrollPane(selectMenuPanel);
		
		GroupLayout gl_selectionPanel = new GroupLayout(selectionPanel);
		gl_selectionPanel.setHorizontalGroup(
			gl_selectionPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_selectionPanel.createSequentialGroup()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(selectMenuScrollPane, GroupLayout.DEFAULT_SIZE, 666, Short.MAX_VALUE))
		);
		gl_selectionPanel.setVerticalGroup(
			gl_selectionPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_selectionPanel.createSequentialGroup()
					.addGroup(gl_selectionPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 213, GroupLayout.PREFERRED_SIZE)
						.addComponent(selectMenuScrollPane, GroupLayout.PREFERRED_SIZE, 213, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		selectionPanel.setLayout(gl_selectionPanel);
		
		JPanel panel_6 = new JPanel();
		panel_6.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel confirmBtnPanel = new JPanel();
		confirmBtnPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton confirmBtn = new JButton("결정");
		confirmBtn.setContentAreaFilled(false);
		confirmBtn.setFont(new Font("굵은안상수체", Font.PLAIN, 24));
		confirmBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				JOptionPane.showMessageDialog(null, "두근두근 뭐가 나올까요");
				finalUI finalUI = new finalUI();
				dispose();
			}
		});
		confirmBtnPanel.add(confirmBtn);
		
		GroupLayout gl_finalDecisionPanel = new GroupLayout(finalDecisionPanel);
		gl_finalDecisionPanel.setHorizontalGroup(
			gl_finalDecisionPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(confirmBtnPanel, GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
				.addComponent(panel_6, GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
		);
		gl_finalDecisionPanel.setVerticalGroup(
			gl_finalDecisionPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_finalDecisionPanel.createSequentialGroup()
					.addComponent(panel_6, GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(confirmBtnPanel, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE))
		);
		
		modifyInfo = new JButton("회원정보수정");
		modifyInfo.setContentAreaFilled(false);
		modifyInfo.setFont(new Font("굵은안상수체", Font.PLAIN, 24));
		modifyInfo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Register2 secondRegister = new Register2(uiUser);
			}
		});
		panel_6.add(modifyInfo);
		finalDecisionPanel.setLayout(gl_finalDecisionPanel);
		allSelectionPanel.setLayout(gl_allSelectionPanel);
		contentPane.setLayout(gl_contentPane);
		
		setVisible(true);
	}
	
	// 메인 메뉴 버튼 생성
	public void menu() {
		
		
		
		// 메뉴 객체를 담은 리스트
 		menuAll = cm.selectMenuAll();
 		// 세일메뉴 객체를 담은 리스트
		saleAll = cm.selectSaleAll();
		
		// 이건 멤버로 위에 있음
		// ArrayList<String> menuSelected;
		
		// 클라이언트 ui에 담을 버튼을 담을 어레이리스트 멤버변수에
		allMenuBtnList = new ArrayList<>();
		
		for (int i = 0; i < 47; i++) {
			String tempStr = menuAll.get(i).getMenu();
			int tempInt = menuAll.get(i).getPrice();
			//menu버튼 만들기.
			allMenuBtnList.add(new JButton(String.format("%s%n (%d)", tempStr, tempInt)));
			
			allMenuBtnList.get(i).setBackground(Color.ORANGE);
			allMenuBtnList.get(i).setIcon(new ImageIcon("C:\\Users\\user\\Documents\\ICTM\\SES_1st_project\\total\\" + i + ".jpg"));
			allMenuBtnList.get(i).addActionListener(new ActionListener() {
			@Override
				public void actionPerformed(ActionEvent e) {
					Object obj = e.getSource();
					JButton jbtn = new JButton();
					jbtn = (JButton) obj;
					int i = 0;
					int j = 0;
					for(JButton jb : allMenuBtnList){
						selectedMenuBtnList = new ArrayList<>();
						
						if(jbtn.equals(jb)){
							selectedMenuBtnList.add(new JButton(menuAll.get(i).getMenu()));
							selectedMenuBtnList.get(j).setBackground(Color.ORANGE);
							selectedMenuBtnList.get(j).setIcon(new ImageIcon("C:\\Users\\user\\Documents\\ICTM\\SES_1st_project\\total\\" + i + ".jpg"));
							
							Menu result = cm.selectMenu(menuAll.get(i).getMenu());
							menuSelected.add(result.getMenu());
							
							selectMenuPanel.add(selectedMenuBtnList.get(j));
							selectedMenuBtnList.get(j).addActionListener(new ActionListener() { 
								@Override
								public void actionPerformed(ActionEvent e2) {
									Object obj = e2.getSource();
									JButton jbtn = new JButton();
									jbtn = (JButton) obj;
									selectMenuPanel.remove(jbtn);
									
									boolean result = cm.selectedMenuCancel(jbtn.getText());
									for (int k = 0; k < menuSelected.size(); k++) {
										if (jbtn.getText().equals(menuSelected.get(k))) {
											menuSelected.remove(k);
											JOptionPane.showMessageDialog(null, "메뉴를 지웠습니다");
											break;
										}
									}
									
									selectedMenuBtnList.remove(jbtn); // 선택한 메뉴가 저장된 리스트의 무결성유지
									selectMenuPanel.repaint();
									selectMenuPanel.revalidate();
								}
							});
							j++;
							selectMenuPanel.revalidate();
							break;
						}
						i++;
					}
				}
			});
			panelInmmsp.add(allMenuBtnList.get(i));
		}
	}
	
	public void recommendBoard() {
		String result;
		ArrayList<SaleMenu> recommendSaleList = new ArrayList<>();
		
		recommendSaleList = cm.selectSaleAll();
		System.out.println(recommendSaleList);
		for (int i = 0; i < recommendSaleList.size(); i++) {
			SaleMenu recommendSaleMenu = recommendSaleList.get(i);
			result = recommendSaleMenu.getSaleWay();
			if (i >= 1) {
				if (result.equals(recommendSaleList.get(i-1).getSaleWay())){
					continue;
				}
			}
			tf_recommend.append(result + "\n");
		}
	}

	public void updateChatLog(String message) {
		ta_printChatTextArea.append(message);
		ta_printChatTextArea.setCaretPosition(ta_printChatTextArea.getText().length());
	}
	
	
	public void setUser(User usr) {
		uiUser = usr;
		String uiName = uiUser.getName();
		String uiId = uiUser.getId();
		csm.setNickName(uiName);
	}
	
}
