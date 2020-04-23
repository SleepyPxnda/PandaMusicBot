package de.pxnda;

import de.pxnda.Utils.CommandExecutor;
import de.pxnda.command.*;
import de.pxnda.music.PlayerManager;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.voice.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main extends ListenerAdapter {

    public static List<VoiceChannel> tempChannelList;
    public static PlayerManager playerManager;
    public static String prefix = "*";

    //ToDo: Replace all Messages with Embed

    public static void main(String[] args) {

        try {
            new JDABuilder("Njk1MzQ3NTI0ODM1MzQ0NDA0.XpMmlw.ck4FgKN8jYsVaxjiwWTe5iQ-Ewg").addEventListeners(new Main()).build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        playerManager = new PlayerManager();
        tempChannelList = new ArrayList<>();

        System.out.println("Bot started with prefix " + prefix);
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if (e.getAuthor().isBot()) return;

        CommandExecutor cmdExecutor;

        Message message = e.getMessage();

        String rawContent = message.getContentRaw();
        List<String> contents = Arrays.asList(rawContent.split(" "));
        String command = contents.get(0).substring(1);

        if (!rawContent.startsWith(prefix)) {
            return;
        }

        Date date = new Date();
        long time = date.getTime();
        Timestamp ts = new Timestamp(time);

        System.out.println(ts + " | " + e.getGuild().getName() + " |" + " [" + command + "] issued by " + e.getAuthor().getName());

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

        if (event.getChannelJoined() != null) {

        if (event.getChannelLeft() != null) {
            //Setze die variable channelLeft auf den Channel welcher geleavet wurde
            VoiceChannel channelLeft = event.getChannelLeft();

            //Bugprevention falls der aus irgendeinem Grund null wird
            if (channelLeft == null) return;

            VoiceChannel currentChannel = guild.getSelfMember().getVoiceState().getChannel();

            if(currentChannel != null) {
                if (channelLeft.getIdLong() == currentChannel.getIdLong()) {
                    if (channelLeft.getMembers().size() == 1) {
                        if (channelLeft.getMembers().contains(guild.getSelfMember())) {
                            guild.getAudioManager().closeAudioConnection();
                            Main.playerManager.getGuildMusicManager(guild).player.stopTrack();
                            Main.playerManager.getGuildMusicManager(guild).scheduler.getQueue().clear();
                        }
                    }
                }
            }
        }
    }
}
