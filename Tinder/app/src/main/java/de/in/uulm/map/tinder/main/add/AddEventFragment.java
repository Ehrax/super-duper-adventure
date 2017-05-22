package de.in.uulm.map.tinder.main.add;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;

import de.in.uulm.map.tinder.R;
import de.in.uulm.map.tinder.util.AsyncImageLoader;

import java.io.File;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Jona on 21.05.2017.
 */

public class AddEventFragment extends Fragment implements AddEventContract.View, AddEventContract.Backend {

    public static final int IMAGE_REQ_CODE = 1;

    public AddEventContract.Presenter mPresenter;

    public ImageView mImage;

    public EditText mTitle;

    public EditText mDescription;

    public EditText mDuration;

    public EditText mLocation;

    public EditText mCategory;

    private String mLastImagePath;

    public static AddEventFragment newInstance() {

        return new AddEventFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_add_event, container, false);

        mImage = (ImageView) view.findViewById(R.id.add_image);
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onImageClicked();
            }
        });

        mDuration = (EditText)  view.findViewById(R.id.add_time);
        mDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onDurationClicked();
            }
        });

        mLocation = (EditText) view.findViewById(R.id.add_location);
        mLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    @Override
    public void setPresenter(AddEventContract.Presenter presenter) {

        mPresenter = presenter;
    }

    @Override
    public void onFragmentBecomesVisible() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == IMAGE_REQ_CODE && resultCode == Activity.RESULT_OK) {
            mPresenter.onImageSelected(data.getData());
            mLastImagePath = null;
        }
    }

    @Override
    public void showImage(Uri fileUri) {

        new AsyncImageLoader(fileUri.toString(),
                new WeakReference<ImageView>(mImage),
                getContext()).execute();
    }

    @Override
    public void showDuration(long duration) {

        long hours = duration / 3600000;
        long minutes = (duration % 3600000) / 60000;

        mDuration.setText(String.format("%02d:%02d", hours, minutes));
    }

    @Override
    public void selectImage() {

        // TODO: request permissions in a better way; this kinda sucks!

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA}, 0);
            return;
        }

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            return;
        }

        ArrayList<Intent> possible_intents = new ArrayList<>();

        Intent pick_intent = new Intent(Intent.ACTION_PICK);
        pick_intent.setType("image/*");

        List<ResolveInfo> l = getActivity().getPackageManager().
                queryIntentActivities(pick_intent, 0);

        for (ResolveInfo r : l) {
            Intent chooser_intent = new Intent(Intent.ACTION_PICK);
            chooser_intent.setType("image/*");
            chooser_intent.setPackage(r.activityInfo.packageName);

            possible_intents.add(chooser_intent);
        }

        Intent capture_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        String file_name = "JPEG_" + new SimpleDateFormat("yyyyMMdd_HHmmss").
                format(new Date()) + ".jpg";
        File directory = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File img = new File(directory, file_name);

        mLastImagePath = img.getAbsolutePath();
        capture_intent.putExtra(MediaStore.EXTRA_OUTPUT, mLastImagePath);

        Intent chooser_intent = Intent.createChooser(capture_intent, "Bild auswählen");
        chooser_intent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
                possible_intents.toArray(new Parcelable[possible_intents.size()]));

        startActivityForResult(chooser_intent, IMAGE_REQ_CODE);
    }

    public void selectDuration() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_time_chooser, null);
        builder.setView(view);
        builder.setTitle("Set Duration");

        final NumberPicker hour_picker =
                (NumberPicker) view.findViewById(R.id.hour_picker);
        final NumberPicker minute_picker =
                (NumberPicker) view.findViewById(R.id.minute_picker);

        hour_picker.setMaxValue(24);
        hour_picker.setMinValue(0);
        minute_picker.setMaxValue(60);
        minute_picker.setMinValue(0);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                long time = hour_picker.getValue() * 3600000 +
                        minute_picker.getValue() * 60000;

                mPresenter.onDurationSelected(time);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                return;
            }
        });

        builder.show();
    }

}
