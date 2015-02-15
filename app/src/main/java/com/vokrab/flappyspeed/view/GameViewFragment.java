package com.vokrab.flappyspeed.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vokrab.flappyspeed.R;
import com.vokrab.flappyspeed.ViewStateController;
import com.vokrab.flappyspeed.model.Arrow;
import com.vokrab.flappyspeed.model.Game;
import com.vokrab.flappyspeed.view.base.BaseFragment;

import java.util.Timer;
import java.util.TimerTask;

public class GameViewFragment extends BaseFragment
{
	private View logoImageView;
	private ImageView arrowImageView;
	private Game game;

	@Override
	public View onCreateView ( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
	{
		View view = inflater.inflate ( R.layout.game_layout, null );
		arrowImageView = ( ImageView ) view.findViewById ( R.id.arrowImageView );
        view.setClickable ( true );
		view.setOnTouchListener ( new View.OnTouchListener ()
		{
			@Override
			public boolean onTouch ( View v, MotionEvent event )
			{
				switch ( event.getAction () )
				{
					case MotionEvent.ACTION_UP:
					{
						game.touch ();
					}
				}
				return false;
			}
		} );
		return view;
	}

	public void onActivityCreated ()
	{

	}

	@Override
	public void init ()
	{
		game = controller.getGame ();
		Timer timer = new Timer ();
		timer.scheduleAtFixedRate ( new TimerTask ()
		{
			@Override
			public void run ()
			{
				game.update ();
				getView ().post ( new Runnable ()
				{
					@Override
					public void run ()
					{
						render ();
					}
				} );
			}
		}, 0, 25 );
	}

	private void render ()
	{
		Arrow arrow = game.getArrow ();
//		arrowImageView.animate ().rotation ( arrow.getRotation () ).setDuration ( 5 ).start ();
        arrowImageView.setRotation ( arrow.getRotation () );
	}

	// TODO Реализовать данную цепочку анимаций с помощью одного xml в папке
	// anim
	private void startAnimation ()
	{
		logoImageView.setScaleX ( 0f );
		logoImageView.setScaleY ( 0f );
		logoImageView.setAlpha ( 0f );
		logoImageView.animate ().scaleX ( 1f ).scaleY ( 1f ).alpha ( 1f ).rotation ( 360f ).setDuration ( 1000 ).setListener ( new AnimatorListenerAdapter ()
		{
			@Override
			public void onAnimationEnd ( Animator animation )
			{
				super.onAnimationEnd ( animation );
				logoImageView.animate ().scaleX ( 1.25f ).scaleY ( 1.25f ).setDuration ( 250 ).setListener ( new AnimatorListenerAdapter ()
				{
					@Override
					public void onAnimationEnd ( Animator animation )
					{
						super.onAnimationEnd ( animation );
						logoImageView.animate ().scaleX ( 1f ).scaleY ( 1f ).setDuration ( 250 ).setListener ( new AnimatorListenerAdapter ()
						{
							@Override
							public void onAnimationEnd ( Animator animation )
							{
								super.onAnimationEnd ( animation );
								controller.setState ( ViewStateController.VIEW_STATE.MENU );
							}
						} ).start ();
					}
				} ).start ();
			}
		} ).start ();
	}
}
