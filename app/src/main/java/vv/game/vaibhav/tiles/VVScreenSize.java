package vv.game.vaibhav.tiles;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;

public class VVScreenSize{
	public float vvScreenHeightDp(Activity activity){
		Display display = activity.getWindowManager().getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);
		float density = activity.getResources().getDisplayMetrics().density;
		float dpHeight = metrics.heightPixels / density;
		return dpHeight;
	}

	public float vvScreenWidthDp(Activity activity){
		Display display = activity.getWindowManager().getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);
		float density = activity.getResources().getDisplayMetrics().density;
		float dpWidth = metrics.widthPixels / density;
		return dpWidth;
	}

	public float vvScreenHeightPixel(Activity activity){
		Display display = activity.getWindowManager().getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);
		//float density = activity.getResources().getDisplayMetrics().density;
		//float dpHeight = metrics.heightPixels / density;
		//return dpHeight;
		return metrics.heightPixels;
	}

	public float vvScreenWidthPixel(Activity activity){
		Display display = activity.getWindowManager().getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);
		//float density = activity.getResources().getDisplayMetrics().density;
		//float dpWidth = metrics.widthPixels / density;
		//return dpWidth;
		return metrics.widthPixels;
	}
}