package de.pxnda.eventhandler;

import de.pxnda.Main;
import de.pxnda.Utils.CommandExecutor;
import de.pxnda.command.*;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MessageEventHandler extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent e) {

        if (e.getAuthor().isBot()) return;

        CommandExecutor cmdExecutor;

        Message message = e.getMessage();

        String rawContent = message.getContentRaw();
        List<String> contents = Arrays.asList(rawContent.split(" "));
        String command = contents.get(0).substring(1);

        if (!rawContent.startsWith(Main.prefix)) {
            return;
        }

        /*
        if(!checkForUsePermission(e.getMember())){
            e.getChannel().sendMessage("You cannot use me without the Role **Panda Master** (case-insensitive)").queue();
            return;
        }
        */

        Date date = new Date();
        long time = date.getTime();
        Timestamp ts = new Timestamp(time);

        Main.Logger.log(ts + " | " + "[" + command + "] issued by " + e.getAuthor().getName(), e.getGuild().getName());

        switch (command) {
            case "join":
                cmdExecutor = new CommandExecutor(new JoinCommand(e));
                break;
            case "leave":
                cmdExecutor = new CommandExecutor(new LeaveCommand(e));
                break;
            case "play":
                cmdExecutor = new CommandExecutor(new PlayCommand(e));
                break;
            case "queue":
                cmdExecutor = new CommandExecutor(new QueueCommand(e));
                break;
            case "np":
                cmdExecutor = new CommandExecutor(new NowPlayingCommand(e));
                break;
            case "skip":
                cmdExecutor = new CommandExecutor(new SkipCommand(e));
                break;
            case "clr":
            case "clear":
                cmdExecutor = new CommandExecutor(new ClearCommand(e));
                break;
            case "fwd":
            case "forward":
                cmdExecutor = new CommandExecutor(new ForwardCommand(e, contents.get(1)));
                break;
            case "del":
            case "delete":
                cmdExecutor = new CommandExecutor(new DeleteSongCommand(e, contents.get(1)));
                break;
            case "pause":
                cmdExecutor = new CommandExecutor(new PauseCommand(e));
                break;
            case "resume":
                cmdExecutor = new CommandExecutor(new ResumeCommand(e));
                break;
            case "playlist":
                cmdExecutor = new CommandExecutor(new PrepareSavedPlaylistCommand(e));
                break;
            case "stop":
                cmdExecutor = new CommandExecutor(new StopCommand(e));
                break;
            case "help":
            default:
                cmdExecutor = new CommandExecutor(new HelpCommand(e));
        }
        cmdExecutor.execute();
    }
}
