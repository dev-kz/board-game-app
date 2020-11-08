package ca.cmpt276.as3.UI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import ca.cmpt276.as3.model.R;

/**
 * This class is responsible for showing the game screen,
 * options menu, and help menu on its screen so the user,
 * so the user can select one of these buttons to go to
 * that screen.
 */
@SuppressLint("Registered")
public class MenuActivity extends AppCompatActivity {
    private String TAG = "MenuActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e(TAG, "Running onCreate()!");     // test
        setContentView(R.layout.activity_menu);     // set up the screen
        setAllButtons();                          // game, options, and help buttons
        setBackgroundImage();
    }

    public static Intent makeIntent(Context context){
        return new Intent(context, MenuActivity.class);
    }

    // ... Helper functions ... //

    private void setAllButtons(){
        setGameButton();
        setOptionsButton();
        setHelpButton();
    }

    private void setGameButton(){
        Button gameScreenBtn = (Button) findViewById(R.id.gameScreenID);
        gameScreenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = GameActivity.makeIntent(MenuActivity.this);
                finish();
                startActivity(intent);
            }
        });
    }

    private void setOptionsButton(){
        Button optionsBtn = (Button) findViewById(R.id.optionsBtnID);
        optionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = OptionsActivity.makeIntent(MenuActivity.this);
                startActivity(intent);
            }
        });
    }

    private void setHelpButton(){
        Button helpBtn = (Button) findViewById(R.id.helpBtnID);
        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = HelpActivity.makeIntent(MenuActivity.this);
                startActivity(intent);
            }
        });
    }

    private void setBackgroundImage(){
        ImageView myImageView = (ImageView) findViewById(R.id.backgroundImageID);
        myImageView.setImageResource(R.drawable.chinese_new_year1);
    }
}
