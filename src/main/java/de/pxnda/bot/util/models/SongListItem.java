package de.pxnda.bot.util.models;

public class SongListItem {

    String Name;
    String Url;
    long Length;
    String Author;

    public SongListItem(String name, String url, long length, String author) {
        Name = name;
        Url = url;
        Length = length;
        Author = author;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public long getLength() {
        return Length;
    }

    public void setLength(long length) {
        Length = length;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }
}
