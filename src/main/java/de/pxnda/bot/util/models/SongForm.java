package de.pxnda.bot.util.models;

public class SongForm {
    public String UserID;
    public String ServerID;
    public String SongURL;
    public String ChannelID;

    public String getChannelID() {
        return ChannelID;
    }

    public void setChannelID(String channelID) {
        ChannelID = channelID;
    }

    public String getServerID() {
        return ServerID;
    }

    public String getSongURL() {
        return SongURL;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setServerID(String serverID) {
        ServerID = serverID;
    }

    public void setSongURL(String songURL) {
        SongURL = songURL;
    }
}
