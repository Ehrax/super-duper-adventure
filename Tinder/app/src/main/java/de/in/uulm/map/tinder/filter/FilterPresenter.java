package de.in.uulm.map.tinder.filter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import de.in.uulm.map.tinder.util.PickerFactory;

/**
 * Created by alexanderrasputin on 23.05.17.
 */

public class FilterPresenter implements FilterContract.Presenter {

    public static final String EVENT_RADIUS = "event_radius";
    public static final String EVENT_CATEGORY = "event_category";

    private final Context mContext;

    private final FilterContract.View mView;

    public FilterPresenter(Context context, FilterContract.View view) {

        mView = view;
        mContext = context;
    }

    @Override
    public void start() {

        SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(mContext);

        mView.setSeekBarProgress(prefs.getInt(EVENT_RADIUS, 5));
        mView.setCategory(prefs.getString(EVENT_CATEGORY, "Sport"));
    }

    @Override
    public void onCategoryClicked() {

        mView.setCategoryEnabled(false);

        PickerFactory.categoryPicker(mContext,
                new PickerFactory.OnConfirmListener<String>() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, String value) {

                        mView.setCategory(value);

                        SharedPreferences prefs =
                                PreferenceManager.getDefaultSharedPreferences(mContext);

                        SharedPreferences.Editor edit = prefs.edit();
                        edit.putString(EVENT_CATEGORY, value);
                        edit.apply();
                    }
                },
                null,
                new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {

                        mView.setCategoryEnabled(true);
                    }
                });
    }

    @Override
    public void onSeekBarChangeFinished(int progress) {

        SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(mContext);

        SharedPreferences.Editor edit = prefs.edit();
        edit.putInt(EVENT_RADIUS, progress);
        edit.apply();
    }
}
