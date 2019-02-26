package gameengine.audio;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Audio {
    private static AudioInputStream backgroundAudioIn;
    private static AudioInputStream soundEffectAudioIn;
    private static Clip backGroundClip;
    private static Clip soundEffectClip;

    public static void play(URL url) {
        try {
            backgroundAudioIn = AudioSystem.getAudioInputStream(url);
            backGroundClip = AudioSystem.getClip();
            if(backGroundClip != null) {
                backGroundClip.open(backgroundAudioIn);
                backGroundClip.start();
                backGroundClip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void end() {
        if (backGroundClip != null && backGroundClip.isOpen()) {
            backGroundClip.close();
        }
    }

    public static void changeSoundState() {
        if (backGroundClip != null) {
            if (backGroundClip.isRunning())
                backGroundClip.stop();
            else {
                backGroundClip.start();
                backGroundClip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }
    }
}
