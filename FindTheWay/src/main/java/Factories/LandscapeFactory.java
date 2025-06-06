package Factories;

import Interfaces.ILandscapeElement;
import org.example.*;

public class LandscapeFactory {
    public static ILandscapeElement create(String type) {
        if (type == null) return null;

        switch (type.toLowerCase()) {
            case "tree": return new Tree();
            case "fire": return new Fire();
            case "flowerbed": return new FlowerBed();
            case "grass": return new WildGrass();
            case "water": return new WaterElement();
            case "burnt": return new BurntFire();
            case "grassroad": return new WildGrassRoad();
            default: return null;
        }
    }
}
