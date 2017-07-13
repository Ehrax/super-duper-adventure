package de.in.uulm.map.tinder.main.event;

import com.google.android.gms.location.places.Place;

import android.content.Intent;
import android.net.Uri;
import android.widget.ImageView;

import de.in.uulm.map.tinder.entities.Event;
import de.in.uulm.map.tinder.util.BasePresenter;
import de.in.uulm.map.tinder.util.BaseView;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Jona on 21.05.2017.
 */

public interface EventContract {

    interface View extends BaseView<Presenter> {

        void showEvent(Event event);
        void showImage(String uri);
        void showStartDate(Date date);
        void showCategory(String category);
        void showLocation(String locationName);
        void showMaxUser(int maxUser);
        void showTitle(String title);
        void showDescription(String description);
        void setEnableSubmitButton(boolean enabled);
        void setProgressBarVisible(boolean visible);
        void showMessage(String message);
        String getImageUri();
    }

    interface Presenter extends BasePresenter {

        void checkEnableSubmitButton();
        void onImageSelected(Uri fileUri);
        void onDurationSelected(Calendar calendar);
        void onLocationSelected(Place location);
        void onMaxUserSelected(int maxUser);
        void onCategorySelected(String category);
        void onSubmitClicked();
        void onTitleChanged(String s);
        void onDescriptionChanged(String s);
        void setImage(String category, ImageView view);
    }

    interface Backend {

        void setResult(int resultCode, Intent intent);
        void finish();
    }
}
