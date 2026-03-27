package com.example.mobile_app_2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_app_2.data.AppDatabase;
import com.example.mobile_app_2.data.User;
import com.example.mobile_app_2.utils.PreferenceManager;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private AppDatabase db;
    private PreferenceManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = AppDatabase.getDatabase(this);
        prefManager = new PreferenceManager(this);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();

            User user = db.appDao().login(username, password);
            if (user != null) {
                prefManager.setLoggedIn(user.id);
                Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
