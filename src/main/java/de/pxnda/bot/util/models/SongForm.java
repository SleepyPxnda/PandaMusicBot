package de.pxnda.bot.util.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SongForm {

    @NotNull @NotBlank
    public String UserID;

    @NotNull @NotBlank
    public String ServerID;

    @NotNull @NotBlank
    public String SongURL;

    @NotNull @NotBlank
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
