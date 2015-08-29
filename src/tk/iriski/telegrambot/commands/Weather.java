package tk.iriski.telegrambot.commands;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import tk.iriski.telegrambot.Constants;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Weather extends Command {
    public Weather() {
        setKey("weather");
        setDescription("Shows weather for choosen city. Example: weather city (date)");
    }

    @Override
    public String start(String values[]) {
        StringBuilder answer = new StringBuilder();
        try {
            StringBuilder url = new StringBuilder();
            StringBuilder city = new StringBuilder();
            SimpleDateFormat requestSDF = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat parseSDF = new SimpleDateFormat("dd.MM.yyyy");
            String date = null;
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, 1);

            for (int i = 0; i < values.length; i++) {
                if (i == (values.length - 1)) {
                    if (values[i].matches("([0-9]){2}\\.([0-9]){2}\\.([0-9]){4}")) {
                        date = requestSDF.format(parseSDF.parse(values[i]));
                    } else if (values[i].toLowerCase().equals("tomorrow")) {
                        date = requestSDF.format(cal.getTime());
                    } else if (values[i].toLowerCase().equals("today"))
                        date = "today";
                    else city.append(values[i]).append(' ');
                } else city.append(values[i]).append(' ');
            }

            if (city.length() == 0) return "City is null";
            CloseableHttpClient client = HttpClientBuilder.create().build();
            url.append(Constants.WEATHER_API_URL).append("?q=").append(URLEncoder.encode(city.toString(), "UTF-8")).append("&showlocaltime=yes&num_of_days=1&format=json&date=")
                    .append(date).append("&key=").append(Constants.WEATHER_API_KEY);
            HttpGet request = new HttpGet(url.toString());

            CloseableHttpResponse response = client.execute(request);

            String responseLine = EntityUtils.toString(response.getEntity(), "UTF-8");
            System.out.println(responseLine);

            JSONObject dataObject = new JSONObject(responseLine).getJSONObject("data");
            answer.append("Weather for ").append(dataObject.getJSONArray("request").getJSONObject(0).getString("query")).append(" on ").append(date).append('\n');
            answer.append("Current time: ").append(dataObject.getJSONArray("time_zone").getJSONObject(0).getString("localtime")).append("\n\n");
            JSONArray hourly = dataObject.getJSONArray("weather").getJSONObject(0).getJSONArray("hourly");

            for (int i = 0; i < hourly.length(); i++) {
                JSONObject currentHour = hourly.getJSONObject(i);
                int time = currentHour.getInt("time");
                answer.append("Time: ").append(time / 100).append(":00 Temperature: ")
                        .append(currentHour.getInt("tempC")).append("°С Description: ")
                        .append(currentHour.getJSONArray("weatherDesc").getJSONObject(0).getString("value")).append('\n');

            }

            response.close();
            client.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return answer.toString();
    }
}
