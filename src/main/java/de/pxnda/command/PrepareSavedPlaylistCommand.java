package de.pxnda.command;

import de.pxnda.Main;
import de.pxnda.Utils.ICommand;
import de.pxnda.Utils.SavedSongStorage;
import de.pxnda.Utils.SongElement;
import de.pxnda.Utils.UtilFuncs;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class PrepareSavedPlaylistCommand implements ICommand {

    private static Guild guild;
    private static TextChannel channel;
    private static Member sentBy;
    public static HashMap<String, String> idToLink = new HashMap<>();
    private static Category mainCat = null;


    public PrepareSavedPlaylistCommand(MessageReceivedEvent event){
        this.guild = event.getGuild();
        this.channel = event.getTextChannel();
        this.sentBy = event.getMember();
    }

    @Override
    public void execute() {
        if(UtilFuncs.hasPermission(sentBy, "playlistmaster")){

            if(guild.getCategoriesByName("playlist manager", true).size() == 0){
                guild.createCategory("Playlist Manager").completeAfter(1, TimeUnit.SECONDS);

                mainCat = guild.getCategoriesByName("playlist manager", true).stream().findFirst().orElse(null);
            }

            if(mainCat != null){
                for(Map.Entry<String, List<SongElement>> entry : SavedSongStorage.globalPlaylistStorage.entrySet()){
                    mainCat.createTextChannel(entry.getKey()).complete();

                    for (TextChannel channel : mainCat.getTextChannels()){
                        Main.Logger.log(channel.getName(), guild.getName());
                    }

                    TextChannel textChannel = mainCat.getTextChannels()
                            .stream()
                            .filter(channel -> channel.getName().equalsIgnoreCase(entry.getKey()))
                            .findFirst()
                            .orElse(null);

                    if(textChannel != null){
                        for (SongElement s : entry.getValue()) {
                            EmbedBuilder eb = new EmbedBuilder()
                                    .addField(s.getName(), s.getUrl(), false);
                            textChannel.sendMessage(eb.build()).queue(message ->
                            {
                                message.addReaction("▶").queue();
                                message.addReaction("❌").queue();
                                idToLink.put(message.getId(), s.getUrl());
                            });
                        }
                    }
                    else
                    {
                        Main.Logger.log("textchannel not found", guild.getName());
                    }
                }
            }
            else
            {
                Main.Logger.log("MainCat not found", guild.getName());
            }
        }
        else
        {
            channel.sendMessage("You can only use this with the **playlistmaster** role").queue();
        }
    }
}