public class Main {

    private static final long TIMEOUT = 1000;

    public static void main(String[] args) {
        new Thread(new Server()::work).start();
        try {
            Thread.sleep(TIMEOUT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(new Client()::work).start();
    }
}