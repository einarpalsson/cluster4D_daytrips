package daytrips;
import java.time.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailSender extends TimerTask {
    private static final String serverEmail = "noreply@vatsimspain.es";
    private static final String emailPassword = "1caraculopara2";
    private Properties props = new Properties();
        
    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            System.out.println(i + " = " + UUID.randomUUID().toString());
        }
        props.put("mail.smtp.host", "135.125.190.125");
        props.put("mail.smtp.socketFactory.port", "25");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "25");
        Timer t = new Timer();
        EmailSender email = new EmailSender();
        Long time = new Date().getTime();
        Date date = new Date(time - time % (24 * 60 * 60 * 1000));
        t.scheduleAtFixedRate(email, new Date(date.getTime() + 24 * 60 * 60 * 1000), 86400000);
    }

    private void sendReminderEmail(Booking b) {
        Hashtable<String, Object> p = new Hashtable();
        p.put("dayTripId", b.getDayTripId());
        DayTrip dt = DayTripController.getDayTrips(p).get(0);
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(serverEmail, emailPassword);
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(b.getClientEmail()));
            message.setSubject("[DayTrip Reminder] One day left!");
            message.setText("Be ready!\n Tomorrow at "+ dt.getTimeStart()+ 
                    " you are going to " + dt.getName()+"\nWe're excited to see you!");
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendConfirmationEmail(Hashtable<String, Object> params) {
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(serverEmail, emailPassword);
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(b.getClientEmail()));
            message.setSubject("[DayTrip Confirmation] Thank you for choosing us!");
            message.setText("You wont be disappointed!\n You have booked a trip with us for the "+ params.get("date")+ 
                    "\nWe look forward to see you!");
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        ArrayList<Booking> bookings = DayTripController.getBookings(new Hashtable<>());
        for (Booking b: bookings) {
            if (Period.between(b.getDate(), LocalDate.now()).getDays() <= 1) {
                sendReminderEmail(b);
            }
        }
    }
}