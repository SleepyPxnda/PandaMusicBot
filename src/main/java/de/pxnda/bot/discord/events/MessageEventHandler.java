package de.pxnda.bot.discord.events;

import de.pxnda.bot.BotApplication;
import de.pxnda.bot.commands.*;
import de.pxnda.bot.util.commands.CommandExecutor;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
        if (!rawContent.startsWith(BotApplication.prefix)) {
            return;
        }

        List<String> contents = Arrays.asList(rawContent.split(" "));
        String command = contents.get(0).substring(1);

        /*
        if(!checkForUsePermission(e.getMember())){
            e.getChannel().sendMessage("You cannot use me without the Role **Panda Master** (case-insensitive)").queue();
            return;
        }
        */

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
        String dateString = formatter.format(new Date());

        BotApplication.Logger.log(dateString + " | " + "[" + command + "] issued by " + e.getAuthor().getName(), e.getGuild().getName());

        switch (command) {
            case "join":
                cmdExecutor = new CommandExecutor(new JoinCommand(e));
                break;
            case "leave":
                cmdExecutor = new CommandExecutor(new LeaveCommand(e));
                break;
            case "play":
                cmdExecutor = new CommandExecutor(new PlayCommand(e.getGuild().getId(), e.getMember().getId(), e.getTextChannel().getId(), contents.get(1)));
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
            case "stop":
                cmdExecutor = new CommandExecutor(new StopCommand(e));
                break;
            case "loop":
                cmdExecutor = new CommandExecutor(new LoopCommand(e));
                break;
            case "help":
            default:
                cmdExecutor = new CommandExecutor(new HelpCommand(e));
        }
        cmdExecutor.execute();
    }
}
