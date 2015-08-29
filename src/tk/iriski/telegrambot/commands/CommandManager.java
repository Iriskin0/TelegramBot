package tk.iriski.telegrambot.commands;

import tk.iriski.telegrambot.telegram.Answer;

import java.util.HashSet;

public class CommandManager {
    static HashSet<Command> allCommands = new HashSet<>();

    public static void init() {
        allCommands.add(new Ping());
        allCommands.add(new Translate());
        allCommands.add(new Help());
        allCommands.add(new Weather());
    }

    public static void parseCommand(String line, long chatId, long reply_id) throws Exception {

        String[] temp = line.split("\\s+");
        String[] temp2 = new String[temp.length - 1];
        for (int i = 0; i < temp.length - 1; i++) {
            temp2[i] = temp[i + 1];
        }
        for (Command c : allCommands) {
            if (temp[0].equals(c.getKey())) {
                new Answer(c.start(temp2), chatId, reply_id);
                //new Answer("test", chatId, reply_id);
                break;
            }
        }

    }

    public static String getKeys() {
        StringBuilder sb = new StringBuilder();
        sb.append("{ ");
        for (Command c : allCommands) {
            sb.append(c.getKey()).append(", ");
        }
        sb.append('}');
        return sb.toString();
    }

    public static String getDescription(String key) {
        for (Command c : allCommands) {
            if (key.equals(c.getKey())) return c.getDescription();
        }
        return "Can't find info about this command";
    }
}
