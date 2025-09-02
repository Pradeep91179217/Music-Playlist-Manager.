package Project_Music;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

// ================= Exceptions =================
class InvalidSongFormatException extends Exception {
    public InvalidSongFormatException(String message) {
        super(message);
    }
}

class PlaylistEmptyException extends Exception {
    public PlaylistEmptyException(String message) {
        super(message);
    }
}

// ================= Base Class =================
interface Playable {
    void play();
}

abstract class Song implements Playable {
    protected String title;
    protected String artist;
    protected double duration;

    public Song(String title, String artist, double duration) {
        this.title = title;
        this.artist = artist;
        this.duration = duration;
    }

    public abstract void play();

    @Override
    public String toString() {
        return title + " by " + artist + " (" + duration + " mins)";
    }
}

// =============== Subclasses ==================
class Mp3Song extends Song {
    public Mp3Song(String title, String artist, double duration) throws InvalidSongFormatException {
        super(title, artist, duration);
        if (!title.endsWith(".mp3")) {
            throw new InvalidSongFormatException("Song must be in .mp3 format!");
        }
    }

    @Override
    public void play() {
        System.out.println("Playing MP3: " + this);
    }
}

class WAVSong extends Song {
    public WAVSong(String title, String artist, double duration) throws InvalidSongFormatException {
        super(title, artist, duration);
        if (!title.toLowerCase().endsWith(".wav")) {
            throw new InvalidSongFormatException("Song must be in .wav format!");
        }
    }

    @Override
    public void play() {
        System.out.println("Playing WAV: " + this );
    }
}

// ============== Playlist Class ===============
class Playlist {
    private String name;
    private List<Song> songs = new ArrayList<>();

    public Playlist(String name) {
        this.name = name;
    }

    public void addSong(Song song) {
        songs.add(song);
        System.out.println("Added: " + song.title + " to playlist " + name);
    }

    public void playAll() throws PlaylistEmptyException {
        if (songs.isEmpty())
            throw new PlaylistEmptyException("Playlist is Empty!");
        for (Song song : songs) {
            song.play();
        }
    }

    public void shuffle() throws PlaylistEmptyException {
        if (songs.isEmpty())
            throw new PlaylistEmptyException("Playlist is Empty!");
        Collections.shuffle(songs);
        for (Song song : songs) {
            song.play();
        }
    }

    public void repeatAll() throws PlaylistEmptyException {
        if (songs.isEmpty())
            throw new PlaylistEmptyException("Playlist is Empty!");
        for (Song song : songs) {
            song.play();
        }
    }

    public void saveToFile() throws IOException {
        FileWriter writer = new FileWriter(name + ".txt");
        for (Song song : songs) {
            writer.write(song.toString() + "\n");
        }
        writer.close();
        System.out.println("Playlist saved to " + name + ".txt");
    }
}

// ================ User Class =================
class User {
    private String username;
    private List<Playlist> playlists = new ArrayList<>();

    public User(String username) {
        this.username = username;
    }

    public Playlist createPlaylist(String name) {
        Playlist playlist = new Playlist(name);
        playlists.add(playlist);
        System.out.println("Playlist created: " + name);
        return playlist;
    }
}

// ============== Main  ==================
public class MusicPlayListManager {
    public static void main(String[] args) {
        try {
            User user = new User("Pradeep Kumar");

            // === Create Playlist ===
            Playlist rockPlaylist = user.createPlaylist("Rockhits");

            // Add songs
            Song s1 = new Mp3Song("song1.mp3", "Artist A", 3.5);
            Song s2 = new WAVSong("song2.wav", "Artist B", 4.0);
            Song s3 = new Mp3Song("song3.mp3", "Artist C", 2.8);

            rockPlaylist.addSong(s1);
            rockPlaylist.addSong(s2);
            rockPlaylist.addSong(s3);

            // Play songs
            System.out.println("\n--- Playing All Songs ---");
            rockPlaylist.playAll();

            // Shuffle
            System.out.println("\n--- Shuffle Playlist ---");
            rockPlaylist.shuffle();

            // Repeat
            System.out.println("\n--- Repeat Playlist ---");
            rockPlaylist.repeatAll();

            // Save playlist
            rockPlaylist.saveToFile();

        } catch (InvalidSongFormatException | PlaylistEmptyException | IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
