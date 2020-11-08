package ca.cmpt276.as3.UI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import ca.cmpt276.as3.Model.Dragon;
import ca.cmpt276.as3.model.R;

/**
 * This class is responsible for allowing the user
 * to play the game. It controls all the number and
 * information happening on the game screen activity.
 * It gets the information received from options acti-
 * vity to set the board size and the number of dragons
 * that the user wants to play with.
 */
@SuppressLint("Registered")
public class GameActivity extends AppCompatActivity {
    private static final String TAG = "GameActivity";

    private static final String AppStates = "Game";
    private static final String NUM_OF_ROWS = "Rows";
    private static final String NUM_OF_COL = "Col";
    private static final String NUM_OF_DRAGONS = "Dragons";
    private static final String NUM_OF_REVEALED_DRAGONS = "Revealed Dragons";
    private static final String NUM_OF_ROWS1 = "numRows";
    private static final String DRAGON_COUNT = "dragCount";
    private static final String SCANS_USED = "scanUsed";
    private static final String REVEALED_LIST = "revealList";
    private static final String DRAGON_LOCATION_LIST = "dragonLocList";
    private static final String BEST_SCORE = "best score";
    private static final String NUMBER_OF_GAMES_PLAYED = "number of games played";
    private static final String BUTTON_TEXT_VAL = "0";

    private static final int MAX_SOUND_STREAM = 10;
    private static final int SOUND_SRC_QUALITY = 0;
    private static final int PRIORITY_ONE = 1;
    private static final int NO_SCAN_ATTEMPTS = 0;

    private static int dragonNumRow;
    private static int dragonNumCol;
    private static int dragonTotalNum;
    private static int dragonRevealedNum;
    private static int dragonFoundCount;
    private static int scanAttempts;

    private Button buttons[][];
    private ArrayList<Integer> dragonLocationList = new ArrayList<>();
    private ArrayList<Integer> revealedList = new ArrayList<>();
    private SoundPool sounds;
    private int sExplosion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);
        Log.e(TAG, "Running onCreate()!");  // test

        initializeUiVars();
        updateUI();

        initializeDragonInfo();
        setupDragons();

        populateButtons();
        setBackgroundImage();
        setupUserGameInfo();
    }

    private void initializeUiVars() {
        dragonFoundCount = 0;
        scanAttempts = 0;
        sounds = new SoundPool(MAX_SOUND_STREAM, AudioManager.STREAM_MUSIC, SOUND_SRC_QUALITY);
        sExplosion = sounds.load(getApplicationContext(), R.raw.scansound, PRIORITY_ONE);
        dragonRevealedNum = 0;
    }

    private void initializeDragonInfo() {
        dragonNumRow = Dragon.getInstance().getRow();
        dragonNumCol = Dragon.getInstance().getCol();
        dragonTotalNum = Dragon.getInstance().getNumDragons();
        buttons = new Button[dragonNumRow][dragonNumCol];
    }

    // information about current best score and the number of times the game is played
    @SuppressLint("SetTextI18n")
    private void setupUserGameInfo() {
        TextView userGameInfoText = findViewById(R.id.userGameInfoID);
        if (Dragon.getInstance().getBestScore() != 0) {
            userGameInfoText.setText(">> Number of times played: "
                    + Dragon.getInstance().getNumGamesPlayed()
                    + "\n>> Best score: " + Dragon.getInstance().getBestScore());
            userGameInfoText.setTextColor(Color.BLUE);
        }
        else {
            userGameInfoText.setText(">> Number of times played: N/A"
                    + "\n>> Best score: N/A ");
            userGameInfoText.setTextColor(Color.BLUE);
        }
    }

    // update the game's information according to the user play
    @SuppressLint("SetTextI18n")
    private void updateUI() {
        TextView numberOfMineTV = findViewById(R.id.numOfRevealDragon);
        numberOfMineTV.setText(">> Dragon Num Total: " + dragonTotalNum
                + "\n>> Dragon Revealed: " + dragonRevealedNum
                + "\n>> Scans used :" + scanAttempts);
        numberOfMineTV.setTextColor(Color.BLUE);
        numberOfMineTV.setTypeface(Typeface.DEFAULT_BOLD);
    }


    private void setupDragons() {
        while (dragonFoundCount != dragonTotalNum) {
            Random rand = new Random();
            int randomRowLocation = rand.nextInt(dragonNumRow);
            int randomColLocation = rand.nextInt(dragonNumCol);
            int randomLocationNum = randomRowLocation * dragonNumCol + randomColLocation;

            boolean found = false;
            for (int i = 0; i < dragonLocationList.size(); i++) {
                if (dragonLocationList.get(i) == randomLocationNum) {
                    found = true;
                }
            }
            if (!found) {
                dragonLocationList.add(randomLocationNum);
                dragonFoundCount++;
            }
        }
    }

    private void populateButtons() {
        TableLayout table = findViewById(R.id.tableForButtons);

        for (int row = 0; row < dragonNumRow; row++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            ));
            table.addView(tableRow);

            for (int col = 0; col < dragonNumCol; col++) {
                final Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));

                //make text not clip on small buttons
                button.setPadding(0, 0, 0, 0);

                if(isInDragonList(col, row))
                    setDragonButtons(col, row, button);
                else
                    setNonDragonButtons(col, row, button);

                tableRow.addView(button);
                buttons[row][col] = button;
            }
        }
    }

    private void setDragonButtons(final int finalCol, final int finalRow, Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gridButtonClicked(finalCol, finalRow);
                updateUI();
            }
        });
    }

    private void setNonDragonButtons(final int finalCol, final int finalRow, final Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                if (!ifHasRevealed(finalCol, finalRow)) {
                    int numOfHiddenMines = scan(finalCol, finalRow);
                    button.setText("" + numOfHiddenMines);
                    updateUI();
                }
            }
        });
    }


    @SuppressLint("SetTextI18n")
    private void gridButtonClicked(int col, int row) {
        Button button = buttons[row][col];
        if (isInDragonList(col, row)) {
            removeDragon(col, row);
            updateRowAndColRevealedBut(col, row);
        }
        else if (!ifHasRevealed(col, row)) {
            int numOfDragonsInRowAndCol = scan(col, row);
            button.setText("" + numOfDragonsInRowAndCol);
        }

        // Lock Button Sizes: before scaling the buttons
        lockButtonSizes();
        scaleImageToButton(button);
    }

    private void scaleImageToButton(Button button){
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.dragon_button);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap,
                button.getWidth(), button.getHeight(), true);
        button.setBackground(new BitmapDrawable(getResources(), scaledBitmap));
        button.setBackground(new BitmapDrawable(getResources(), scaledBitmap));
    }

    @SuppressLint("SetTextI18n")
    private void updateRowAndColRevealedBut(int col, int row) {
        // updating game board row
        for (int i = 0; i < dragonNumCol; i++) {
            if (ifHasRevealed(i, row)) {
                buttons[row][i].setText("" + scan2(i, row));
            }
        }
        // updating game board column
        for (int i = 0; i < dragonNumRow; i++) {
            if (ifHasRevealed(col, i)) {
                buttons[i][col].setText("" + scan2(col, i));
            }
        }
    }

    private void removeDragon(int col, int row) {
        int location = row * dragonNumCol + col;
        sounds.play(sExplosion, 1.0f, 1.0f, 0, 0, 1.5f);
        for (int i = 0; i < dragonLocationList.size(); i++) {
            if (dragonLocationList.get(i) == location) {
                dragonLocationList.remove(i);
                dragonFoundCount--;
                dragonRevealedNum++;
                if (dragonRevealedNum == dragonTotalNum) {
                    redrawTable();
                    setState();
                    Dragon.getInstance().setNumGamesPlayed(Dragon.getInstance().getNumGamesPlayed() + 1);
                    if (Dragon.getInstance().getBestScore() == NO_SCAN_ATTEMPTS) {
                        Dragon.getInstance().setBestScore(scanAttempts);
                    }

                    saveGameStatus();
                    FragmentManager manager = getSupportFragmentManager();
                    MessageFragment dialog = new MessageFragment();
                    dialog.show(manager, "MessageDialog");
                    Log.i(TAG, "Just showed the dialog");
                }
            }
        }
    }

    private void redrawTable(){
        for (int rowIndex = 0; rowIndex < dragonNumRow; rowIndex++) {
            for (int colIndex = 0; colIndex < dragonNumCol; colIndex++) {
                buttons[rowIndex][colIndex].setText(BUTTON_TEXT_VAL);
            }
        }
    }


    // purpose?
    private int scan(int col, int row) {
        sounds.play(sExplosion, 1.0f, 1.0f, 0, 0, 1.5f);
        scanAttempts++;
        revealedList.add(row * dragonNumCol + col);
        int dragonCount = 0;
        for (int i = 0; i < dragonNumCol; i++) {
            if (isInDragonList(i, row)) {
                dragonCount++;
            }
        }
        for (int i = 0; i < dragonNumRow; i++) {
            if (isInDragonList(col, i)) {
                dragonCount++;
            }
        }
        return dragonCount;
    }

    // purpose?
    private int scan2(int col, int row) {
        int dragonCount = 0;
        for (int i = 0; i < dragonNumCol; i++) {
            if (isInDragonList(i, row)) {
                dragonCount++;
            }
        }
        for (int i = 0; i < dragonNumRow; i++) {
            if (isInDragonList(col, i)) {
                dragonCount++;
            }
        }
        return dragonCount;
    }

    private boolean ifHasRevealed(int col, int row) {
        int location = row * dragonNumCol + col;
        for (int i = 0; i < revealedList.size(); i++) {
            if (revealedList.get(i) == location) {
                return true;
            }
        }
        return false;
    }

    private boolean isInDragonList(int col, int row) {
        int location = row * dragonNumCol + col;
        for (int i = 0; i < dragonLocationList.size(); i++) {
            if (dragonLocationList.get(i) == location) {
                return true;
            }
        }
        return false;
    }

    private void lockButtonSizes() {
        for (int row = 0; row < dragonNumRow; row++) {
            for (int col = 0; col < dragonNumCol; col++) {
                Button button = buttons[row][col];

                button.setMinWidth(button.getWidth());
                button.setMaxWidth(button.getWidth());

                button.setMinHeight(button.getHeight());
                button.setMaxHeight(button.getHeight());
            }
        }
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, GameActivity.class);
    }

    private void setBackgroundImage() {
        ImageView myImageView = findViewById(R.id.backgroundImageID);
        myImageView.setImageResource(R.drawable.chinese_new_year1);
    }


    // perform shared preferences to save the game state
    private void saveGameStatus() {
        SharedPreferences preferences = getSharedPreferences(AppStates, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt(NUM_OF_ROWS, dragonNumRow);
        editor.putInt(NUM_OF_COL, dragonNumCol);
        editor.putInt(NUM_OF_DRAGONS, dragonTotalNum);
        editor.putInt(NUM_OF_REVEALED_DRAGONS, dragonRevealedNum);
        editor.putInt(NUM_OF_ROWS1, dragonNumRow);
        editor.putInt(DRAGON_COUNT, dragonFoundCount);
        editor.putInt(SCANS_USED, scanAttempts);
        editor.putInt(BEST_SCORE, Dragon.getInstance().getBestScore());
        editor.putInt(NUMBER_OF_GAMES_PLAYED, Dragon.getInstance().getNumGamesPlayed());

        StringBuilder dragonLocationStr = new StringBuilder();
        for (int i = 0; i < dragonLocationList.size(); i++) {
            dragonLocationStr.append(dragonLocationList.get(i)).append(",");
        }

        editor.putString(DRAGON_LOCATION_LIST, dragonLocationStr.toString());

        StringBuilder revealedListStr = new StringBuilder();
        for (int i = 0; i < revealedList.size(); i++) {
            revealedListStr.append(revealedList.get(i)).append(",");
        }

        editor.putString(REVEALED_LIST, revealedListStr.toString());
        editor.apply();
    }

    private static int getPreviousBestCore(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(AppStates, MODE_PRIVATE);
        return preferences.getInt(BEST_SCORE, 0);
    }

    // set the state for best score
    private void setState() {
        if (getPreviousBestCore(getApplicationContext()) == NO_SCAN_ATTEMPTS)
            Dragon.getInstance().setBestScore(scanAttempts);

        else if (scanAttempts < getPreviousBestCore(getApplicationContext()))
            Dragon.getInstance().setBestScore(scanAttempts);

    }
}
