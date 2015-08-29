package tk.iriski.telegrambot.commands;

import tk.iriski.telegrambot.telegram.Answer;

/**
 * Created by Victor on 27.08.2015.
 */
public class Help extends Command {
    public Help() {
        setKey("help");
        setDescription("Shows all commands or info about one. Usage: help command");
    }

    @Override
    public void start(String[] value, long chatId, long reply_id) throws Exception {
        if (value.length == 0) {
            new Answer(CommandManager.getKeys(), chatId, reply_id);
        } else {
            new Answer(CommandManager.getDescription(value[0]), chatId, reply_id);
        }
    }
}
