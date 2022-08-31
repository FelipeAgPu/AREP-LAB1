# ECI STOCKS

This repository contains a server application for searching stocks by company names
between two different API's

- [AlphaVantage](https://www.alphavantage.co)
- [Polygon](https://polygon.io)

## Heroku Deployment

The app is currently deployed in Heroku so you can use it if you click [here](https://stocks-server-arep.herokuapp.com) or go to the link below

```url
https://stocks-server-arep.herokuapp.com
```

## Architecture

This application uses a Client-Server architecture, distributed in one web page(Made in HTML, CSS and JS) that gets the information from a backend server(Made in Java Spark) that is connected to the API´s. Everything running in the same machine since it is not so complex.

## Stocks API

### Request

For using the stocks API you have to create an HTTP request to the server.

The request needed is a GET and it should look like this:

```
/stocks?provider=alpha&type=intra&company=AAPL
```

- Company: The stock company's name
- Provider: Name of the API you want to see (alpha or polygon)
- Type: For alpha you can search in different ways (intra, dia, semana, mes)

### Response

The response of the server will be the same from the API it is connecting so right now you can have two different responses but both are contained in a json file.

- Response for *AlphaVantage* API:

```json
{
  "Meta Data": {
    "1. Information": "Monthly Prices (open, high, low, close) and Volumes",
    "2. Symbol": "IBM",
    "3. Last Refreshed": "2022-08-30",
    "4. Time Zone": "US/Eastern"
  },
  "Monthly Time Series": {
    "2022-08-30": {
      "1. open": "130.7500",
      "2. high": "139.3400",
      "3. low": "129.1200",
      "4. close": "129.5800",
      "5. volume": "73901962"
    }...
}
```


- Response for *Polygon* API:

```json
{
 "status": "OK",
 "from": "2020-10-14",
 "symbol": "AAPL",
 "open": 121,
 "high": 123.03,
 "low": 119.62,
 "close": 121.19,
 "volume": 151057198,
 "afterHours": 120.81,
 "preMarket": 121.55
}
```

## Running the app


If you want to run the app locally you can do it by cloning this repository and run the *SparkWebApp* class in your preferred IDE or by CLI with this command:

```
java -cp target/classes:target/dependency/* edu.escuelaing.arem.designprimer.SparkWebApp
```

Be careful with the cp parameter since it is different for each OS, this example is for Linux OS.

## Features

### Cache

For managing the requests faster a cache memory system was added so if you have done a request before it is going to be loaded instantly and the server is not going to need to connect with the external API´s.

You can notice of this feature in the logs of the app:

```python
2022-08-31T04:36:37.547588+00:00 app[web.1]: Requesting to: https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=GE&interval=5min&apikey=*
2022-08-31T04:36:37.547622+00:00 app[web.1]: GET Response Code :: 200
2022-08-31T04:36:37.548164+00:00 app[web.1]: Retrieved stock from cache
```
More features coming soon ...

## Author

- **Juan Felipe Aguas Pulido** - Escuela Colombiana De Ingeniería Julio Garavito(2022)


