package de.in.uulm.map.tinder.register;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import de.in.uulm.map.tinder.R;
import de.in.uulm.map.tinder.login.LoginActivity;
import de.in.uulm.map.tinder.network.Network;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

/**
 * Created by maxka on 05.06.2017.
 */

public class RegisterPresenter implements RegisterContract.Presenter {

    private final RegisterContract.View mView;
    private final RegisterContract.Backend mBackend;

    //See LoginPresenter why we need both
    private Context mApplicationContext;
    private Context mContext;

    public static final String INTENT_EXTRA_USERNAME = "IEUSERNAME";
    public static final String INTENT_EXTRA_PASSWORD = "IEPASSWORD";

    public RegisterPresenter(RegisterContract.View view, RegisterContract
            .Backend backend, Context applicationContext, Context context) {

        mView = view;
        mBackend = backend;
        mApplicationContext = applicationContext;
        mContext = context;

    }

    @Override
    public void start() {

    }

    @Override
    public void signUp(String email, final String username, final String
            password, String
                               confirmPassword) {

        String url = mContext.getString(R.string.API_base) + mContext.getString
                (R.string.API_register);
        String jsonString = String.format("{\"email\":\"%s\",\"name\":\"%s\"," +
                        "\"password\":\"%s\",\"confirmPassword\":\"%s\"}",
                email, username,
                password, confirmPassword);
        JSONObject registerBody;
        try {
            registerBody = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
            mView.showJSONRegisterError();
            return;
        }
        JsonObjectRequest registerRequest = new JsonObjectRequest(Request
                .Method.POST, url, registerBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Intent intent = new Intent(mContext, LoginActivity.class);
                intent.putExtra(INTENT_EXTRA_USERNAME, username);
                intent.putExtra(INTENT_EXTRA_PASSWORD, password);
                mBackend.startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ByteArrayInputStream bis = new ByteArrayInputStream(error
                        .networkResponse.data);

                //ToDO:Error Handling like username already in use etc.
                JsonObject body = new JsonParser().parse(new JsonReader(new
                        InputStreamReader(bis))).getAsJsonObject();

                Toast.makeText(mContext, body.toString(), Toast
                        .LENGTH_LONG).show();
            }
        });

        Network.getInstance(mApplicationContext).getRequestQueue().add
                (registerRequest);
    }

    @Override
    public void startLoginActivity() {

        mBackend.startActivity(new Intent(mContext, LoginActivity.class));
    }

}
