package ca.cmpt276.as3.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

@SuppressLint("Registered")
public class Menu extends AppCompatActivity{

    public static Intent makeIntent(Context context) {
        return new Intent(context, Main.class);
    }
}
