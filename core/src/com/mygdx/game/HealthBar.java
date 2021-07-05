package com.mygdx.game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class HealthBar extends Actor{

    //ShapeRenderer For Out
    private ShapeRenderer healthBarOut;
    //ShapeRenderer For In
    private ShapeRenderer healthBarIn;

    //X coordinates
    private  int x;
    //Y coordinates
    private  int y;

    //Width Inner Bar
    private int widthInner;
    //Height Inner Bar
    private int heightInner;

    //Width Outer Bar
    private int widthOuter;
    //Height Outer Bar
    private int heightOuter;

    //Method to return widthInner
    public int getWidthInner() {
        if(widthInner < 0)
            widthInner =0;
        return widthInner;
    }




    //Method to set X
    public void setX(int x) {
        this.x = x;
    }

    //Method to Set Y
    public void setY(int y) {
        this.y = y;
    }


    //Method to set Width of Inner Bar
    public void setWidthInner(int width) {
        this.widthInner = width;
    }

    //Method to Set Height of Inner Bar
    public void setHeightInner(int height) {
        this.heightInner = height;
    }

    //Method to return Width of Outer Bar
    public int getWidthOuter() {
        return widthOuter;
    }

    //Method to set Width of Outer Bar
    public void setWidthOuter(int widthOuter) {
        this.widthOuter = widthOuter;
    }

    //Method to get Height of Outer Bar
    public int getHeightOuter() {
        return heightOuter;
    }

    //Method to set Height of Outer Bar
    public void setHeightOuter(int heightOuter) {
        this.heightOuter = heightOuter;
    }

    //Boolean for Projection Matrix Set Status
    static private boolean projectionMatrixSet;


    //Constructor for initialize a health bar
    public HealthBar(){
        healthBarOut = new ShapeRenderer();
        healthBarIn = new ShapeRenderer();
        projectionMatrixSet = false;
    }

    //Method to Draw Health Bar on this given batch
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
