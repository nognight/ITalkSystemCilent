package com.nognight.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;

import com.nognight.entity.FriendList;
import com.nognight.entity.Message;
import com.nognight.util.IoUtil;

public class ItalkClient {
	public static String ip = "127.0.0.1";
	public static int port = 10000; // �����������˿�
	public static String resultMessage = "�����쳣";

	public static ClientReadThread readThread;

	// ��¼����ɣ�
	public static boolean handleLogin(String userName, String password)
	/* throws ConnectException */{
		boolean flag = false;
		Socket socket = null;
		try {
			// �����ͻ���Socket�����ӵ�����ˡ�Ҫ��˿ںźͷ���˵�ServerSocket�Ķ˿ں���ͬ��
			socket = new Socket(ip, port);
			DataInputStream dis = new DataInputStream(new BufferedInputStream(
					socket.getInputStream()));
			DataOutputStream dos = new DataOutputStream(
					new BufferedOutputStream(socket.getOutputStream()));

			// ͨ��Socket�����˴�����������
			dos.writeUTF("LOGIN");
			dos.writeUTF(userName);
			dos.writeUTF(password);
			dos.flush();

			/*
			 * �ͻ���û��ҵ�����ֵĴ��� ʵ��ҵ�������ɷ����ʵ��
			 */

			// ͨ��Socket���շ���˵Ĵ�����
			String resultType = dis.readUTF(); // �������
			String status = dis.readUTF(); // �������״̬ �ɹ� ʧ��
			String resultMessage = dis.readUTF(); // ������Ϣ
			messageOut(resultMessage);

			// ��¼�ɹ����ڷ���˵�ClientInfo�б�������Ӧ��Socket������Ҳ�ᴴ��һ����Ӧ���߳�
			if (status.equalsIgnoreCase("SUCCESS")) {
				try {
					// ������̨���߳�
					ItalkClient.readThread = new ClientReadThread(socket);
					// �����߳�
					new Thread(ItalkClient.readThread).start();
				} catch (IOException e) {
					e.printStackTrace();
				}

				flag = true; // ��ʾ��¼�ɹ�
			} else {
				// ����ر�����
				IoUtil.close(socket);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return flag;
	}

	// ���죨��ɣ�
	public static void handleChat(String sender, String receiver, String message) {
		Socket socket = null;
		try {
			socket = new Socket(ip, port);
			DataInputStream dis = new DataInputStream(new BufferedInputStream(
					socket.getInputStream()));
			DataOutputStream dos = new DataOutputStream(
					new BufferedOutputStream(socket.getOutputStream()));

			// ͨ��Socket�����˴�����������
			dos.writeUTF("CHAT");
			dos.writeUTF(sender);
			dos.writeUTF(receiver);
			dos.writeUTF(message);
			dos.flush();

			// ͨ��Socket���շ���˵Ĵ�����
			String resultType = dis.readUTF(); // �������
			String status = dis.readUTF(); // �������״̬ �ɹ� ʧ��
			String resultMessage = dis.readUTF(); // ������Ϣ
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IoUtil.close(socket);// ���߷���
		}
	}

	// ע�ᣨ��ɣ�
	public static boolean handlerRegister(String userName, String password) {
		boolean flag = false;

		Socket socket = null;
		try {
			// �����ͻ���Socket�����ӵ�����ˡ�Ҫ��˿ںźͷ���˵�ServerSocket�Ķ˿ں���ͬ��
			socket = new Socket(ip, port);
			DataInputStream dis = new DataInputStream(new BufferedInputStream(
					socket.getInputStream()));
			DataOutputStream dos = new DataOutputStream(
					new BufferedOutputStream(socket.getOutputStream()));

			// ͨ��Socket�����˴�����������
			dos.writeUTF("REGISTER");
			dos.writeUTF(userName);
			dos.writeUTF(password);
			dos.flush();

			/*
			 * �ͻ���û��ҵ�����ֵĴ��� ʵ��ҵ�������ɷ����ʵ��
			 */

			// ͨ��Socket���շ���˵Ĵ�����
			String resultType = dis.readUTF(); // �������
			String status = dis.readUTF(); // �������״̬ �ɹ� ʧ��
			String resultMessage = dis.readUTF(); // ������Ϣ
			messageOut(resultMessage);

			if (status.equalsIgnoreCase("SUCCESS")) {
				flag = true; // ע��ɹ�
			} else {
				// �ر�����
				IoUtil.close(socket);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return flag;
	}

	// �����б�����ɣ�
	public static ArrayList<FriendList> friendListshow(String username) {
		Socket socket = null;
		ArrayList<FriendList> friendname = new ArrayList<FriendList>();
		try {
			// �����ͻ���Socket�����ӵ�����ˡ�Ҫ��˿ںźͷ���˵�ServerSocket�Ķ˿ں���ͬ��
			socket = new Socket(ip, port);
			DataInputStream dis = new DataInputStream(new BufferedInputStream(
					socket.getInputStream()));
			DataOutputStream dos = new DataOutputStream(
					new BufferedOutputStream(socket.getOutputStream()));

			// ͨ��Socket�����˴�����������
			dos.writeUTF("FRIENDLIST");
			dos.writeUTF(username);
			dos.flush();

			/*
			 * �ͻ���û��ҵ�����ֵĴ��� ʵ��ҵ�������ɷ����ʵ��
			 */

			// ͨ��Socket���շ���˵Ĵ�����

			String aString = dis.readUTF();
			if (aString.equals("FRIENDRESULT")) {
				// ����Э���
				while (dis.readUTF().equals("NAME")) {
					FriendList friendList = new FriendList();
					friendList.setFriendname(dis.readUTF());// �������״̬ �ɹ� ʧ��
					friendList.setFriendIsOnline(dis.readUTF());
					friendname.add(friendList);
				}
				// ����(�ɹ�)
				// System.out.print(friendname.toString());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return friendname;

	}

	// ��Ӻ����б�ͨ�����ҳ����ݿ�user���
	public static boolean findUser(String username, String friendname) {
		boolean flag = false;
		Socket socket = null;
		try {
			// �����ͻ���Socket�����ӵ�����ˡ�Ҫ��˿ںźͷ���˵�ServerSocket�Ķ˿ں���ͬ��
			socket = new Socket(ip, port);
			DataInputStream dis = new DataInputStream(new BufferedInputStream(
					socket.getInputStream()));
			DataOutputStream dos = new DataOutputStream(
					new BufferedOutputStream(socket.getOutputStream()));

			// ͨ��Socket�����˴�����������
			dos.writeUTF("FINDUSER");
			dos.writeUTF(username);
			dos.writeUTF(friendname);
			dos.flush();

			/*
			 * �ͻ���û��ҵ�����ֵĴ��� ʵ��ҵ�������ɷ����ʵ��
			 */

			// ͨ��Socket���շ���˵Ĵ�����
			String status = dis.readUTF(); // �������״̬ �ɹ� ʧ��
			String resultMessage = dis.readUTF(); // ������Ϣ
			messageOut(resultMessage);
			if (status.equalsIgnoreCase("SUCCESS")) {
				flag = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IoUtil.close(socket);// ���߷���
		}
		return flag;
	}

	// ��ʷ��Ϣ����ok��
	public static ArrayList<Message> historyListshow(String username,
			String receiver) {
		Socket socket = null;
		ArrayList<Message> historyList = new ArrayList<Message>();
		try {
			// �����ͻ���Socket�����ӵ�����ˡ�Ҫ��˿ںźͷ���˵�ServerSocket�Ķ˿ں���ͬ��
			socket = new Socket(ip, port);
			DataInputStream dis = new DataInputStream(new BufferedInputStream(
					socket.getInputStream()));
			DataOutputStream dos = new DataOutputStream(
					new BufferedOutputStream(socket.getOutputStream()));

			// ͨ��Socket�����˴�����������
			dos.writeUTF("HISTORYLIST");
			dos.writeUTF(username);
			dos.writeUTF(receiver);
			dos.flush();

			// ͨ��Socket���շ���˵Ĵ�����

			String aString = dis.readUTF();
			if (aString.equals("HISTORYRESULT")) {
				// ����Э���
				while (dis.readUTF().equals("HIS")) {
					Message message = new Message();
					message.setMessageStr(dis.readUTF());
					// message.setDate(dis.readUTF());
					historyList.add(message);
				}
				// ����(�ɹ�)
				// System.out.print(friendname.toString());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return historyList;
	}

	// �ϴ��ļ�
	public static boolean updateFile(String fileDirectory, String fileName,
			String sender) {
		boolean flag = false;
		Socket socket = null;
		try {
			socket = new Socket(ip, port);
			DataInputStream dis = new DataInputStream(new BufferedInputStream(
					socket.getInputStream()));
			DataOutputStream dos = new DataOutputStream(
					new BufferedOutputStream(socket.getOutputStream()));

			dos.writeUTF("SAVEFILE");
			dos.writeUTF(sender);
			File file = new File(fileDirectory + fileName);
			FileInputStream fis = new FileInputStream(file);
			// ������ ������
			dos.writeUTF(file.getName());
			// ����(ok)
			// System.out.println(file.getPath());
			// System.out.println(file.getName());

			byte[] buf = new byte[1024 * 4];
			int length = 0;
			while ((length = fis.read(buf)) != -1) {
				dos.writeInt(1);
				dos.writeInt(length);
				dos.write(buf, 0, length);
				// ����
				// System.out.println("1");
			}
			// ������ ��������ɱ�ʶ
			dos.writeInt(-1);
			dos.flush();
			// �ر��� ����
			IoUtil.close(fis);

			// ͨ��Socket���շ���˵Ĵ�����
			String resultType = dis.readUTF(); // �������
			String status = dis.readUTF(); // �������״̬ �ɹ� ʧ��
			String resultMessage = dis.readUTF(); // ������Ϣ
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			flag = true;
			IoUtil.close(socket);// ���߷���
		}
		return flag;
	}

	// ��Ե㷢���ļ���δ��ɣ�
	public static boolean sendFile(String sender, String receiver,
			String fileDirectory, String fileName) {
		boolean flag = false;
		Socket socket = null;
		try {
			socket = new Socket(ip, port);
			DataInputStream dis = new DataInputStream(new BufferedInputStream(
					socket.getInputStream()));
			DataOutputStream dos = new DataOutputStream(
					new BufferedOutputStream(socket.getOutputStream()));

			dos.writeUTF("SENDFILE");
			dos.writeUTF(sender);
			dos.writeUTF(receiver);
			File file = new File(fileDirectory + fileName);
			// FileInputStream fis = new FileInputStream(file);
			// ������ ������
			dos.writeUTF(file.getName());
			// �����ļ���δ��ɣ�
			// byte[] buf = new byte[1024 * 4];
			// int length = 0;
			// while ((length = fis.read(buf)) != -1) {
			// dos.writeInt(1);
			// dos.writeInt(length);
			// dos.write(buf, 0, length);
			// }
			// ������ ��������ɱ�ʶ
			// dos.writeInt(-1);
			dos.flush();
			// �ر��� ����
			// IoUtil.close(fis);

			// ͨ��Socket���շ���˵Ĵ�����
			String resultType = dis.readUTF(); // �������
			String status = dis.readUTF(); // �������״̬ �ɹ� ʧ��
			String resultMessage = dis.readUTF(); // ������Ϣ
			flag = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IoUtil.close(socket);// ���߷���
		}
		return flag;
	}

	// �ش���Ϣ����ɣ�
	public static String messageOut(String message) {
		resultMessage = message;
		return resultMessage;
	}

	// ע����ok��
	public static void handleLogout(String username) {

		Socket socket = null;
		try {
			// �����ͻ���Socket�����ӵ�����ˡ�Ҫ��˿ںźͷ���˵�ServerSocket�Ķ˿ں���ͬ��
			socket = new Socket(ip, port);
			DataInputStream dis = new DataInputStream(new BufferedInputStream(
					socket.getInputStream()));
			DataOutputStream dos = new DataOutputStream(
					new BufferedOutputStream(socket.getOutputStream()));

			// ͨ��Socket�����˴�����������
			dos.writeUTF("LOGOUT");
			dos.writeUTF(username);
			dos.flush();

			// ͨ��Socket���շ���˵Ĵ�����
			String resultType = dis.readUTF(); // �������
			String status = dis.readUTF(); // �������״̬ �ɹ� ʧ��
			String resultMessage = dis.readUTF(); // ������Ϣ
			messageOut(resultMessage);
			IoUtil.close(socket);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}