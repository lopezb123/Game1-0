package base.gameobjects;

import base.Game;
import base.graphicsservice.Rectangle;
import base.graphicsservice.RenderHandler;
import base.graphicsservice.Sprite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

import static base.Game.TILE_SIZE;

public class Plant implements GameObject {

    protected static final Logger logger = LoggerFactory.getLogger(Plant.class);
    public static final int DEFAULT_GROWING_TIME = 400;

    Sprite previewSprite;
    AnimatedSprite animatedSprite;

    Rectangle rectangle;

    int growingTicks;
    int growingStage;
    int growingTime;

    String plantType;

    public Plant(Sprite previewSprite, AnimatedSprite animatedSprite, int x, int y, String plantType) {
        this.previewSprite = previewSprite;
        this.animatedSprite = animatedSprite;
        this.plantType = plantType;

        rectangle = new Rectangle(x, y, TILE_SIZE, TILE_SIZE);
        rectangle.generateGraphics(1, 123);
    }


    @Override
    public void render(RenderHandler renderer, int xZoom, int yZoom) {
        if (animatedSprite != null) {
            renderer.renderSprite(animatedSprite, rectangle.getX(), rectangle.getY(), xZoom, yZoom, false);
        }
    }

    @Override
    public void update(Game game) {
        if (growingStage < 3) {
            growingTicks++;
            if (growingTicks > getGrowingTime()) {
                animatedSprite.incrementSprite();
                growingStage++;
                growingTicks = 0;
            }
        }
    }

    @Override
    public int getLayer() {
        return 1;
    }

    @Override
    public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom, Game game) {
        if (mouseRectangle.intersects(rectangle)) {
            logger.info("Plant is clicked");
            if (growingStage == 3) {
                game.pickUpPlant(this);
            }
            return true;
        }
        return false;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public int getGrowingStage() {
        return growingStage;
    }

    public void setGrowingStage(int growingStage) {
        for (int i = 0; i < growingStage; i++) {
            animatedSprite.incrementSprite();
        }
        this.growingStage = growingStage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plant plant = (Plant) o;
        return growingTicks == plant.growingTicks && growingStage == plant.growingStage && growingTime == plant.growingTime
                && previewSprite.equals(plant.previewSprite) && animatedSprite.equals(plant.animatedSprite)
                && rectangle.equals(plant.rectangle) && plantType.equals(plant.plantType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(previewSprite, animatedSprite, rectangle, growingTicks, growingStage, growingTime, plantType);
    }

    public Sprite getPreviewSprite() {
        return previewSprite;
    }

    public int getGrowingTime() {
        return growingTime > 0 ? growingTime : DEFAULT_GROWING_TIME;
    }

    public void setGrowingTime(int growingTime) {
        this.growingTime = growingTime;
    }

    public String getPlantType() {
        return plantType;
    }
}
