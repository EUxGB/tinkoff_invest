package repository;

import api.ApiTinkoff;
import bot.Bot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.tinkoff.invest.openapi.models.market.Candle;
import ru.tinkoff.invest.openapi.models.market.CandleInterval;
import ru.tinkoff.invest.openapi.models.market.Instrument;
import sound.SoundBip;


import java.sql.Array;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Handler;

import static ru.tinkoff.invest.openapi.models.market.CandleInterval.DAY;
import static ru.tinkoff.invest.openapi.models.market.CandleInterval.ONE_MIN;

public class Shares extends BaseTable implements TableOperation {
    int avVolume = 3000;
    int setupProcent = 20;
    int k = 1;
    Bot myBot = new Bot();

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public void setAvVolume(int avVolume) {
        this.avVolume = avVolume;
    }

    public int getAvVolume() {
        return avVolume;
    }

    public int getSetupProcent() {
        return setupProcent;
    }

    public void setSetupProcent(int setupProcent) {
        this.setupProcent = setupProcent;
    }

    public Shares(String tableName) throws SQLException, ClassNotFoundException, TelegramApiException {
        super(tableName);
    }

    @Override
    public void insertTestData() throws SQLException, ClassNotFoundException {
        super.executeSqlStatement("INSERT INTO shares VALUES (50, 'n' )"
                , "Создана таблица " + tableName);


    }

    @Override
    public void getData() {

    }

    @Override
    public void updateUpdate() {

    }

    @Override
    public void creatTable() throws SQLException {

    }

    @Override
    public void creatForeignKeys() throws SQLException {

    }

    public void insertData(List<Instrument> list) throws SQLException, ClassNotFoundException {

        final double[] d = new double[1];
        final int[] i = {0};
        list.forEach((e) ->
                {
                    try {

//                        if (e.minPriceIncrement == null) {
//                            d[0] = 0;
//                        } else d[0] = e.minPriceIncrement.doubleValue();
//                        i[0]++;

                        if (e.minPriceIncrement != null) {
                            if (true) {
                                super.executeSqlStatement("INSERT INTO allshares VALUES (" +
                                                "NULL," +                                    // Id
                                                "'" + e.figi + "'," +                       //STRING
                                                "'" + e.ticker + "'," +                     //STRING
                                                "'" + e.isin + "'," +                       //STRING
                                                e.minPriceIncrement.doubleValue() + "," +                                //BigDecimal
                                                e.lot + "," +                                //int
                                                "'" + e.currency.toString() + "'," +         //toString
                                                "'" + e.name.replace('\'', ';') + "'," +                       //STRING
                                                "'" + e.type.toString() + "'," +
                                                0.0 + "," +
                                                0.0 + "," +
                                                0.0 + "," +
                                                0.0 + "," +
                                                0.0 + "," +
                                                0.0 + "," +
                                                0.0 + ")"              //toString
                                        , "В базу добавлен элемент " + e.ticker + " - " + e.name);
                            }
                        }
                    } catch (SQLException troubles) {
                        troubles.printStackTrace();
                    } catch (ClassNotFoundException classNotFoundException) {

                        classNotFoundException.printStackTrace();
                    }
                }


        );
        System.out.println("В таблице строк: " + super.extractSqlStatement("SELECT count(*) from allshares; "
                , null));
        if (i[0] == 0) {
            System.out.println("Новых инструментов Тинек не добавил");
        } else {
            System.out.println("В Таблицу [allshares]  добавлено " + i[0] + " элементов");
        }
//figi='BBG005BT60Y8', ticker='NVEE', isin='US62945V1098', minPriceIncrement=0.01, lot=1, currency=USD, name='NV5 Global Inc', type=Stock
//        String figi;
//        String ticker;
//        String isin;
//        BigDecimal minPriceIncrement;
//        int lot;
//        Currency currency;
//        String name;
//        Instrument type;
//        BigDecimal high52 = null;
//        BigDecimal low52 = null;

    }

    public String updateDataAveragePV(int numberElementOfMinute, int threadSleep, LocalDateTime localDateTime, CandleInterval candleInterval) throws SQLException, ClassNotFoundException {
        final int[] NumberЕlement = {0};
        List listSql = super.extractSqlStatement("SELECT figi from allshares; "
                , "выбран столбец [allhares] -figi ");

        double[] highestPrice = {0};
        double[] lowestPrice = {1000000};
        long[] volumeFigi = {0};
        AtomicReference<AtomicInteger> N = new AtomicReference<>(new AtomicInteger(0));
        double[] arrAveragePriceVolume = new double[2]; //[цена][объем]
        listSql.forEach((myFigi) -> {
            ApiTinkoff.getCandles((String) myFigi, localDateTime, candleInterval).forEach(element -> {
                lowestPrice[0] = Math.min(lowestPrice[0], element.lowestPrice.longValue());
                highestPrice[0] = Math.max(highestPrice[0], element.highestPrice.doubleValue());
                volumeFigi[0] = volumeFigi[0] + element.tradesValue.longValue();
                N.get().getAndIncrement();
//                System.out.println(element);
            });

            arrAveragePriceVolume[0] = (highestPrice[0] + lowestPrice[0]) / 2;  //присваиваем первому эл. массива значение средней цены
            arrAveragePriceVolume[1] = N.get().get() != 0 ? volumeFigi[0] / N.get().get() : 1; //присваиваем второму эл. массива значение среднего объема

            try {
                if (NumberЕlement[0] % numberElementOfMinute == 0) Thread.sleep(threadSleep * 1000);
                super.executeSqlStatement(" UPDATE allshares SET " +
                                "avrPrice = " + arrAveragePriceVolume[0] + ", " +
                                "avrVolume = " + arrAveragePriceVolume[1] + "" +
                                " WHERE figi = '" + myFigi + "'"
                        , "В базу добавлен годовой элемент " + ++NumberЕlement[0] + " ) " + myFigi + " цена- " + arrAveragePriceVolume[0] + " объем- " + arrAveragePriceVolume[1]);


                highestPrice[0] = 0;
                lowestPrice[0] = 1000000;
                volumeFigi[0] = 0;
                N.set(new AtomicInteger(0));
                arrAveragePriceVolume[0] = 0;
                arrAveragePriceVolume[1] = 0;

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("Обновлены средние цена и объем шт. = " + NumberЕlement[0]);

        return "Обновлены значения средних Цены и Объема";
    }

    public String updateDataOneDayAverageV(int numberElementOfMinute, int threadSleep, LocalDateTime localDateTime, CandleInterval candleInterval) throws SQLException, ClassNotFoundException {

//        Интервал свечи и допустимый промежуток запроса:
//        - 1min [1 minute, 1 day]
//        - 2min [2 minutes, 1 day]
//        - 3min [3 minutes, 1 day]
//        - 5min [5 minutes, 1 day]
//        - 10min [10 minutes, 1 day]
//        - 15min [15 minutes, 1 day]
//        - 30min [30 minutes, 1 day]
//        - hour [1 hour, 7 days]
//        - day [1 day, 1 year]
//        - week [7 days, 2 years]
//        - month [1 month, 10 years]
        final int[] NumberЕlement = {1};
        List listSql = super.extractSqlStatement("SELECT figi from allshares; "
                , "выбран столбец [allhares] -figi ");
        String name = null;
        double[] highestPrice = {0};
        double[] lowestPrice = {1000000};
        long[] volumeFigi = {0};
        double[] arrAveragePriceVolume = new double[2];
        listSql.forEach((myFigi) -> {
            System.out.println(myFigi);
            AtomicInteger N = new AtomicInteger();
            ApiTinkoff.getCandles((String) myFigi, localDateTime, candleInterval).forEach(element -> {
                volumeFigi[0] = volumeFigi[0] + element.tradesValue.longValue();
                N.getAndIncrement();
            });

            arrAveragePriceVolume[0] = (highestPrice[0] - lowestPrice[0]) / 2;  //присваиваем первому эл. массива значение средней цены
            arrAveragePriceVolume[1] = N.get() != 0 ? volumeFigi[0] / N.get() : 1; //присваиваем второму эл. массива значение среднего объема
            try {
                if (NumberЕlement[0] % numberElementOfMinute == 0) Thread.sleep(threadSleep * 1000);
                super.executeSqlStatement(" UPDATE allshares SET " +
                                "avr1MinDayVolume = " + arrAveragePriceVolume[1] + "" +
                                " WHERE figi = '" + myFigi + "'"
                        , "В базу добавлен элемент средний дневной объем " + NumberЕlement[0]++ + " ) " +
                                myFigi + " - " + arrAveragePriceVolume[1]);


                highestPrice[0] = 0;
                lowestPrice[0] = 1000000;
                volumeFigi[0] = 0;
                N.set(0);
                arrAveragePriceVolume[0] = 0;
                arrAveragePriceVolume[1] = 0;

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
        List listTop = super.extractSqlStatement("select ticker from allshares where avr1mindayvolume>5000;", "Акции с большим потенциалом изменения");
        listTop.forEach(e -> {
            System.out.println(e);
        });
        return "Обновлено заначение среднего объема для промежутка времени " + candleInterval;
    }

    public void update5MinProcentV(int NumberElementOfMinute, int threadSleap, int numberOfCandles, int setupDeltaTimeCandle) throws SQLException, ClassNotFoundException, InterruptedException, TelegramApiException {
        SoundBip soundBip = new SoundBip();
        AtomicReference<List<Candle>> tempCandles = null;
        final int[] NumberЕlement = {0};
        List listSql = super.extractSqlStatement("SELECT figi from allshares; "
                , "выбран столбец [allhares] -figi ");
        double procentik = 0;
        long[] volumeFigi = {0};
        int[] N = {0};
        double averageVolume = 0; // [цена ] [ объем ]

        List blackListFigi = new ArrayList();
        HashMap<String, Long> lastfigiAvr_V = new HashMap<>();
        HashMap<String, Long> lastfigiAvr_P = new HashMap<>();
        HashMap<String, Long> curentfigiAvr_V = new HashMap<>();
        HashMap<String, Long> curentfigiAvr_P = new HashMap<>();
        List<Candle> candles1;
        int deltaTime = 1_000_000;

//        Добавить реализацию добавления цены и разницы открытия и закрытия цены свечи в базу
        double currentPrice = 0;
        double deltaOpenCloseCandle = 0;


        //ловим ошибку оброботки дат
        try {

            for (Object myFigi : listSql) {
                candles1 = ApiTinkoff.getCandles((String) myFigi, LocalDateTime.now().minusDays(1), CandleInterval.ONE_MIN);

                if (candles1 != null && candles1.size() > 0) {
                    LocalDateTime ldt = LocalDateTime.now();
                    ZoneOffset offset = ZoneOffset.ofHours(+3);
                    OffsetDateTime odt = ldt.atOffset(offset);
                    deltaTime = (int) ChronoUnit.MINUTES.between((OffsetDateTime) candles1.get(candles1.size() - 1).time, odt);
                    currentPrice = candles1.get(candles1.size() - 1).closePrice.doubleValue();
                    deltaOpenCloseCandle = candles1.get(candles1.size() - 1).closePrice.doubleValue() - candles1.get(candles1.size() - 1).openPrice.doubleValue();

                }

                if (candles1 == null || candles1.size() == 0 || deltaTime > setupDeltaTimeCandle) {
                    blackListFigi.add(myFigi);
                } else {
                    candles1.stream().skip(candles1.size() - numberOfCandles).forEach(element -> {
                        volumeFigi[0] = volumeFigi[0] + element.tradesValue.longValue();
                        N[0]++;
                    });

                    //присваиваем второму эл. массива значение среднего объема
                    if (N[0] != 0) {
                        averageVolume = volumeFigi[0] / numberOfCandles;
                    } else {
                        averageVolume = 0;
                    }

                    NumberЕlement[0]++;

                    String ticker = extractOneStringElementSql("SELECT ticker FROM allshares WHERE figi = '" + myFigi + "'", null);
                    double currentAvrV = extractOneElementSql("SELECT currentAvrV FROM allshares WHERE figi = '" + myFigi + "'", null);

                    if (currentAvrV != 0) {
                        procentik = (int) (((averageVolume / currentAvrV) - 1) * 100);  // процент изменения объема
                    }

                    //добавляем в базу текущий средний объем за N свечей и процент изменения объма
                    super.executeSqlStatement(" UPDATE allshares SET " +
                                    "currentAvrV = " + averageVolume +
                                    ",QCandlesOfDay = " + candles1.size() +
                                    ",ProcentVolumeCandle = " + procentik +
                                    " WHERE figi = '" + myFigi + "'"
                            , null);
                    int procentBot = getSetupProcent();
                    if (procentik >= 20 && averageVolume > getAvVolume() && candles1.size() > 0) {
                        soundBip.extractedSound(1, 67, 20, 500);
                        if (procentik > 20 * 10) {
                            soundBip.extractedSound(3, 67, 50, 2000);

                        }

                        System.out.println(NumberЕlement[0] + " ) Превышение " + ticker + " объем был = " + currentAvrV + "  объем стал =" + averageVolume + " процент " + procentik + "                       время " + LocalTime.now());
                        myBot.sentMessageToChat(ticker + "        " + (int) procentik + " %\n " + (int) currentAvrV + " объем был\n" + (int) averageVolume + " объем стал\n" + LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS) + " время");

                    }

                    volumeFigi[0] = 0;
                    N[0] = 0;
                    averageVolume = 0;
                    procentik = 0;


                    if (NumberЕlement[0] % NumberElementOfMinute == 0) {

                        System.out.println("ждем");
                        Thread.sleep(threadSleap * 1000);
                        //myBot.sentMessageToChat("Поиск № " + k);

                        System.out.println("start № " + k++);
                        setK(k);
                        System.out.println(getK());
                    }
                }
            }
        } catch (CompletionException e) {
            System.out.println("Catch *повторный запуск метода*");
            e.printStackTrace();
            Thread.sleep(threadSleap * 1000);
            update5MinProcentV(numberOfCandles, threadSleap, numberOfCandles, setupDeltaTimeCandle);
        }

        if (blackListFigi.size() > 0) {
            System.out.println("Удалены figi: " + blackListFigi.toString());
            blackListFigi.forEach(e -> {
                try {
                    super.executeSqlStatement("DELETE from allshares WHERE figi = '" + e + "'; "
                            , null);
                    System.out.println("Удален: " + e);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }
            });
            System.out.println("Время обработки сократилось до " + (listSql.size() - blackListFigi.size()) / NumberElementOfMinute + "минут");
        }
        System.out.println("STOP");
    }

    //обновить figi  , исключить акции с небольшим кол-вом минутных свечей за сутки
    public String updateData() throws SQLException, ClassNotFoundException, ExecutionException, InterruptedException {
        List<Instrument> list = ApiTinkoff.getListFigi();
        List listSql = super.extractSqlStatement("SELECT figi from allshares; "
                , "выбран столбец [allhares] -figi ");

        List<Instrument> newList = new ArrayList<>();
        for (Instrument instrument : list) {


            if (!listSql.contains(instrument.figi)) {
                newList.add(instrument);
                System.out.println(instrument.name + " = " + instrument.ticker);
            }


        }
        // сформирован лист с новыми интсрументами, давляем в базу только новые старые данные не трогаем.
        double lastCountShares = super.extractOneElementSql("select count(*) from allshares;", null);
        insertData(newList);
        Thread.sleep(10 * 1000);
        double currentCountShares = super.extractOneElementSql("select count(*) from allshares;", null);
        myBot.sentMessageToChat("Акций было " + (int) lastCountShares + ", стало " + (int) currentCountShares);

        return "Добавлено" + newList.size() + " строк";
    }


    //        Интервал свечи и допустимый промежуток запроса:
//        - 1min [1 minute, 1 day]
//        - 2min [2 minutes, 1 day]
//        - 3min [3 minutes, 1 day]
//        - 5min [5 minutes, 1 day]
//        - 10min [10 minutes, 1 day]
//        - 15min [15 minutes, 1 day]
//        - 30min [30 minutes, 1 day]
//        - hour [1 hour, 7 days]
//        - day [1 day, 1 year]
//        - week [7 days, 2 years]
//        - month [1 month, 10 years]
    public double[] getEMA(String myFigi, double EMA9, double EMA26, double EMA52, double EMA100, double EMA200, MyIntervalsCandles interval) {

        double EMA = 0;
        double closePrice = 0;
        double[] emaArr = {closePrice, EMA9, EMA26, EMA52, EMA100, EMA200};
        double F = 0;

        List<Candle> list_Candle = ApiTinkoff.getCandles(
                myFigi,
                (LocalDateTime) DateHandler.dateHandler(interval).get(0),
                (CandleInterval) DateHandler.dateHandler(interval).get(1)
        );

        int listSize = list_Candle.size();

        if (listSize < emaArr[5]) {
            emaArr[5] = listSize;
            if (listSize < emaArr[4]) {
                emaArr[4] = listSize;
                if (listSize < emaArr[3]) {
                    emaArr[3] = listSize;
                    if (listSize < emaArr[2]) {
                        emaArr[2] = listSize;
                        if (listSize < emaArr[1]) {
                            emaArr[1] = listSize;
                        }
                    }
                }
            }
        }


        System.out.println(list_Candle.size());
        if (list_Candle != null && list_Candle.size() > emaArr[1]) {

            //получаем значение сдвига  v0 = 9
            for (int i = 1; i < emaArr.length; i++) {
                double v = emaArr[i];

                //вычисляем первое сколзящее среднее для дальнеейшего расчета
                for (int k = 0; k < v; k++) {
                    EMA = list_Candle.get(k).closePrice.doubleValue() + EMA;

                }

                // получено значение скользящего среднего EMA0
                EMA = EMA / (v);

                for (int j = 0; j < list_Candle.size() - v; j++) {
                    //вычисляем коффициент для EMA9 и последующих EMA
                    F = 2 / (v + 1);
                    //основнаая формула
                    EMA = F * list_Candle.get((int) v + j).closePrice.doubleValue() + (1 - F) * EMA;
                }
                emaArr[i] = EMA;

            }
            emaArr[0] = list_Candle.get(list_Candle.size() - 1).closePrice.doubleValue();
            System.out.println(Arrays.toString(emaArr));

        }

        return emaArr;
    }

}


