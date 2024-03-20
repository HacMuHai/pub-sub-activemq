package vn.edu.iuh.fit.mq;

import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import vn.edu.iuh.fit.models.Product;

public class SenderMq {
    public static void main(String[] args) throws JMSException {
        System.out.println("waiting...");
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = factory.createConnection("admin", "admin");
        connection.start();

        Session session = connection.createSession(/*trans*/false, /*ACK*/ Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue("dymamicQueues/week5");
        MessageProducer producer = session.createProducer(destination);

        //Send obj
        Product product = new Product(1,"Ao dai",120000);
        ObjectMessage oMsg = session.createObjectMessage(product);
        producer.send(oMsg);

        //Send mess
        TextMessage msg = session.createTextMessage("I sent 1 product");
        producer.send(msg);
        connection.close();

        System.out.println("Sented");
    }
}
