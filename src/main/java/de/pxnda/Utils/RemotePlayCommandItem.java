package de.pxnda.Utils;

public class RemotePlayCommandItem {
    private static String videoUrl;
    private static String serverId;
    private static String userId;

    public RemotePlayCommandItem(String videoUrl, String serverId, String userId){
        this.videoUrl = videoUrl;
        this.serverId = serverId;
        this.userId = userId;
    }

    public String getVideoUrl(){
        return "https://www.youtube.com/watch?v=" + videoUrl;
    }
    public String getServerId(){
        return serverId;
    }
    public String getUserId(){
        return userId;
    }
}
