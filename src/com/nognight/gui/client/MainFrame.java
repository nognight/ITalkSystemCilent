package com.nognight.gui.client;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FileDialog;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JToolBar;
import javax.swing.JMenuBar;
import javax.swing.JToggleButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JList;

import com.nognight.client.ClientReadThread;
import com.nognight.client.ItalkClient;
import com.nognight.entity.FriendList;
import com.nognight.util.WindowUtils;

import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
	private static String userName;

	private JPanel contentPane;
	private JList list;
	private DefaultListModel model;

	// private DefaultListModel model;

	/**
	 * Create the frame.
	 */
	public MainFrame(String userName) {
		setResizable(false);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 191, 455);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu menu = new JMenu("\u529F\u80FD");
		menuBar.add(menu);

		JMenuItem mntmNewMenuItem = new JMenuItem("\u9000\u51FA\u767B\u5F55");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainFrame.this.setVisible(false);
				// 登出
				ItalkClient.handleLogout(MainFrame.userName);
				ItalkClient.readThread.setExitFlag(true);
				LoginFrame loginFrame = new LoginFrame();
				loginFrame.setVisible(true);
			}
		});

		JMenuItem mntmNewMenuItem_2 = new JMenuItem("\u4E0A\u4F20\u6587\u4EF6");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateFile();
			}
		});

		JMenuItem menuItem = new JMenuItem("\u6DFB\u52A0\u597D\u53CB");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addFriend();

			}
		});
		menu.add(menuItem);
		// 好友列表

		// 创建用户好友列表
		initListModel(userName);
		// 设置列表刷新

		list = new JList(model);

		JMenuItem mntmNewMenuItem_3 = new JMenuItem("\u5237\u65B0\u5217\u8868");
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			// 刷新好友列表
			public void actionPerformed(ActionEvent arg0) {

				// how to do??
				// initListModel(MainFrame.userName);
				// list = new JList(model);
				MainFrame mainFrame = new MainFrame(MainFrame.userName);
				MainFrame.this.setVisible(false);
				mainFrame.setVisible(true);
			}
		});
		menu.add(mntmNewMenuItem_3);

		menu.add(mntmNewMenuItem_2);
		menu.add(mntmNewMenuItem);

		JMenu menu_1 = new JMenu("\u5E2E\u52A9");
		menuBar.add(menu_1);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("\u5173\u4E8E");
		menu_1.add(mntmNewMenuItem_1);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel_1 = new JLabel(
				"\u5F53\u524D\u7528\u6237\u5217\u8868");
		contentPane.add(lblNewLabel_1, BorderLayout.NORTH);

		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		// 显示当前用户
		this.userName = userName;

		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));

		panel_2.add(list, BorderLayout.CENTER);

		JLabel lblNewLabel = new JLabel("");
		panel_2.add(lblNewLabel, BorderLayout.SOUTH);
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setText("当前用户" + userName);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// 获得sender，receiver
				int index = list.getSelectedIndex();
				ArrayList<FriendList> receivers = ItalkClient
						.friendListshow(MainFrame.userName);
				String receiver = receivers.get(index).getFriendname();
				// 测试
				System.out.println(receiver);
				String sender = MainFrame.userName;
				showChatFrame(sender, receiver);
			}
		});
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	// 好友列表
	public DefaultListModel initListModel(String username) {
		model = new DefaultListModel();
		Object[] friendList = ItalkClient.friendListshow(username).toArray();
		for (int i = 0; i < friendList.length; i++) {
			model.addElement(friendList[i]);
		}
		return model;
	}

	// 添加好友
	public void addFriend() {
		AddFriendFrame addFriendFrame = new AddFriendFrame(userName);
		WindowUtils.setWindowCenter(addFriendFrame);
		addFriendFrame.setVisible(true);

	}

	// 上传文件到服务器
	public void updateFile() {
		FileDialog dialog = new FileDialog(MainFrame.this, "选择上传文件",
				FileDialog.LOAD);
		dialog.setVisible(true);
		if (dialog.getFile() != null) {
			String fileDirectory = dialog.getDirectory();// 目录
			String fileName = dialog.getFile();// 文 件名称
			boolean flag = false;
			flag = ItalkClient.updateFile(fileDirectory, fileName,
					MainFrame.userName);
			if (flag) {
				JOptionPane.showMessageDialog(null, "上传成功！");

			} else {
				JOptionPane.showMessageDialog(null, "上传失败！");
			}
		}
	}

	// 打开聊天窗口
	public void showChatFrame(String sender, String receiver) {
		ChatFrame chatFrame = new ChatFrame(sender, receiver);
		WindowUtils.setWindowCenter(chatFrame);
		chatFrame.setVisible(true);

	}
}
