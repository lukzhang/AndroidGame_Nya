package com.example.assignment1_comp486;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The player's gun which can fire bullets by keeping track of the number of instansiated bullets as
 * well as the rate of fire.
 */

public class Gun extends GameObject{
    private int maxBullets = 7;        //Change this to improve bullet refresh rate
    private int numBullets;
    private int nextBullet;
    private int rateOfFire = 1;//bullets per second
    private long lastShotTime;

    //***********MY OWN******************
    private int damage = 1;
    private float bulletWidth = 5.0f;
    private float bulletHeight = 0.6f;

    private CopyOnWriteArrayList<Bullet> bullets;

    float speed = 0.85f;

    Gun(){
        bullets = new CopyOnWriteArrayList<Bullet>();
        lastShotTime = -1;
        nextBullet = -1;
    }

    public void update(long fps){
        //update all the bullets
        for(Bullet bullet: bullets){
            bullet.update(fps);
        }
    }

    public int getRateOfFire(){
        return rateOfFire;
    }

    public void setFireRate(int rate){
        rateOfFire = rate;
    }

    public int getNumBullets(){
        //tell the view how many bullets there are
        return numBullets;
    }
    public float getBulletX(int bulletIndex){
        if(bullets != null && bulletIndex < numBullets) {
            return bullets.get(bulletIndex).getX();
        }
        return -1f;
    }
    public float getBulletY(int bulletIndex){
        if(bullets != null) {
            return bullets.get(bulletIndex).getY();
        }
        return -1f;
    }

    public void hideBullet(int index){
        bullets.get(index).hideBullet();
    }

    public int getDirection(int index){
        return bullets.get(index).getDirection();
    }

    //Allows a shot to be taken if the set amount of time (1sec) has passed, which a bullet is
    //spawned
    public boolean shoot(float ownerX, float ownerY, int ownerFacing, float ownerHeight){
        boolean shotFired = false;
        if(System.currentTimeMillis() - lastShotTime  > 1000/rateOfFire){
            //spawn another bullet;
            nextBullet ++;
            //numBullets++;
            if(numBullets >= maxBullets){numBullets = maxBullets;}
            if(nextBullet == maxBullets){
                nextBullet = 0;
            }
            lastShotTime = System.currentTimeMillis();
            bullets.add(nextBullet, new Bullet(ownerX, (ownerY), speed, ownerFacing));
            shotFired = true;
            numBullets++;
        }
        return shotFired;
    }


    public void setDamage(int damage){
        this.damage=damage;
    }

    public int getDamage(){
        return damage;
    }

    public void setBulletWidth(float bulletWidth){
        this.bulletWidth = bulletWidth;
    }

    public float getBulletWidth(){
        return bulletWidth;
    }

    public void setBulletHeight(float bulletHeight){
        this.bulletHeight=bulletHeight;
    }

    public float getBulletHeight(){
        return bulletHeight;
    }


}
