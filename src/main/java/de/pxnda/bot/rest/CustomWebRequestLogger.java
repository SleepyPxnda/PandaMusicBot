package de.pxnda.bot.rest;

import de.pxnda.bot.BotApplication;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;

public class CustomWebRequestLogger extends CommonsRequestLoggingFilter {

    public CustomWebRequestLogger() {
        super.setIncludeQueryString(true);
        super.setIncludePayload(true);
        super.setMaxPayloadLength(10000);
        super.setIncludeHeaders(false);
        super.setAfterMessagePrefix("");
    }

    @Override
    protected boolean shouldLog(HttpServletRequest request) {
        return true;
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        BotApplication.Logger.systemLog(message);
    }
}
