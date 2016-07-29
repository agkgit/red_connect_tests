import org.jivesoftware.smack.*;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;

/**
 * Бот, относящийся к одной учетной записи жабера.
 * Реализует интерфейс Runnable, так что разных ботов можно будет запускать
 * в разных потоках, или комбинировать их.
 *
 *  Использует библиотеку smack.jar и smackx.jar:
 *  org.jivesoftware.smack
 */
public class XMPPBot implements Runnable
{
	private String nick;
	private String password;
	private String domain;
	private String server;
	private int port;

	private ConnectionConfiguration connConfig;
	private XMPPConnection connection;

	/**
	 * В конструктор должны передаваться данные, необходимые для авторизации на жабер-сервере
	 * @param nick - ник
	 * @param password - пароль
	 * @param domain - домен
	 * @param server - сервер
	 * @param port - порт
	 */
	public XMPPBot(String nick, String password, String domain, String server, int port)
	{
		this.nick = nick;
		this.password = password;
		this.domain = domain;
		this.server = server;
		this.port = port;
	}

	@Override
	public void run()
	{
		connConfig = new ConnectionConfiguration(server, port, domain);
		connection = new XMPPConnection(connConfig);

		try
		{
			int priority = 10;
			SASLAuthentication.supportSASLMechanism("PLAIN", 0);
			connection.connect();
			connection.login(nick, password);
			Presence presence = new Presence(Presence.Type.available);
			presence.setStatus("статус бота");
			connection.sendPacket(presence);
			presence.setPriority(priority);

			PacketFilter filter = new AndFilter(new PacketTypeFilter(Message.class));

			PacketListener myListener = new PacketListener()
			{
				public void processPacket(Packet packet)
				{
					if (packet instanceof Message)
					{
						Message message = (Message) packet;
						// обработка входящего сообщения
						processMessage(message);
					}
				}
			};

			connection.addPacketListener(myListener, filter);

			// раз в минуту просыпаемся и проверяем, что соединение не разорвано
			while(connection.isConnected())
			{
				Thread.sleep(60000);
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Обработка входящего сообщения<hr>
	 * @param message входящее сообщение
	 */
	private void processMessage(Message message)
	{
		String messageBody = message.getBody();
		String JID = message.getFrom();

		// обрабатываем сообщение. можно писать что угодно :)
		// пока что пусть будет эхо-бот
		sendMessage(JID, messageBody);
	}

	/**
	 * Отправка сообщения пользователю<hr>
	 * @param to JID пользователя, которому надо отправить сообщение
	 * @param message сообщение
	 */
	public void sendMessage(String to, String message)
	{
		if(!message.equals(""))
		{
			ChatManager chatmanager = connection.getChatManager();
			Chat newChat = chatmanager.createChat(to, null);

			try
			{
				newChat.sendMessage(message);
			}
			catch (XMPPException e)
			{
				System.out.println(e.getMessage());
			}
		}
	}
}