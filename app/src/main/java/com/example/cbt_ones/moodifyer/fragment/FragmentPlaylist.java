package com.example.cbt_ones.moodifyer.fragment;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cbt_ones.moodifyer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPlaylist extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String[] colors_array = {"Item 1", "Item 1", "Item 1", "Item 1", "Item 1", "Item 1", "Item 1", "Item 1", "Item 1", "Item 1", "Item 1", "Item 1", "Item 1", "Item 1", "Item 1"};
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("#HugotPlaylist")
                .setItems(colors_array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                    }
                });
        return builder.create();
    }

}
