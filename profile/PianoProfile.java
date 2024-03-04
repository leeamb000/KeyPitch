package profile;

import javax.sound.midi.*;
import java.util.Objects;

/**
 * PianoProfile.java
 * MIDI piano default profile
 *
 * @author Caleb Henry
 */
public class PianoProfile implements Profile {
    MidiChannel channel;

    public PianoProfile() {
        try {
            Synthesizer synth = MidiSystem.getSynthesizer();

            synth.open();

            synth.loadAllInstruments(synth.getDefaultSoundbank());

            Instrument[] instruments = synth.getLoadedInstruments();

            MidiChannel[] channels = synth.getChannels();

            for (MidiChannel midiChannel : channels) {
                if (midiChannel != null) {
                    channel = midiChannel;
                    break;
                }
            }

            for (int i = 0; i < instruments.length; i++) {
                if (instruments[i].toString().startsWith("Instrument MidiPiano")) {
                    Objects.requireNonNull(channel).programChange(i);
                    break;
                }
            }

        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void play(int note) {
        channel.noteOn(note, 100);
    }

    public void stop(int note) {
        channel.noteOff(note, 0);
    }
}
