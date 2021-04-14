package sound;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public class SoundBip  {


    Synthesizer synth;



    public void extractedSound(int instrument, int note, int volume, int time) {
        try {
            synth = MidiSystem.getSynthesizer();
            synth.open();
            MidiChannel[] channels = synth.getChannels();
            channels[0].programChange(instrument);
            channels[0].noteOn(note, volume);
            Thread.sleep(time);
            channels[0].noteOff(note);
            synth.close();
        } catch (MidiUnavailableException | InterruptedException e) {
            e.printStackTrace();
        }
    }


}
