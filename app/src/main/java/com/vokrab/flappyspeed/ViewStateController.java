package com.vokrab.flappyspeed;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.vokrab.flappyspeed.view.GameViewFragment;
import com.vokrab.flappyspeed.view.SplashViewFragment;
import com.vokrab.flappyspeed.view.base.BaseFragment;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Oleg Barkov
 * 
 *         Controller for all data/events related with view states, fragments
 *         and navigation scheme of application
 */
public class ViewStateController implements Serializable
{
	/**
	 * All application view state
	 */
	public enum VIEW_STATE
	{
		EXIT, SPLASH, MENU, GAME;
	}

	private static final long serialVersionUID = 1L;

	/**
	 * Current view state
	 */
	private VIEW_STATE state;
	/**
	 * Main controller
	 */
	private transient MainActivity controller;
	/**
	 * Current Fragment
	 */
	private transient BaseFragment currentFragment;
	private FragmentManager manager;
	/**
	 * map of created fragments
	 */
	private Map < VIEW_STATE, BaseFragment > fragments;
	/**
	 * Previous view state
	 */
	private VIEW_STATE prevState;

	public ViewStateController(MainActivity controller)
	{
		this.state = VIEW_STATE.SPLASH;
		this.controller = controller;
		manager = controller.getSupportFragmentManager ();
		fragments = new HashMap < VIEW_STATE, BaseFragment > ();
	}

	/**
	 * Change view state
	 */
	public void setState ( VIEW_STATE newState )
	{
		setState ( newState, null );
	}

	private void commitState ( FragmentTransaction fragmentTransaction )
	{
		fragmentTransaction.replace ( R.id.content_frame, currentFragment );
		fragmentTransaction.commitAllowingStateLoss ();
	}

	/**
	 * Change view state with params
	 */
	public void setState ( VIEW_STATE newState, Bundle bundle )
	{
		// System.out.println ( prevState + " " + newState + " " + state );
		prevState = VIEW_STATE.valueOf(state.toString());
		switch ( newState )
		{
            case GAME:
            {
                currentFragment = new GameViewFragment();
                FragmentTransaction fragmentTransaction = manager.beginTransaction ();

                commitState(fragmentTransaction);
                break;
            }

			case SPLASH:
			{
				currentFragment = new SplashViewFragment ();
				FragmentTransaction fragmentTransaction = manager.beginTransaction ();

				commitState(fragmentTransaction);
				break;
			}

			case EXIT:
			{
				Intent intent = new Intent ( Intent.ACTION_MAIN );
				intent.addCategory ( Intent.CATEGORY_HOME );
				intent.setFlags ( Intent.FLAG_ACTIVITY_NEW_TASK );
				controller.startActivity ( intent );
				break;
			}
			default:
				break;
		}
		if ( newState != VIEW_STATE.EXIT )
		{
			this.state = VIEW_STATE.valueOf(newState.toString());
			if ( currentFragment != null )
			{
				fragments.put ( state, currentFragment );
			}
		}
	}

	public void onBackPressed ()
	{
		VIEW_STATE newState = getBackState ( state );
		if ( newState != null )
		{
			setState ( newState );
		}
	}

	private VIEW_STATE getBackState ( VIEW_STATE currentState )
	{
		switch ( currentState )
		{
			case SPLASH:
			{
				return VIEW_STATE.EXIT;
			}
			default:
				return prevState;
		}
	}

	private BaseFragment getFragment ( Class fragmentClass, VIEW_STATE newState )
	{
		Fragment fragment = fragments.get ( newState );
		if ( fragment != null )
		{
			return ( BaseFragment ) fragment;
		}
		try
		{
			fragment = (Fragment) fragmentClass.newInstance ();
		} catch ( InstantiationException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace ();
		} catch ( IllegalAccessException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace ();
		}
		return ( BaseFragment ) fragment;
	}

	public FragmentActivity getView ()
	{
		return controller;
	}

	public void setView ( MainActivity view )
	{
		this.controller = view;
	}

	public Fragment getCurrentFragment ()
	{
		return currentFragment;
	}

	public void clearBackStateStack ()
	{
		for ( Fragment fragment : manager.getFragments () )
		{
			if ( fragment != null )
			{
				manager.beginTransaction ().remove ( fragment ).commit ();
			}
		}
		fragments.clear ();
	}

	public VIEW_STATE getState ()
	{
		return state;
	}
}
