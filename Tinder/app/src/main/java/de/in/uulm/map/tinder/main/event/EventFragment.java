package de.in.uulm.map.tinder.main.event;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import de.in.uulm.map.tinder.R;
import de.in.uulm.map.tinder.entities.Event;
import de.in.uulm.map.tinder.util.AsyncImageLoader;
import de.in.uulm.map.tinder.util.PickerFactory;

import java.io.File;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Jona on 21.05.2017.
 */

public class EventFragment extends Fragment implements EventContract.View {

    private static final int IMAGE_REQ_CODE = 1;

    private static final int LOCATION_REQ_CODE = 2;

    private EventContract.Presenter mPresenter;

    private ImageView mImage;

    private EditText mTitle;

    private EditText mDescription;

    private EditText mStartDate;

    private EditText mLocation;

    private EditText mMaxUser;

    private EditText mCategory;

    private MenuItem mSubmit;

    private String mLastImagePath;

    public static EventFragment newInstance() {

        return new EventFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_event, container, false);


        mImage = (ImageView) view.findViewById(R.id.add_image);
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage();
            }
        });

        TextWatcher titleWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                mPresenter.onTitleChanged(s.toString());
            }
        };

        mTitle = (EditText) view.findViewById(R.id.add_title);
        mTitle.addTextChangedListener(titleWatcher);

        TextWatcher descriptionWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                mPresenter.onDescriptionChanged(s.toString());
            }
        };

        mDescription = (EditText) view.findViewById(R.id.add_description);
        mDescription.addTextChangedListener(descriptionWatcher);

        mStartDate = (EditText) view.findViewById(R.id.add_time);
        mStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mStartDate.setEnabled(false);
                selectDuration();
            }
        });

        mLocation = (EditText) view.findViewById(R.id.add_location);
        mLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mLocation.setEnabled(false);
                selectLocation();
            }
        });

        mMaxUser = (EditText) view.findViewById(R.id.add_max_users);
        mMaxUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMaxUser.setEnabled(false);
                selectMaxUser();
            }
        });

        mCategory = (EditText) view.findViewById(R.id.add_category);
        mCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCategory.setEnabled(false);
                selectCategory();
            }
        });

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.event_activity_toolbar);
        activity.setSupportActionBar(toolbar);

        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.top_nav_bar_event, menu);
        mSubmit = menu.getItem(0);

        mPresenter.start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.top_nav_submit) {
            mPresenter.onSubmitClicked();
            return true;
        }

        return false;
    }

    @Override
    public void setPresenter(EventContract.Presenter presenter) {

        mPresenter = presenter;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == IMAGE_REQ_CODE && resultCode == Activity.RESULT_OK) {
            mPresenter.onImageSelected(data.getData());
            mLastImagePath = null;
        }

        if (requestCode == LOCATION_REQ_CODE) {
            mLocation.setEnabled(true);

            if (resultCode == Activity.RESULT_OK) {
                Place place = PlacePicker.getPlace(getContext(), data);
                mPresenter.onLocationSelected(place);
            }
        }
    }

    @Override
    public void showTitle(String title) {

        mTitle.setText(title);
    }

    @Override
    public void showDescription(String description) {

        mDescription.setText(description);
    }

    @Override
    public void setEnableSubmitButton(boolean enabled) {

        mSubmit.getIcon().setAlpha(enabled ? 255 : 50);
        mSubmit.setEnabled(enabled);
    }

    @Override
    public void showEvent(Event event) {

        if(event.has_image) {
            String uri = getString(R.string.API_base);
            uri += getString(R.string.API_event_image);
            uri += "/" + event.id;
            showImage(uri);
        } else {
            //TODO: load category image here
        }

        mTitle.setText(event.title);
        mDescription.setText(event.description);
        mLocation.setText(event.location);
        mMaxUser.setText("" + event.max_user_count);
        mStartDate.setText(event.getFormattedStartDate());

        final String[] categories =
                getResources().getStringArray(R.array.categories);
        int index = Math.max(0, Integer.parseInt(event.category));
        String category = categories[index];
        mCategory.setText(category);
        mPresenter.onCategorySelected(category);
    }

    @Override
    public String getImageUri() {

        return (String) mImage.getTag();
    }

    @Override
    public void showImage(String uri) {

        new AsyncImageLoader(uri,
                new WeakReference<>(mImage),
                getContext()).execute();
    }

    @Override
    public void showStartDate(long duration) {

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        mStartDate.setText(format.format(new Date(duration)));
    }

    @Override
    public void showCategory(String category) {

        mCategory.setText(category);
    }

    @Override
    public void showLocation(String locationName) {

        mLocation.setText(locationName);
    }

    @Override
    public void showMaxUser(int maxUser) {

        mMaxUser.setText("" + maxUser + " people");
    }

    @Override
    public void showMessage(String message) {

        Snackbar snackbar = Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT);
        TextView view = (TextView) snackbar.getView().findViewById(
                android.support.design.R.id.snackbar_text);
        view.setTextColor(Color.WHITE);

        snackbar.show();
    }

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

        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog dialog = new TimePickerDialog(
                getActivity(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        mPresenter.onDurationSelected(c.getTime().getTime());
                    }
                },
                hour,
                minute,
                DateFormat.is24HourFormat(getActivity()));

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

                mStartDate.setEnabled(true);
            }
        });

        dialog.show();
    }

    public void selectLocation() {

        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            mLocation.setEnabled(true);
            return;
        }

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            Intent intent = builder.build(getActivity());
            startActivityForResult(intent, LOCATION_REQ_CODE);

        } catch (GooglePlayServicesRepairableException e) {
            GooglePlayServicesUtil
                    .getErrorDialog(e.getConnectionStatusCode(), getActivity(), 0);
        } catch (GooglePlayServicesNotAvailableException e) {
            Toast.makeText(getContext(), "Google Services not available!",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void selectMaxUser() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Set Max Participants");

        final NumberPicker picker = new NumberPicker(getContext());
        picker.setMinValue(2);
        picker.setMaxValue(32);

        builder.setView(picker);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                mMaxUser.setEnabled(true);
                mPresenter.onMaxUserSelected(picker.getValue());
            }
        });

        builder.setNegativeButton("Cancel", null);

        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

                mMaxUser.setEnabled(true);
            }
        });

        builder.show();
    }

    public void selectCategory() {

        PickerFactory.categoryPicker(getContext(),
                new PickerFactory.OnConfirmListener<String>() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, String value) {

                        mCategory.setEnabled(true);
                        mPresenter.onCategorySelected(value);
                    }
                },
                null,
                new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {

                        mCategory.setEnabled(true);
                    }
                });
    }
}
