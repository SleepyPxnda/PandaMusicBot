package de.pxnda.bot.rest;

import de.pxnda.bot.BotApplication;
import de.pxnda.bot.commands.PlayCommand;
import net.dv8tion.jda.api.entities.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


@RestController
public class PlayController {

    @PostMapping("/addSong")
    public String queueSong( @RequestBody SongForm songForm ){

        final Guild guild = BotApplication.jda.getGuildById(songForm.ServerID);

        if(guild == null){
            return "Guild not found";
        }

        final Member member = guild.getMemberById(songForm.UserID);
        final TextChannel textChannel = guild.getTextChannelById(songForm.ChannelID);

        if(textChannel == null){
            return "Textchannel not found on Guild";
        }

        if(member == null){
            return "No Member with that Id on the Guild";
        }

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
        String dateString = formatter.format(new Date());

        BotApplication.Logger.log(dateString + " | " + "[ queue ] issued on Web by " + member.getUser().getName(), guild.getName());

        new PlayCommand(songForm.ServerID, songForm.UserID, songForm.ChannelID, songForm.SongURL).execute();

        return "Added Song to Queue";
    }
}

class SongForm{
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
