package repository;

import ru.tinkoff.invest.openapi.models.market.Instrument;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Candles extends BaseTable implements TableOperation {

    Candles(String tableName) throws SQLException, ClassNotFoundException {
        super(tableName);
    }

    @Override
    public void insertTestData() throws SQLException, ClassNotFoundException {

    }

    @Override
    public void getData() {

    }

    @Override
    public void updateUpdate() {

    }

    @Override
    public void creatTable() throws SQLException, ClassNotFoundException {
        List list = new ArrayList();
        super.executeSqlStatement("CREATE TABLE IF NOT EXISTS share_rates(" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                "operDate datetime NOT NULL," +
                "share BIGINT NOT NULL,"+
                "rate DECIMAL(15,2) NOT NULL)", "Создана таблица " + tableName);

    }

    @Override
    public void creatForeignKeys() throws SQLException, ClassNotFoundException {

        super.executeSqlStatement(" ALTER TABLE share_rates ADD FOREIGN KEY (share) REFERENCES shares(id)",
                "Cоздан внешний ключ rates.share -> shares.id");
    }


       // Candle{figi='BBG000QCW561', interval=HOUR, openPrice=1.77, closePrice=1.77, highestPrice=1.77, lowestPrice=1.76, tradesValue=5035, time=2021-03-01T12:00Z}

        public void insertData(List< Instrument > list) throws SQLException, ClassNotFoundException {
            super.executeSqlStatement("DELETE from allshares; "
                    , "Очистка таблицы [allhares] ");
            super.executeSqlStatement("ALTER TABLE allshares AUTO_INCREMENT=0 "
                    , "Обнудение инкремента [allhares] ");

            final double[] d = new double[1];
            final int[] i = {0};
            list.forEach((e) ->
                    {
                        try {

                            if (e.minPriceIncrement == null) {
                                d[0] = 0;
                            } else d[0] = e.minPriceIncrement.doubleValue();
                            i[0]++;
                            super.executeSqlStatement("INSERT INTO allshares VALUES (NULL," +
                                            "'" + e.figi + "'," +                       //STRING
                                            "'" + e.ticker + "'," +                     //STRING
                                            "'" + e.isin + "'," +                       //STRING
                                            d[0] + "," +                                //BigDecimal
                                            e.lot + "," +                                //int
                                            "'" + e.currency.toString() + "'," +         //toString
                                            "'" + e.name.replace('\'', ';') + "'," +                       //STRING
                                            "'" + e.type.toString() + "')"              //toString
                                    , "В базу добавлен элемент "+e.ticker);

                        } catch (SQLException troubles) {
                            troubles.printStackTrace();
                        } catch (ClassNotFoundException classNotFoundException) {

                            classNotFoundException.printStackTrace();
                        }
                    }

            );
            System.out.println("Таблица [allshares] создана, в ней "+ i[0] +" элементов");

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

    }



