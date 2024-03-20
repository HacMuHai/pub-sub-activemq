package vn.edu.iuh.fit;

import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

public class ReceiverMq {
    public static void main(String[] args) throws JMSException {
        System.out.println("waiting...");
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = factory.createConnection("admin", "admin");

        connection.start();

        Session session = connection.createSession(/*trans*/false, /*ACK*/ Session.AUTO_ACKNOWLEDGE);

        Destination destination = session.createQueue("dymamicQueues/thanthidet");
        MessageProducer producer = session.createProducer(destination);

        /*TextMessage msg = session.createTextMessage("hi hello");
        producer.send(msg);
        connection.close();*/
        student st = new student(123l, "Le Huu Hiep");
        ObjectMessage oMsg = session.createObjectMessage(st);
        producer.send(oMsg);
        connection.close();

        System.out.println("sent.");
    }
}
