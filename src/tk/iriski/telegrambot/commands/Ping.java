package tk.iriski.telegrambot.commands;

import tk.iriski.telegrambot.telegram.Answer;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Ping extends Command {
    String key = "ping";
    String description = "Pings some server from bot's host. Usage: ping server";
    private String server = null;
    private long chatId = 0;
    private long reply_id = 0;

    public Ping() {
        setKey(key);
        setDescription(description);
    }

    @Override
    public void start(String values[], long chatId, long reply_id) throws Exception {
        System.out.println("User requested ping:");
        if (values.length < 2) {
            new Answer("Please input server", chatId, reply_id);
            return;
        }
        server = values[1].replace("\"", "").replace("'", "").replace(";", "").toLowerCase();
        StringBuilder sb = new StringBuilder();

        try {
            String command = "ping " + server;
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), "IBM866"));

            String temp;
            while ((temp = br.readLine()) != null) {
                sb.append(temp).append('\n');
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Answer(sb.toString(), chatId, reply_id);
    }
}
