package tk.iriski.telegrambot;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import tk.iriski.telegrambot.commands.CommandManager;

import java.util.ArrayList;

public class Main {
    private static String method = "/getUpdates";
    private static long timeBetweenUpdates = 500;
    private static Database database;

    public static void main(String[] args) throws Exception {
        long offset = 0L;
        CommandManager.init();
        if (Constants.DATABASE_ENABLE) database = new Database();
        while (true) {
            offset = getUpdates(offset);
        }
    }

    public static long getUpdates(long update_id) {
        long offset = update_id; // omg'''
        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost(Constants.TELEGRAM_API_URL + Constants.TELEGRAM_API_KEY + method);
            ArrayList<BasicNameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("offset", String.valueOf(update_id + 1)));

            request.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

            CloseableHttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() != 200) return update_id;

            String responseLine = EntityUtils.toString(response.getEntity(), "UTF-8");
            System.out.println(responseLine);

            JSONObject responseObject = new JSONObject(responseLine);
            JSONArray responseArray = responseObject.getJSONArray("result");
            for (int i = 0; i < responseArray.length(); i++) {
                JSONObject resultObject = responseArray.getJSONObject(i);
                offset = resultObject.getLong("update_id");
                System.out.println(offset);
                if (!resultObject.isNull("message")) {
                    JSONObject messageObject = resultObject.getJSONObject("message");
                    String text = "";
                    if (!messageObject.isNull("text")) {
                        long message_id = messageObject.getLong("message_id");
                        long chatId = messageObject.getJSONObject("chat").getLong("id");
                        JSONObject from = messageObject.getJSONObject("from");
                        String username = null;
                        if (from.isNull("username")) {
                            username = String.valueOf(from.getLong("id"));
                        } else {
                            username = from.getString("username");
                        }
                        database.updateUserMessages(username, from.getLong("id"), chatId);
                        if ((text = messageObject.getString("text")).toLowerCase().startsWith(Constants.COMMAND_STARTS_WITH)) {
                            String[] temp = CommandManager.parseCommand(text.substring(Constants.COMMAND_STARTS_WITH.length()).toLowerCase());
                            CommandManager.work(temp, chatId, message_id, username);
                        }

                    }
                }
            }

            response.close();
            client.close();

            Thread.sleep(timeBetweenUpdates);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return offset;
    }
}
