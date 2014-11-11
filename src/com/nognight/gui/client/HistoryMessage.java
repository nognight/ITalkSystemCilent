package com.nognight.gui.client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JList;

import com.nognight.client.ItalkClient;
import com.nognight.entity.Message;

public class HistoryMessage extends JFrame {
	private String userName;

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public HistoryMessage(String username, String receiver) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 305, 413);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JLabel lblNewLabel = new JLabel("\u5386\u53F2\u6D88\u606F");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		contentPane.add(lblNewLabel, BorderLayout.NORTH);

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		this.userName = userName;
		// 插入数组显示
		Object[] messages = ItalkClient.historyListshow(username, receiver)
				.toArray();

		JList list = new JList(messages);
		scrollPane.setViewportView(list);
	}

}
