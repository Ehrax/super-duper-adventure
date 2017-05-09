package de.in.uulm.map.tinder.chat;

/**
 * Created by Jona on 08.05.17.
 */

public class ChatPresenter implements ChatContract.Presenter {

    private final ChatContract.View mView;

    public ChatPresenter(ChatContract.View view) {

        mView = view;
    }

    @Override
    public void start() {

    }
}
