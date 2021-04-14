import java.sql.SQLOutput;

class WOorkThread extends Thread {
    @Override
    public void run() {
        int i = 0;
        while (true) {
            i++;
            if (interrupted()) {
                System.out.println("Intrrrupted " + Thread.currentThread().getName() +"  i= " + i);
                break;
            }
        }
    }

}


class SleepThread extends Thread {

    @Override
    public void run() {
        try {
            Thread.sleep(100000000L);
        } catch (InterruptedException e) {
            System.out.println("Intrrrupted " + Thread.currentThread().getName());
        }

    }
}


public class THTest {
    public static void main(String[] args) throws InterruptedException {
        WOorkThread wOorkThread = new WOorkThread();
        SleepThread sleepThread = new SleepThread();

        wOorkThread.setDaemon(true);
        sleepThread.setDaemon(true);

        System.out.println("Start");
        wOorkThread.start();
        sleepThread.start();

        Thread.sleep(100L);

        System.out.println("Interrupt");

//        wOorkThread.interrupt();
//        sleepThread.interrupt();

//        System.out.println("Joined Threads");
//        wOorkThread.join();
//        sleepThread.join();

        System.out.println("All done");

    }

}
