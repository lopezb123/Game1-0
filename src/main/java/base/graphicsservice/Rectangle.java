package base.graphicsservice;

import base.Game;
import base.map.MapTile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Rectangle {

    private int x;
    private int y;
    private int width;
    private int height;
    private int[] pixels;

    protected static final Logger logger = LoggerFactory.getLogger(Rectangle.class);

    public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rectangle() {
        this(0, 0, 0, 0);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int[] getPixels() {
        if (pixels != null) {
            return pixels;
        }
        logger.error("Attempt of getting pixels without generating graphics first");
        return new int[0];
    }

    public void generateGraphics(int borderWidth, int color) {
        pixels = new int[width * height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i < borderWidth || i >= height - borderWidth || j < borderWidth || j >= width - borderWidth) {
                    pixels[i + j * width] = color;
                } else {
                    pixels[i + j * width] = Game.ALPHA;
                }
            }
        }
    }

    public boolean intersects(Rectangle otherRectangle) {
        return (intersectsByX(otherRectangle) && intersectsByY(otherRectangle));
    }

    private boolean intersectsByY(Rectangle otherRectangle) {
        return !(y > otherRectangle.getY() + otherRectangle.getHeight() || otherRectangle.getY() > y + height);
    }

    private boolean intersectsByX(Rectangle otherRectangle) {
        return !(x > otherRectangle.getX() + otherRectangle.getWidth() || otherRectangle.getX() > x + width);
    }

    public boolean intersects(MapTile tile) {
        return (intersectsByX(tile) && intersectsByY(tile));
    }

    private boolean intersectsByY(MapTile tile) {
        return !(y > tile.getY() * 64 + 32 || tile.getY() * 64 > y + height);
    }

    private boolean intersectsByX(MapTile tile) {
        return !(x > tile.getX() * 64 + 32 || tile.getX() * 64 > x + width);
    }

    public boolean potentialIntersects(MapTile tile, int xPos, int yPos) {
        return (intersectsByX(tile, xPos) && intersectsByY(tile, yPos));
    }

    private boolean intersectsByY(MapTile tile, int yPos) {
        return !(yPos > tile.getY() * (Game.TILE_SIZE * Game.ZOOM) + Game.TILE_SIZE || tile.getY() * (Game.TILE_SIZE * Game.ZOOM) > yPos + ((Game.TILE_SIZE * Game.ZOOM) - (Game.TILE_SIZE / 2)));     //48 = 32 + 32/2
    }

    private boolean intersectsByX(MapTile tile, int xPos) {
        return !(xPos > tile.getX() * (Game.TILE_SIZE * Game.ZOOM) + Game.TILE_SIZE || tile.getX() * (Game.TILE_SIZE * Game.ZOOM) > xPos + width);
    }
}
