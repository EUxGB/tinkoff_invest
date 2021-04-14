import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


class MyFirstThread extends Thread {

        @Override
        public void run() {
                System.out.println("Start " + getName());

        }

        void sendMsgHello() {
                String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";
                String apiToken = "1732743498:AAFNrsLv5_uONntFA2vLYR8hB-rbDHZQ2To";
                String chatId = "463569095";
                String text = "Начал работу поток №  " + getName();
                urlString = String.format(urlString, apiToken, chatId, text);

                URL url = null;
                try {
                        url = new URL(urlString);
                } catch (MalformedURLException e) {
                        e.printStackTrace();
                }

                URLConnection conn = null;
                try {
                        conn = url.openConnection();
                } catch (IOException e) {
                        e.printStackTrace();
                }


                InputStream is = null;
                try {
                        is = new BufferedInputStream(conn.getInputStream());
                } catch (IOException e) {
                        e.printStackTrace();
                }
                try {
                        Thread.sleep(10*1000);
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }


        }

        void dateAnaliz() {
                LocalDateTime time1 =LocalDateTime.now();
                LocalDateTime time2 =LocalDateTime.now();
                System.out.println(time1);
                System.out.println(time2);

        }
}

class MyFirstRunnable implements Runnable {

        @Override
        public void run() {
             //   System.out.println("Поток номер: " + Thread.currentThread().getName());

        }
}



public class Test   {
    public static void main(String[] args) throws InterruptedException {
//https://api.telegram.org/bot1732743498:AAFNrsLv5_uONntFA2vLYR8hB-rbDHZQ2To/sendMessage?chat_id=463569095&text=DDDDDD

            for (int i = 0; i < 10; i++) {
                    new Thread(() ->System.out.println("OOOOOO   " +Thread.currentThread().getName())).start();
            }


            System.out.println("END");

             //Thread.sleep(5*1000);


//            t1.join();
//            t2.join();



//            String inputLine = "";
//            while ((inputLine = br.readLine()) != null) {
//                sb.append(inputLine);
//            }
//            String response = sb.toString();



    }
}


