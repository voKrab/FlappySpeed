package com.vokrab.flappyspeed.view.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;

import com.vokrab.flappyspeed.MainActivity;

public class BaseFragment extends Fragment
{
	protected MainActivity controller;

	@Override
	public void onAttach ( Activity controller )
	{
		super.onAttach ( controller );
		this.controller = ( MainActivity ) controller;
	}

	@Override
	public void onCreate ( final Bundle savedInstanceState )
	{
		super.onCreate ( savedInstanceState );
	}

	@Override
	public void onActivityCreated ( Bundle savedInstanceState )
	{
		super.onActivityCreated ( savedInstanceState );

		clearFields ();
	}

	@Override
	public void onResume ()
	{
		super.onResume ();

//		Tracker tracker = controller.getTracker ();
//		tracker.setScreenName ( getClass ().getSimpleName () );
//        tracker.send ( new HitBuilders.AppViewBuilder ().build () );
	}

	// public void sendEvent ( String category, String action, String label )
	// {
	// tracker.send ( MapBuilder.createEvent ( category, action, label, null
	// ).build () );
	// }

	protected void clearFields ()
	{

	}

	@Override
	public Animation onCreateAnimation ( int transit, final boolean enter, int nextAnim )
	{
		if ( nextAnim == 0 )
		{
			init ();
			return null;
		} else
		{
			Animation anim = AnimationUtils.loadAnimation ( getActivity (), nextAnim );
			anim.setAnimationListener ( new AnimationListener ()
			{
				public void onAnimationStart ( Animation animation )
				{
				}

				public void onAnimationRepeat ( Animation animation )
				{
				}

				public void onAnimationEnd ( Animation animation )
				{
					if ( enter )
					{
						init ();
					}
				}
			} );
			return anim;
		}
	}

	protected void init ()
	{

	}
}
