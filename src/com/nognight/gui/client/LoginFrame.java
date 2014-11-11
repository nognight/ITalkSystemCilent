package com.nognight.gui.client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.ConnectException;

import javax.swing.JPasswordField;

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.theme.SubstanceTerracottaTheme;

import com.nognight.client.ItalkClient;
import com.nognight.util.*;

public class LoginFrame extends JFrame {
	private int port = 10000;
	private String ip = "127.0.0.1";

	private JPanel contentPane;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private boolean flag = false;

	// private boolean flag = true;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Swing皮肤
					UIManager.setLookAndFeel(new SubstanceLookAndFeel());
					JFrame.setDefaultLookAndFeelDecorated(true);
					JDialog.setDefaultLookAndFeelDecorated(true);
					SubstanceLookAndFeel
							.setCurrentTheme(new SubstanceTerracottaTheme());// 默认皮肤

					LoginFrame frame = new LoginFrame();
					WindowUtils.setWindowCenter(frame);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginFrame() {
		setAutoRequestFocus(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel = new JLabel("ITalkSystem");
		lblNewLabel.setFont(new Font("微软雅黑", Font.BOLD, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel, BorderLayout.NORTH);

		JLabel lblNewLabel_1 = new JLabel("\u7248\u672C:alpha");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		contentPane.add(lblNewLabel_1, BorderLayout.SOUTH);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		usernameField = new JTextField();
		usernameField.setBounds(161, 42, 131, 21);
		panel.add(usernameField);
		usernameField.setColumns(10);

		JButton btnNewButton = new JButton("\u767B\u5F55");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				login();
			}
		});
		btnNewButton.setBounds(70, 145, 93, 23);
		panel.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("\u6CE8\u518C");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RegisterFrame registerFrame = new RegisterFrame();
				WindowUtils.setWindowCenter(registerFrame);
				registerFrame.setVisible(true);
				LoginFrame.this.setVisible(false);

			}
		});
		btnNewButton_1.setBounds(170, 145, 93, 23);
		panel.add(btnNewButton_1);

		JLabel lblNewLabel_2 = new JLabel("\u7528\u6237\u540D");
		lblNewLabel_2.setBounds(107, 45, 54, 15);
		panel.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("\u5BC6  \u7801");
		lblNewLabel_3.setBounds(107, 96, 54, 15);
		panel.add(lblNewLabel_3);

		passwordField = new JPasswordField();
		passwordField.setBounds(161, 93, 131, 21);
		panel.add(passwordField);

		JButton btnNewButton_2 = new JButton("\u8BBE\u7F6E");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showSettingFrame();
			}
		});
		btnNewButton_2.setBounds(273, 145, 93, 23);
		panel.add(btnNewButton_2);
	}

	// login
	public void login() {

		ItalkClient.ip = this.ip;
		ItalkClient.port = this.port;
		String username = usernameField.getText();
		String password = new String(passwordField.getPassword());
		flag = ItalkClient.handleLogin(username, password);

		if (flag) {
			MainFrame mainFrame = new MainFrame(username);
			mainFrame.setVisible(true);
			LoginFrame.this.setVisible(false);

		} else {
			JOptionPane.showMessageDialog(null, ItalkClient.resultMessage);
		}

	}

	// 打开设置面板
	public void showSettingFrame() {
		SettingDialog dialog = new SettingDialog();
		dialog.setPort(this.port); // 设置端口显示的初始值
		dialog.setIp(ip);

		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);// 设置关闭时释放资源
		WindowUtils.setWindowCenter(dialog);// 窗口居中
		dialog.setVisible(true);

		// 获得返回值
		this.ip = dialog.getIp();
		this.port = dialog.getPort();

		dialog = null;
	}

}
