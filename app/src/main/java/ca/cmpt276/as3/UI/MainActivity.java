package ca.cmpt276.as3.UI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import ca.cmpt276.as3.Model.Dragon;
import ca.cmpt276.as3.model.R;

/**
 * Main activity class is the welcome screen of
 * this application. In this class there we have
 * implemented skip button, background image, the
 * user game information, and an animation.
 */
@SuppressLint("Registered")
public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";
    private static final int ANI_DURATION = 1000;
    private static final int ANI_REPEAT_COUNT = 4;
    private static final int ANI_REPEAT_MODE = 2;
    private static final float ANI_INI_X_DELTA = 400.0f;
    private static final float ANI_FINAL_X_DELTA = 1100.0f;
    private static final float ANI_INI_Y_DELTA = 0.0f;
    private static final float ANI_FINAL_Y_DELTA = 400.0f;
    private static final int PAGE_DELAY_MILLI_SEC = 4500;

    // the animation will not skip again if the user clicks on skip button
    private static boolean alreadySkipped = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Log.e(TAG, "Running onCreate()!");

        setBackgroundImage();
        setupSkipButton();
        setUpImageAnimation();
        setupUserGameInfo();
    }

    // Intent for Main activity if necessary
    public static Intent makeIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }


    // ..... Helper functions ..... //

    private void setBackgroundImage(){
        ImageView myImageView = findViewById(R.id.backgroundImageID);
        myImageView.setImageResource(R.drawable.chinese_new_year1);
    }

    private void setupSkipButton(){
        Button skipBtn = findViewById(R.id.skipBtnID);
        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alreadySkipped = true;
                Intent intent = MenuActivity.makeIntent(MainActivity.this);
                finish();
                startActivity(intent);
            }
        });
    }

    // set up the number of times the user played the game, and the best score
    @SuppressLint("SetTextI18n")
    private void setupUserGameInfo() {
        TextView userGameInfoText = findViewById(R.id.uesrGameInfoID);

        if(Dragon.getInstance().getBestScore() != 0){
            userGameInfoText.setText("Number of times played: "
                    + Dragon.getInstance().getNumGamesPlayed()
                    + "\nBest score: "
                    + Dragon.getInstance().getBestScore());
        }
        else {
            userGameInfoText.setText("Number of times played: N/A" + "\nBest score: N/A");
        }
    }

    // the moving animation on the welcome screen
    private void setUpImageAnimation(){
        ImageView imageAnimation = findViewById(R.id.animationID);
        imageAnimation.startAnimation(getTranslatedAnimation());

        setupSkipAnimation();
    }

    // move animation from one location to another
    private TranslateAnimation getTranslatedAnimation(){
        TranslateAnimation translateAnimation = new TranslateAnimation(
                ANI_INI_X_DELTA, ANI_FINAL_X_DELTA,
                ANI_INI_Y_DELTA, ANI_FINAL_Y_DELTA);

        translateAnimation.setDuration(ANI_DURATION);
        translateAnimation.setRepeatCount(ANI_REPEAT_COUNT);
        translateAnimation.setRepeatMode(ANI_REPEAT_MODE);  // repeat animation L to R and R to L

        return translateAnimation;
    }

    // check if the skip button is clicked, if yes then the page will skip once
    private void setupSkipAnimation(){
        Handler handler  = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!alreadySkipped){  // don't go to Menu if user has clicked on skip already
                    Intent intent = MenuActivity.makeIntent(MainActivity.this);
                    startActivity(intent);
                }
            }
        }, PAGE_DELAY_MILLI_SEC);
    }
}

