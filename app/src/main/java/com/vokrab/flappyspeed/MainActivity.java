package com.vokrab.flappyspeed;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import com.google.android.gms.analytics.Tracker;
import com.vokrab.flappyspeed.ViewStateController.VIEW_STATE;
import com.vokrab.flappyspeed.model.Game;
import com.vokrab.flappyspeed.view.base.DialogFactory;

import java.util.HashMap;

/**
 * @author Oleg Barkov
 *         <p/>
 *         The class MainActivity. The main controller of application
 */
public class MainActivity extends FragmentActivity
{
    public enum ApplicationAction
	{
	}

	public enum ApplicationState
	{
		RESUME, PAUSE, CLOSE
	}

	/**
	 * Constants.
	 */
	public static int SCREEN_WIDTH;
	public static int SCREEN_HEIGHT;

	public static ApplicationState applicationState;

	/**
	 * instance of singleton.
	 */
	private static MainActivity instance;

	/**
	 * Return instance.
	 */
	public static MainActivity getInstance ()
	{
		return instance;
	}

	/**
	 * Controller for all possibles view states.
	 */
	private ViewStateController viewStateController;
	/**
	 * Dialog using for showing loader animation.
	 */
	private Dialog dialog;
	/**
	 * Using for communication between fragments.
	 */
	public HashMap < String, Object > communicationMap;

	/**
	 * Using for communication between fragments.
	 */
	private CommunicationManager communicationManager;
	private Tracker tracker;
    private Game game;

	// Define a DialogFragment that displays the error dialog
	public static class ErrorDialogFragment extends DialogFragment
	{
		// Global field to contain the error dialog
		private Dialog mDialog;

		// Default constructor. Sets the dialog field to null
		public ErrorDialogFragment ()
		{
			super ();
			mDialog = null;
		}

		// Set the dialog to display
		public void setDialog ( Dialog dialog )
		{
			mDialog = dialog;
		}

		// Return a Dialog to the DialogFragment.
		@Override
		public Dialog onCreateDialog ( Bundle savedInstanceState )
		{
			return mDialog;
		}
	}

	/**
	 * Init most application controllers.
	 */
	@Override
	protected void onCreate ( Bundle savedInstanceState )
	{
		super.onCreate ( savedInstanceState );

		setRequestedOrientation ( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );
		requestWindowFeature ( Window.FEATURE_NO_TITLE );
		setContentView ( R.layout.activity_main );

		instance = this;
		viewStateController = new ViewStateController ( instance );
		communicationMap = new HashMap < String, Object > ();
		communicationManager = CommunicationManager.getInstance ();
        game = new Game ();
//		GoogleAnalytics analytics = GoogleAnalytics.getInstance ( this );
//		tracker = analytics.newTracker ( R.xml.analytics );

		Display display = getWindowManager ().getDefaultDisplay ();
		int width = display.getWidth (); // deprecated
		int height = display.getHeight (); // deprecated
		SCREEN_WIDTH = width;

		setState ( VIEW_STATE.GAME );
	}

	@Override
	protected void onStart ()
	{
		super.onStart ();
	}

	public void onResume ()
	{
		super.onResume ();
	}

	@Override
	protected void onPause ()
	{
		super.onPause ();
	}

	/*
	 * Called when the Activity is no longer visible.
	 */
	@Override
	protected void onStop ()
	{
		save ();
		// Log.d ( "Log", "onStop" );
		super.onStop ();
	}

	@Override
	protected void onDestroy ()
	{
		// Log.d ( "Log", "onDestroy" );
		super.onDestroy ();
	}

	/**
	 * Hardware back button pressed.
	 */
	@Override
	public void onBackPressed ()
	{
		viewStateController.onBackPressed ();
	}

	public void hideKeyboard ()
	{

		try
		{
			InputMethodManager inputManager = ( InputMethodManager ) getSystemService ( Context.INPUT_METHOD_SERVICE );

			// check if no view has focus:
			View v = getCurrentFocus ();
			if ( v == null )
				return;

			inputManager.hideSoftInputFromWindow ( v.getWindowToken (), InputMethodManager.HIDE_NOT_ALWAYS );
		} catch ( Exception ex )
		{
			ex.printStackTrace ();
		}

	}

	/**
	 * Change view state using viewStateController.
	 */
	public void setState ( VIEW_STATE newState )
	{
		viewStateController.setState ( newState );
	}

	public void showLoader ()
	{
		hideLoader ();
		dialog = DialogFactory.show ( getString ( R.string.loading ), this);
	}

	public void hideLoader ()
	{
		if ( dialog != null )
		{
			dialog.dismiss ();
			dialog = null;
		}
	}

	public Object getCommunicationValue ( String key )
	{
		Object value = communicationMap.remove ( key );
		return value;
	}

	public void setCommunicationValue ( String key, Object value )
	{
		communicationMap.put ( key, value );
	}

	private void save ()
	{

	}

	@Override
	protected void onSaveInstanceState ( Bundle outState ) // Bug on API Level >
	// 11
	{
		// outState.putString ( "WORKAROUND_FOR_BUG_19917_KEY",
		// "WORKAROUND_FOR_BUG_19917_VALUE" );
		// super.onSaveInstanceState ( outState );
	}

	synchronized public Tracker getTracker ()
	{
		return tracker;
	}

    public Game getGame ()
    {
        return game;
    }
}
