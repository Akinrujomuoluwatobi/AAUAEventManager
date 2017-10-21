package com.royalteck.progtobi.aauaeventmanager.Fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.royalteck.progtobi.aauaeventmanager.Adapters.UpEventAdapter;
import com.royalteck.progtobi.aauaeventmanager.Contract.OngoingEventContract;
import com.royalteck.progtobi.aauaeventmanager.EventManagerApp;
import com.royalteck.progtobi.aauaeventmanager.EventPreviewActivity;
import com.royalteck.progtobi.aauaeventmanager.Model.EventModel;
import com.royalteck.progtobi.aauaeventmanager.Presenter.OngoingEventPresenter;
import com.royalteck.progtobi.aauaeventmanager.Presenter.UpcomingEventPresenter;
import com.royalteck.progtobi.aauaeventmanager.R;
import com.royalteck.progtobi.aauaeventmanager.Util.Data;
import com.royalteck.progtobi.aauaeventmanager.Util.EventRecycler;
import com.royalteck.progtobi.aauaeventmanager.Util.SharedPreferenceHandler;

import java.util.List;

/**
 * Created by PROG. TOBI on 08-Aug-17.
 */

public class OngoingEventFrag extends Fragment implements OngoingEventContract.View, EventRecycler.ClickListener {
    SwipeRefreshLayout refreshLayout;
    private OngoingEventPresenter presenter;
    private RecyclerView mEventRecycler;
    List<EventModel> mEventList;
    private UpEventAdapter meventAdapter;
    ProgressBar ongoingeventpb;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.ongoing_eventlayout, container, false);
        ongoingeventpb = (ProgressBar) rootView.findViewById(R.id.ongoingeventpb);
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.ongoingeventswipe);
        presenter = new OngoingEventPresenter(this);

        bindRecycler(rootView);

        return rootView;
    }

    private void bindRecycler(View view) {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.fetchEventOnline();
            }
        });
        mEventRecycler = (RecyclerView) view.findViewById(R.id.ongoingeventrecyc);
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
    public void setPresenter(OngoingEventPresenter ongoingEventPresenter) {
        presenter = ongoingEventPresenter;
    }

    @Override
    public void checkInternet() {
        if (EventManagerApp.getInstance().isOnline(getActivity())) {
            presenter.fetchEventOnline();
        } else {
            fetchEventLocally();
        }
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
    public void showLoading(boolean b) {
        ongoingeventpb.setVisibility(b ? View.VISIBLE : View.GONE);
        refreshLayout.setRefreshing(b);
    }

    @Override
    public void showDeveloperDetails(EventModel eventModel) {
        Intent i = new Intent(getActivity(), EventPreviewActivity.class);
        i.putExtra(Data.PROFILE_DETAILS, eventModel);
        showmessage(eventModel.getTitle());
        startActivity(i);
    }

    @Override
    public void onClick(View view, int position) {
        presenter.OnShowDeveloperDetails(position);
    }

    @Override
    public void onLongClick(View view, int position) {

    }
}
