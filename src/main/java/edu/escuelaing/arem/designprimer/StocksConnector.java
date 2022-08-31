package edu.escuelaing.arem.designprimer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import org.apache.commons.lang3.time.DateUtils;

public class StocksConnector {

    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String GET_ERROR = "Ocurrió un error al conectarse al proveedor";
    private static final String ALPHA_URL = "https://www.alphavantage.co/query?function=%s&symbol=%s&interval=5min&apikey=%s";
    public static final String ALPHA = "alpha";
    private static final String ALPHA_KEY = "O934MYI2J5T3ZWY1";
    private static final String POLYGON_URL = "https://api.polygon.io/v1/open-close/%s/%s?apiKey=%s";
    public static final String POLYGON = "polygon";
    private static final String POLYGON_KEY = "G4Vz6gFNEeXXlIcwWBRMond5Klsh3jY3";


    public static String connect(String provider, String company, String type) throws IOException {

        URL obj = new URL(Objects.requireNonNull(generateURL(provider, company, type)));
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("Requesting to: " + obj.toString());
        System.out.println("GET Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        } else {
            return GET_ERROR;
        }
    }

    private static String generateURL(String provider, String company, String type){
        if (provider.equals(ALPHA)){
            switch (type){
                case "intra":
                    type = "TIME_SERIES_INTRADAY";
                    break;
                case "dia":
                    type = "TIME_SERIES_DAILY";
                    break;
                case "semana":
                    type = "TIME_SERIES_WEEKLY";
                    break;
                case "mes":
                    type = "TIME_SERIES_MONTHLY";
                    break;
            }
            return String.format(ALPHA_URL, type, company, ALPHA_KEY);
        }else if (provider.equals(POLYGON)){
            Date date = DateUtils.addHours(new Date(), -5);
            date = DateUtils.addDays(date, -1);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return String.format(POLYGON_URL, company, sdf.format(date), POLYGON_KEY);
        }
        return null;
    }
}
