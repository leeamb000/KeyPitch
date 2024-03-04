package profile;

import javax.sound.sampled.*;

/**
 * PlaySound.java
 * The output for default profiles PianoProfile and SynthProfile which do not require pitch shifting
 *
 * @author Caleb Henry
 */
public class PlaySound {
    private static final int SAMPLE_RATE = 44100; // Samples per second
    private static final int SAMPLE_SIZE = 16; // 16-bit audio
    private static final int CHANNELS = 2; // 1 for mono, 2 for stereo
    private static final int FRAME_SIZE = 4; // Number of bytes in a frame (compressed)
    private static final int FRAME_RATE = 44100; // Same as SAMPLE_RATE
    private static final boolean BIG_ENDIAN = false; // Least-significant bytes come first

    private static final AudioFormat format = new AudioFormat(
            AudioFormat.Encoding.PCM_SIGNED,
            SAMPLE_RATE,
            SAMPLE_SIZE,
            CHANNELS,
            FRAME_SIZE,
            FRAME_RATE,
            BIG_ENDIAN
    );

    private static final SourceDataLine line;

    static {
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

        try {
            line = (SourceDataLine) AudioSystem.getLine(info);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }

        try {
            line.open(format);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }

        line.start();
    }

    public static void writeBuffer(byte[] buffer, int start, int end) {
        line.write(buffer, start, end);
    }
}