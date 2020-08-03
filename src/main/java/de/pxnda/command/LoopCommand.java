package de.pxnda.command;

import de.pxnda.Main;
import de.pxnda.Utils.ICommand;
import de.pxnda.music.GuildMusicManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class LoopCommand implements ICommand {

    private final VoiceChannel voiceChannel;
    private final TextChannel textChannel;
    private final Guild guild;
    private final GuildMusicManager manager;
    private boolean isLooping;

    public LoopCommand(MessageReceivedEvent event){
        this.voiceChannel =  event.getMember().getVoiceState().getChannel();
        this.textChannel = event.getTextChannel();
        this. guild = event.getGuild();
        this.manager = Main.playerManager.getGuildMusicManager(guild);
    }


    @Override
    public void execute() {

    }
}
