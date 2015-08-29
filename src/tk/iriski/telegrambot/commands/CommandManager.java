package tk.iriski.telegrambot.commands;

import tk.iriski.telegrambot.Constants;

import java.util.HashSet;

public class CommandManager {
    static HashSet<Command> allCommands = new HashSet<>();

    public static void init() {
        allCommands.add(new Ping());
        allCommands.add(new Translate());
        allCommands.add(new Help());
        allCommands.add(new Weather());
        if (Constants.DATABASE_ENABLE) {
            try {
                allCommands.add(new Messages());
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static String[] parseCommand(String line) throws Exception {
        return line.split("\\s+");
    }

    public static void work(String[] temp, long chatId, long reply_id, String username) throws Exception {
        String[] temp2 = new String[temp.length - 1];
        for (int i = 0; i < temp.length - 1; i++) {
            temp2[i] = temp[i + 1];
        }
        for (Command c : allCommands) {
            if (temp[0].equals(c.getKey())) {
                if (c.getKey().equals("messages")) ((Messages) c).start(temp2, chatId, reply_id, username);
                else c.start(temp2, chatId, reply_id);
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
