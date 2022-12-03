import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(robot.createScreenCapture(rectangle), "png", os);
            return Base64.getEncoder().encodeToString(os.toByteArray());
        } catch (AWTException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
