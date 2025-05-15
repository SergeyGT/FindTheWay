package UI;


import org.example.DirectionEnum;
import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class ImageManager {
    private final Map<String, ImageIcon> directionIcons;
    private final ImageIcon startIcon;
    private final ImageIcon endIcon;
    private final ImageIcon emptyIcon;

    public ImageManager() {
        directionIcons = new HashMap<>();
        startIcon = loadImage("/images/start.jpg");
        endIcon = loadImage("/images/end.jpg");
        emptyIcon = loadImage("/images/empty.jpg");

        directionIcons.put("leftup", loadImage("/images/leftUp.jpg"));
        directionIcons.put("leftdown", loadImage("/images/leftDown.jpg"));
        directionIcons.put("leftright", loadImage("/images/leftRight.jpg"));

        directionIcons.put("rightup", loadImage("/images/rightUp.jpg"));
        directionIcons.put("rightdown", loadImage("/images/rightDown.jpg"));
        directionIcons.put("rightleft", loadImage("/images/rightLeft.jpg"));

        directionIcons.put("downup", loadImage("/images/downUp.jpg"));
        directionIcons.put("downleft", loadImage("/images/downLeft.jpg"));
        directionIcons.put("downright", loadImage("/images/downRight.jpg"));

        directionIcons.put("updown", loadImage("/images/upDown.jpg"));
        directionIcons.put("upleft", loadImage("/images/upLeft.jpg"));
        directionIcons.put("upright", loadImage("/images/upRight.jpg"));


    }

    public Map<String, ImageIcon> getDirectionIcons() {
        return directionIcons;
    }

    private ImageIcon loadImage(String path) {
        try {
            return new ImageIcon(getClass().getResource(path));
        } catch (Exception e) {
            System.err.println("Error loading image: " + path);
            return null;
        }
    }

    public ImageIcon getStartIcon() { return startIcon; }
    public ImageIcon getEndIcon() { return endIcon; }
    public ImageIcon getEmptyIcon() { return emptyIcon; }
}