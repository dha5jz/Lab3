package com.example.lab3;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SubActivity extends AppCompatActivity {
    private Button btnAdd;
    private EditText edId, edName, edPhone;
    private ImageView img;
    private String pathimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        edId = findViewById(R.id.sub_edId);
        edName = findViewById(R.id.sub_edName);
        edPhone = findViewById(R.id.sub_edPhone);
        img = findViewById(R.id.sub_imgview);

        btnAdd = findViewById(R.id.sub_btnAdd);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,130);
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,130);
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int id = Integer.parseInt(edId.getText().toString());
                    String name = edName.getText().toString();
                    String phone = edPhone.getText().toString();
                    Boolean status = false;
                    if (name.isEmpty() || phone.isEmpty() || edId.getText().toString().isEmpty()){
                        Toast.makeText(getApplicationContext(),"Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                        return ;
                    }else{
                        Intent intent = new Intent();
                        //bundler duoc su dung de dong goi du lieu
                        //va gui tu activity hien tại toi activity khac thong qua intent
                        Bundle b = new Bundle();
                        b.putInt("Id",id);
                        b.putString("Image", pathimg);
                        b.putString("Name",name);
                        b.putString("Phone",phone);

                        intent.putExtras(b);
                        setResult(150, intent);
                        finish();
                    }
                }catch (NumberFormatException e){
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập số cho ID", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 130 && resultCode == RESULT_OK && data != null){
            Uri img_uri = data.getData();
            img.setImageURI(img_uri);
            pathimg = img_uri.toString();
        }
    }
}