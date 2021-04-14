import api.ApiTinkoff;
import bot.Bot;
import repository.BlackListShares;
import repository.Shares;
import ru.tinkoff.invest.openapi.OpenApi;
import ru.tinkoff.invest.openapi.models.market.Candle;

import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Logger;

import static ru.tinkoff.invest.openapi.models.market.CandleInterval.*;

public class Application {

    static Logger logger = Logger.getLogger(Application.class.toString());

    public static void  main(String[] args) throws Exception {
        OpenApi api = ApiTinkoff.initApi(true);
        new Bot();
        Thread comutatorMain = new Comutator();
//        comutatorMain.start();


                comutatorMain.start();





//        String myFigi = "BBG000C496P7"; //VIAC

//        String myFigi = "BBG000QCW561"; //VEON
//        String myFigi = "BBG000C1JTL6 "; //total
//        String myFigi = "BBG004731032"; //Лукойл
//        String myFigi = "BBG00HTN2CQ3"; //Спэйс



// !!!!!!!!!!!!       **********************************
//        List<Instrument> list = new ArrayList();
//        list = getListFigi(api);
//        System.out.println(list);
//        int i=0;
//        for (Instrument instrument : list) {
//            getFigiAveragePriceVolume(instrument.figi, api);
//            System.out.println(i++);
//            Thread.sleep(500);
//            Thread.sleep(100);
//            if (i>=120) Thread.sleep(100);
//        }
//
//
//        if (false) {
//            getHashMapPriceCandle(myFigi, api);
//            getListFigi(api); //        Все Figi
//            System.out.println(getMyPortfolio(api));
//        }
//        **********************************

//        writeFileAllFigiCandles(getNulltMapAllFigi_100_Candles(100));
            //!!!  writeFileAllFigiCandles(getMapAllFigiCandles(api));


//        int myQ = 1;
//        BigDecimal myPrice = new BigDecimal(1);

            //Покупка
//        api.getOrdersContext().placeMarketOrder(myFigi,
//                new MarketOrder(myQ,Operation.Buy),
//                api.getUserContext().getAccounts().get().accounts.get(0).brokerAccountId).get();

//Покупка лимитный ордер
//        System.out.println("buyLimit");
//        api.getOrdersContext().placeLimitOrder(myFigi,
//                new LimitOrder(myQ,Operation.Buy,myPrice),
//                api.getUserContext().getAccounts().get().accounts.get(0).brokerAccountId).get();
//        System.out.println("ready");

            //Продажа
//        api.getOrdersContext().placeMarketOrder(myFigi,
//                new MarketOrder(myQ,Operation.Sell),
//                api.getUserContext().getAccounts().get().accounts.get(0).brokerAccountId).get();
//Продажа лимитный ордер
//        System.out.println("sellLimit");
//        api.getOrdersContext().placeLimitOrder(myFigi,
//                new LimitOrder(myQ,Operation.Sell,myPrice),
//                api.getUserContext().getAccounts().get().accounts.get(0).brokerAccountId).get();
//        System.out.println("ready");
//Получение значения свечи


//        hashMapVolumeList.clear();
//        api.getMarketContext().getMarketCandles(myFigi,
//                OffsetDateTime.of(LocalDateTime.from(LocalDateTime.now().minusDays(1)), ZoneOffset.UTC),
//                OffsetDateTime.of(LocalDateTime.from(LocalDateTime.now()), ZoneOffset.UTC),
//                CandleInterval.HOUR).join().get().candles.forEach( element ->{
//           // System.out.println(element.time+" " + element.closePrice + " " + element.tradesValue);
//            hashMapVolumeList.put(element.time, element.tradesValue);
//            hashMapDatePrice.put(element.time, element.closePrice);
            // hashMapDatePrice.containsKey(OffsetDateTime.of(LocalDateTime.now()));

//        });
//        System.out.println(hashMapVolumeList);
//        System.out.println(hashMapDatePrice);

//
//        while (true) {
//
//            api.getMarketContext().getMarketCandles(myFigi,
//                    OffsetDateTime.of(LocalDateTime.from(LocalDateTime.now().minusMonths(10)), ZoneOffset.UTC),
//                    OffsetDateTime.of(LocalDateTime.from(LocalDateTime.now()), ZoneOffset.UTC),
//                    CandleInterval.DAY).join().get().candles.forEach(element -> {
//                System.out.println(element.time + " " + element.closePrice + " " + element.tradesValue);
//
//            });
//            Thread.sleep(60 * 1000);
//
//
//        }


//Получение глубины стакана
//        api.getMarketContext().getMarketOrderbook(myFigi, 10).join().get().asks.forEach(element -> {
//            System.out.println(element.quantity);
//        });
//Поиск по figi
            //System.out.println(api.getMarketContext().searchMarketInstrumentByFigi(myFigi).join().get());


    }

}

