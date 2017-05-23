package de.in.uulm.map.tinder.main.add;

import com.google.android.gms.location.places.Place;

import android.net.Network;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;

import de.in.uulm.map.tinder.entities.Event;
import de.in.uulm.map.tinder.entities.Image;
import de.in.uulm.map.tinder.main.DbMock;

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

        mView.showTitle("");
        mView.showDescription("");
        mView.showImage(mImageUri);
        mView.showDuration(mDuration);
        mView.showCategory(mCategory);
        mView.showMaxUser(mMaxUser);
        mView.showLocation("");
    }

    public void checkEnableCreateButton() {

        boolean enabled = true;
        enabled &= mCategory != null;
        enabled &= mDuration != -1;
        enabled &= mLocation != null;
        enabled &= mMaxUser != -1;
        enabled &= !mView.getDescription().isEmpty();
        enabled &= !mView.getTitle().isEmpty();

        mView.setEnableCreateButton(enabled);
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
        checkEnableCreateButton();
    }

    @Override
    public void onDurationSelected(long time) {

        mDuration = time;
        mView.showDuration(mDuration);
        checkEnableCreateButton();
    }

    @Override
    public void onLocationSelected(Place location) {

        mLocation = location;

        String name = mLocation.getName() == null ? "" :
                mLocation.getName().toString();
        String address = mLocation.getAddress() == null ? "" :
                ", " + mLocation.getAddress().toString();

        String string = name + address;

        if(string.isEmpty()) {
            string = "" + location.getLatLng().latitude + ", " +
                    location.getLatLng().longitude;
        }

        mView.showLocation(string);

        checkEnableCreateButton();
    }

    @Override
    public void onMaxUserSelected(int maxUser) {

        mMaxUser = maxUser;
        mView.showMaxUser(mMaxUser);
        checkEnableCreateButton();
    }

    @Override
    public void onCategorySelected(String category) {

        mCategory = category;
        mView.showCategory(mCategory);
        checkEnableCreateButton();
    }

    @Override
    public void onCreateClicked() {

        Image image = new Image();
        image.path = mImageUri == null ? null : mImageUri.toString();

        Event event = new Event();
        event.creator = DbMock.getInstance().getCurrentUser();
        event.participants.add(DbMock.getInstance().getCurrentUser());
        event.title = mView.getTitle();
        event.description = mView.getDescription();
        event.end_date = new Date().getTime() + mDuration;
        event.category = mCategory;
        event.image = image;
        event.max_user_count = mMaxUser;
        event.latitude = mLocation.getLatLng().latitude;
        event.longitude = mLocation.getLatLng().longitude;

        DbMock.getInstance().addEvent(event);

        mView.showMessage("Event created!");

        mImageUri = null;
        mMaxUser = 4;
        mCategory = "Ausgehen";
        mDuration = 3600000 * 2;
        mLocation = null;

        mView.showTitle("");
        mView.showDescription("");
        mView.showImage(mImageUri);
        mView.showDuration(mDuration);
        mView.showCategory(mCategory);
        mView.showMaxUser(mMaxUser);
        mView.showLocation("");
    }
}
