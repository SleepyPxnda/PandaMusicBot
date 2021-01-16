package de.pxnda.bot.util.models;

public class ResponseJSON {
    int id;
    String errorText;

    /**
     * @param id of the Response, 0 = Error, 1 = Warn, 2 = Info
     * @param errorText of the Error if its id = 0
     */
    public ResponseJSON(int id, String errorText) {
        this.id = id;
        this.errorText = errorText;
    }

    public int getId() {
        return id;
    }

    public String getErrorText() {
        return errorText;
    }
}
