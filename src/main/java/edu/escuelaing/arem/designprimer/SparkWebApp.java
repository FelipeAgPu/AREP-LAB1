package edu.escuelaing.arem.designprimer;

import java.util.HashMap;

import static spark.Spark.*;

public class SparkWebApp {
    private static final HashMap<String, String> cache = new HashMap<String, String>();

    public static void main(String[] args) {
        port(getPort());
        staticFiles.location("/public");

        get("/stocks", (req, res) -> {
            String provider = req.queryParams("provider");
            String company = req.queryParams("company");
            String type = req.queryParams("type");

            String result = StocksConnector.connect(provider, company, type);

            String alpha = "A" + company + type;
            String poly = "P" + company;

            if (cache.containsKey(alpha) && provider.equals(StocksConnector.ALPHA)) {
                System.out.println("Retrieved stock from cache");
                return  cache.get(alpha);
            } else if (cache.containsKey(poly) && provider.equals(StocksConnector.POLYGON)) {
                System.out.println("Retrieved stock from cache");
                return  cache.get(poly);
            } else {
                if (provider.equals(StocksConnector.ALPHA)){
                    cache.put(alpha, result);
                }else {
                    cache.put(poly, result);
                }
            }

            return result;
        });


    }

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }
}
