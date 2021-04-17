package com.example.text;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class NewProductActivity extends AppCompatActivity {

    private String categoryName , Description , Price , Name, saveCurrentDate, saveCurrentTime, ProductRandomKey;
    private String downloadImageUrl;
    private ImageView pic_view;
    private EditText name_text , disk_text, price_text;
    private Button add_button;
    private static final int GALLERYPICK = 1;
    private Uri imageUri;
    private StorageReference ProductImageRef;
    private DatabaseReference ProductRef;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);

        init();

        pic_view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                OpenGallery();
            }
        });


        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ValidateProductData();
            }
        });

        categoryName = getIntent().getExtras().get("category").toString();
        Toast.makeText(this,"Выбрана категория " + categoryName,Toast.LENGTH_SHORT).show();

    }

    private void ValidateProductData()
    {
        Description = disk_text.getText().toString();
        Price = price_text.getText().toString();
        Name = name_text.getText().toString();

        if (imageUri == null)
        {
            Toast.makeText(this,"Добавьте изображение товара", Toast.LENGTH_SHORT).show();
        }

        else  if (TextUtils.isEmpty(Description))
        {
            Toast.makeText(this,"Добавьте описание товара", Toast.LENGTH_SHORT).show();
        }

        else  if (TextUtils.isEmpty(Price))
        {
            Toast.makeText(this,"Добавьте стоимость товара", Toast.LENGTH_SHORT).show();
        }
        else  if (TextUtils.isEmpty(Name))
        {
            Toast.makeText(this,"Добавьте название товара", Toast.LENGTH_SHORT).show();
        }

        else
        {
            StoreProductInformation();
        }
    }

    private void StoreProductInformation()
    {

        loadingBar.setTitle("Загрузка.");
        loadingBar.setMessage("Одну минуту...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("ddMMyyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HHmmss");
        saveCurrentTime = currentTime.format(calendar.getTime());


        ProductRandomKey = saveCurrentDate + saveCurrentTime;

        StorageReference filePath = ProductImageRef.child(imageUri.getLastPathSegment() + ProductRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(imageUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String message = e.toString();
                Toast.makeText(NewProductActivity.this,"Ошибка :(",Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(NewProductActivity.this, "Изображение загружено", Toast.LENGTH_SHORT).show();
                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();
                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(NewProductActivity.this, "Сохранено", Toast.LENGTH_SHORT).show();


                            SaveProductInfoToDatabase();
                        }
                    }
                });
            }
        });

    }

    private void SaveProductInfoToDatabase()
    {
        HashMap<String,Object> productMap = new HashMap<>();

        productMap.put("pid",ProductRandomKey );
        productMap.put("date",saveCurrentDate );
        productMap.put("time",saveCurrentTime );
        productMap.put("description",Description );
        productMap.put("image",downloadImageUrl );
        productMap.put("category",categoryName );
        productMap.put("price",Price );
        productMap.put("pname",Name );

        ProductRef.child(ProductRandomKey).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if (task.isSuccessful())
                {

                    loadingBar.dismiss();
                    Toast.makeText(NewProductActivity.this, "Товар добавлен", Toast.LENGTH_SHORT).show();
                    Intent loginIntent = new Intent (NewProductActivity.this, AdminAddNewActivity.class);
                    startActivity(loginIntent);

                }

                else
                {
                    String message = task.getException().toString();
                    Toast.makeText(NewProductActivity.this, "Ошибка: " + message, Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }
        });

    }

    private void OpenGallery()
    {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,GALLERYPICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERYPICK && resultCode == RESULT_OK && data != null )
        {
            imageUri = data.getData();
            pic_view.setImageURI(imageUri);
        }
    }

    private void init()
    {
        categoryName = getIntent().getExtras().get("category").toString();
        pic_view = findViewById(R.id.pic_view);
        name_text = findViewById(R.id.name_text);
        disk_text = findViewById(R.id.disc_text);
        price_text = findViewById(R.id.price_text);
        add_button = findViewById(R.id.add_button);
        ProductImageRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        ProductRef = FirebaseDatabase.getInstance().getReference().child("Products");
        loadingBar = new ProgressDialog(this);
    }
}