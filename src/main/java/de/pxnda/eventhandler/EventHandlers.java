package de.pxnda.eventhandler;

import de.pxnda.Main;
import de.pxnda.Utils.CommandExecutor;
import de.pxnda.Utils.CustomLogger;
import de.pxnda.command.*;
import javafx.application.Platform;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class EventHandlers extends ListenerAdapter {

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

        Platform.runLater(() -> {
            CustomLogger.addEntry(ts + " #-# " + e.getGuild().getName() + " #-# " + "[" + command + "] issued by " + e.getAuthor().getName());
        });

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
            case "help":
            default:
                cmdExecutor = new CommandExecutor(new HelpCommand(e));
        }
        cmdExecutor.execute();
    }

    //Event welches bei einem Voicechannel join gefeuert wird wenn man davor in KEINEM Channel ist
    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent event) {
        HandleTempChannelBot(event, event.getGuild());
    }

    //Event welches bei jeglichen Arten von Bewegung eines Users zwischen Voicechannels gefeuert wird
    @Override
    public void onGuildVoiceMove(GuildVoiceMoveEvent event) {
        HandleTempChannelBot(event, event.getGuild());
    }

    //Event welches bei einem Voicechannel leave gefeuert wird, wenn man den Voicechannel verlässt, also via Verbindung trennen, nicht einfach rausgehen
    @Override
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent event) {
        if (event != null) {
            HandleTempChannelBot(event, event.getGuild());
        }
    }

    //Funktion welche als Handler für dei Events dient, [GuildVoiceUpdateEvent] ist das Interface welches von allen Events
    // implementiert wird, somit kann man das hier als parameter benutzen um alle Events einzufangen
    public void HandleTempChannelBot(GuildVoiceUpdateEvent event, Guild guild) {

        //Falls das Event null ist, um Fehler zu verhindern da der Fehler beim testen paarmal kam
        if (event == null) return;

        //Falls die Evententity wirklich ein User oder Bot ist, also eine wirkliche Instanz ist
        if (event.getEntity().isFake()) return;

        if (event.getChannelLeft() != null) {
            //Setze die variable channelLeft auf den Channel welcher geleavet wurde
            VoiceChannel channelLeft = event.getChannelLeft();

            //Bugprevention falls der aus irgendeinem Grund null wird
            if (channelLeft == null) return;

            VoiceChannel currentChannel = guild.getSelfMember().getVoiceState().getChannel();

            if (currentChannel != null) {
                if (channelLeft.getIdLong() == currentChannel.getIdLong()) {
                    if (channelLeft.getMembers().size() == 1) {

                        guild.getAudioManager().closeAudioConnection();
                        Main.playerManager.getGuildMusicManager(guild).player.stopTrack();
                        Main.playerManager.getGuildMusicManager(guild).scheduler.getQueue().clear();
                    }
                }
            }
        }
    }

    public boolean checkForUsePermission(Member member){
        List<Role> roles = member.getRoles();

        for(Role r : roles){
            if(r.getName().toLowerCase().equalsIgnoreCase("panda master")){
                return true;
            }
        }

        if(member.getIdLong() ==  171984500480409603L){
            return true;
        }

        return false;
    }
}
