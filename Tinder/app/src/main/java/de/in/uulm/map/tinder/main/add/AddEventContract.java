package de.in.uulm.map.tinder.main.add;

import com.google.android.gms.location.places.Place;

import android.net.Uri;

import de.in.uulm.map.tinder.main.MainContract;
import de.in.uulm.map.tinder.util.BasePresenter;

/**
 * Created by Jona on 21.05.2017.
 */

public interface AddEventContract {

    interface View extends MainContract.MainView<Presenter> {

        void showImage(Uri fileUri);
        void showDuration(long duration);
        void showCategory(String category);
        void showLocation(String locationName);
        void showMaxUser(int maxUser);

        String getTitle();
        String getDescription();
    }

    interface Presenter extends BasePresenter {

        void onImageClicked();
        void onDurationClicked();
        void onLocationClicked();
        void onMaxUserClicked();
        void onCategoryClicked();
        void onImageSelected(Uri fileUri);
        void onDurationSelected(long time);
        void onLocationSelected(Place location);
        void onMaxUserSelected(int maxUser);
        void onCategorySelected(String category);
        void onCreateClicked();
    }

    interface Backend {

        void selectImage();
        void selectDuration();
        void selectLocation();
        void selectMaxUser();
        void selectCategory();
    }
}
