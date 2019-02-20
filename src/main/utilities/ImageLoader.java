package main.utilities;

import gameobjects.renderables.RenderableObject;
import main.utilities.Debug;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public final class ImageLoader {

    private static final URL base = ImageLoader.class.getResource("/assets");
    private static final URL missingImage = ImageLoader.class.getResource("/assets/testAssets/Error-MissingImage.png");

    public static BufferedImage load(RenderableObject renderable) {
        try {
            BufferedImage image = ImageIO.read(ImageLoader.class.getResource(renderable.getImagePath()));
            if (image == null) {
                Debug.error(true, "Failed to load og image - expected");
                convertImage(image = ImageIO.read(missingImage));
                if (image == null) {
                    Debug.error(true, "Failed to load og image - expected");
                    return convertImage(image);
                }

            }
            return image;
        } catch (IOException exception) {
            return null;
        }

    }

    private static BufferedImage convertImage(BufferedImage image) {
        BufferedImage newImage = new BufferedImage(
                image.getWidth(), image.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }
}
