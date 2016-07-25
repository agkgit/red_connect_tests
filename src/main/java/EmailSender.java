import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.*;
import javax.mail.*;
import javax.swing.JOptionPane;

class EmailSender {

	static public void sendEmail(String sendTo, String emailText) {
		Properties property = new Properties();
		property.put("mail.smtp.host", "smtp.yandex.ru");
		property.put("mail.smtp.socketFactory.port", "465");
		property.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		property.put("mail.smtp.auth", "true");
		property.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(
				property,
				new javax.mail.Authenticator(){
					protected PasswordAuthentication getPasswordAuthentication(){
						return new PasswordAuthentication("a.g.krupen@yandex.ru", "Agk19900829");
					}
				}
		);

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("a.g.krupen@yandex.ru"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sendTo));
			message.setSubject("Ошибка");
			message.setText(emailText);
			Transport.send(message);

			JOptionPane.showMessageDialog(null, "Письмо отправлено");

		} catch (MessagingException exception) {
			System.err.println("Письмо не отправлено! Ошибка " + exception);
		}
}

	}