package com.royalteck.progtobi.aauaeventmanager.Presenter;

import android.content.Intent;
import android.widget.ImageView;

import com.royalteck.progtobi.aauaeventmanager.Contract.EventViewContract;
import com.royalteck.progtobi.aauaeventmanager.EventManagerApp;
import com.royalteck.progtobi.aauaeventmanager.EventPreviewActivity;
import com.royalteck.progtobi.aauaeventmanager.Model.EventModel;
import com.royalteck.progtobi.aauaeventmanager.R;
import com.royalteck.progtobi.aauaeventmanager.Util.Data;
import com.squareup.picasso.Picasso;

/**
 * Created by PROG. TOBI on 22-Aug-17.
 */

public class EventViewPresenter {
    EventViewContract.View mView;
    EventModel event;

    public EventViewPresenter(EventViewContract.View view) {
        this.mView = view;
        mView.setPresenter(this);
    }

    public void onLoad(Intent intent) {
        event = (EventModel) intent.getSerializableExtra(Data.PROFILE_DETAILS);
        mView.showDevDetails(event);
    }

    public void OnShareDevDetails() {
        mView.shareDevDetails(event);
    }

    public void displayImageWith(ImageView imageView) {
        Picasso.with(EventManagerApp.getInstance()).load(event.getImadeUrl())
                .placeholder(R.color.colorPrimary).into(imageView);
    }

    public EventViewContract.View getView() {
        return mView;
    }
}
