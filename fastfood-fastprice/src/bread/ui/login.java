package bread.ui;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import bread.client.ClientManager;
import bread.vo.User;

/**
 * Log in UI클래스.
 * @author 정동영
 * */
public class login extends JFrame {
	private JPanel contentPane;
	private JPasswordField passwordField;
	private JTextField textField;
	
	private ClientManager cm = new ClientManager();

	/**
	 * Create the frame.
	 */
	public login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 360, 240);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel loginCenter = new JPanel();
		contentPane.add(loginCenter, BorderLayout.CENTER);
		loginCenter.setLayout(new GridLayout(2, 2, 0, 0));
		
		JPanel panel_5 = new JPanel();
		loginCenter.add(panel_5);
		panel_5.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panel_7 = new JPanel();
		panel_5.add(panel_7);
		panel_7.setLayout(new GridLayout(0, 3, 0, 0));
		
		JPanel panel_19 = new JPanel();
		panel_7.add(panel_19);
		
		JPanel panel_11 = new JPanel();
		panel_11.setLayout(new GridLayout(0, 1, 0, 0));
		JLabel idLabel = new JLabel("아이디");
		panel_11.add(idLabel);
		panel_7.add(panel_11);
		
		JPanel panel_20 = new JPanel();
		panel_7.add(panel_20);
		
		JPanel panel_8 = new JPanel();
		panel_5.add(panel_8);
		panel_8.setLayout(new GridLayout(3, 3, 0, 0));
		
		JPanel panel_16 = new JPanel();
		panel_8.add(panel_16);
		
		JPanel panel_17 = new JPanel();
		panel_8.add(panel_17);
		
		textField = new JTextField();
		panel_17.add(textField);
		textField.setColumns(10);
		
		JPanel panel_18 = new JPanel();
		panel_8.add(panel_18);
		
		JPanel panel_6 = new JPanel();
		loginCenter.add(panel_6);
		panel_6.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panel_9 = new JPanel();
		panel_6.add(panel_9);
		panel_9.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_12 = new JPanel();
		panel_12.setLayout(new GridLayout(0, 3, 0, 0));
		panel_9.add(panel_12);
		
		JPanel panel_21 = new JPanel();
		panel_12.add(panel_21);
		
		JPanel panel_22 = new JPanel();
		panel_12.add(panel_22);
		panel_22.setLayout(new GridLayout(0, 1, 0, 0));
		JLabel pwdLabel = new JLabel("암호");
		panel_22.add(pwdLabel);
		
		JPanel panel_10 = new JPanel();
		panel_6.add(panel_10);
		panel_10.setLayout(new GridLayout(3, 3, 0, 0));
		
		JPanel panel_13 = new JPanel();
		panel_10.add(panel_13);
		
		JPanel panel_14 = new JPanel();
		panel_10.add(panel_14);
		
		passwordField = new JPasswordField();
		passwordField.setColumns(10);
		panel_14.add(passwordField);
		
		JPanel panel_15 = new JPanel();
		panel_10.add(panel_15);
		
		JPanel mainNorth = new JPanel();
		contentPane.add(mainNorth, BorderLayout.NORTH);
		
		JPanel mainSouth = new JPanel();
		contentPane.add(mainSouth, BorderLayout.SOUTH);
		
		JButton loginBtn = new JButton("\uB85C\uADF8\uC778");
		mainSouth.add(loginBtn);
		
		JButton registerBtn = new JButton("회원가입");
		registerBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Register register = new Register();
			}
		});
		mainSouth.add(registerBtn);
		
		JPanel mainWest = new JPanel();
		contentPane.add(mainWest, BorderLayout.WEST);
		
		JPanel mainEast = new JPanel();
		contentPane.add(mainEast, BorderLayout.EAST);
		setTitle("VOID 로그인창");
		
		setVisible(true);
		
		// 로그인 버튼을 눌렀을때의 ActionListener에 대한 이벤트 메소드 작성
			loginBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					String compareTextField = textField.getText();
					String comparePasswordField = "";
					char[] getPasswordArray = passwordField.getPassword();
					for (int i = 0; i < getPasswordArray.length; i++) {
						comparePasswordField += "" + getPasswordArray[i]; 
					}
					cm.selectUserAll();
					
					String id = compareTextField;
					String password = comparePasswordField;  
					User result = cm.selectUser(id, password);
					
					textField.setEditable(false);
					passwordField.setEditable(false);
					
					if (result != null) {
						dispose();
						JOptionPane.showMessageDialog(null, "로그인 성공");
						UI ui = new UI();
						ui.setUser(result);
					} else {
						JOptionPane.showMessageDialog(null, "아이디/비번을 다시 확인해주세요", "아이디/비번 오류", JOptionPane.ERROR_MESSAGE);
						textField.setEditable(true);
						textField.setText("");
						passwordField.setEditable(true);
						passwordField.setText("");
					}
				}
			});
		
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					loginBtn.doClick();
				}
			}
		});
		
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					loginBtn.doClick();
				}
			}
		});
	}
}
