package de.pxnda.bot.rest;

import de.pxnda.bot.BotApplication;
import de.pxnda.bot.commands.PlayCommand;
import de.pxnda.bot.util.models.ResponseJSON;
import de.pxnda.bot.util.models.SongForm;
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
    public ResponseJSON queueSong(@RequestBody SongForm songForm ){

        System.out.println(songForm);

        final Guild guild = BotApplication.jda.getGuildById(songForm.ServerID);

        if(guild == null){
            return new ResponseJSON(1 , "Guild not found");
        }

        final Member member = guild.getMemberById(songForm.UserID);
        final TextChannel textChannel = guild.getTextChannelById(songForm.ChannelID);

        if(textChannel == null){
            return new ResponseJSON(1 , "Textchannel not found");
        }

        if(member == null){
            return new ResponseJSON(1 , "Member not found");
        }

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
        String dateString = formatter.format(new Date());

        BotApplication.Logger.log(dateString + " | " + "[ queue ] issued on Web by " + member.getUser().getName(), guild.getName());

        new PlayCommand(songForm.ServerID, songForm.UserID, songForm.ChannelID, songForm.SongURL).execute();

        return new ResponseJSON(2 , "Song added to Queue");
    }
}

