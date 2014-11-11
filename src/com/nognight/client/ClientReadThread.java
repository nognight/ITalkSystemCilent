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
 * ��̨���շ����ת��������������Ϣ������߳�ֻ���ڽ�������
 */
public class ClientReadThread implements Runnable {
	private boolean exitFlag = false;

	private Socket socket;
	private DataInputStream dis;

	private DefaultListModel listModel; // ������������е�JList

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
					// ��¼��������ClientManage�м��뵱ǰ�ͻ�����Ϣ���Ӷ��������������
					handleChatMessage();
				} else if (cmd.equalsIgnoreCase("FILE")) {
					// �����ļ�
					receiveFile();

				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// �ر�����
		IoUtil.close(socket);
	}

	public void handleChatMessage() throws IOException {
		// �����������
		String sender = dis.readUTF(); // ������
		String receiver = dis.readUTF(); // ������
		String message = dis.readUTF(); // ��Ϣ
		// ����
		Date date = new Date();
		DateFormat df3 = DateFormat.getDateTimeInstance(2, 2);

		// ����Model
		if (listModel != null) {
			listModel.addElement(sender +":"+ df3.format(date) + ":");
			listModel.addElement("    " + message);
			// ����(ok)
			// System.out.print("model");
		}
	}

	// �����ļ�
	public void receiveFile() throws IOException {
		String sender = dis.readUTF(); // ������
		String receiver = dis.readUTF(); // ������
		String filename = dis.readUTF(); // ��Ϣ

		// ����Model
		if (listModel != null) {
			listModel.addElement(sender + "->�����ļ�->" + filename);
			// ����
			System.out.print("model");
		}
	}

	public void setExitFlag(boolean exitFlag) {
		this.exitFlag = exitFlag;
	}
}