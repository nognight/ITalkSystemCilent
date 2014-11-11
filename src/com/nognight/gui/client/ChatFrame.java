package com.nognight.gui.client;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import com.nognight.client.ItalkClient;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

public class ChatFrame extends JFrame {

	private DefaultListModel model = new DefaultListModel();// ģ�Ͷ���ΪList�ṩ����

	private String sender; // ������
	private String receiver; // ������

	private final JPanel contentPanel = new JPanel();
	private JLabel senderLabel;
	private JLabel receiverLabel;
	private JTextArea messageArea;

	public ChatFrame(String sender, String receiver) {
		setResizable(false);
		setTitle("\u804A\u5929");

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				ItalkClient.readThread.setModel(null);// �ر�ʱ�����߳��е�ModelֵΪnull
			}
		});
		this.sender = sender;
		this.receiver = receiver;

		setBounds(100, 100, 450, 365);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel label = new JLabel("\u53D1\u9001\u4EBA\uFF1A");
			label.setBounds(10, 10, 54, 15);
			contentPanel.add(label);
		}
		{
			senderLabel = new JLabel("");
			senderLabel.setBounds(74, 10, 82, 15);
			contentPanel.add(senderLabel);
		}
		{
			JLabel label = new JLabel("\u63A5\u6536\u4EBA\uFF1A");
			label.setBounds(220, 10, 54, 15);
			contentPanel.add(label);
		}
		{
			receiverLabel = new JLabel("");
			receiverLabel.setBounds(300, 10, 67, 15);
			contentPanel.add(receiverLabel);
		}
		{
			JList resultList = new JList(model);
			resultList.setValueIsAdjusting(true); // ��ʾList�ǿɵ�����

			// ΪList���ӹ�������
			JScrollPane scrollPane = new JScrollPane(resultList); // ֧�ֹ���
			scrollPane
					.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);// ������ˮƽ����
			scrollPane.setViewportView(resultList); // List��Table��Tree��Ҫ������仰�ſɼ�
			scrollPane.setBounds(24, 35, 392, 168);
			contentPanel.add(scrollPane);
		}

		messageArea = new JTextArea();
		messageArea.setLineWrap(true); // �ı��Զ�����
		// ���ӹ���֧��
		JScrollPane sta = new JScrollPane(messageArea); // ʹ�ı���֧�ֹ�����
		sta.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); // ���������ˮƽ������
		sta.setBounds(23, 200, 393, 82);
		contentPanel.add(sta);

		JButton button = new JButton("\u53D1\u9001\u6D88\u606F");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendChat();
			}
		});
		button.setBounds(132, 300, 93, 23);
		contentPanel.add(button);

		JButton btnNewButton = new JButton("\u5386\u53F2\u6D88\u606F");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				historyMessage();
			}
		});
		btnNewButton.setBounds(323, 300, 93, 23);
		contentPanel.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("\u53D1\u9001\u6587\u4EF6");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendFile();
			}
		});
		btnNewButton_1.setBounds(230, 300, 93, 23);
		contentPanel.add(btnNewButton_1);

		getValue();
	}

	// ��ý�����齨�ĳ�ʼֵ
	public void getValue() {
		this.senderLabel.setText(this.sender);
		this.receiverLabel.setText(this.receiver);
		ItalkClient.readThread.setModel(model); // ��ʼ���߳��е�Modelֵ
	}

	// �����ʷ��Ϣ
	public void historyMessage() {
		HistoryMessage historyMessage = new HistoryMessage(sender, receiver);
		historyMessage.setVisible(true);

	}

	// ������Ϣ
	public void sendChat() {
		String message = messageArea.getText();
		if (!message.equalsIgnoreCase("")) {
			ItalkClient.handleChat(sender, receiver, message);
			this.messageArea.setText("");
			// ����Ϣ����
			Date date = new Date();
			DateFormat df3 = DateFormat.getDateTimeInstance(2, 2);

			model.addElement(sender + "  " + df3.format(date) + ":");
			model.addElement("    " + message);
		}
	}

	// �����ļ�
	public void sendFile() {
		FileDialog dialog = new FileDialog(ChatFrame.this, "ѡ���ϴ��ļ�",
				FileDialog.LOAD);
		dialog.setVisible(true);
		if (dialog.getFile() != null) {
			String fileDirectory = dialog.getDirectory();// Ŀ¼
			String fileName = dialog.getFile();// �� ������
			boolean flag = false;
			flag = ItalkClient.sendFile(sender, receiver, fileDirectory,
					fileName);
			if (flag) {
				model.addElement(sender + ">�����ļ�" + "(" + fileName + ")>"
						+ receiver);
				JOptionPane.showMessageDialog(null, "���ͳɹ���");

			} else {
				JOptionPane.showMessageDialog(null, "����ʧ�ܣ�");
			}
		}
	}
}
