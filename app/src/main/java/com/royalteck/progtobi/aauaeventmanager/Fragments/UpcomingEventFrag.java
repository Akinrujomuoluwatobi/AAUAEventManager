package com.royalteck.progtobi.aauaeventmanager.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.royalteck.progtobi.aauaeventmanager.Adapters.UpEventAdapter;
import com.royalteck.progtobi.aauaeventmanager.Contract.UpcomingEventContract;
import com.royalteck.progtobi.aauaeventmanager.EventManagerApp;
import com.royalteck.progtobi.aauaeventmanager.EventPreviewActivity;
import com.royalteck.progtobi.aauaeventmanager.Model.EventModel;
import com.royalteck.progtobi.aauaeventmanager.Presenter.UpcomingEventPresenter;
import com.royalteck.progtobi.aauaeventmanager.R;
import com.royalteck.progtobi.aauaeventmanager.Util.Data;
import com.royalteck.progtobi.aauaeventmanager.Util.EventRecycler;
import com.royalteck.progtobi.aauaeventmanager.Util.EventsResponse;
import com.royalteck.progtobi.aauaeventmanager.Util.SharedPreferenceHandler;

import java.util.List;

/**
 * Created by PROG. TOBI on 08-Aug-17.
 */

public class UpcomingEventFrag extends Fragment implements UpcomingEventContract.View, EventRecycler.ClickListener {
    SwipeRefreshLayout refreshLayout;
    private UpcomingEventPresenter presenter;
    private RecyclerView mEventRecycler;
    private UpEventAdapter meventAdapter;
    public ProgressBar upcomingeventpb;
    List<EventModel> mEventList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.upcoming_eventlayout, container, false);
        upcomingeventpb = (ProgressBar) rootView.findViewById(R.id.progressBar);
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.upcomingeventswipe);
        presenter = new UpcomingEventPresenter(this);

        bindRecycler(rootView);
        return rootView;
    }


    private void bindRecycler(final View view) {

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.fetchEventOnline();
            }
        });
        mEventRecycler = (RecyclerView) view.findViewById(R.id.upcomingeventrecycle);
        mEventRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mEventRecycler.addOnItemTouchListener(new EventRecycler.
                RecyclerTouchListener(getActivity(), mEventRecycler, this));
    }

    @Override
    public void showmessage(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
        Log.d("MESSAGE", s);
    }

    @Override
    public void setDevelopersAdapter(List<EventModel> eventlist) {

        meventAdapter = new UpEventAdapter(eventlist, getActivity());
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setAddDuration(1000);
        defaultItemAnimator.setMoveDuration(1000);
        defaultItemAnimator.setChangeDuration(1000);
        mEventRecycler.setItemAnimator(defaultItemAnimator);
        mEventRecycler.setAdapter(meventAdapter);
    }


    @Override
    public void showLoading(boolean b) {
        upcomingeventpb.setVisibility(b ? View.VISIBLE : View.GONE);
        refreshLayout.setRefreshing(b);

    }

    @Override
    public void checkInternet() {
        if (EventManagerApp.getInstance().isOnline(getActivity())) {
            presenter.fetchEventOnline();
        } else {
            fetchEventLocally();
        }
    }

    @Override
    public void saveEventsDetails(final EventsResponse response) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                SharedPreferenceHandler.saveDeveloperRecords(getActivity(), response);
            }
        };
        AsyncTask.execute(runnable);
    }

    @Override
    public void setPresenter(UpcomingEventPresenter upcomingEventPresenter) {
        presenter = upcomingEventPresenter;
    }

    @Override
    public void showDeveloperDetails(EventModel eventModel) {
        Intent i = new Intent(getActivity(), EventPreviewActivity.class);
        i.putExtra(Data.PROFILE_DETAILS, eventModel);
        showmessage(eventModel.getTitle());
        startActivity(i);
    }

    public void fetchEventLocally() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                mEventList = SharedPreferenceHandler.
                        fetchDeveloperRecords(getActivity()).events;
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (mEventList.size() < 1) {
                    showmessage("Events list is empty");
                } else {
                    setDevelopersAdapter(mEventList);
                    //meventAdapter.notifyDataSetChanged();
                }
            }
        }.execute();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_upcoming, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view, int position) {

        presenter.OnShowDeveloperDetails(position);
    }

    @Override
    public void onLongClick(View view, int position) {

    }
}
