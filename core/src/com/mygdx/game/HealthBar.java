package com.mygdx.game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class HealthBar extends Actor{

    private ShapeRenderer healthBarOut;
    private ShapeRenderer healthBarIn;

    private  int x;
    private  int y;
    private int widthInner;
    private int heightInner;
    private int widthOuter;
    private int heightOuter;



    public void setX(int x) {
        this.x = x;
    }


    public void setY(int y) {
        this.y = y;
    }


    public void setWidthInner(int width) {
        this.widthInner = width;
    }


    public void setHeightInner(int height) {
        this.heightInner = height;
    }

    public int getWidthOuter() {
        return widthOuter;
    }

    public void setWidthOuter(int widthOuter) {
        this.widthOuter = widthOuter;
    }

    public int getHeightOuter() {
        return heightOuter;
    }

    public void setHeightOuter(int heightOuter) {
        this.heightOuter = heightOuter;
    }

    static private boolean projectionMatrixSet;

    public HealthBar(){
        healthBarOut = new ShapeRenderer();
        healthBarIn = new ShapeRenderer();
        projectionMatrixSet = false;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        if(!projectionMatrixSet){
            healthBarOut.setProjectionMatrix(batch.getProjectionMatrix());
            healthBarIn.setProjectionMatrix(batch.getProjectionMatrix());
        }

        healthBarOut.begin(ShapeRenderer.ShapeType.Filled);
        healthBarOut.setColor(Color.DARK_GRAY);
        healthBarOut.rect(x-5,y-5,widthOuter,heightOuter);
        healthBarOut.end();

        healthBarIn.begin(ShapeRenderer.ShapeType.Filled);
        healthBarIn.setColor(Color.RED);
        healthBarIn.rect(x, y, widthInner, heightInner);
        healthBarIn.end();
        batch.begin();
    }


}
