package tk.iriski.telegrambot.commands;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import tk.iriski.telegrambot.Constants;
import tk.iriski.telegrambot.telegram.Answer;

import java.net.URLEncoder;

public class Translate extends Command {
    public Translate() {
        setKey("translate");
        setDescription("Translates some text to choosen language. Example: translate text ru");
    }

    public void start(String values[], long chatId, long reply_id) throws Exception {
        StringBuilder answer = new StringBuilder();
        try {
            String language = null;
            StringBuilder text = new StringBuilder();

            for (int i = 1; i < values.length; i++) {
                if (i == (values.length - 1) && values[i].length() == 2) language = values[i];
                else text.append(values[i]).append(" ");
            }
            if (language == null) {
                new Answer("Choose language.", chatId, reply_id);
                return;
            }

            String url = Constants.TRANSLATOR_URL.replace("%", language) + URLEncoder.encode(text.toString(), "UTF-8");

            CloseableHttpClient client = HttpClientBuilder.create().build();

            HttpGet request = new HttpGet(url);

            CloseableHttpResponse response = client.execute(request);
            String line = EntityUtils.toString(response.getEntity(), "UTF-8");
            answer.append(line.substring(line.indexOf("\"") + 1, line.indexOf("\",\"")).toLowerCase());

            response.close();
            client.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        if (answer.length() == 0) answer.append("Can't translate text");
        new Answer(answer.toString(), chatId, reply_id);
    }
}
