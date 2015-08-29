package tk.iriski.telegrambot.commands;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Ping extends Command {
    String key = "ping";
    String description = "Pings some server from bot's host";
    private String server = null;
    private long chatId = 0;
    private long reply_id = 0;

    public Ping() {
        setKey(key);
        setDescription(description);
    }

    @Override
    public String start(String values[]) {
        System.out.println("User requested ping:");
        if (values.length < 2) return "Please input server";
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
        return sb.toString();
    }
}
