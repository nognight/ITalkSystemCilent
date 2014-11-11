package com.nognight.util;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class WindowUtils {
	
	//将窗口居中
	public static void setWindowCenter(JFrame frame) {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int)dimension.getWidth();
		int screenHeight = (int)dimension.getHeight();
		
		int frameWidth = frame.getWidth();
		int frameHeight = frame.getHeight();
		
		int x = (screenWidth - frameWidth) / 2;
		int y = (screenHeight - frameHeight) / 2;
		
		frame.setLocation(x, y);//设置左上角图标
	}
	
	//将对话框居中
	public static void setWindowCenter(JDialog dialog) {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int)dimension.getWidth();
		int screenHeight = (int)dimension.getHeight();
		
		int frameWidth = dialog.getWidth();
		int frameHeight = dialog.getHeight();
		
		int x = (screenWidth - frameWidth) / 2;
		int y = (screenHeight - frameHeight) / 2;
		
		dialog.setLocation(x, y);
	}

}
