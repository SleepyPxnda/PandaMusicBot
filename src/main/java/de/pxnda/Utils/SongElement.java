package de.pxnda.Utils;

public class SongElement {
    private String name;
    private String Url;

    public SongElement(String name, String Url){
        this.name = name;
        this.Url = Url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return Url;
    }
}
