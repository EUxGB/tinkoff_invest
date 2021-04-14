import api.ApiTinkoff;
import bot.Bot;
import repository.BlackListShares;
import repository.MyIntervalsCandles;
import repository.Shares;
import ru.tinkoff.invest.openapi.OpenApi;
import ru.tinkoff.invest.openapi.models.market.CandleInterval;

import java.time.LocalDateTime;
import java.util.List;

import static ru.tinkoff.invest.openapi.models.market.CandleInterval.MONTH;
import static ru.tinkoff.invest.openapi.models.market.CandleInterval.ONE_MIN;

public class Comutator extends Thread {

    @Override
    public void run() {
        try {


            String myFigi = "BBG002BHBHM1"; //MNK
            List blackListShares = BlackListShares.getBlackListShares(true, true, false, false, false);
//        Bot bot = new Bot();  // запуск телеграм бота

            //ApiTinkoff.writeFileAllFigiCandles("notes3.txt",LocalDateTime.now().minusMonths(1), DAY);
            // List<Candle> candles1 = ApiTinkoff.getCandles(myFigi, LocalDateTime.now().minusDays(1), ONE_MIN);
            //System.out.println(candles1);


            //************************
            boolean killAllShares = false;                  // Удалить все данные из таблицы
            boolean sharesUpdate = false;                   // Добавить figi если они отсутсвуют в таблице "allshares"
            boolean killBlackShares = false;                // Удалить из базы тикеры для уменьшения количества записей в ней и ускорения обработки всех строк
            boolean sharesAVRUpdaterPV = false;             // Добавляет в базу средний объем и среднюю цену за месяц
            boolean sharesAVRUpdaterOneDayV = false;        // Добавляет в базу средний объем за сутки
            boolean FastScanerCandles = true;               // Сканирование всех акций 120шт./мин быстрое изменение объема
            boolean updateEMA = false;


            Shares shares = new Shares("allshares");

            if (killAllShares)
                shares.killAllData("allshares");
            if (updateEMA) {
                System.out.println("EMA true");
                shares.getEMA("BBG000N9MNX3",9,26,52,100,200, MyIntervalsCandles.MONTH);
            }

            while (sharesUpdate) {
                shares.updateData();
                sharesUpdate = false;
            }
            if (killBlackShares) {
                shares.killListData("allshares", "ticker", blackListShares);
                System.out.println("Удалено " + blackListShares.size() + " элементов");
            }
            if (sharesAVRUpdaterPV)
                shares.updateDataAveragePV(120, 60, LocalDateTime.now().minusYears(10), MONTH);  // средняя цена и средний объем за 10 лет
            if (sharesAVRUpdaterOneDayV)
                while (true) {
                    System.out.println(LocalDateTime.from(LocalDateTime.now()));
                    shares.updateDataOneDayAverageV(120, 60, LocalDateTime.now().minusMinutes(1339), ONE_MIN); // средний объем в минуту за 1 день (фиксируем) avr1MinDayVolume
                    Thread.sleep(70 * 1000);
                    //select figi, ticker, name, avr1MinDayVolume, currentavrv from allshares where avr1MinDayVolume>3008;
                }
            int p = 1;
            while (FastScanerCandles) {
                int q = 1;
                if (FastScanerCandles) {

                    System.out.println("start FastScanerCandles");
                    System.out.println("Этап № " + q++ + " Цикл № " + p++);
                    System.out.println("Объем из Телеграм установлен: " + Bot.getExternal_data_volume());
                    shares.setAvVolume(Bot.getExternal_data_volume());  //запрос объема по которому будет выводится сообщение в телеграм
                    shares.setSetupProcent(Bot.getExternal_data_procent());  //запрос объема по которому будет выводится сообщение в телеграм
                    shares.update5MinProcentV(120, 60, 10, 100);  // средний объем за последние 5 свечей (обновляем постоянно) procentAvrV
                    Thread.sleep(20 * 1000);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
