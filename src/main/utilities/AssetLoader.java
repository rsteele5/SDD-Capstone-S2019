package main.utilities;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public final class AssetLoader {

    private static final URL missingImage = AssetLoader.class.getResource("/assets/testAssets/Error-MissingImage.png");

    public static BufferedImage load(String imagePath) {
        try {
            BufferedImage image = ImageIO.read(AssetLoader.class.getResource(imagePath));
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
            Debug.error(true, "Failed to load - MissingImage.png");
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
