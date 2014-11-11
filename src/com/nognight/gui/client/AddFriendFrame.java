package com.nognight.gui.client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;

import com.nognight.client.ItalkClient;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddFriendFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private static String friendname;
	private static String username;

	/**
	 * Create the frame.
	 */
	public AddFriendFrame(String username) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 301, 153);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JLabel lblNewLabel = new JLabel("\u8BF7\u8F93\u5165\u597D\u53CB\u540D");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel, BorderLayout.NORTH);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		textField = new JTextField();
		textField.setBounds(23, 40, 117, 21);
		panel.add(textField);
		textField.setColumns(10);
		this.username = username;
		// 测试（ok）
		// System.out.println(username);

		JButton btnNewButton = new JButton("\u786E\u8BA4");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				friendname = textField.getText();
				// 测试（ok）
				// System.out.println(friendname);
				boolean flag = false;
				flag = ItalkClient
						.findUser(AddFriendFrame.username, friendname);
				if (flag) {
					JOptionPane.showMessageDialog(null, "添加成功");
				} else {
					JOptionPane.showMessageDialog(null, "添加失败该用户不存在");
				}
				// 测试（）
				// System.out.println(friendname + AddFriendFrame.username);
				AddFriendFrame.this.setVisible(false);
			}
		});
		btnNewButton.setBounds(150, 23, 93, 23);
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("\u53D6\u6D88");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddFriendFrame.this.setVisible(false);
			}
		});
		btnNewButton_1.setBounds(150, 56, 93, 23);
		panel.add(btnNewButton_1);
	}
}
