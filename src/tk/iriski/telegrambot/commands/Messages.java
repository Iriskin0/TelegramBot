package tk.iriski.telegrambot.commands;

import tk.iriski.telegrambot.Database;
import tk.iriski.telegrambot.telegram.Answer;

public class Messages extends Command {
    Database database;

    public Messages() throws Exception {
        setKey("messages");
        setDescription("Returns quantity of messages sent by user in this chat. Usage: messages (user)");
        database = new Database();
    }

    public void start(String[] values, long chatId, long reply_id, String username) throws Exception {
        if (values.length > 0) {
            if (values[0].startsWith("@"))
                username = values[0].substring(1);
            else username = values[0];
        }
        new Answer(database.countMessages(username, chatId), chatId, reply_id);
    }
}
