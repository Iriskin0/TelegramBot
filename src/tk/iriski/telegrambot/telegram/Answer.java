package tk.iriski.telegrambot.telegram;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import tk.iriski.telegrambot.Constants;

import java.util.ArrayList;

public class Answer {
    private String method = "/sendMessage";

    public Answer(String text, long chatId, long reply_id) throws Exception {
        CloseableHttpClient answer = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(Constants.TELEGRAM_API_URL + Constants.TELEGRAM_API_KEY + method);

        ArrayList<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("chat_id", String.valueOf(chatId)));
        params.add(new BasicNameValuePair("text", text));
        params.add(new BasicNameValuePair("reply_to_message_id", String.valueOf(reply_id)));
        params.add(new BasicNameValuePair("disable_web_page_preview", "true"));

        request.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

        CloseableHttpResponse response = answer.execute(request);
        System.out.println(EntityUtils.toString(response.getEntity(), "UTF-8"));

        response.close();
        answer.close();
    }
}
