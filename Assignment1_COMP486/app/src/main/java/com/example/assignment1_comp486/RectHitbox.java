package com.example.assignment1_comp486;

/**
 * Hitbox for player and other GameObjects. If two hitboxes intersect, the intersects method
 * will return as such. This is used in PlatformView to check if it has hit any other gameobject
 * with a hitbox
 */

//Rectangular hitbox which player collides with
public class RectHitbox {
    float top;
    float left;
    float bottom;
    float right;
    float height;

    boolean intersects(RectHitbox rectHitbox){
        boolean hit = false;

        if(this.right > rectHitbox.left
                && this.left < rectHitbox.right ){
            // Intersecting on x axis

            if(this.top < rectHitbox.bottom
                    && this.bottom > rectHitbox.top ){
                // Intersecting on y as well
                // Collision
                hit = true;
            }
        }


        return hit;
    }

    public void setTop(float top) {
        this.top = top;
    }

    public float getLeft() {
        return left;
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public void setBottom(float bottom) {
        this.bottom = bottom;
    }

    public float getRight() {
        return right;
    }

    public void setRight(float right) {
        this.right = right;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}