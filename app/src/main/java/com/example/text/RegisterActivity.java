package com.example.text;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private Button button_reg;
    private EditText name_input, login_input, reg_input;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        button_reg = (Button)findViewById(R.id.button_reg);
        name_input = (EditText)findViewById(R.id.name_input);
        login_input = (EditText)findViewById(R.id.login_input);
        reg_input = (EditText)findViewById(R.id.pass_input);
        loadingBar = new ProgressDialog(this);

        button_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccount();
            }
        });
    }

    private void CreateAccount()
    {
        String name = name_input.getText().toString();
        String login = login_input.getText().toString();
        String password = reg_input.getText().toString();


        if (TextUtils.isEmpty(name))
        {
            Toast.makeText(this,"Введите имя :)",Toast.LENGTH_LONG).show();
        }
        else   if (TextUtils.isEmpty(login))
        {
            Toast.makeText(this,"Введите ваш логин :)",Toast.LENGTH_LONG).show();
        }
       else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"Введите ваш пароль :)",Toast.LENGTH_LONG).show();
        }
       else
        {
            loadingBar.setTitle("Создание аккаунта");
            loadingBar.setMessage("Одну минуту...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidateLogin(name,login,password);
        }


    }

    private void ValidateLogin(String name, String login, String password)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.child("Users").child(login).exists())
                {
                    HashMap<String, Object> userDataMap = new HashMap<>();
                    userDataMap.put("login",login);
                    userDataMap.put("name",name);
                    userDataMap.put("password",password);

                    RootRef.child("Users").child(login).updateChildren(userDataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                loadingBar.dismiss();
                                Toast.makeText(RegisterActivity.this,"Регистрация прошла успешно",Toast.LENGTH_SHORT).show();
                                Intent loginIntent = new Intent (RegisterActivity.this, LoginActivity.class);
                                startActivity(loginIntent);
                            }

                            else
                            {
                                loadingBar.dismiss();
                                Toast.makeText(RegisterActivity.this,"Ошибка регистрации :(",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

                else
                {
                    loadingBar.dismiss();
                    Toast.makeText(RegisterActivity.this,"Логин"+login + "зарегистрирован",Toast.LENGTH_SHORT).show();
                 
                    Intent loginIntent = new Intent (RegisterActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}