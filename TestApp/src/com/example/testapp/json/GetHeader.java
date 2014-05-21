package com.example.testapp.json;

import com.example.testapp2.R;
import com.example.testapp2.R.anim;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.widget.TextView;



public class GetHeader extends AsyncTask<Void, Void, Void>  {
	

// URL to get Data JSON n
private static String url = "http://www.adrodev.de/tests/2332-server-teaser.php";



private TextView header;
private String headerString;
private Context context;

public GetHeader(Context context, TextView header) {
	this.header = header;
	this.context = context;
}
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        
    }

    @Override
    protected Void doInBackground(Void... arg0) {
    	
    	
        // Creating service handler class instance
        ServiceHandler sh = new ServiceHandler();

        // Making a request to url and getting response
         headerString = sh.makeServiceCall(url, ServiceHandler.GET);

        

       
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        
        //start animation to dismiss the old header
        header.startAnimation(AnimationUtils.loadAnimation(context, R.anim.headeranimold));
        // waiting until the first animation is done 
        Handler mHandler = new Handler();
		mHandler.postDelayed(new Runnable() {
			
            public void run() {
            	// set the new header text and start the animation 
            	 header.setText(headerString);  
            	 header.startAnimation(AnimationUtils.loadAnimation(context, R.anim.headeranim));
            }
        }, 800);
       
      
  
       
        
        }
        
      
     
        
    	} 
    
