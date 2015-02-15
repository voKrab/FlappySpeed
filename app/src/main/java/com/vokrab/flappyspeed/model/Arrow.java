package com.vokrab.flappyspeed.model;

/**
 * Created by Олег on 15.02.2015.
 */
public class Arrow
{
    private float downVelocity;
    private float rotation;

    public Arrow ()
    {
        downVelocity = -1;
        rotation = 0;
    }

    public void down ()
    {
        downVelocity--;
        downVelocity = Math.max ( -10, downVelocity );
        rotation += downVelocity / 2f;
    }

    public float getRotation ()
    {
        return rotation;
    }

    public void up ()
    {
        downVelocity += 20;
        downVelocity = Math.min ( 50, downVelocity );
    }
}
