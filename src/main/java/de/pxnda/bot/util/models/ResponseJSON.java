package de.pxnda.bot.util.models;

public class ErrorMessage {
    int id;
    String message;
    String errorText;

    public ErrorMessage(int id, String message, String errorText) {
        this.id = id;
        this.message = message;
        this.errorText = errorText;
    }
}
