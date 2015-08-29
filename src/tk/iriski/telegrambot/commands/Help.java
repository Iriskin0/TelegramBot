package tk.iriski.telegrambot.commands;

/**
 * Created by Victor on 27.08.2015.
 */
public class Help extends Command {
    public Help() {
        setKey("help");
        setDescription("Shows all commands or info about one. Example: help command");
    }

    @Override
    public String start(String[] value) {
        if (value.length == 0) {
            return CommandManager.getKeys();
        } else {
            return CommandManager.getDescription(value[0]);
        }
    }
}
