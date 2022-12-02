import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Base64;

import javax.imageio.ImageIO;

public class ScreenCapture {
    public static String Capture() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        Rectangle rectangle = new Rectangle(0, 0, dimension.width, dimension.height);
        Robot robot;
        try {
            robot = new Robot();
            final ByteArrayOutputStream os = new ByteArrayOutputStream();
            try {
                ImageIO.write(robot.createScreenCapture(rectangle), "png", os);
                return Base64.getEncoder().encodeToString(os.toByteArray());
            } catch (final IOException ioe) {
                throw new UncheckedIOException(ioe);
            }
        } catch (AWTException e) {
            e.printStackTrace();
        }
        return null;
    }
}
