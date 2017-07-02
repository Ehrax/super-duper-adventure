package de.in.uulm.map.tinder.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import de.in.uulm.map.tinder.R;

/**
 * Created by Jona on 15.06.2017.
 */

public class PickerFactory {

    public interface OnConfirmListener<T> {

        void onClick(DialogInterface dialog, int which, T value);
    }

    public static void categoryPicker(Context context,
                                      final OnConfirmListener<String> positiveListener,
                                      DialogInterface.OnClickListener negativeListener,
                                      DialogInterface.OnDismissListener dismissListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        final String[] categories =
                context.getResources().getStringArray(R.array.categories);

        final NumberPicker picker = new NumberPicker(context);
        picker.setMinValue(0);
        picker.setMaxValue(categories.length - 1);
        picker.setDisplayedValues(categories);

        builder.setView(picker);
        builder.setTitle("Set Category");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                positiveListener.onClick(dialog, which, categories[picker.getValue()]);
            }
        });
        builder.setNegativeButton("Cancel", negativeListener);
        builder.setOnDismissListener(dismissListener);

        builder.show();
    }
}
