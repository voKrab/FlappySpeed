package com.vokrab.flappyspeed.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vokrab.flappyspeed.R;
import com.vokrab.flappyspeed.ViewStateController;
import com.vokrab.flappyspeed.view.base.BaseFragment;

public class SplashViewFragment extends BaseFragment
{
	private View logoImageView;

	@Override
	public View onCreateView ( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
	{
		View view = inflater.inflate ( R.layout.splash_layout, null );

		logoImageView = view.findViewById ( R.id.logoImageView );

		return view;
	}

	public void onActivityCreated ()
	{

	}

	@Override
	public void init ()
	{
		startAnimation ();
	}

    // TODO Реализовать данную цепочку анимаций с помощью одного xml в папке anim
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
