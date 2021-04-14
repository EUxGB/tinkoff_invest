package repository;

import ru.tinkoff.invest.openapi.models.market.CandleInterval;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// класс введен для исключения появления ошибки при установки значений интервалов в методе getCandles
public class DateHandler {
    //        Api intervals
    //        - 1min [1 minute, 1 day]
    //        - hour [1 hour, 7 days]
    //        - day [1 day, 1 year]
    //        - week [7 days, 2 years]
    //        - month [1 month, 10 years]

    public static List<Object> dateHandler(MyIntervalsCandles myIntervalsCandles) {
        List<Object> list = new ArrayList<>();

        switch (myIntervalsCandles) {
            case MIN:
                list.add(0, LocalDateTime.now().minusDays(1));
                list.add(1, CandleInterval.ONE_MIN);
                list.add(2, 200);
            break;
            case HOUR:
                list.add(0, LocalDateTime.now().minusDays(7));
                list.add(1, CandleInterval.HOUR);
                list.add(2, 120);
            break;
            case DAY:
                list.add(0, LocalDateTime.now().minusYears(1));
                list.add(1, CandleInterval.DAY);
                list.add(2, 200);
            break;
            case WEEK:
                list.add(0, LocalDateTime.now().minusMonths(23));
                list.add(1, CandleInterval.WEEK);
                list.add(2, 96);
                break;
            case MONTH:
                list.add(0, LocalDateTime.now().minusYears(10));
                list.add(1, CandleInterval.MONTH);
                list.add(2, 120);
                break;
        }
        return list;
    }
}
