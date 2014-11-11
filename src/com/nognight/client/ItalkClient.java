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
	public static int port = 10000; // 服务器侦听端口
	public static String resultMessage = "连接异常";

	public static ClientReadThread readThread;

	// 登录（完成）
	public static boolean handleLogin(String userName, String password)
	/* throws ConnectException */{
		boolean flag = false;
		Socket socket = null;
		try {
			// 创建客户端Socket，连接到服务端。要求端口号和服务端的ServerSocket的端口号相同。
			socket = new Socket(ip, port);
			DataInputStream dis = new DataInputStream(new BufferedInputStream(
					socket.getInputStream()));
			DataOutputStream dos = new DataOutputStream(
					new BufferedOutputStream(socket.getOutputStream()));

			// 通过Socket向服务端传送请求数据
			dos.writeUTF("LOGIN");
			dos.writeUTF(userName);
			dos.writeUTF(password);
			dos.flush();

			/*
			 * 客户端没有业务处理部分的代码 实际业务处理是由服务端实现
			 */

			// 通过Socket接收服务端的处理结果
			String resultType = dis.readUTF(); // 结果类型
			String status = dis.readUTF(); // 操作结果状态 成功 失败
			String resultMessage = dis.readUTF(); // 操作消息
			messageOut(resultMessage);

			// 登录成功后，在服务端的ClientInfo中保留了相应的Socket，这里也会创建一个对应读线程
			if (status.equalsIgnoreCase("SUCCESS")) {
				try {
					// 创建后台读线程
					ItalkClient.readThread = new ClientReadThread(socket);
					// 启动线程
					new Thread(ItalkClient.readThread).start();
				} catch (IOException e) {
					e.printStackTrace();
				}

				flag = true; // 表示登录成功
			} else {
				// 否则关闭链接
				IoUtil.close(socket);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return flag;
	}

	// 聊天（完成）
	public static void handleChat(String sender, String receiver, String message) {
		Socket socket = null;
		try {
			socket = new Socket(ip, port);
			DataInputStream dis = new DataInputStream(new BufferedInputStream(
					socket.getInputStream()));
			DataOutputStream dos = new DataOutputStream(
					new BufferedOutputStream(socket.getOutputStream()));

			// 通过Socket向服务端传送请求数据
			dos.writeUTF("CHAT");
			dos.writeUTF(sender);
			dos.writeUTF(receiver);
			dos.writeUTF(message);
			dos.flush();

			// 通过Socket接收服务端的处理结果
			String resultType = dis.readUTF(); // 结果类型
			String status = dis.readUTF(); // 操作结果状态 成功 失败
			String resultMessage = dis.readUTF(); // 操作消息
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IoUtil.close(socket);// 工具方法
		}
	}

	// 注册（完成）
	public static boolean handlerRegister(String userName, String password) {
		boolean flag = false;

		Socket socket = null;
		try {
			// 创建客户端Socket，连接到服务端。要求端口号和服务端的ServerSocket的端口号相同。
			socket = new Socket(ip, port);
			DataInputStream dis = new DataInputStream(new BufferedInputStream(
					socket.getInputStream()));
			DataOutputStream dos = new DataOutputStream(
					new BufferedOutputStream(socket.getOutputStream()));

			// 通过Socket向服务端传送请求数据
			dos.writeUTF("REGISTER");
			dos.writeUTF(userName);
			dos.writeUTF(password);
			dos.flush();

			/*
			 * 客户端没有业务处理部分的代码 实际业务处理是由服务端实现
			 */

			// 通过Socket接收服务端的处理结果
			String resultType = dis.readUTF(); // 结果类型
			String status = dis.readUTF(); // 操作结果状态 成功 失败
			String resultMessage = dis.readUTF(); // 操作消息
			messageOut(resultMessage);

			if (status.equalsIgnoreCase("SUCCESS")) {
				flag = true; // 注册成功
			} else {
				// 关闭链接
				IoUtil.close(socket);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return flag;
	}

	// 好友列表处理（完成）
	public static ArrayList<FriendList> friendListshow(String username) {
		Socket socket = null;
		ArrayList<FriendList> friendname = new ArrayList<FriendList>();
		try {
			// 创建客户端Socket，连接到服务端。要求端口号和服务端的ServerSocket的端口号相同。
			socket = new Socket(ip, port);
			DataInputStream dis = new DataInputStream(new BufferedInputStream(
					socket.getInputStream()));
			DataOutputStream dos = new DataOutputStream(
					new BufferedOutputStream(socket.getOutputStream()));

			// 通过Socket向服务端传送请求数据
			dos.writeUTF("FRIENDLIST");
			dos.writeUTF(username);
			dos.flush();

			/*
			 * 客户端没有业务处理部分的代码 实际业务处理是由服务端实现
			 */

			// 通过Socket接收服务端的处理结果

			String aString = dis.readUTF();
			if (aString.equals("FRIENDRESULT")) {
				// 按照协议读
				while (dis.readUTF().equals("NAME")) {
					FriendList friendList = new FriendList();
					friendList.setFriendname(dis.readUTF());// 操作结果状态 成功 失败
					friendList.setFriendIsOnline(dis.readUTF());
					friendname.add(friendList);
				}
				// 测试(成功)
				// System.out.print(friendname.toString());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return friendname;

	}

	// 添加好友列表通过查找出数据库user添加
	public static boolean findUser(String username, String friendname) {
		boolean flag = false;
		Socket socket = null;
		try {
			// 创建客户端Socket，连接到服务端。要求端口号和服务端的ServerSocket的端口号相同。
			socket = new Socket(ip, port);
			DataInputStream dis = new DataInputStream(new BufferedInputStream(
					socket.getInputStream()));
			DataOutputStream dos = new DataOutputStream(
					new BufferedOutputStream(socket.getOutputStream()));

			// 通过Socket向服务端传送请求数据
			dos.writeUTF("FINDUSER");
			dos.writeUTF(username);
			dos.writeUTF(friendname);
			dos.flush();

			/*
			 * 客户端没有业务处理部分的代码 实际业务处理是由服务端实现
			 */

			// 通过Socket接收服务端的处理结果
			String status = dis.readUTF(); // 操作结果状态 成功 失败
			String resultMessage = dis.readUTF(); // 操作消息
			messageOut(resultMessage);
			if (status.equalsIgnoreCase("SUCCESS")) {
				flag = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IoUtil.close(socket);// 工具方法
		}
		return flag;
	}

	// 历史消息处理（ok）
	public static ArrayList<Message> historyListshow(String username,
			String receiver) {
		Socket socket = null;
		ArrayList<Message> historyList = new ArrayList<Message>();
		try {
			// 创建客户端Socket，连接到服务端。要求端口号和服务端的ServerSocket的端口号相同。
			socket = new Socket(ip, port);
			DataInputStream dis = new DataInputStream(new BufferedInputStream(
					socket.getInputStream()));
			DataOutputStream dos = new DataOutputStream(
					new BufferedOutputStream(socket.getOutputStream()));

			// 通过Socket向服务端传送请求数据
			dos.writeUTF("HISTORYLIST");
			dos.writeUTF(username);
			dos.writeUTF(receiver);
			dos.flush();

			// 通过Socket接收服务端的处理结果

			String aString = dis.readUTF();
			if (aString.equals("HISTORYRESULT")) {
				// 按照协议读
				while (dis.readUTF().equals("HIS")) {
					Message message = new Message();
					message.setMessageStr(dis.readUTF());
					// message.setDate(dis.readUTF());
					historyList.add(message);
				}
				// 测试(成功)
				// System.out.print(friendname.toString());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return historyList;
	}

	// 上传文件
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
			// 发送文 件名称
			dos.writeUTF(file.getName());
			// 测试(ok)
			// System.out.println(file.getPath());
			// System.out.println(file.getName());

			byte[] buf = new byte[1024 * 4];
			int length = 0;
			while ((length = fis.read(buf)) != -1) {
				dos.writeInt(1);
				dos.writeInt(length);
				dos.write(buf, 0, length);
				// 测试
				// System.out.println("1");
			}
			// 发送文 件传送完成标识
			dos.writeInt(-1);
			dos.flush();
			// 关闭文 件流
			IoUtil.close(fis);

			// 通过Socket接收服务端的处理结果
			String resultType = dis.readUTF(); // 结果类型
			String status = dis.readUTF(); // 操作结果状态 成功 失败
			String resultMessage = dis.readUTF(); // 操作消息
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			flag = true;
			IoUtil.close(socket);// 工具方法
		}
		return flag;
	}

	// 点对点发送文件（未完成）
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
			// 发送文 件名称
			dos.writeUTF(file.getName());
			// 发送文件（未完成）
			// byte[] buf = new byte[1024 * 4];
			// int length = 0;
			// while ((length = fis.read(buf)) != -1) {
			// dos.writeInt(1);
			// dos.writeInt(length);
			// dos.write(buf, 0, length);
			// }
			// 发送文 件传送完成标识
			// dos.writeInt(-1);
			dos.flush();
			// 关闭文 件流
			// IoUtil.close(fis);

			// 通过Socket接收服务端的处理结果
			String resultType = dis.readUTF(); // 结果类型
			String status = dis.readUTF(); // 操作结果状态 成功 失败
			String resultMessage = dis.readUTF(); // 操作消息
			flag = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IoUtil.close(socket);// 工具方法
		}
		return flag;
	}

	// 回传信息（完成）
	public static String messageOut(String message) {
		resultMessage = message;
		return resultMessage;
	}

	// 注销（ok）
	public static void handleLogout(String username) {

		Socket socket = null;
		try {
			// 创建客户端Socket，连接到服务端。要求端口号和服务端的ServerSocket的端口号相同。
			socket = new Socket(ip, port);
			DataInputStream dis = new DataInputStream(new BufferedInputStream(
					socket.getInputStream()));
			DataOutputStream dos = new DataOutputStream(
					new BufferedOutputStream(socket.getOutputStream()));

			// 通过Socket向服务端传送请求数据
			dos.writeUTF("LOGOUT");
			dos.writeUTF(username);
			dos.flush();

			// 通过Socket接收服务端的处理结果
			String resultType = dis.readUTF(); // 结果类型
			String status = dis.readUTF(); // 操作结果状态 成功 失败
			String resultMessage = dis.readUTF(); // 操作消息
			messageOut(resultMessage);
			IoUtil.close(socket);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}