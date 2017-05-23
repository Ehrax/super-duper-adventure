package de.in.uulm.map.tinder.main.add;

import com.google.android.gms.location.places.Place;

import android.net.Uri;

import de.in.uulm.map.tinder.entities.Event;
import de.in.uulm.map.tinder.entities.Image;

import java.util.Date;

/**
 * Created by Jona on 21.05.2017.
 */

public class AddEventPresenter implements AddEventContract.Presenter {

    private final AddEventContract.View mView;

    private final AddEventContract.Backend mBackend;

    private Uri mImageUri = null;

    private long mDuration = 3600000 * 2;

    private String mCategory = "Ausgehen";

    private Place mLocation = null;

    private int mMaxUser = 5;

    public AddEventPresenter(AddEventContract.View view, AddEventContract.Backend backend) {

        mView = view;
        mBackend = backend;
    }

    @Override
    public void start() {

        mView.showImage(mImageUri);
        mView.showDuration(mDuration);
        mView.showCategory(mCategory);
        mView.showMaxUser(mMaxUser);
    }

    @Override
    public void onImageClicked() {

        mBackend.selectImage();
    }

    @Override
    public void onDurationClicked() {

       mBackend.selectDuration();
    }

    @Override
    public void onLocationClicked() {

        mBackend.selectLocation();
    }

    @Override
    public void onMaxUserClicked() {

        mBackend.selectMaxUser();
    }

    @Override
    public void onCategoryClicked() {

        mBackend.selectCategory();
    }

    @Override
    public void onImageSelected(Uri imageUri) {

        mImageUri = imageUri;
        mView.showImage(mImageUri);
    }

    @Override
    public void onDurationSelected(long time) {

        mDuration = time;
        mView.showDuration(mDuration);
    }

    @Override
    public void onLocationSelected(Place location) {

        mLocation = location;
        mView.showLocation(mLocation.getName().toString() + ", "
                + mLocation.getAddress().toString());
    }

    @Override
    public void onMaxUserSelected(int maxUser) {

        mMaxUser = maxUser;
        mView.showMaxUser(mMaxUser);
    }

    @Override
    public void onCategorySelected(String category) {

        mCategory = category;
        mView.showCategory(mCategory);
    }

    @Override
    public void onCreateClicked() {

        Image image = new Image();
        image.path = mImageUri.toString();

        Event event = new Event();
        event.title = mView.getTitle();
        event.description = mView.getDescription();
        event.end_date = new Date().getTime() + mDuration;
        event.category = mCategory;
        event.image = image;
        event.latitude = mLocation.getLatLng().latitude;
        event.longitude = mLocation.getLatLng().longitude;

    }
}
