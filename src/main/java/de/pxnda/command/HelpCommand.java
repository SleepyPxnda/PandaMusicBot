package de.pxnda.command;

import de.pxnda.Main;
import de.pxnda.Utils.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class HelpCommand implements ICommand {

    private TextChannel channel;

    public HelpCommand(MessageReceivedEvent e) {
        this.channel = e.getTextChannel();
    }

    @Override
    public void execute() {
        EmbedBuilder embed = new EmbedBuilder();

        embed.setAuthor("Botprefix ist [ " + Main.prefix + " ]");

        embed.addField("join", "Bot joins your VoiceChannel", true);
        embed.addField("leave", "Bot leaves your VoiceChannel", true);
        embed.addField("play", "Bot starts playing your song [!play [songurl]]", true);
        embed.addField("queue", "Shows the current Songqueue", true);
        embed.addField("np", "Shows the Song currently playing", true);
        embed.addField("help", "Shows this Windows", true);
        embed.addField("clear", "Clears the entire queue", true);
        embed.addField("skip", "Skips the current Song ", true);
        embed.addField("forward [zahl]", "Skips the given amount of Songs ", true);

        channel.sendMessage(embed.build()).queue();

    }
}
