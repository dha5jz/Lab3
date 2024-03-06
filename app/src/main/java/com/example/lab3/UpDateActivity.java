package com.example.lab3;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class UpDateActivity extends AppCompatActivity {
    EditText id;
    EditText name ;
    EditText phone;
    Button btnEdit;
    Button btnCancel;
    ImageView img;
    String imagePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_date);
        findID();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            int id_up = bundle.getInt("id");
            String name_up = bundle.getString("name");
            String phone_up = bundle.getString("Phone");
            String img_path = bundle.getString("Image");
            imagePath = img_path;
            id.setText(String.valueOf(id_up));
            name.setText(name_up);
            phone.setText(phone_up);
            Log.d("l", "onCreate: " + img_path);
            Glide.with(UpDateActivity.this)
                    .load(img_path)
                    .into(img);
        }

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int new_Id = Integer.parseInt(id.getText().toString());
                String new_Name = name.getText().toString();
                String new_Phone = phone.getText().toString();
                Intent i = new Intent();
                Bundle b = new Bundle();
                b.putInt("Update_Id",new_Id);
                b.putString("Update_Name",new_Name);
                b.putString("Update_Phone",new_Phone);
                b.putString("Update_Image",imagePath);
                i.putExtras(b);
                setResult(120,i);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 130);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 130 && resultCode == RESULT_OK && data != null){
            Uri img_uri = data.getData();
            img.setImageURI(img_uri);
            imagePath = img_uri.toString();
        }
    }

    public void findID(){
        id = findViewById(R.id.update_Id);
        name = findViewById(R.id.update_Name);
        phone = findViewById(R.id.update_Phone);
        btnEdit = findViewById(R.id.update_Edit);
        btnCancel = findViewById(R.id.update_Cancel);
        img = findViewById(R.id.update_img);

    }
}