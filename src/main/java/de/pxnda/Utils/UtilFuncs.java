package de.pxnda.Utils;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.util.concurrent.TimeUnit;

public class UtilFuncs {
    public static String convertToTimeStamp(long millis){
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        );
    }

    public static boolean hasPermission(Member member, String roleName){
        for (Role role : member.getRoles()) {
            if(role.getName().equalsIgnoreCase(roleName)){
                return true;
            }
        }
        return false;
    }



}
