package de.in.uulm.map.tinder.main.add;

import android.net.Uri;

/**
 * Created by Jona on 21.05.2017.
 */

public class AddEventPresenter implements AddEventContract.Presenter {

    private final AddEventContract.View mView;

    private final AddEventContract.Backend mBackend;

    private Uri mImageUri = null;

    private long mDuration = -1;

    public AddEventPresenter(AddEventContract.View view, AddEventContract.Backend backend) {

        mView = view;
        mBackend = backend;
    }

    @Override
    public void start() {

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

    }

    @Override
    public void onCategoryClicked() {

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
}
