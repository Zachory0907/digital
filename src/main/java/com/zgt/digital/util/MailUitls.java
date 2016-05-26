package com.zgt.digital.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUitls {
	
	public static void sendMail(String to, String code) {
		// 1.获得连接对象
		Properties props = new Properties();
		props.setProperty("mail.host", "localhost");
		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("q@hp.com", "123");
			}
		});

		// 2.创建邮件对象:
		Message message = new MimeMessage(session);

		try {
			// 设置发件人:
			message.setFrom(new InternetAddress("q@hp.com"));
			message.addRecipient(RecipientType.TO, new InternetAddress(to));
			// 设置标题
			message.setSubject("Digital数码商城激活");
			// 设置邮件正文:
			String url = "<a href='http://localhost:8080/digital/home/mailCheck?check="+code+"'>点此验证邮箱</a>";
			System.out.println(url);
			message.setContent(url, "text/html;charset=UTF-8");
			// 3.发送邮件:
			Transport.send(message);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		sendMail("w@hp.com", "数码产品");
	}
}
