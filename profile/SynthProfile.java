package profile;

/**
 * SynthProfile.java
 * Synth default profile. Uses sine function to synthesize notes
 *
 * @author Caleb Henry
 */
public class SynthProfile implements Profile {
    private SynthThread st;

    public void play(int note) {
        st = new SynthThread(note);
        st.beginNote();
    }

    public void stop(int note) {
        st.endNote();
    }
}
