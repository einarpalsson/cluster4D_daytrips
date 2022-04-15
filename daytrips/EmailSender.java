import java.util.UUID;

class EmailSender {
    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            System.out.println(i + " = " + UUID.randomUUID().toString());
        }
    }
}