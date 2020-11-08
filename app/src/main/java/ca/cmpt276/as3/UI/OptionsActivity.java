package ca.cmpt276.as3.UI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import ca.cmpt276.as3.Model.Dragon;
import ca.cmpt276.as3.model.R;

/**
 * Options class is responsible for showing the board size
 * and the number of dragons that user wants to select to
 * play the game. Also, this class is using a Singleton design
 * pattern.
 */

@SuppressLint("Registered")
public class OptionsActivity extends AppCompatActivity {
    private final static int NO_GAMES = 0;
    private final static int NO_SCORE = 0;
    private static final String TAG = "OptionsActivity";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        Log.e(TAG, "Running onCreate()!");

        setBoardSize();
        setNumDragons();
        setUpSetGameButton();
        setBackgroundImage();
        setupUserGameInfo();
        setupResetButton();
    }

    private void setupResetButton() {
        Button restBtn = findViewById(R.id.resetButtonID);
        restBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dragon.getInstance().setNumGamesPlayed(NO_GAMES);
                Dragon.getInstance().setBestScore(NO_SCORE);
                setupUserGameInfo();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setupUserGameInfo() {
        TextView userGameInfoText = findViewById(R.id.userGameInfoID);
        if(Dragon.getInstance().getBestScore() != NO_SCORE){
            userGameInfoText.setText("Number of times played: "
                    + Dragon.getInstance().getNumGamesPlayed()
                    + "\nBest score: " + Dragon.getInstance().getBestScore());
        }
        else {
            userGameInfoText.setText("Number of times played: N/A"
                    + "\nBest score: N/A");
        }
    }

    public static Intent makeIntent(Context context){
        return new Intent(context, OptionsActivity.class);
    }

    // ... development: make using of the existing try and catch
    private void setUpSetGameButton() {
        Button button = findViewById(R.id.setGameID);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // setting up the board size and the number of dragons
                String boardSizeInput = null;
                String dragonNumInput = null;
                try{
                    boardSizeInput = getRadioButton(R.id.radio_group_install_boardSize)
                            .getText().toString();

                    if(boardSizeInput == null){
                        throw new RuntimeException("please select game size!");
                    }

                    dragonNumInput = getRadioButton(R.id.radio_group_install_dragons)
                            .getText().toString();

                    if(dragonNumInput == null){
                        throw new RuntimeException("please select num of mines!");
                    }

                }
                catch (RuntimeException e){
                    Toast.makeText(OptionsActivity.this, e.getMessage()
                            , Toast.LENGTH_SHORT).show();
                }

                // set the game
                if(dragonNumInput != null && boardSizeInput != null){
                    setUserDragonNum(dragonNumInput);
                    setUserBoardSize(boardSizeInput);
                }
            }
        });
    }

    private RadioButton getRadioButton(int id){
        int radioGroupId = getRadioGroup(id).getCheckedRadioButtonId();
        return findViewById(radioGroupId);
    }

    private RadioGroup getRadioGroup(int id){
        return (RadioGroup) findViewById(id);
    }

    // set the number of dragons according to the user's number of dragons
    private void setUserDragonNum(String dragonNumInput){
        switch (dragonNumInput){
            case "6 dragons":
                Dragon.getInstance().setNumDragons(6);
                break;
            case "10 dragons":
                Dragon.getInstance().setNumDragons(10);
                break;
            case "15 dragons":
                Dragon.getInstance().setNumDragons(15);
                break;
            case "20 dragons":
                Dragon.getInstance().setNumDragons(20);
            default:
        }
    }

    // set the board size according to user's dimensions
    private void setUserBoardSize(String messageBoard){
        switch (messageBoard) {
            case "4 rows by 6 columns":
                Dragon.getInstance().setRow(4);
                Dragon.getInstance().setCol(6);
                break;
            case "5 rows by 10 columns":
                Dragon.getInstance().setRow(5);
                Dragon.getInstance().setCol(10);
                break;
            case "6 rows by 15 columns":
                Dragon.getInstance().setRow(6);
                Dragon.getInstance().setCol(15);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("SetTextI18n")
    private void setBoardSize(){
        RadioGroup radioGroup =(RadioGroup) findViewById(R.id.radio_group_install_boardSize);

        int [] boardRowArray = getResources().getIntArray(R.array.number_of_rows);
        int [] boardColArray = getResources().getIntArray(R.array.number_of_columns);

        // create the buttons:
        for(int i = 0; i < boardRowArray.length; i++){
            setupRadioButton(boardRowArray[i], boardColArray[i], radioGroup);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("SetTextI18n")
    private void setupRadioButton(final int boardRow, final int boardCol, RadioGroup radioGroup){
        RadioButton button = new RadioButton(this);
        button.setTextColor(Color.WHITE);
        button.setText(boardRow + " rows by " + boardCol + " columns");

        // Add to radio group
        radioGroup.addView(button);
        button.setTextColor(Color.BLUE);
        button.setTextColor(getColorSateList());
        button.setButtonTintList(getColorSateList());
        button.setTypeface(Typeface.DEFAULT_BOLD);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("SetTextI18n")
    private void setNumDragons(){
        RadioGroup group =(RadioGroup) findViewById(R.id.radio_group_install_dragons);
        int [] dragonArr = getResources().getIntArray(R.array.number_of_dragons);

        for (int dragonNum : dragonArr) {
            RadioButton button = new RadioButton(this);
            button.setText(dragonNum + " dragons");
            button.setTextColor(Color.WHITE);

            // Add to radio group
            group.addView(button);
            button.setTextColor(Color.BLUE);
            button.setTextColor(getColorSateList());
            button.setButtonTintList(getColorSateList());
            button.setTypeface(Typeface.DEFAULT_BOLD);
        }
    }

    // set the background for options screen only
    private void setBackgroundImage(){
        ImageView myImageView = (ImageView) findViewById(R.id.backgroundImageID);
        myImageView.setImageResource(R.drawable.chinese_new_year1);
    }

    // change the color of the selected radio buttons and circle
    private ColorStateList getColorSateList(){
        return new ColorStateList(
                new int[][]{new int[]{-android.R.attr.state_checked},
                            new int[]{android.R.attr.state_checked}
                            },
                new int[]{Color.WHITE, Color.rgb (0,0,255),}
        );
    }
}
