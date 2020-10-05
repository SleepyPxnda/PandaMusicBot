package de.pxnda.web;

import de.pxnda.Main;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.api.managers.AudioManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PlayController {

    @PostMapping("/addSong")
    public String queueSong(
            @RequestBody SongForm songForm){

        final Guild guild = Main.jda.getGuildById(songForm.ServerID);

        if(guild == null){
            return "Guild not found";
        }

        final AudioManager audioManager = guild.getAudioManager();
        final Member member = guild.getMemberById(songForm.UserID);
        final TextChannel textChannel = guild.getTextChannelById(songForm.ChannelID);


        if(textChannel == null){
            return "Textchannel not found on Guild";
        }

        if(member == null){
            return "No Member with that Id on the Guild";
        }

        final VoiceChannel voiceChannel = member.getVoiceState().getChannel();

        if(voiceChannel == null){
            return "User is not in a Voicechannel";
        }

        try {
            audioManager.openAudioConnection(voiceChannel);
        }catch (UnsupportedOperationException e){
            textChannel.sendMessage("Can't join your Channel | Unsupported Operation").queue();
            return "Can't join your Channel | Unsupported Operation";
        }catch (InsufficientPermissionException e){
            textChannel.sendMessage("Can't join your Channel | Missing Permission: " + e.getPermission().getName()).queue();
            return "Can't join your Channel | Missing Permission: " + e.getPermission().getName();
        }catch (Exception e){
            textChannel.sendMessage("Can't join your Channel | New Exception").queue();
            return "Can't join your Channel | New Exception";
        }

        Main.playerManager.loadAndPlay(textChannel, songForm.SongURL, member.getUser(), true);

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
