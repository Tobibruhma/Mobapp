package com.example.text;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class AdminAddNewActivity extends AppCompatActivity {

    private ImageView shini, diski, lampi, akb, maslo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new);

        Toast.makeText(this, "Добро пожаловать, администратор", Toast.LENGTH_SHORT).show();

        init();

        shini.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               Intent intent = new Intent(AdminAddNewActivity.this, NewProductActivity.class);
            intent.putExtra("category","shini");
               startActivity(intent);
            }
        });

        diski.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AdminAddNewActivity.this, NewProductActivity.class);
                intent.putExtra("category","diski");
                startActivity(intent);
            }
        });

        lampi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AdminAddNewActivity.this, NewProductActivity.class);
                intent.putExtra("category","lampi");
                startActivity(intent);
            }
        });

        akb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminAddNewActivity.this, NewProductActivity.class);
                intent.putExtra("category","akb");
                startActivity(intent);
            }
        });

        maslo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminAddNewActivity.this, NewProductActivity.class);
                intent.putExtra("category","maslo");
                startActivity(intent);
            }
        });
    }

    private void init()
    {
        shini = findViewById(R.id.imageView);
        diski = findViewById(R.id.imageView2);
        lampi = findViewById(R.id.imageView3);
        akb = findViewById(R.id.imageView4);
        maslo = findViewById(R.id.imageView5);
    }
}