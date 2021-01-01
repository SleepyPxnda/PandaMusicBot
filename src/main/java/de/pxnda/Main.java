package de.pxnda;

import de.pxnda.Logging.ConsoleLogger;
import de.pxnda.Logging.ILogger;
import de.pxnda.eventhandler.GuildVoiceEventHandler;
import de.pxnda.eventhandler.MessageEventHandler;
import de.pxnda.music.PlayerManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.VoiceChannel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import javax.security.auth.login.LoginException;
import java.util.*;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class Main {

    public static List<VoiceChannel> tempChannelList;
    public static PlayerManager playerManager;
    public static String prefix = "*";
    public static ILogger Logger;
    public static JDA jda;

    //ToDo: Replace all Messages with Embed

    public static void main(String[] args) throws LoginException {

        jda = JDABuilder.createDefault("NzM5MTAwNDQ4NjkwMTQzMjYy.XyVi7g.k2gyw6vBGnBBwph_PnitgzhGM-w")
                .addEventListeners(new GuildVoiceEventHandler(), new MessageEventHandler())
                .build();

        jda.getPresence().setPresence(Activity.listening("to *"), false);

        playerManager = new PlayerManager();
        tempChannelList = new ArrayList<>();

        System.out.println("Bot started with prefix " + prefix);
        Logger = new ConsoleLogger();

        SpringApplication app = new SpringApplication(Main.class);
        app.run(args);

        System.out.println("Spring Boot started");
    }
}
