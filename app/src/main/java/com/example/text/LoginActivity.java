package com.example.text;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.text.Model.Users;
import com.example.text.Rules.Rules;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    private Button login_button;
    private EditText name_input, login_input, reg_input;
    private ProgressDialog loadingBar;
    private TextView admin_panel_link;
    private TextView not_admin_panel_link;
    private String parentDbName = "Users";
    private CheckBox checkBoxRemember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_button = (Button)findViewById(R.id.button_sign_login);

        login_input = (EditText)findViewById(R.id.login_input);
        reg_input = (EditText)findViewById(R.id.pass_input);
        loadingBar = new ProgressDialog(this);
        checkBoxRemember = (CheckBox)findViewById(R.id.login_checkbox);
        Paper.init(this);
        admin_panel_link = (TextView)findViewById(R.id.admin_panel_link) ;
        not_admin_panel_link = (TextView)findViewById(R.id.not_admin_panel_link);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        admin_panel_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin_panel_link.setVisibility(View.INVISIBLE);
                not_admin_panel_link.setVisibility(View.VISIBLE);
                login_button.setText("admin input");
                parentDbName = "Admins";
            }
        });

        not_admin_panel_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin_panel_link.setVisibility(View.VISIBLE);
                not_admin_panel_link.setVisibility(View.INVISIBLE);
                login_button.setText("Войти");
                parentDbName = "Users";
            }
        });
    }

    private void loginUser()
    {
        String login = login_input.getText().toString();
        String password = reg_input.getText().toString();

        if (TextUtils.isEmpty(login))
        {
            Toast.makeText(this,"Введите ваш логин :)",Toast.LENGTH_LONG).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"Введите ваш пароль :)",Toast.LENGTH_LONG).show();
        }
        else
        {
            loadingBar.setTitle("Вход.");
            loadingBar.setMessage("Одну минуту...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidateUser(login,password);
        }
    }

    private void ValidateUser(String login, String password)
    {
        if (checkBoxRemember.isChecked())
        {
            Paper.book().write(Rules.UserLoginKey, login);
            Paper.book().write(Rules.UserPasswordKey, password);
        }

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(parentDbName).child(login).exists())
                {
                    Users usersData = snapshot.child(parentDbName).child(login).getValue(Users.class);

                    if (usersData.getLogin().equals(login))
                    {
                        if (usersData.getPassword().equals(password))
                        {
                           if (parentDbName.equals("Users"))
                           {
                               loadingBar.dismiss();
                               Toast.makeText(LoginActivity.this, "Успешный вход", Toast.LENGTH_SHORT).show();

                               Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
                               startActivity(homeIntent);
                           }

                           else if (parentDbName.equals("Admins"))
                           {
                               loadingBar.dismiss();
                               Toast.makeText(LoginActivity.this, "Успешный вход", Toast.LENGTH_SHORT).show();

                               Intent homeIntent = new Intent(LoginActivity.this, AdminAddNewActivity.class);
                               startActivity(homeIntent);
                           }
                        }

                        else
                        {
                            loadingBar.dismiss();
                            Toast.makeText(LoginActivity.this, "Неверный пароль", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                else
                    {
                        loadingBar.dismiss();
                        Toast.makeText(LoginActivity.this ,"Аккаунт с логином "+ login +"не существует.", Toast.LENGTH_SHORT).show();
                        Intent regIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                        startActivity(regIntent);
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}