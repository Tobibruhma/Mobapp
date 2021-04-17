package com.example.text;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.text.Model.Users;
import com.example.text.Rules.Rules;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private Button joinButton , loginButton;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        joinButton = (Button)findViewById(R.id.button_reg);
        loginButton = (Button)findViewById(R.id.button_sign);
        loadingBar = new ProgressDialog(this);

        Paper.init(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent (MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(regIntent);
            }
        });

        String UserLoginKey = Paper.book().read(Rules.UserLoginKey);
        String UserPasswordKey = Paper.book().read(Rules.UserPasswordKey);

         if (UserLoginKey != ""  &&  UserPasswordKey != "")
         {
             if (!TextUtils.isEmpty(UserLoginKey) && !TextUtils.isEmpty(UserPasswordKey) )

             {
                 ValidateUser(UserLoginKey, UserPasswordKey);

                 loadingBar.setTitle("Вход.");
                 loadingBar.setMessage("Одну минуту...");
                 loadingBar.setCanceledOnTouchOutside(false);
                 loadingBar.show();
             }
         }
    }

    private void ValidateUser(final String login, final  String password)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("Users").child(login).exists())
                {
                    Users usersData = snapshot.child("Users").child(login).getValue(Users.class);

                    if (usersData.getLogin().equals(login))
                    {
                        if (usersData.getPassword().equals(password))
                        {
                            loadingBar.dismiss();
                            Toast.makeText(MainActivity.this, "Успешный вход", Toast.LENGTH_SHORT).show();

                            Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(homeIntent);
                        }

                        else
                        {
                            loadingBar.dismiss();

                        }
                    }
                }

                else
                {
                    loadingBar.dismiss();
                    Toast.makeText(MainActivity.this ,"Аккаунт с логином "+ login +"не существует.", Toast.LENGTH_SHORT).show();
                    Intent regIntent = new Intent(MainActivity.this, RegisterActivity.class);
                    startActivity(regIntent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}