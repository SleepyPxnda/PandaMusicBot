package de.pxnda.bot;

import de.pxnda.bot.discord.events.GuildVoiceEventHandler;
import de.pxnda.bot.discord.events.MessageEventHandler;
import de.pxnda.bot.util.logging.ConsoleLogger;
import de.pxnda.bot.util.logging.ILogger;
import de.pxnda.bot.util.musichandlers.PlayerManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;

@SpringBootApplication
public class BotApplication {

    public static PlayerManager playerManager;
    public static String prefix = "*";
    public static ILogger Logger;
    public static JDA jda;

    public static void main(String[] args) {
        try {
            jda = JDABuilder.createDefault("NzM5MTAwNDQ4NjkwMTQzMjYy.XyVi7g.k2gyw6vBGnBBwph_PnitgzhGM-w")
                    .addEventListeners(new GuildVoiceEventHandler(), new MessageEventHandler())
                    .build();
        } catch (LoginException e) {
            e.printStackTrace();
        }

        jda.getPresence().setPresence(Activity.listening("to *"), false);

        playerManager = new PlayerManager();
        Logger = new ConsoleLogger();

        Logger.systemLog("Bot started with prefix " + prefix);


        SpringApplication app = new SpringApplication(BotApplication.class);
        app.run(args);

        System.out.println("Spring Boot started");
    }

}
