package de.pxnda.bot.util.models;

public class SongListItem {

    private int Id;
    private String Name;
    private String Url;
    private long Length;
    private String Author;
    private String Requestor;

    public SongListItem(int id, String name, String url, long length, String author, String requestor) {
        Id = id;
        Name = name;
        Url = url;
        Length = length;
        Author = author;
        Requestor = requestor;
    }

    public String getName() {
        return Name;
    }

    public String getUrl() {
        return Url;
    }

    public long getLength() {
        return Length;
    }

    public String getAuthor() {
        return Author;
    }

    public String getRequestor() {
        return Requestor;
    }

    public int getId() {
        return Id;
    }
}
