package com.mygdx.game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class HealthBar extends Actor{

    private ShapeRenderer healthBarOut;
    private ShapeRenderer healthBarIn;
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
        healthBarOut.rect(100,950,730,60);
        healthBarOut.end();
        healthBarIn.begin(ShapeRenderer.ShapeType.Filled);
        healthBarIn.setColor(Color.RED);
        healthBarIn.rect(105, 955, 720, 50);
        healthBarIn.end();
        batch.begin();
    }


}
