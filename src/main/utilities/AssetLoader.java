package main.utilities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public final class AssetLoader {

    private static final URL missingImage = AssetLoader.class.getResource("/assets/testAssets/Error-MissingImage.png");

    public static BufferedImage load(String imagePath) {
        BufferedImage image;
        try {
            image = ImageIO.read(AssetLoader.class.getResource(imagePath));
            return image;
        } catch (Exception e) {
            try {
                Debug.error(true, "Failed to load image - " + imagePath);
                Debug.error(true, "EXCEPTION MESSAGE:" + e.getMessage());
                image = convertImage(ImageIO.read(missingImage));
                return image;
            }catch (Exception ex) {
                Debug.error(true, "Failed to load - MissingImage.png");
                Debug.error(true, "EXCEPTION MESSAGE:" + ex.getMessage());
                return null;
            }
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

    public static BufferedImage resizeImage(BufferedImage image, int newWidth, int newHeight) {

        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizedImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(image, 0, 0, newWidth, newHeight, 0, 0, image.getWidth(),
                image.getHeight(), null);
        g.dispose();

        return resizedImage;
    }

    public static BufferedImage scaleImage(BufferedImage image, double scaleFactor) {
        return resizeImage(image,
                (int)(image.getWidth()*scaleFactor),
                (int)(image.getHeight()*scaleFactor));
    }
}
