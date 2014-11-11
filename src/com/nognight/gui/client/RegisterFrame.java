package com.nognight.gui.client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.crypto.AEADBadTagException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.ConnectException;

import javax.swing.JOptionPane;

import com.nognight.client.ItalkClient;
import com.nognight.util.*;

public class RegisterFrame extends JFrame {
	private int port = 10000;
	private String ip = "127.0.0.1";

	private JPanel contentPane;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;

	private boolean flag = false;

	/**
	 * Create the frame.
	 */
	public RegisterFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel = new JLabel("\u65B0\u7528\u6237\u6CE8\u518C");
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel, BorderLayout.NORTH);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		usernameField = new JTextField();
		usernameField.setHorizontalAlignment(SwingConstants.LEFT);
		usernameField.setBounds(189, 46, 90, 21);
		panel.add(usernameField);
		usernameField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setHorizontalAlignment(SwingConstants.LEFT);
		passwordField.setBounds(189, 77, 90, 21);
		panel.add(passwordField);

		passwordField_1 = new JPasswordField();
		passwordField_1.setHorizontalAlignment(SwingConstants.LEFT);
		passwordField_1.setBounds(189, 108, 90, 21);
		panel.add(passwordField_1);

		JLabel lblNewLabel_1 = new JLabel("\u7528\u6237\u540D");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(125, 49, 54, 15);
		panel.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("\u5BC6  \u7801");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(125, 80, 54, 15);
		panel.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("\u786E\u8BA4\u5BC6\u7801");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(125, 111, 54, 15);
		panel.add(lblNewLabel_3);

		JButton btnNewButton = new JButton("\u6CE8\u518C");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				register();
			}
		});
		btnNewButton.setBounds(125, 164, 93, 23);
		panel.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("\u53D6\u6D88");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RegisterFrame.this.setVisible(false);
				LoginFrame loginFrame = new LoginFrame();
				WindowUtils.setWindowCenter(loginFrame);
				loginFrame.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(228, 164, 93, 23);
		panel.add(btnNewButton_1);
	}

	// 注册
	public void register() {
		ItalkClient.ip = this.ip;
		ItalkClient.port = this.port;
		String a = new String(passwordField.getPassword());
		String b = new String(passwordField_1.getPassword());
		if (a.equals(b)) {
			String usrname = usernameField.getText();
			String password = new String(passwordField.getPassword());
			flag = ItalkClient.handlerRegister(usrname, password);

			if (flag) {
				LoginFrame loginFrame = new LoginFrame();
				WindowUtils.setWindowCenter(loginFrame);
				JOptionPane.showMessageDialog(null, "注册成功");
				loginFrame.setVisible(true);
				RegisterFrame.this.setVisible(false);
			} else {
				JOptionPane.showMessageDialog(null, ItalkClient.resultMessage);
			}

		} else {
			JOptionPane.showMessageDialog(null, "两次输入密码不同");
		}
	}
}
