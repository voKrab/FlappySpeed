package com.vokrab.flappyspeed.model;

/**
 * Created by Олег on 15.02.2015.
 */
public class Game
{
    private Arrow arrow;

    public Game ()
    {
        arrow = new Arrow ();
    }

    public Arrow getArrow ()
    {
        return arrow;
    }

    public void update ()
    {
        arrow.down ();
    }

    public void touch ()
    {
        arrow.up ();
    }
}
