package de.pxnda.command;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import de.pxnda.Main;
import de.pxnda.Utils.ICommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;



public class ClearCommand implements ICommand {

    private Member member;
    private Guild guild;
    private TextChannel channel;

    public ClearCommand(MessageReceivedEvent e) {
        this.guild = e.getGuild();
        this.channel = e.getTextChannel();
        this.member = e.getMember();
    }

    @Override
    public void execute() {
        channel.sendMessage("Queue with " + Main.playerManager.getGuildMusicManager(guild).scheduler.getQueue().size() + " was cleared by " + member.getEffectiveName()).queue();
        Main.playerManager.getGuildMusicManager(guild).scheduler.getQueue().clear();
    }
}
