package de.pxnda.command;

import de.pxnda.Utils.ICommand;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class JoinCommand implements ICommand {

    private final Member member;
    private final VoiceChannel voiceChannel;
    private final TextChannel textChannel;
    private final Guild guild;

    public JoinCommand(MessageReceivedEvent event){
        this.member = event.getMember();
        this.voiceChannel =  member.getVoiceState().getChannel();
        this.textChannel = event.getTextChannel();
        this. guild = event.getGuild();
    }

    @Override
    public void execute() {
        if(voiceChannel != null){
            AudioManager manager = guild.getAudioManager();
            manager.openAudioConnection(voiceChannel);
        }
        else
        {
            textChannel.sendMessage("I can't find you in any Voicechannel...").queue();
        }
    }
}
