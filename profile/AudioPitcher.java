package profile;

import be.tarsos.dsp.*;
import be.tarsos.dsp.io.TarsosDSPAudioFloatConverter;
import be.tarsos.dsp.io.TarsosDSPAudioFormat;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
import be.tarsos.dsp.io.jvm.AudioPlayer;
import be.tarsos.dsp.resample.RateTransposer;


import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Class to model a simple audio player that play's and pause's audio.
 * <p>
 *     It achieves this using the TarsosDSP library. To shift the pitch, it utilizes an overlap-add technique wave form
 *     similarity (WSOLA) to speed and pitch up the audio, then a rate transposer to return it to the original length
 *     while maintaining the pitch.
 * </p>
 * @author Daniel Molnar
 * @version 1.1
 */
public class AudioPitcher implements AudioProcessor {

    private double pitchFactor;
    private double gainFactor;
    private File audioFile;
    private AudioInputStream audioInputStream;
    private WaveformSimilarityBasedOverlapAdd wsola;
    private RateTransposer rateTransposer;
    private GainProcessor gainProcessor;
    private AudioDispatcher dispatcher;
    private AudioPlayer audioPlayer;
    AudioFormat format;
    URL url;

    /**
     * Constructor for the SimpleAudioPlayer class.
     * <p>
     *     Creates necessary audio processors, dispatcher to play said audio, and adds the processor to the dispatcher.
     *     Also reformats pitching factor to more logical orientation.
     * </p>
     * <p>
     *    pitchFactor works based on an exponential with base 2, i.e. 2 = first octave, 4 = second octave, ect.
     * </p>
     * @param inputStream AudioInputStream: stream for the audio.
     * @param path URL: path location of audio file
     * @param pitchFactor double: pitching factor, must be between .25 and 10; 1.0 is original pitch.
     * @param gainFactor double: volume factor; 1.0 is original volume.
     */
    public AudioPitcher(AudioInputStream inputStream, URL path, double pitchFactor, double gainFactor) {

        this.url = path;
        this.gainFactor = gainFactor;
        this.pitchFactor = 1 / pitchFactor;

        try {
            audioInputStream = inputStream;

            /*if (audioFile != null) {
                format = AudioSystem.getAudioFileFormat(audioFile).getFormat();
            } else*/
                format = new AudioFormat(44100, 16, 1, true, false);

            audioPlayer = new AudioPlayer(format);
            wsola = new WaveformSimilarityBasedOverlapAdd(
                    WaveformSimilarityBasedOverlapAdd.Parameters.musicDefaults(this.pitchFactor, format.getSampleRate()));
            rateTransposer = new RateTransposer(this.pitchFactor);
            gainProcessor = new GainProcessor(this.gainFactor);

            if (format.getChannels() != 1) {
                dispatcher = AudioDispatcherFactory.fromFile(audioFile,
                        wsola.getInputBufferSize() * format.getChannels(),
                        wsola.getOverlap() * format.getChannels());
                dispatcher.addAudioProcessor(new MultichannelToMono(format.getChannels(), true));
            } else
                dispatcher = AudioDispatcherFactory.fromURL(this.url, wsola.getInputBufferSize(), wsola.getOverlap());

            wsola.setDispatcher(dispatcher);

            dispatcher.addAudioProcessor(wsola);
            dispatcher.addAudioProcessor(rateTransposer);
            dispatcher.addAudioProcessor(gainProcessor);
            dispatcher.addAudioProcessor(audioPlayer);
            dispatcher.addAudioProcessor(this);

        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Forced implementation from AudioProcessor interface. Unmodified and unused.
     * @param audioEvent The audio event that contains audio data.
     * @return boolean representing if processing was finished
     */
    @Override
    public boolean process(AudioEvent audioEvent) {
        return false;
    }

    /**
     * Forced implementation from AudioProcessor interface. Not utilized.
     */
    @Override
    public void processingFinished() {
    }

    /**
     * Method to start thread pitching the audio for live playback.
     */
    public void start(){
        Thread thread = new Thread(dispatcher);
        thread.start();
    }

    /**
     * Method to pitch shift audio using the raw data stored as a byte array.
     * @param buffer byte array holding the audio data:byte[]
     * @return byte array holding the pitch shifted data:byte[]
     */
    public byte[] pitchShiftBytes(byte [] buffer) {


        TarsosDSPAudioFormat tarsosFormat = new TarsosDSPAudioFormat(this.format.getSampleRate(),
                this.format.getSampleSizeInBits(), this.format.getChannels(),true, this.format.isBigEndian());
        AudioEvent event = new AudioEvent(tarsosFormat);

        TarsosDSPAudioFloatConverter converter = TarsosDSPAudioFloatConverter.getConverter(tarsosFormat);
        float[] floatBuff = new float[buffer.length / 2];
        event.setFloatBuffer(converter.toFloatArray(buffer, floatBuff));

        this.wsola.process(event);
        this.rateTransposer.process(event);

        return event.getByteBuffer();
    }

    /**
     * Method to calculate the pitch factor scalar based on midi note protocol.
     * @param note midi note protocol ID: int
     * @return pitch factor scalar (between .25 and 10): double
     */
    public static double calculatePitchFactor(int note) {

        double factor = 0.0;
        int rem = note % 12;
        double octave = 0;
        octave = Math.pow(2, (int)(note / 12 - 5));

        if(octave >= .25 && octave <= 10) {
            switch (rem) {
                case 0:     //root
                    factor = octave;
                    break;
                case 1:     //minor second
                    factor = octave * (float) 17 / 16;
                    break;
                case 2:     //major second
                    factor = octave * (float) 9 / 8;
                    break;
                case 3:    //minor third
                    factor = octave * (float) 19 / 16;
                    break;
                case 4:     //major third
                    factor = octave * (float) 5 / 4;
                    break;
                case 5:     //perfect fourth
                    factor = octave * (float) 21 / 16;
                    break;
                case 6:    //diminished fifth
                    factor = octave * (float) 23 / 16;
                    break;
                case 7:     //perfect fifth
                    factor = octave * (float) 3 / 2;
                    break;
                case 8:     //minor sixth
                    factor = octave * (float) 25 / 16;
                    break;
                case 9:     //major sixth
                    factor = octave * (float) 27 / 16;
                    break;
                case 10:    //minor seventh
                    factor = octave * (float) 29 / 16;
                    break;
                case 11:   //major seventh
                    factor = octave * (float) 15 / 8;
                    break;
            }
        }
        return factor;
    }
}