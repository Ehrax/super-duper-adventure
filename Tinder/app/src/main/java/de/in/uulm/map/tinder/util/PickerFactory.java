package de.in.uulm.map.tinder.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.NumberPicker;

import de.in.uulm.map.tinder.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Jona on 15.06.2017.
 */

public class PickerFactory {

    public interface OnConfirmListener<T> {

        void onClick(DialogInterface dialog, int which, T value);
    }

    public static void categoryPicker(Context context,
                                      boolean includeCategoryAll,
                                      final OnConfirmListener<String> positiveListener,
                                      DialogInterface.OnClickListener negativeListener,
                                      DialogInterface.OnDismissListener dismissListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        final List<String> categories = new ArrayList<>(Arrays.asList(
                context.getResources().getStringArray(R.array.categories)));

        if(!includeCategoryAll) {
            categories.remove("Alle");
        }

        final NumberPicker picker = new NumberPicker(context);
        picker.setMinValue(0);
        picker.setMaxValue(categories.size() - 1);
        picker.setDisplayedValues(categories.toArray(new String[]{}));

        builder.setView(picker);
        builder.setTitle("Category");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                positiveListener.onClick(dialog, which, categories.get(picker.getValue()));
            }
        });
        builder.setNegativeButton("Cancel", negativeListener);
        builder.setOnDismissListener(dismissListener);

        builder.show();
    }
}
