package tk.iriski.telegrambot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Database {
    private static Connection connection;
    private Statement statement;

    public Database() throws Exception {
        if (connection == null)
            connection = DriverManager.getConnection(Constants.DATABASE_STRING, Constants.DATABASE_LOGIN, Constants.DATABASE_PASS);
    }

    public void updateUserMessages(String username, long userId, long chatId) throws Exception {
        init();
        username = username.toLowerCase().replace("\"", "").replace("'", "").replace(";", "");
        StringBuilder request = new StringBuilder()
                .append("select * from telegramusers ")
                .append("where userId=").append(userId)
                .append(" and chatId=").append(chatId);
        ResultSet rs = statement.executeQuery(request.toString());
        if (!rs.next()) {
            System.out.println("Inserting new user info");
            request = new StringBuilder()
                    .append("insert into telegramusers (userid, username, chatid, numberofmessages) values (")
                    .append(userId).append(",'").append(username).append("',").append(chatId).append(",1)");
            statement.executeUpdate(request.toString());
        } else {
            System.out.println("Updating old user information");
            request = new StringBuilder()
                    .append("update telegramusers ")
                    .append("set numberofmessages=numberofmessages+1, username='").append(username).append("' ")
                    .append("where userId=").append(userId).append(" and chatid=").append(chatId);
            statement.executeUpdate(request.toString());
        }
        finish();
    }

    public String countMessages(String username, long chatId) throws Exception {
        init();
        username = username.toLowerCase().replace("\"", "").replace("'", "").replace(";", "");
        StringBuilder request = new StringBuilder()
                .append("select username, chatId, numberofmessages from telegramUsers ")
                .append("where username='").append(username).append("' and chatId=").append(chatId);
        ResultSet rs = statement.executeQuery(request.toString());
        String answer = null;
        if (rs.next()) {
            answer = "Quantity of messages for user @" + username + " for this chat: " + rs.getInt("numberofmessages");
        } else {
            answer = "Can't find this user.";
        }
        finish();
        return answer;
    }

    private void init() throws Exception {
        statement = connection.createStatement();
    }

    private void finish() throws Exception {
        statement.close();
    }

}
