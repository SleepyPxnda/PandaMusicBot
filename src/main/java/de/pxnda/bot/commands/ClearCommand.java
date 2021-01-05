package de.pxnda.bot.commands;


import de.pxnda.bot.BotApplication;
import de.pxnda.bot.util.commands.ICommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ClearCommand implements ICommand {

    private final Member member;
    private final Guild guild;
    private final TextChannel channel;

    public ClearCommand(MessageReceivedEvent e) {
        this.guild = e.getGuild();
        this.channel = e.getTextChannel();
        this.member = e.getMember();
    }

    @Override
    public void execute() {
        channel.sendMessage("My **Queue** with " + BotApplication.playerManager.getGuildMusicManager(guild).scheduler.getQueue().size() + " was **destroyed** by " + member.getEffectiveName()).queue();
        BotApplication.playerManager.getGuildMusicManager(guild).scheduler.getQueue().clear();
    }
}
