package daytrips;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class EmailSender extends TimerTask {
    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            System.out.println(i + " = " + UUID.randomUUID().toString());
        }
        Timer t = new Timer();
        EmailSender email = new EmailSender();
        Long time = new Date().getTime();
        Date date = new Date(time - time % (24 * 60 * 60 * 1000));
        t.scheduleAtFixedRate(email, new Date(date.getTime() + 24 * 60 * 60 * 1000), 86400000);
    }

    private void sendEmail(Booking b) {
        
    }

    @Override
    public void run() {
        ArrayList<Booking> bookings = DayTripController.getBookings(new Hashtable<>());
        for (Booking b: bookings) {
            if (Period.between(b.getDate(), LocalDate.now()).getDays() <= 1) {
                sendEmail(b);
            }
        }
    }
}