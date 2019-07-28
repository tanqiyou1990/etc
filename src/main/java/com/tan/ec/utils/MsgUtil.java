package com.tan.ec.utils;

import com.sun.mail.util.MailSSLSocketFactory;
import com.tan.ec.constants.CommonConstant;
import lombok.extern.slf4j.Slf4j;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.util.Properties;

@Slf4j
public class MsgUtil {

	public static boolean initEmail(String content, String email, String title) {
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		Properties props = System.getProperties();
		MailSSLSocketFactory sf = null;
		try {
			sf = new MailSSLSocketFactory();
			// gmail邮箱
			sf.setTrustAllHosts(true);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		props.put("mail.debug", "false");
		props.put("mail.smtp.host", "smtp.exmail.qq.com");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
		props.put("mail.smtp.port", "993");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.auth", "true");
		// 建立邮件会话
		Session session = Session.getDefaultInstance(props, new Authenticator() {
			// 身份认证
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("", "");
			}
		});
		// 开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
		session.setDebug(false);
		// 4、创建邮件
		Message message = null;
		try {
			message = createSimpleMail(session, content, email, title);
			// // 5、发送邮件
			Transport.send(message);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		}

	}

	private static MimeMessage createSimpleMail(Session session, String content, String email, String title)
			throws Exception {
		// 创建邮件对象
		MimeMessage message = new MimeMessage(session);
		// 设置自定义发件人昵称
		String nick = "";
		try {
			nick = javax.mail.internet.MimeUtility.encodeText(CommonConstant.TITLE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 指明邮件的发件人
		message.setFrom(new InternetAddress("", nick));
		// 指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
		// 邮件的标题
		message.setSubject(title);
		// 邮件的文本内容
		message.setContent(content, "text/html;charset=UTF-8");
		// 返回创建好的邮件对象
		return message;
	}
}
