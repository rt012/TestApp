package com.example.testapp.fragments;


import com.example.testapp2.R;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Fragment which displays after starting the application. 
 * @author remi
 *
 */
public class LoadFragment extends Fragment implements OnClickListener {

private Button loadButton;
private Handler mHandler = new Handler();
private float x;
private float y;
private View mAddListingButton;

@Override
public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	 	inflater.inflate(R.menu.reload, menu);

	    // Find the menu item we are interested in.
	    final MenuItem it = menu.findItem(R.id.reload);
//	    Bitmap originalImage= Bitmap.createScaledBitmap (BitmapFactory.decodeResource(getResources(), R.drawable.btn_reload), 105, 105, true);
	    Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(), 
	               R.drawable.btn_reload);
	    
	    int width = bitmapOrg.getWidth();
        int height = bitmapOrg.getHeight();
        int newWidth = 35;
        int newHeight = 35;
        
        // calculate the scale - in this case = 0.4f
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // createa matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        

        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, 
                          width, height, matrix, true); 

        // make a Drawable from Bitmap to allow to set the BitMap 
        // to the ImageView, ImageButton or what ever
        BitmapDrawable bmd = new BitmapDrawable(bitmapOrg);


	    // Create a new view to replace the standard one.
	    // It would be nice if we could just *get* the standard one
	    // but unfortunately it.getActionView() returns null.

	    // actionButtonStyle makes the 'pressed' state work.
	    ImageView button = new ImageView(getActivity(), null, android.R.attr.actionButtonStyle);
	    button.setImageDrawable(bmd);
	   
	    // The onClick attributes in the menu resource no longer work (I assume since
	    // we passed null as the attribute list when creating this button.
	    button.setOnClickListener(this);
	    button.setVisibility(View.INVISIBLE);

	    // Ok, now listen for when layouting is finished and the button is in position.
	    button.addOnLayoutChangeListener(new OnLayoutChangeListener() {
	        @Override
	        public void onLayoutChange(View v, int left, int top, int right, int bottom,
	                int oldLeft, int oldTop, int oldRight, int oldBottom)
	        {
	            // Apparently this can be called before layout is finished, so ignore it.
	            // Remember also that it might never be called with sane values, for example
	            // if your action button is pushed into the "more" menu thing.
	            if (left == 0 && top == 0 && right == 0 && bottom == 0)
	                return;

	            // This is the only way to get the absolute position on the screen.
	            // The parameters passed to this function are relative to the containing View
	            // and v.getX/Y() return 0.
	            int[] location = new int[2];
	            v.getLocationOnScreen(location);
	            System.out.println(location[0]+ (right-left)/2);
	            // Ok, now we know the centre location of the button in screen coordinates!
	            x = (location[0] + (right -left));
	            y = (location[1] + (bottom));
	            it.setVisible(false);
	        }
	    });
	   
	   it.setActionView(button);
	   mAddListingButton = it.getActionView();
	    int[] location = new int[2];
	    mAddListingButton.getLocationOnScreen(location);
	 
	
	}


	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		// Initilization
        View rootView = inflater.inflate(R.layout.fragment_load, container, false);
        loadButton = (Button) rootView.findViewById(R.id.laden);
        loadButton.setClickable(true);
        loadButton.setOnClickListener(this);
        
        setHasOptionsMenu(true);
        
        return rootView;
    }
	
	@Override
	public void onClick(View arg0) {
		System.out.println(x + "" + y);
		// initialize a animationset for the scale and tranlate animation 
		AnimationSet animationSet = new AnimationSet(true);
		// delete text from Button
		loadButton.setText(null);
		// initialize the scale animation
		ScaleAnimation scale = new ScaleAnimation(1F, 0.28F, 0F, 0.28F);
		scale.setDuration(1000);
		scale.setFillAfter(true);
		// load scale animation from anim ressource and add it to the animationset
		animationSet.addAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.buttonscale));
		// initialize the slide animation and add it to the animation set 
		TranslateAnimation slide = new TranslateAnimation(0 , y ,0 , -x);   
		slide.setDuration(5000);
		slide.setZAdjustment(Animation.ZORDER_TOP);
		animationSet.addAnimation(slide);
		// start the animation 
		loadButton.startAnimation(animationSet);

		/**
		 * Method to insure that the fragment starts not till translate animation has finished. 
		 */
		mHandler.postDelayed(new Runnable() {
		        public void run() {
		        	ListFragment firstFragment = new ListFragment();
		    	     FragmentTransaction transaction = getFragmentManager().beginTransaction();
		    	     transaction.replace(R.id.root_frame, firstFragment);
		    	     transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		    	     transaction.addToBackStack(null);
		    	     transaction.commit();
		        }
		    }, 4000);
		}
	}
	

