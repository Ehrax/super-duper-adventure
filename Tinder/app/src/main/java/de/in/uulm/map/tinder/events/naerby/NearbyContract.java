package de.in.uulm.map.tinder.events.naerby;

import de.in.uulm.map.tinder.util.BasePresenter;
import de.in.uulm.map.tinder.util.BaseView;

/**
 * Created by alexanderrasputin on 02.05.17.
 */

public interface NearbyContract {
    interface NearbyView extends BaseView<NearbyPresenter> {};

    interface NearbyPresenter extends BasePresenter{};
}
