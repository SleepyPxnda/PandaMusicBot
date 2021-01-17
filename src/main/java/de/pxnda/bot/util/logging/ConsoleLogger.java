package de.pxnda.bot.util.logging;

import java.sql.Timestamp;

public class ConsoleLogger implements  ILogger{

    @Override
    public void log(String log, String guildName) {
        System.out.println(new Timestamp(System.currentTimeMillis()).toString() + "\t[ " + guildName + " ] | " + log);
    }

    @Override
    public void systemLog(String log) {
        System.out.println(new Timestamp(System.currentTimeMillis()).toString() + "\t[ System ] | " + log);
    }
}
