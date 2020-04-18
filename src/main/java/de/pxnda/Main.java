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
    public static String prefix = "#";

    public static void main(String[]args) {

        try {
            new JDABuilder("Njk1MzQ3NTI0ODM1MzQ0NDA0.XpMmlw.ck4FgKN8jYsVaxjiwWTe5iQ-Ewg").addEventListeners(new Main()).build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        playerManager = new PlayerManager();
        tempChannelList = new ArrayList<>();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent e){
        if(e.getAuthor().isBot()) return;

        CommandExecutor cmdExecutor;

        Message message = e.getMessage();

        String rawContent = message.getContentRaw();
        List<String> contents = Arrays.asList(rawContent.split(" "));
        String command = contents.get(0).substring(1);

        if(!rawContent.startsWith(prefix)){
            return;
        }

        Date date = new Date();
        long time = date.getTime();
        Timestamp ts = new Timestamp(time);

/*
        Role dj = e.getMember().getRoles().stream().filter( role -> role.getName().equals("DJ")).findFirst().orElse(null);

        if(dj == null){
            e.getChannel().sendMessage("You need the Role DJ to use this Bot").queue();
            return;
        }
*/
        System.out.println(ts + "| "+  e.getGuild().getName() +" |" + " [" + command +"] issued by " + e.getAuthor().getName() );



        switch (command){
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
            case "clear":
                cmdExecutor = new CommandExecutor(new ClearCommand(e));
                break;
            case "help":
            default:
                cmdExecutor = new CommandExecutor(new HelpCommand(e));
        }
        cmdExecutor.execute();
    }

    //Event welches bei einem Voicechannel join gefeuert wird wenn man davor in KEINEM Channel ist
    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent event){
        HandleTempChannelBot(event, event.getGuild());
    }

    //Event welches bei jeglichen Arten von Bewegung eines Users zwischen Voicechannels gefeuert wird
    @Override
    public void onGuildVoiceMove(GuildVoiceMoveEvent event) {
        HandleTempChannelBot(event, event.getGuild());
    }

    //Event welches bei einem Voicechannel leave gefeuert wird, wenn man den Voicechannel verlässt, also via Verbindung trennen, nicht einfach rausgehen
    @Override
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent event)
    {
        if(event != null){
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
            //Wenn der Channel welcher gejoined wird die ID 698984062387355768L hat
            if (event.getChannelJoined().getIdLong() == 698984062387355768L) {

                //Speichere Aktuelle Category in currentCat
                Category currentCat = event.getChannelJoined().getParent();

                //Starte einen neuen Thread, Erklärung zu Threads https://www.dpunkt.de/java/Programmieren_mit_Java/Multithreading/3.html
                Runnable task = () -> {
                    //Create den Channel und pausieren den
                    currentCat.createVoiceChannel("TempChannel: " + event.getEntity().getEffectiveName()).complete();

                    //Initlisierung einer temp Variable für die ChannelId
                    long tempchannel = 0;

                    //Suche in allen Voicechanneln der Kategorie nach dem einen mit dem gleichen Namen
                    for (Category temp : guild.getCategories()) {
                        if (temp.getName().equals("TempBotKategorie")) {
                            for (VoiceChannel channel : temp.getVoiceChannels()) {
                                if (channel.getName().equals("TempChannel: " + event.getEntity().getEffectiveName())) {
                                    //Speichere dann die ID in der temp Variable
                                    tempchannel = channel.getIdLong();
                                }
                            }
                        }
                    }

                    //Logging
                    System.out.println(tempchannel);

                    //falls ein Channel gefunden wurde
                    if (tempchannel != 0) {

                        //Suche den Channel mit der gefundenen Id
                        VoiceChannel createdChannel = guild.getVoiceChannelById(tempchannel);

                        //Move den Aktuellen Member in den Channel, nach 2 sekunden
                        guild.moveVoiceMember(event.getEntity(), createdChannel).queueAfter(2, TimeUnit.SECONDS);

                        //Setze den Voicechannel in die TempListe
                        tempChannelList.add(createdChannel);
                    }
                };
                task.run();
            }
        }

        if (event.getChannelLeft() != null) {
            //Setze die variable channelLeft auf den Channel welcher geleavet wurde
            VoiceChannel channelLeft = event.getChannelLeft();

            //Bugprevention falls der aus irgendeinem Grund null wird
            if (channelLeft == null) return;

            //Falls der Channel in der Liste ist und kein Spieler mehr in dem Channel ist
            if (tempChannelList.contains(channelLeft) && channelLeft.getMembers().size() == 0) {

                //removen den Channel von der Templist
                tempChannelList.remove(channelLeft);

                //Und lösche den Channel nach ner Sekunde
                channelLeft.delete().queueAfter(1, TimeUnit.SECONDS);
            }
        }
    }
}
