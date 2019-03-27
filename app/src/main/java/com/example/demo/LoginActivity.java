package com.example.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.example.demo.databinding.ActivityLoginBinding;
import com.example.demo.utils.activity.ActivityUtils;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding = null;
    private TextView.OnEditorActionListener editorListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            switch (actionId) {
                case EditorInfo.IME_ACTION_NEXT:
                    break;
                case EditorInfo.IME_ACTION_DONE:
                    login(v);
                    break;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);
        binding.userName.setOnEditorActionListener(editorListener);
        binding.password.setOnEditorActionListener(editorListener);
    }


    public void login(View view) {
        // validating credentials
        if (validate()) {
            startActivity(new Intent(this, HomeActivity.class));
        }
    }

    private boolean validate() {
        if (binding.loginType.getSelectedItemPosition() <= 0) {
            ((TextView) binding.loginType.getSelectedView()).setError("select login type");
            return false;
        } else if (Objects.requireNonNull(binding.userName.getText()).toString().equals("")) {
            binding.userName.setError("enter user name");
            binding.userName.requestFocus();
            return false;
        } else if (Objects.requireNonNull(binding.password.getText()).toString().equals("")) {
            binding.password.setError("enter password");
            binding.password.requestFocus();
            return false;
        } else {
            if (binding.userName.getText().toString().matches(DemoUtils.MobilePattern)) {
                if (binding.userName.getText().toString().length() < 10 && binding.userName.getText().toString().length() > 10) {
                    binding.userName.setError("please enter valid phone number");
                    binding.userName.requestFocus();
                    return false;
                }
            } else {
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(binding.userName.getText().toString()).matches()) {
                    binding.userName.setError("please enter valid email");
                    binding.userName.requestFocus();
                    return false;
                }
            }
        }
        return true;
    }

    public void register(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    public void forgot(View view) {
        startActivity(new Intent(this, ForgotActivity.class));
    }
}
