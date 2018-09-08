package bread.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import bread.client.ClientManager;
import bread.vo.SaleMenu;

/**
 * UI마지막 창 클래스.
 * @author 정동영.
 * */
public class finalUI extends JFrame {

	private JPanel contentPane;
	private JPanel sg2Panel;
	
	private ArrayList<ArrayList<SaleMenu>> result = new ArrayList<>();	
	
	
	ClientManager cm = new ClientManager();

	public finalUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel resultPanel = new JPanel();
		contentPane.add(resultPanel, BorderLayout.CENTER);
		resultPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel titlePanel = new JPanel();
		resultPanel.add(titlePanel, BorderLayout.NORTH);
		titlePanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		titlePanel.add(panel_2, BorderLayout.NORTH);
		JLabel panelTitle = new JLabel("이번의 추천메뉴");
		panel_2.add(panelTitle);
		
		sg2Panel = new JPanel();
		JScrollPane sgPanel = new JScrollPane(sg2Panel);
		resultPanel.add(sgPanel, BorderLayout.CENTER);
		sg2Panel.setLayout(new GridLayout(2, 3, 0, 0));
		
		menuSelectedChoice();
		
		
		JPanel pricePanel = new JPanel();
		resultPanel.add(pricePanel, BorderLayout.SOUTH);
		pricePanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JTextArea textArea = new JTextArea();
		pricePanel.add(textArea);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		panel_1.setVisible(false);
		
		JButton confirmButton = new JButton("\uD655\uC778");
		panel_1.add(confirmButton);
		setTitle("버거킹 추천 메뉴");
		
		setVisible(true);
		
		
	}
	
	public void menuSelectedChoice() {
		ArrayList<ArrayList<String>> menuTotal2 = new ArrayList<>();
		result = cm.totalMerge();
		for (ArrayList<SaleMenu> asm : result) {
			ArrayList<String> menuTotal1 = new ArrayList<>();
			for (SaleMenu sm : asm) {
				menuTotal1.add(sm.getSaleMenu());
			}
			menuTotal2.add(menuTotal1);
		}
		finalChoice(menuTotal2);
	}
	
	/**
	 * 마지막 창의 추천 조합 메뉴 생성 버튼(메뉴 조합, 조합 가격)
	 * @author 정동영, 김기홍
	 * */
	public void finalChoice(ArrayList<ArrayList<String>> menuTotal22) {
		ArrayList<ArrayList<String>> tempMenuTotal = menuTotal22;
		ArrayList<String> finalMenuList = new ArrayList<>();
		
		
		for (int i = 0; i < tempMenuTotal.size(); i++) {
			finalMenuList = tempMenuTotal.get(i);
			
			String finalMenu = "";
			for (int j = 0; j < finalMenuList.size(); j++) {
				finalMenu += " " + finalMenuList.get(j);
			}
			ArrayList<String> finalChoiceTempStringList = new ArrayList<>();
			ArrayList<Double> finalChoiceTempSalePriceList = new ArrayList<>();
			ArrayList<Double> ListOffinalChoiceTempSalePriceList = new ArrayList<>();
			double finalMenuPrice = 0.0;
			for(int a = 0; a<tempMenuTotal.size(); a++){
				for(int b = 0; b<result.size(); b++){
					for(int c = 0; c<result.get(b).size(); c++){
					finalChoiceTempSalePriceList.add(result.get(b).get(c).getSalePrice());
					}
						finalMenuPrice = 0.0;
						for(double d : finalChoiceTempSalePriceList){
							finalMenuPrice += d;
						}
						ListOffinalChoiceTempSalePriceList.add(finalMenuPrice);
					finalChoiceTempSalePriceList.clear();
				}
			}
				JButton finalBtn = new JButton(String.format("%s%n (%.0f)", finalMenu, ListOffinalChoiceTempSalePriceList.get(i)));
				finalBtn.setFont(new Font("굴림", Font.BOLD, 20));
				finalBtn.setBackground(Color.YELLOW);
				finalBtn.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						Object obj = e.getSource();
						JButton tempBtn = (JButton) obj;
						setVisible(false);
						JOptionPane.showMessageDialog(null, tempBtn.getText() + "를 구매하셨습니다");
						
						cm.userFinalChoice(tempBtn.getText());
						
						dispose();
						System.exit(0);
					}
				});
				sg2Panel.add(finalBtn);
		}
	}
}
