package com.example.rkg09.domesticservicesfreelancing;

/**
 * Created by rkg09 on 4/26/2019.
 */

import com.example.rkg09.domesticservicesfreelancing.Notifications.MyResponse;
import com.example.rkg09.domesticservicesfreelancing.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAARhCW4kE:APA91bE54sERw4qco4XpWt0sm6tQNEEmk3ikrtOaZFQVUiR35W_Xhngx_SpsmN3R4uWn7J6nlZ9LtHvSf7l5QJaud1KAS1gwHe2NEHT8L5m0usLjBiqcrKC14JCeiZeo8qaNHjwL0j2d"
    }
    )
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
