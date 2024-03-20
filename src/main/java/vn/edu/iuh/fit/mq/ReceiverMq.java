package vn.edu.iuh.fit.mq;

import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import vn.edu.iuh.fit.models.Product;

import java.util.ArrayList;
import java.util.Arrays;

public class ReceiverMq {
    public static void main(String[] args) throws JMSException {
        System.out.println("waiting...");
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        factory.setTrustedPackages(new ArrayList<>(Arrays.asList("vn.edu.iuh.fit.models")));
        Connection connection = factory.createConnection("admin", "admin");
        connection.start();

        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue("dymamicQueues/week5");
        MessageConsumer consumer = session.createConsumer(destination);
        consumer.setMessageListener(message -> {
            try{
                if (message instanceof TextMessage){
                    String msg = ((TextMessage) message).getText();
                    System.out.println(msg);
                }
                else if (message instanceof ObjectMessage){
                    Product oMsg = message.getBody(Product.class);
                    System.out.println(oMsg);
                }
            } catch (JMSException e) {
                throw new RuntimeException(e);
            }

        });


    }
}
