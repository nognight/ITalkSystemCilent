package com.nognight.gui.client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class SettingDialog extends JDialog {

	private int port; //服务端侦听端口
	private String ip;

	private final JPanel contentPanel = new JPanel();
	private JTextField portField;
	private JTextField ipField;

	/**
	 * Create the dialog.
	 */
	public SettingDialog() {
		setModal(true);//设置模式对话框，只有对话框关闭后，主窗口才可继续使用
		setResizable(false);
		setTitle("\u7CFB\u7EDF\u8BBE\u7F6E");
		setBounds(100, 100, 246, 153);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel label = new JLabel("\u7AEF\u53E3\uFF1A");
			label.setBounds(44, 60, 54, 15);
			contentPanel.add(label);
		}
		{
			portField = new JTextField();
			portField.setBounds(107, 57, 123, 21);
			contentPanel.add(portField);
			portField.setColumns(10);
		}
		
		JLabel lblIp = new JLabel("IP:");
		lblIp.setBounds(44, 22, 54, 15);
		contentPanel.add(lblIp);
		
		ipField = new JTextField();
		ipField.setBounds(107, 26, 123, 21);
		contentPanel.add(ipField);
		ipField.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("\u786E\u5B9A");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						//获得IP值
						String strIP = ipField.getText();
						if(strIP.equalsIgnoreCase("")) {
							JOptionPane.showMessageDialog(null, "IP值不能为空", "系统设置错误", JOptionPane.ERROR_MESSAGE);
							return;
						} else {
							ip = strIP;
						}

						//获得端口值
						String strPort = portField.getText();
						int tempPort = 0;
						if(!strPort.equalsIgnoreCase("")) {
							tempPort = Integer.parseInt(strPort);  
						}
						
						//如果设置值错误则提示
						if((tempPort < 1024) || (tempPort > 65535)) {
							//错误消息框
							JOptionPane.showMessageDialog(null, "端口值的范围在1024到65535之间", "系统设置错误", JOptionPane.ERROR_MESSAGE);
							portField.setText("");//清除设置值
							return;
						}
						port = tempPort;
						
						//关闭对话框
						closeDialog();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("\u53D6\u6D88");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						//关闭对话框
						closeDialog();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public int getPort() {
		return this.port;
	}
	
	public void setPort(int port) {
		this.port = port;
		this.portField.setText("" + port);
	}
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
		this.ipField.setText(ip);
	}

	private void closeDialog() {
		this.setVisible(false);
		/* public void dispose()
		* 释放由此 Window、其子组件及其拥有的所有子组件所使用的所有本机屏幕资源。
		* 即这些 Component 的资源将被破坏，它们使用的所有内存都将返回到操作系统，
		* 并将它们标记为不可显示。 */
		this.dispose();
	}
}
