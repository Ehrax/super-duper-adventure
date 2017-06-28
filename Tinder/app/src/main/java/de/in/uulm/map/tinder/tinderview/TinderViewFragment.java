package de.in.uulm.map.tinder.tinderview;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import de.in.uulm.map.tinder.R;
import de.in.uulm.map.tinder.entities.Event;
import de.in.uulm.map.tinder.util.DisplaySizeHelper;

import java.util.List;

/**
 * Created by maxka on 26.06.2017.
 */

public class TinderViewFragment extends Fragment implements
        TinderViewContract.View {

    private TinderViewContract.Presenter mPresenter;
    private TinderViewContract.Backend mBackend;

    private SwipePlaceHolderView mSwipeView;
    private Context mContext;
    private Toolbar mToolbar;

    public static TinderViewFragment newInstance() {

        return new TinderViewFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_tinder_view, container,
                false);

        mToolbar = (Toolbar) view.findViewById(R.id
                .tinder_activity_toolbar);
        mSwipeView = (SwipePlaceHolderView) view.findViewById(R.id.swipeView);

        mContext = getActivity();

        Point windowSize = DisplaySizeHelper.getDisplaySize(getActivity()
                .getWindowManager());

        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setViewWidth(windowSize.x)
                        .setViewHeight(windowSize.y)
                        .setViewGravity(Gravity.TOP)
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.tinder_swipe_in_msg)
                        .setSwipeOutMsgLayoutId(R.layout.tinder_swipe_out_msg));


        List<Event> events = mPresenter.loadEvents();

        for (Event event : events) {
            mSwipeView.addView(new TinderCard(mContext, event, mSwipeView));
        }


        activity.setSupportActionBar(mToolbar);
        //ActionBar actionBar = activity.getSupportActionBar();
        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void setPresenter(TinderViewContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return mPresenter.topNavOnOptionSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.top_nav_bar_tinder_view,menu);
    }
}
