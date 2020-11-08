package ca.cmpt276.as3.UI;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import ca.cmpt276.as3.model.R;

/**
 * This class is responsible for showing the congratulations
 * message at the end of the game when the user has found all
 * the number of dragons.
 */
public class MessageFragment extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Create the view to show
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.message_layout, null);

        // Build the alert dialog
        return new AlertDialog.Builder(getActivity())
                // message is associated with the team of the game
                .setTitle("Congratulations, You won! Happy Chinese New Year!")
                .setView(returnView())
                .setPositiveButton(android.R.string.ok, getOnclickListenerButton())
                .create();
    }

    private View returnView(){
        return LayoutInflater.from(getActivity())
                .inflate(R.layout.message_layout, null);
    }

    // create a button listener
    private DialogInterface.OnClickListener getOnclickListenerButton(){
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = MenuActivity.makeIntent(getActivity());
                getActivity().finish();
                startActivity(intent);
            }
        };
    }
}
