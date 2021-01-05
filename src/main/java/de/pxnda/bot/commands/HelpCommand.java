package de.pxnda.bot.commands;


import de.pxnda.bot.BotApplication;
import de.pxnda.bot.util.commands.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class HelpCommand implements ICommand {

    private final TextChannel channel;

    public HelpCommand(MessageReceivedEvent e) {
        this.channel = e.getTextChannel();
    }

    @Override
    public void execute() {
        EmbedBuilder embed = new EmbedBuilder();

        embed.setAuthor("Botprefix ist [ " + BotApplication.prefix + " ]");

        embed.addField("join", "Bot joins your Voicechannel", true);
        embed.addField("leave", "Bot leaves your Voicehannel", true);
        embed.addField("play", "Bot starts playing your song " + BotApplication.prefix + "play (songurl)", true);
        embed.addField("queue", "Shows the current Songqueue", true);
        embed.addField("np", "Shows the Song currently playing", true);
        embed.addField("help", "Shows this Windows", true);
        embed.addField("clear", "Clears the entire queue", true);
        embed.addField("skip", "Skips the current Song ", true);
        embed.addField("forward [number]", "Skips the given amount of Songs ", true);
        embed.addField("delete [index]", "Deletes Song at given Index (pls use Index Numbers from " + BotApplication.prefix + "queue) ", true);
        embed.addField("loop", "Enables / Disables the Loop of the Current Song ", true);

        embed.setFooter("Built with \\U+2665");

        channel.sendMessage(embed.build()).queue();

    }
}
