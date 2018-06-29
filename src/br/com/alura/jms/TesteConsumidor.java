package br.com.alura.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

public class TesteConsumidor {

	public static void main(String[] args)  {

		try {
		
			InitialContext context = new InitialContext();
			ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
			
			Connection connection = factory.createConnection();
			connection.start();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			Destination destination = (Destination) context.lookup("financeiro");
			MessageConsumer consumer = session.createConsumer(destination);
			
			consumer.setMessageListener(new MessageListener() {
				@Override
				public void onMessage(Message message) {
					
					TextMessage textMessage = (TextMessage) message;
					
					System.out.println("## Mensagem: " + textMessage);
				}
			});
			
//			Message message = consumer.receive();
			
			
			
			new Scanner(System.in).nextLine();
			
			session.close();
			connection.close();
			context.close();
			
		} catch (Exception e) {
			System.out.println("DEU ERRO: " + e.getMessage());
		}
	}
}
