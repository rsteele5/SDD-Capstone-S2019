package gameengine.rendering.animation;

public class PlayerSSIdleAnimation extends Animation{

    public PlayerSSIdleAnimation() {
        AnimationImage idle = new AnimationImage("/assets/testAssets/square2.png", 60);

        addAnimationImage(idle);
    }

    @Override
    public String getName() {
        return "SS_Idle";
    }
}
