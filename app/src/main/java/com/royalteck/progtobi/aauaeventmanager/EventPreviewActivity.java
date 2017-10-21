package com.royalteck.progtobi.aauaeventmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.royalteck.progtobi.aauaeventmanager.Contract.EventViewContract;
import com.royalteck.progtobi.aauaeventmanager.Model.EventModel;
import com.royalteck.progtobi.aauaeventmanager.Presenter.EventViewPresenter;

public class EventPreviewActivity extends AppCompatActivity implements EventViewContract.View {

    private EventViewPresenter mPresenter;
    ImageView eventImage;
    FloatingActionButton mShareFloatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_preview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
        new EventViewPresenter(this);
    }



    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onLoad(getIntent());
    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)
                findViewById(R.id.toolbar_layout);
        //devDetailsView = (TextView) findViewById(R.id.profile_url);
        eventImage = (ImageView) collapsingToolbarLayout.findViewById(R.id.dev_image);
        mShareFloatButton = (FloatingActionButton) findViewById(R.id.fab);
        mShareFloatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.OnShareDevDetails();
            }
        });
    }

    @Override
    public void showDevDetails(EventModel event) {
        setTitle(event.getTitle());
        //devDetailsView.setText(developer.getProfileURL());
        mPresenter.displayImageWith(eventImage);
    }

    @Override
    public void shareDevDetails(EventModel event) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Check out this awesome developer @" + event.getTitle() + " \n" +
                event.getTitle();
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Event");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }



    @Override
    public void setPresenter(EventViewPresenter presenter) {
        this.mPresenter = presenter;
    }

}
