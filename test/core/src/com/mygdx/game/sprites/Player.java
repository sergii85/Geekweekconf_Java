package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.PlatformerGame;

/**
 * Player
 */
public class Player {
    // Movement on X axis
    public static final int MOVEMENT_ON_X = 70;
    // Resistance movement
    public static final int RESISTANCE = -10;
    // Gravity takes away from the Y axis
    public static final int GRAVITY = -10;

    private Vector2 velocity;
    private Vector2 position;
    private Texture image;
    private Rectangle bounds;
    private boolean stayOnObject;

    /**
     * Constructor for initialized position, velocity and texture
     */
    public Player() {
        image = new Texture("player.png");
        // Initial player position
        position = new Vector2(0, 50);
        // Initial velocity for player down on ground
        velocity = new Vector2(0, 0);
        bounds = new Rectangle(position.x, position.y, image.getWidth(), image.getHeight());
        stayOnObject = false;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Texture getImage() {
        return image;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public boolean isStayOnObject() {
        return stayOnObject;
    }

    public void setStayOnObject(boolean stayOnObject) {
        this.stayOnObject = stayOnObject;
        if (stayOnObject) {
            resetVelocity();
        }
    }

    public void jump() {
        velocity.y = 250;
    }

    public void right() {
        velocity.x = MOVEMENT_ON_X;
    }

    public void left() {
        velocity.x = -MOVEMENT_ON_X;
    }

    /**
     * Stop move object
     */
    public void resetVelocity() {
        velocity.x = 0;
        velocity.y = 0;
    }

    /**
     * Calculating new position
     * @param delta time after last call method render
     */
    public void update(float delta) {
        if (position.y > 0) {
            // Add velocity vector and gravity vector  (0, -15)
            // For additional information see: https://goo.gl/lD9I77
            velocity.add(0, GRAVITY);
        }

        // Calculation new position
        if (velocity.x != 0) {
            position.add(velocity.x * delta, 0);
        }
        if (!stayOnObject || velocity.y > 0) {
            position.add(0, velocity.y * delta);
        }

        //Checking overlaps with game world bounds
        if (position.y < 0) {
            position.y = 0;
            resetVelocity();
        }
        if (position.x < 0) {
            position.x = 0;
            resetVelocity();
        }
        if (position.y > PlatformerGame.HEIGHT - image.getHeight()) {
            position.y = PlatformerGame.HEIGHT - image.getHeight();
            resetVelocity();
        }
        if (position.x > PlatformerGame.WIDTH - image.getWidth()) {
            position.x = PlatformerGame.WIDTH - image.getWidth();
            resetVelocity();
        }
        bounds.setPosition(position);
//        System.out.println("v = " + velocity + "; p = " + position + "; d = " + delta);
    }

    /**
     * Unload resources
     */
    public void dispose() {
        image.dispose();
    }
}
