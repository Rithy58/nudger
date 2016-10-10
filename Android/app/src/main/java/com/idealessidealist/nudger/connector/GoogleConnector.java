package com.idealessidealist.nudger.connector;

import com.idealessidealist.nudger.model.ChoreResponse;
import com.idealessidealist.nudger.model.GroupList;
import com.idealessidealist.nudger.model.GroupResponse;
import com.idealessidealist.nudger.model.Success;
import com.idealessidealist.nudger.model.User;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by Kenny Tsui on 10/8/2016.
 */

public class GoogleConnector {

    private static GoogleService sumuService;

    private static final String API_PATH = "https://1-dot-project-nudger.appspot.com/";

    public static Observable<Response<User>> login(String email, String password) {
        GoogleService service = retrieveService();
        return service.login(new AccessToken(email, password))
                .subscribeOn(Schedulers.io());
    }

    public static Observable<Response<User>> register(String email, String name, String password) {
        GoogleService service = retrieveService();
        return service.register(new AccessCreator(email, name, password))
                .subscribeOn(Schedulers.io());
    }

    public static Observable<Response<Success>> assignChore(String email, String chore) {
        GoogleService service = retrieveService();
        return service.assignChore(new Assignment(email, chore))
                .subscribeOn(Schedulers.io());
    }

    public static Observable<Response<ChoreResponse>> getChores(String email) {
        GoogleService service = retrieveService();
        return service.getChores(new Email(email))
                .subscribeOn(Schedulers.io());
    }

    public static Observable<Response<GroupList>> getGroup(String email) {
        GoogleService service = retrieveService();
        return service.getGroups(new Email(email))
                .subscribeOn(Schedulers.io());
    }

    public static Observable<Response<GroupResponse>> createGroup(String email, String groupName, ArrayList<String> emailList) {
        GoogleService service = retrieveService();
        return service.createGroup(new GroupCreator(email, groupName, emailList.toArray(new String[emailList.size()])))
                .subscribeOn(Schedulers.io());
    }

    private static GoogleService retrieveService() {
        if (sumuService == null) {
            OkHttpClient interceptClient = new OkHttpClient();
            interceptClient = interceptClient.newBuilder().build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API_PATH)
                    .client(interceptClient)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            sumuService = retrofit.create(GoogleService.class);
        }
        return sumuService;
    }

}

class AccessToken {

    String email;
    String password;

    AccessToken(String email, String password) {
        this.email = email;
        this.password = password;
    }

}

class Assignment {

    String email;
    String name;

    Assignment(String email, String name) {
        this.email = email;
        this.name = name;
    }
}

class AccessCreator {

    String email;
    String name;
    String password;

    AccessCreator(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }
}

class Email {

    String email;

    Email(String email) {
        this.email = email;
    }
}

class GroupCreator {

    String email;
    String name;
    String[] invite;

    GroupCreator(String email, String groupName, String[] users) {
        this.email = email;
        this.name = groupName;
        this.invite = users;
    }
}

interface GoogleService {
    @POST("auth")
    @Headers({
            "Content-Type: application/json"
    })
    Observable<Response<User>> login(@Body AccessToken accessToken);

    @POST("auth/new")
    @Headers({
            "Content-Type: application/json"
    })
    Observable<Response<User>> register(@Body AccessCreator accessCreator);

    @POST("group")
    @Headers({
            "Content-Type: application/json"
    })
    Observable<Response<GroupList>> getGroups(@Body Email email);

    @POST("group/new")
    @Headers({
            "Content-Type: application/json"
    })
    Observable<Response<GroupResponse>> createGroup(@Body GroupCreator creator);

    @POST("group/assign")
    @Headers({
            "Content-Type: application/json"
    })
    Observable<Response<Success>> assignChore(@Body Assignment assignment);

    @POST("group/chore")
    @Headers({
            "Content-Type: application/json"
    })
    Observable<Response<ChoreResponse>> getChores(@Body Email email);


}