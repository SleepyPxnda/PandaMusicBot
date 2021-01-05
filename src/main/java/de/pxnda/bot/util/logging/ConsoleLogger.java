package de.pxnda.bot.util.logging;

public class ConsoleLogger implements  ILogger{

    @Override
    public void log(String log, String guildname) {
        System.out.println(guildname + " | " + log);
    }
}
