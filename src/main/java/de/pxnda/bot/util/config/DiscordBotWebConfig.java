package de.pxnda.bot.util.config;

import de.pxnda.bot.rest.CustomWebRequestLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class DiscordBotWebConfig {

    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        return new CustomWebRequestLogger();
    }

}
