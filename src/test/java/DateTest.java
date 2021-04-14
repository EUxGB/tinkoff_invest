import api.ApiTinkoff;
import ru.tinkoff.invest.openapi.OpenApi;
import ru.tinkoff.invest.openapi.models.market.Candle;
import ru.tinkoff.invest.openapi.models.market.CandleInterval;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class DateTest {


    public static void main(String[] args) throws InterruptedException {
        OpenApi api = ApiTinkoff.initApi(true);
        List<Candle> list = new ArrayList();
         list =ApiTinkoff.getCandles("BBG000C496P7", LocalDateTime.now().minusDays(1), CandleInterval.ONE_MIN);
        List listtime = new ArrayList();

        list.stream().skip(53).forEach(element -> {
            listtime.add(element.time);
//            System.out.println(element.time.getClass());

        });

        for (int i = 0; i < 2; i++) {

            LocalDateTime ldt = LocalDateTime.now();
            ZoneOffset offset = ZoneOffset.UTC;
            OffsetDateTime odt = ldt.atOffset(offset);

            System.out.println();
            System.out.println(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
            System.out.println(listtime.get(i) + "  минус "+listtime.get(i+1)+ "   =  " +ChronoUnit.MINUTES.between((OffsetDateTime) listtime.get(i),
                    odt));
        }


            LocalDateTime localDateTime1 = LocalDateTime.now();
        LocalDateTime localDateTime2;
//        Thread.sleep(10 * 1000);

        System.out.println(ChronoUnit.SECONDS.between(localDateTime1, LocalDateTime.now()));
        System.out.println(list);


    }
}
