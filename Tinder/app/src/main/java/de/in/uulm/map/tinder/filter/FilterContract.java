package de.in.uulm.map.tinder.filter;

import de.in.uulm.map.tinder.util.BasePresenter;
import de.in.uulm.map.tinder.util.BaseView;

/**
 * Created by alexanderrasputin on 23.05.17.
 */

public interface FilterContract {

    interface View extends BaseView<Presenter> {

        void setCategory(String category);
        void setCategoryEnabled(boolean enabled);
        void setSeekBarProgress(int progress);
    }

    interface Presenter extends BasePresenter {

        void onCategoryClicked();
        void onSeekBarChangeFinished(int progress);
    }
}
