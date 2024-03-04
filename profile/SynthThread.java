package profile;

/**
 * SynthThread.java
 * Creates a thread that generates a sustainable sine wave to play a note
 *
 * @author Caleb Henry
 */
public class SynthThread implements Runnable {
    private final int note;
    private Thread thread;
    private boolean end = false;

    public SynthThread(int note) {
        this.note = note;
    }

    public void beginNote() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    public void endNote() {
        end = true;
    }

    public void run() {
        final int AMPLITUDE = 32000 / (note - 35); // Varies with pitch
        final int SAMPLE_RATE = 44100; // Samples per second

        byte[] buffer = new byte[1024];

        int bufferPosition = 0;

        // Calculate pitch
        double power = (note - 69) / 12.0;
        double pitch = Math.pow(2, power) * 440;

        int dt = 0; // Used to track place in sine function

        while(!end) {
            short sample = (short) (Math.sin(2 * Math.PI * pitch * dt / SAMPLE_RATE) * AMPLITUDE);

            // We are using 16-bit audio samples, but the line.write() method requires a byte array
            // Solution: split each sample into two halves and store them individually

            sample = Short.reverseBytes(sample); // Convert to little-endian

            buffer[bufferPosition] = (byte) (sample >>> 8); // First half

            bufferPosition++;

            buffer[bufferPosition] = (byte) (sample & 0xff); // Second half

            bufferPosition++;

            // If the buffer is full, send it to output
            if (bufferPosition >= buffer.length) {
                PlaySound.writeBuffer(buffer, 0, buffer.length);
                bufferPosition = 0;
            }

            dt++;
        }
    }
}
