package de.in.uulm.map.tinder.login;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import de.in.uulm.map.tinder.R;
import de.in.uulm.map.tinder.main.MainActivity;
import de.in.uulm.map.tinder.register.RegisterActivity;
import de.in.uulm.map.tinder.network.Network;
import de.in.uulm.map.tinder.network.NetworkHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by maxka on 20.05.2017.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private final LoginContract.View mView;
    private final LoginContract.Backend mBackend;

    private SharedPreferences mSharedPref;
    private SharedPreferences.Editor mSharedEditor;

    //We need the application Context because the network class is a singleton
    // and we don't want it to reference on the login activity´s context due to
    // memory leaks.
    private Context mApplicationContext;
    //This is the Context from the Login Activity for every other stuff we
    // have to do with context we should use this one
    private Context mContext;

    public LoginPresenter(LoginContract.View view, Context applicationContext,
                          Context context, LoginContract.Backend backend) {

        mView = view;
        mApplicationContext = applicationContext;
        mContext = context;
        mSharedPref = mContext.getSharedPreferences(mContext.getString(R.string
                        .store_account),
                Context.MODE_PRIVATE);
        mSharedEditor = mSharedPref.edit();
        mBackend = backend;
    }

    @Override
    public void start() {

    }

    @Override
    public void signIn(String password, String username) {

        if (NetworkHelper.isNetworkOnline(mContext)) {

            final String USERNAME = username;
            final String PASSWORD = password;

            String url = mContext.getString(R.string.API_base) + mContext
                    .getString(R.string.API_getToken);

            StringRequest signInRequest = new StringRequest(Request
                    .Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    JsonObject jsonResponse = new JsonParser().parse
                            (response).getAsJsonObject();
                    String userName = jsonResponse.get("userName").getAsString();
                    String accessToken = jsonResponse.get("access_token").getAsString();
                    String tokenExpireDate = jsonResponse.get(".expires").getAsString();

                    mSharedEditor.putString(mContext.getString(R.string
                            .store_token), accessToken).commit();
                    mSharedEditor.putString(mContext.getString(R.string
                            .store_token_expire), tokenExpireDate);
                    mSharedEditor.putString(mContext.getString(R.string
                            .store_username), userName);
                    mSharedEditor.commit();

                    mBackend.startActivity(new Intent(mContext, MainActivity
                            .class));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    //ToDO: propper error handling for wrong
                    // username/password combination and server errors.
                    JsonObject body = new Gson().fromJson(new String(error
                                    .networkResponse
                                    .data),
                            JsonObject.class);
                    Toast.makeText(mContext, body.toString().replace("{", "")
                            .replace("}", ""), Toast
                            .LENGTH_LONG).show();
                }
            }) {
                @Override
                public String getBodyContentType() {

                    return "application/x-www-form-urlencoded; charset=UTF-8";
                }

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<>();
                    params.put("username", USERNAME);
                    params.put("password", PASSWORD);
                    params.put("grant_type", "password");
                    return params;
                }
            };
            Network.getInstance(mApplicationContext).getRequestQueue().add
                    (signInRequest);
        } else {
            Toast.makeText(mContext, mContext.getString(R.string
                    .no_connection), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void startRegisterActivity() {

        mBackend.startActivity(new Intent(mContext, RegisterActivity.class));
    }
}
