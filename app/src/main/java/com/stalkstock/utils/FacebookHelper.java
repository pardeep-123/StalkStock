package com.stalkstock.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import org.json.JSONObject;
import java.net.URL;
import java.util.Collections;

public class FacebookHelper implements FacebookCallback<LoginResult>, GraphRequest.GraphJSONObjectCallback{

    public final static String FACEBOOK_ID = "idFacebook";
    public final static String FIRST_NAME = "first_name";
    public final static String LAST_NAME = "last_name";
    public final static String EMAIL = "email";
    public final static String PROFILE_PIC = "profile_pic";

    public interface FacebookHelperCallback {
        void onSuccessFacebook(Bundle bundle);
        void onCancelFacebook();
        void onErrorFacebook(FacebookException ex);
    }

    private final FacebookHelperCallback callback;
    private final CallbackManager callbackManager;

    public FacebookHelper(FacebookHelperCallback callback) {
        this.callback = callback;
        callbackManager = CallbackManager.Factory.create();
    }

    public void login(Context context) {
        LoginButton loginButton = new LoginButton(context);
        loginButton.setPermissions(Collections.singletonList("public_profile, email"));
        loginButton.performClick();
        LoginManager.getInstance().registerCallback(callbackManager, this);
    }

    public void onResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        getFaceBookProfile(loginResult);
    }

    private void getFaceBookProfile(LoginResult loginResult) {
        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), this);
        Bundle parameters = new Bundle();

        Log.e("profile_getAccessToken", loginResult.getAccessToken().getToken() + "");
        Log.e("profile_getAccessToken", loginResult.getAccessToken().getUserId() + "");

        parameters.putString("fields", "id, email, first_name, last_name");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private Bundle getFacebookData(JSONObject object) {

        try {
            Bundle bundle = new Bundle();
            String id = object.getString("id");

            URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
            Log.e("profile_pic", profile_pic + "");
            Log.e("profile_object", object + "");
            bundle.putString(PROFILE_PIC, profile_pic.toString());
            bundle.putString(FACEBOOK_ID, id);

            if (object.has(FIRST_NAME))
                bundle.putString(FIRST_NAME, object.getString("first_name"));
            if (object.has(LAST_NAME))
                bundle.putString(LAST_NAME, object.getString("last_name"));
            if (object.has(EMAIL))
                bundle.putString(EMAIL, object.getString("email"));
            return bundle;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public void onCancel() {
        callback.onCancelFacebook();
    }

    @Override
    public void onError(FacebookException error) {
        callback.onErrorFacebook(error);
    }

    @Override
    public void onCompleted(JSONObject object, GraphResponse response) {

        Log.e("asdfsdfads","=11===="+object);
        Log.e("asdfsdfads","=22===="+response);

        Bundle facebookData = getFacebookData(object);
        callback.onSuccessFacebook(facebookData);
    }
}