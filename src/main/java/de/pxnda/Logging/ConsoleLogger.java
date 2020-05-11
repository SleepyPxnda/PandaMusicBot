package de.pxnda.Logging;

public class ConsoleLogger implements  ILogger{

    @Override
    public void log(String log) {
        System.out.println(log.replace(" #-# ", " | "));
    }
}
