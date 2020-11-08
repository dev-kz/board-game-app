package ca.cmpt276.as3.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import ca.cmpt276.as3.model.R;

/**
 * Help from Android website for fragments + side menu:
 * https://developer.android.com/training/implementing-navigation/nav-drawer.html#DrawerLayout
 *
 * Displays and controls the flow of fragments that are visible to the user screen
 * takes no inputs and sets the screen
 *
 */
@SuppressLint("Registered")
public class Main extends AppCompatActivity {
    private final String GAME_FRAGMENT_TAG = "game_tag";
    private final String HELP_FRAGMENT_TAG = "help_tag";
    private final String OPTIONS_FRAGMENT_TAG = "options_tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
