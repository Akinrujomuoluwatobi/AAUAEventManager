package com.royalteck.progtobi.aauaeventmanager;

import com.royalteck.progtobi.aauaeventmanager.Model.EventModel;
import com.royalteck.progtobi.aauaeventmanager.Util.EventsResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by PROG. TOBI on 10-Jul-17.
 */

public interface APIService {
    /*@FormUrlEncoded
    @POST("")
    Call<> userLogin(@Field("uname") String uname,
                     @Field("pk") String upass);*/

    @GET("api")
    Call<ArrayList<EventModel>> getData();

}
