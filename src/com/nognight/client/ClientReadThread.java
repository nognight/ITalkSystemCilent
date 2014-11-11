package com.nognight.client;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.DefaultListModel;

import com.nognight.util.IoUtil;

/*
 * 后台接收服务端转发过来的聊天信息，这个线程只用于接收数据
 */
public class ClientReadThread implements Runnable {
	private boolean exitFlag = false;

	private Socket socket;
	private DataInputStream dis;

	private DefaultListModel listModel; // 用于聊天界面中的JList

	public ClientReadThread(Socket socket) throws IOException {
		this.socket = socket;
		this.dis = new DataInputStream(new BufferedInputStream(
				socket.getInputStream()));
	}

	public void setModel(DefaultListModel listModel) {
		this.listModel = listModel;
	}

	@Override
	public void run() {
		while (!exitFlag) {
			try {
				String cmd = dis.readUTF();

				if (cmd.equalsIgnoreCase("CHAT")) {
					// 登录操作将向ClientManage中加入当前客户端信息，从而服务于聊天服务
					handleChatMessage();
				} else if (cmd.equalsIgnoreCase("FILE")) {
					// 接受文件
					receiveFile();

				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 关闭链接
		IoUtil.close(socket);
	}

	public void handleChatMessage() throws IOException {
		// 获得输入数据
		String sender = dis.readUTF(); // 发送者
		String receiver = dis.readUTF(); // 接收者
		String message = dis.readUTF(); // 消息
		// 日期
		Date date = new Date();
		DateFormat df3 = DateFormat.getDateTimeInstance(2, 2);

		// 更改Model
		if (listModel != null) {
			listModel.addElement(sender +":"+ df3.format(date) + ":");
			listModel.addElement("    " + message);
			// 测试(ok)
			// System.out.print("model");
		}
	}

	// 接收文件
	public void receiveFile() throws IOException {
		String sender = dis.readUTF(); // 发送者
		String receiver = dis.readUTF(); // 接收者
		String filename = dis.readUTF(); // 消息

		// 更改Model
		if (listModel != null) {
			listModel.addElement(sender + "->发送文件->" + filename);
			// 测试
			System.out.print("model");
		}
	}

	public void setExitFlag(boolean exitFlag) {
		this.exitFlag = exitFlag;
	}
}