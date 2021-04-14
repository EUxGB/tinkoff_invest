package model;

import ru.tinkoff.invest.openapi.models.market.Candle;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

public class CandlesFigi   {

    String figi;
    String interval;
    Double openPrice;
    Double closePrice;
    Double highestPrice;
    Double lowestPrice;
    Long tradesValue;
    OffsetDateTime time;


    public CandlesFigi(String figi, String interval, Double openPrice, Double closePrice, Double highestPrice, Double lowestPrice, Long tradesValue, OffsetDateTime time) {
        this.figi = figi;
        this.interval = interval;
        this.openPrice = openPrice;
        this.closePrice = closePrice;
        this.highestPrice = highestPrice;
        this.lowestPrice = lowestPrice;
        this.tradesValue = tradesValue;
        this.time = time;
    }

    public Double getClosePrice() {
        return closePrice;
    }

    public Long getTradesValue() {
        return tradesValue;
    }

    public OffsetDateTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "CandlesFigi{" +
                "figi='" + figi + '\'' +
                ", interval='" + interval + '\'' +
                ", openPrice=" + openPrice +
                ", closePrice=" + closePrice +
                ", highestPrice=" + highestPrice +
                ", lowestPrice=" + lowestPrice +
                ", tradesValue=" + tradesValue +
                ", time=" + time +
                '}';
    }
}
