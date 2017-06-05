package de.in.uulm.map.tinder.register;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import de.in.uulm.map.tinder.R;
import de.in.uulm.map.tinder.util.EmailValidator;

/**
 * Created by maxka on 05.06.2017.
 */

public class RegisterFragment extends Fragment implements RegisterContract.View {

    private RegisterContract.Presenter mPresenter;

    public static RegisterFragment newInstance() {

        return new RegisterFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register, container,
                false);
        TextView txtViewLogo = (TextView)view.findViewById(R.id.txt_view_logo_register);
        txtViewLogo.setTypeface(Typeface.createFromAsset(getContext()
                .getAssets(),"Youth_and_Beauty.ttf"));

        TextView txtViewLogin = (TextView) view.findViewById(R.id
                .txt_view_login_link);

        final TextView txtViewWarningConfirm = (TextView) view.findViewById(R.id
                .txt_view_warning_confirm_register);
        final TextView txtViewWarningEmail = (TextView) view.findViewById(R
                .id.txt_view_warnings_email_register);
        final TextView txtViewWarningUsername = (TextView) view.findViewById
                (R.id.txt_view_warnings_username_register);
        final TextView txtViewWarningPassword = (TextView) view.findViewById
                (R.id.txt_view_warnings_password_register);

        final Button btnSignUp = (Button) view.findViewById(R.id.btn_signup);
        btnSignUp.setEnabled(false);

        final EditText editTxtEmail = (EditText) view.findViewById(R.id
                .edit_txt_email_register);
        final EditText editTxtUsername = (EditText) view.findViewById(R.id
                .edit_txt_username_register);
        final EditText editTxtPassword = (EditText) view.findViewById(R.id
                .edit_txt_password_register);
        final EditText editTxtConfirmPassword = (EditText) view.findViewById
                (R.id
                        .edit_txt_confirm_password_register);


        // Validate user input "on the fly"
        editTxtConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s.toString().isEmpty()){
                    txtViewWarningConfirm.setVisibility(View.GONE);
                }
                if (!s.toString().equals(editTxtPassword.getText().toString())) {
                    txtViewWarningConfirm.setText(getString(R.string
                            .confirm_password_no_match));
                    txtViewWarningConfirm.setVisibility(View.VISIBLE);
                    btnSignUp.setEnabled(false);
                } else if (EmailValidator.validate(editTxtEmail.getText()
                        .toString()) && editTxtUsername.getText().toString()
                        .length() > 2) {
                    txtViewWarningConfirm.setVisibility(View.GONE);
                    btnSignUp.setEnabled(true);
                } else{
                    txtViewWarningConfirm.setVisibility(View.GONE);
                }
            }
        });

        editTxtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s.toString().isEmpty()){
                    txtViewWarningPassword.setVisibility(View.GONE);
                }
                if (s.toString().length() < 6) {
                    txtViewWarningPassword.setText(getString(R.string.password_warning));
                    txtViewWarningPassword.setVisibility(View.VISIBLE);
                    btnSignUp.setEnabled(false);
                } else if ((EmailValidator.validate(editTxtEmail.getText()
                        .toString()) && editTxtUsername.getText().toString()
                        .length() > 2) && editTxtConfirmPassword.getText()
                        .toString().equals(s.toString())) {
                    txtViewWarningPassword.setVisibility(View.GONE);
                    txtViewWarningConfirm.setVisibility(View.GONE);
                    btnSignUp.setEnabled(true);
                } else if (editTxtConfirmPassword.getText()
                        .toString().equals(editTxtPassword.getText().toString
                                ())) {
                    txtViewWarningPassword.setVisibility(View.GONE);
                    txtViewWarningConfirm.setVisibility(View.GONE);
                } else {
                    txtViewWarningPassword.setVisibility(View.GONE);
                    txtViewWarningConfirm.setText(getString(R.string.confirm_password_no_match));
                    txtViewWarningConfirm.setVisibility(View.VISIBLE);
                    btnSignUp.setEnabled(false);
                }
            }
        });

        editTxtUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s.toString().isEmpty()){
                    txtViewWarningUsername.setVisibility(View.GONE);
                }
                if (s.toString().length() < 3) {
                    txtViewWarningUsername.setText(getString(R.string.username_warning));
                    txtViewWarningUsername.setVisibility(View.VISIBLE);
                    btnSignUp.setEnabled(false);
                } else if (EmailValidator.validate(editTxtEmail.getText()
                        .toString()) && editTxtConfirmPassword.getText()
                        .toString().equals(editTxtPassword.getText().toString
                                ()) && editTxtPassword.getText().toString()
                        .length() >= 6) {
                    txtViewWarningUsername.setVisibility(View.GONE);
                    btnSignUp.setEnabled(true);
                } else {
                    txtViewWarningUsername.setVisibility(View.GONE);
                }
            }
        });

        editTxtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s.toString().isEmpty()){
                    txtViewWarningEmail.setVisibility(View.GONE);
                }
                if (!EmailValidator.validate(s.toString())) {
                    txtViewWarningEmail.setText(getString(R.string.enter_email_warning));
                    txtViewWarningEmail.setVisibility(View.VISIBLE);
                    btnSignUp.setEnabled(false);
                } else if (editTxtConfirmPassword.getText()
                        .toString().equals(editTxtPassword.getText().toString
                                ()) && editTxtPassword.getText().toString()
                        .length() >= 6 && editTxtUsername.getText().toString
                        ().length() >= 3) {
                    txtViewWarningEmail.setVisibility(View.GONE);
                    btnSignUp.setEnabled(true);
                } else{
                    txtViewWarningEmail.setVisibility(View.GONE);
                }
            }
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validate user input again before sending the form data to
                // our API
                if (!editTxtConfirmPassword.getText().toString().equals
                        (editTxtPassword.getText().toString
                        ())) {
                    txtViewWarningConfirm.setText(getString(R.string
                            .confirm_password_no_match));
                    txtViewWarningConfirm.setVisibility(View.VISIBLE);
                }
                if (!EmailValidator.validate(editTxtEmail.getText().toString())) {
                    txtViewWarningEmail.setText(getString(R.string
                            .enter_email_warning));
                    txtViewWarningEmail.setVisibility(View.VISIBLE);
                }
                if (editTxtUsername.getText().toString().length() < 3) {
                    txtViewWarningUsername.setText(getString(R.string
                            .username_warning));
                    txtViewWarningUsername.setVisibility(View.VISIBLE);
                }
                if (editTxtPassword.getText().toString().length() < 6) {
                    txtViewWarningPassword.setText(getString(R.string
                            .password_warning));
                    txtViewWarningPassword.setVisibility(View.VISIBLE);
                }
                if (EmailValidator.validate(editTxtEmail.getText().toString()
                ) && editTxtConfirmPassword.toString().equals(editTxtPassword
                        .getText().toString()) && !editTxtUsername.getText()
                        .toString().isEmpty()) {
                    
                    mPresenter.signUp(editTxtEmail.getText().toString(), editTxtUsername
                                    .getText().toString(), editTxtPassword.getText().toString(),
                            editTxtConfirmPassword.getText().toString());
                }
            }
        });


        txtViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPresenter.startLoginActivity();
            }
        });
        return view;

    }


    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {

        mPresenter = presenter;
    }
}
