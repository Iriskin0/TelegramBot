package tk.iriski.telegrambot;

public class Constants {
    public static final String TELEGRAM_API_KEY = "132644010:AAEW6LYrY4c9BSwoTrYkNPl0LRfhn00GMF0";
    public static final String COMMAND_STARTS_WITH = "!";
    public static final int NUMBER_OF_THREADS = 20;
    public static final String WEATHER_API_KEY = "5a7fb018ffaaee995794c0a676708";

    public static final boolean DATABASE_ENABLE = false; //don't touch other database config if you not need it
    public static final String DATABASE_STRING = "jdbc:mysql://127.0.0.1:3306/telegram";
    public static final String DATABASE_LOGIN = "root";
    public static final String DATABASE_PASS = "";

    //don't touch this
    public static final String WEATHER_API_URL = "https://api.worldweatheronline.com/free/v2/weather.ashx";
    public static final String TELEGRAM_API_URL = "https://api.telegram.org/bot";
    public static final String TRANSLATOR_URL = "http://translate.google.com/translate_a/single?client=t&sl=auto&tl=%&hl=%" +
            "&dt=bd&dt=ex&dt=ld&dt=md&dt=qc&dt=rw&dt=rm&dt=ss&dt=t&dt=at&ie=UTF-8&oe=UTF-8&otf=1&ssel=0&tsel=0&q="; //replace % to language
    public static final String HLTV_URL = "http://www.hltv.org/?pageid=188&offset=";
    public static final String CSGL_URL = "http://csgolounge.com/match?m=";
    public static final String TELEGRAM_URL = TELEGRAM_API_URL + TELEGRAM_API_KEY + '/';
}
