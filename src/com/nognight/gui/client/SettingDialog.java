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

	private int port; //����������˿�
	private String ip;

	private final JPanel contentPanel = new JPanel();
	private JTextField portField;
	private JTextField ipField;

	/**
	 * Create the dialog.
	 */
	public SettingDialog() {
		setModal(true);//����ģʽ�Ի���ֻ�жԻ���رպ������ڲſɼ���ʹ��
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
						//���IPֵ
						String strIP = ipField.getText();
						if(strIP.equalsIgnoreCase("")) {
							JOptionPane.showMessageDialog(null, "IPֵ����Ϊ��", "ϵͳ���ô���", JOptionPane.ERROR_MESSAGE);
							return;
						} else {
							ip = strIP;
						}

						//��ö˿�ֵ
						String strPort = portField.getText();
						int tempPort = 0;
						if(!strPort.equalsIgnoreCase("")) {
							tempPort = Integer.parseInt(strPort);  
						}
						
						//�������ֵ��������ʾ
						if((tempPort < 1024) || (tempPort > 65535)) {
							//������Ϣ��
							JOptionPane.showMessageDialog(null, "�˿�ֵ�ķ�Χ��1024��65535֮��", "ϵͳ���ô���", JOptionPane.ERROR_MESSAGE);
							portField.setText("");//�������ֵ
							return;
						}
						port = tempPort;
						
						//�رնԻ���
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
						//�رնԻ���
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
		* �ͷ��ɴ� Window�������������ӵ�е������������ʹ�õ����б�����Ļ��Դ��
		* ����Щ Component ����Դ�����ƻ�������ʹ�õ������ڴ涼�����ص�����ϵͳ��
		* �������Ǳ��Ϊ������ʾ�� */
		this.dispose();
	}
}
