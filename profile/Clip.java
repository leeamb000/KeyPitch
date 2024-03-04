package profile;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

/**
 * Clip.java
 * Plays one-off sounds in menus, buttons, etc
 *
 * @author Caleb Henry
 */
public class Clip {
    private final URL file;
    private int bytesRead;
    private final byte[] buffer = new byte[1024];
    private final AudioInputStream stream;

    Clip(String fileName) {
        file = Clip.class.getResource(fileName);

        try {
            stream = AudioSystem.getAudioInputStream(Objects.requireNonNull(file));
        } catch (UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void play() {
        while (true) {
            try {
                if ((bytesRead = stream.read(buffer)) == -1) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            PlaySound.writeBuffer(buffer, 0, bytesRead);
        }
    }

    public static void play(String fileName) {
        URL file = Clip.class.getResource(fileName);

        AudioInputStream stream;
        try {
            stream = AudioSystem.getAudioInputStream(Objects.requireNonNull(file));
        } catch (UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }

        int bytesRead;

        byte[] buffer = new byte[1024];

        while (true) {
            try {
                if ((bytesRead = stream.read(buffer)) == -1) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            PlaySound.writeBuffer(buffer, 0, bytesRead);
        }
    }

    public String getFileName() {
        return file.toString();
    }

    public int getBytesRead() {
        return bytesRead;
    }
}
