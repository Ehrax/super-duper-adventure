package de.in.uulm.map.tinder.main.add;

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
    }

    interface Presenter extends BasePresenter {

        void onImageClicked();
        void onDurationClicked();
        void onLocationClicked();
        void onCategoryClicked();
        void onImageSelected(Uri fileUri);
        void onDurationSelected(long time);
    }

    interface Backend {

        void selectImage();
        void selectDuration();
    }
}
