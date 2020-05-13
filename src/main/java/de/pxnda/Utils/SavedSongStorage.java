package de.pxnda.Utils;

import com.iwebpp.crypto.TweetNaclFast;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SavedSongStorage {

    public static HashMap<String, List<SongElement>> globalPlaylistStorage = new HashMap<>();

    public static List<SongElement> FinnsList = Arrays.asList(
            new SongElement("Hardstyle Mix New Year Edition 2019 BASS BOOSTED HD", "https://youtu.be/7J9mdrJwTss"),
            new SongElement("HBz - Bass & Bounce Mix #111 (Oldschool Hands Up/Techno Remix Special)", "https://youtu.be/diAIMSi8hgY")
    );
}


