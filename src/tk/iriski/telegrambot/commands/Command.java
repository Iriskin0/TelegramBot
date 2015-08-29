package tk.iriski.telegrambot.commands;

public class Command {
    String key = null;
    String description = null;

    public String getKey() {
        return this.key;
    }

    protected void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return this.description;
    }

    protected void setDescription(String description) {
        this.description = description;
    }

    public void start(String[] values, long chatId, long reply_id) throws Exception {
    }
}
