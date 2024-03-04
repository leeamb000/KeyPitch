package profile;

import java.io.File;

/**
 * ProfileController.java
 * Stores current active profile and allows switching between profiles
 *
 * @author Caleb Henry
 */
public class ProfileController {
    private static final Profile synth = new SynthProfile();
    private static final Profile piano = new PianoProfile();
    private static Profile custom = piano; // Starts as piano but can be changed
    private static Profile currentProfile = piano; // Starts as piano but can be changed

    public static void setProfileSynth() {
        currentProfile = synth;
    }

    public static void setProfilePiano() {
        currentProfile = piano;
    }

    public static void setProfileCustom() {
        currentProfile = custom;
    }

    public static void setCustomFile(File file) {
        custom = new CustomProfile(file);
        currentProfile = custom;
    }

    public static void play(int note) {
        currentProfile.play(note);
    }

    public static void stop(int note) {
        currentProfile.stop(note);
    }
}
