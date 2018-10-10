package com.example.assignment1_comp486;

/**
 * The main projectile in the game used for killing enemies. This is instansiated by the Gun class,
 * which in turn is instantiated by the Player class. When player is ready to fire, this Class is
 * instansiated
 */

public class Bullet  {

    private float x;
    private float y;
    private float xVelocity;
    private int direction;


    //Sets the paramaters based on the Gun and Player classes which use this Bullet class
    Bullet(float x, float y, float speed, int direction){
        this.direction = direction;
        this.x = x;
        this.y = y;
        this.xVelocity = speed * direction;
    }

    public int getDirection(){
        return direction;
    }

    public void update(long fps){
        x += xVelocity / fps;
    }

    //Hides the bullet far to the left when not used
    public void hideBullet(){
        this.x = -150;
        this.xVelocity = 0;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

}