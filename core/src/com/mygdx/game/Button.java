package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Button {

    //Button x axis position
    float x;
    //Button y axis position
    float y;
    //Button width
    float w;
    //Button height
    float h;

    //Button isPressed or Down
    boolean isDown;

    //Button texture for up
    Texture textureUp;
    //Button texture for Down
    Texture textureDown;

    //Button Constructor to initialize a button
    public Button(float x, float y, float w, float h, Texture textureUp, Texture textureDown) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        isDown = false;

        this.textureUp = textureUp;
        this.textureDown = textureDown;
    }

    //Method to update button touch using x,y coordinates
    public void update(boolean checkTouch, int touchX, int touchY) {
        isDown = false;

        if (checkTouch) {
            int h2 = Gdx.graphics.getHeight();
            //Touch coordinates have origin in top-left instead of bottom left
            if (touchX >= x && touchX <= x + w && h2 - touchY >= y && h2 - touchY <= y + h) {
                isDown = true;
            }
        }
    }

    //Method to draw the button on SpriteBatch
    public void draw(SpriteBatch batch) {
        if (! isDown) {
            batch.draw(textureUp, x, y, w, h);
        } else {
            batch.draw(textureDown, x, y, w, h);
        }
    }
}
