package org.ultime78;

/**
 * Created by abc1 on 1/11/2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by Administrator on 1/3/2017.
 */
public class SplashActivity extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;

    Animation animation;

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        imageView = (ImageView) findViewById(R.id.app);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.falling);
        imageView.startAnimation(animation);


        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashActivity.this, Login.class);
                startActivity(i);
               /* Start right to left animation*/
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }


}
