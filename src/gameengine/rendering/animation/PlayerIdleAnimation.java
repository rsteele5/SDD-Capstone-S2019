package gameengine.rendering.animation;

public class PlayerIdleAnimation extends Animation{

    public PlayerIdleAnimation() {
        String path = "/assets/player/teddyIdleAnimation/";
        AnimationImage lookCenter = new AnimationImage(path + "Overworld-Teddy-Center.png", 60);
        AnimationImage lookLeft1 = new AnimationImage(path + "Overworld-Teddy-Left1.png", 5);
        AnimationImage lookLeft2 = new AnimationImage(path + "Overworld-Teddy-Left2.png", 5);
        AnimationImage lookLeft3 = new AnimationImage(path + "Overworld-Teddy-Left3.png", 60);
        AnimationImage lookRight1 = new AnimationImage(path + "Overworld-Teddy-Right1.png", 5);
        AnimationImage lookRight2 = new AnimationImage(path + "Overworld-Teddy-Right2.png", 5);
        AnimationImage lookRight3 = new AnimationImage(path + "Overworld-Teddy-Right3.png", 60);

        addAnimationImage(lookCenter);
        addAnimationImage(lookLeft1);
        addAnimationImage(lookLeft2);
        addAnimationImage(lookLeft3);
        addAnimationImage(lookLeft2);
        addAnimationImage(lookLeft1);
        addAnimationImage(lookCenter);
        addAnimationImage(lookRight1);
        addAnimationImage(lookRight2);
        addAnimationImage(lookRight3);
        addAnimationImage(lookRight2);
        addAnimationImage(lookRight1);
    }
}
