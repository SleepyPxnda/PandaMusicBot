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

        //General Commands
        embed.addField("join", "bot joins your voicechannel", false);
        embed.addField("leave", "bot leaves your voicehannel", false);
        embed.addField("help", "Shows this window", false);

        //Song Commands
        embed.addField("play", "bot starts playing your song " + BotApplication.prefix + "play (songurl)", false);
        embed.addField("np", "shows the Song currently playing", false);
        embed.addField("pause", "pauses the current song", false);
        embed.addField("resume", "resumes the song if the bot was paused", false);
        embed.addField("skip", "skips the current song ", false);

        //Queue Commands
        embed.addField("queue", "shows the current songqueue", false);
        embed.addField("clear", "clears the entire queue", false);

        //Advanced Queue Commands
        embed.addField("forward [number]", "Skips the given amount of songs ", false);
        embed.addField("delete [index]", "Deletes song at given index (pls use index numbers from " + BotApplication.prefix + "queue) ", false);
        embed.addField("loop", "Enables / Disables the loop of the current song ", false);

        embed.setFooter("Pandas > everything");

        channel.sendMessage(embed.build()).queue();

    }
}
