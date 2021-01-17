package de.pxnda.bot.rest;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import de.pxnda.bot.BotApplication;
import de.pxnda.bot.commands.PlayCommand;
import de.pxnda.bot.util.models.SongForm;
import de.pxnda.bot.util.models.SongListItem;
import net.dv8tion.jda.api.entities.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;


@RestController
public class WebController {

    @PostMapping("/addSong")
    public ResponseEntity<String> queueSong(@Valid @RequestBody SongForm songForm ){

        final Guild guild = BotApplication.jda.getGuildById(songForm.ServerID);
        final Member member = guild.getMemberById(songForm.UserID);

        BotApplication.Logger.log("[ queue ] issued on Web by " + member.getUser().getName(), guild.getName());

        new PlayCommand(songForm.ServerID, songForm.UserID, songForm.ChannelID, songForm.SongURL).execute();

        return ResponseEntity.ok("Song added to Queue");
    }

    @GetMapping("/getSongQueue/{guildId}")
    public ResponseEntity<List<SongListItem>> getSongList(@Valid@PathVariable(value = "guildId") String guildId) {
        Guild guild = BotApplication.jda.getGuildById(guildId);

        List<SongListItem> songList = new ArrayList<>();

        BlockingQueue<AudioTrack> trackQueue = BotApplication.playerManager.getGuildMusicManager(guild).scheduler.getQueue();

        trackQueue.forEach(song -> songList.add(new SongListItem(song.getInfo().title, song.getInfo().uri, song.getInfo().length, song.getInfo().author)));

        return ResponseEntity.ok(songList);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }
}

