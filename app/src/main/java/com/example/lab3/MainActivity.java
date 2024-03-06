package com.example.lab3;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Contact> ContactList;
    private Adapter ListAdapter;
    private EditText etSearch;
    private ListView lvContact;
    private Button btnThem, btnSua, btnXoa;
    private Contact c;
    private int selectItemId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContactList = new ArrayList<>();
        ContactList.add(new Contact(1,"https://i.pinimg.com/originals/89/4f/15/894f156accbcbff8a5a406a1ba504bb8.jpg","Mot","123",false));
        ContactList.add(new Contact(2,"https://i.pinimg.com/originals/c1/61/c8/c161c8eb9755205f66a499c50d807ead.jpg","Hai","234",false));
        ContactList.add(new Contact(3,"https://i.pinimg.com/originals/ce/43/c3/ce43c38ad60db6bbda8f74279887105c.jpg","Ba","345",true));
        ListAdapter = new Adapter(ContactList,this);
        lvContact = findViewById(R.id.main_lview);
        lvContact.setAdapter(ListAdapter);

        etSearch = findViewById(R.id.main_edSearch);
        btnThem = findViewById(R.id.main_btnAdd);
        btnSua = findViewById(R.id.main_btnEdit);
        btnXoa = findViewById(R.id.main_btnDelete);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SubActivity.class);
                startActivityForResult(intent,100);
            }
        });
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean anyItemSelected = false;
                for(int i=0;i<ContactList.size();){
                    if(ContactList.get(i).getStatus()==true){
                        anyItemSelected = true;
                        selectItemId = i;
                        break;
                    }else {
                        i++;
                    }
                }
                if(anyItemSelected){ // Nếu có mục được chọn
                    Intent intent = new Intent(MainActivity.this, UpDateActivity.class);
                    c= ContactList.get(selectItemId);
                    Bundle b = new Bundle();
                    b.putInt("id", c.getId());
                    b.putString("Image",c.getImages());
                    b.putString("name", c.getName());
                    b.putString("Phone",c.getPhone());
                    intent.putExtras(b);
                    startActivityForResult(intent,300);
                } else { // Nếu không có mục nào được chọn
                    Toast.makeText(MainActivity.this, "Vui lòng chọn một mục để sửa", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //xay dựng hộp thoại trong activity:Main
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có chắc chắn muốn xóa?");
                //thiết lập nút có
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for(int i=0;i<ContactList.size();)
                        {
                            if(ContactList.get(i).getStatus()==true){
                                ContactList.remove(i);
                            }
                            else i++;
                        }
                        ListAdapter.notifyDataSetChanged();
                    }
                });
                //thiết lập nút không
                builder.setNegativeButton("Không",null);
                // hiển thị hộp thoại
                builder.create().show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100 && resultCode ==150){
            Bundle b = data.getExtras();
            int id = b.getInt("Id");
            String name = b.getString("Name");
            String phone = b.getString("Phone");
            String image = b.getString("Image");
            Contact newcontact = new Contact(id,image,name,phone,false);
            //truong hop them
            ContactList.add(newcontact);
            ListAdapter.notifyDataSetChanged();
        }if(requestCode == 300 && resultCode == 120){
            Bundle bu =data.getExtras();
            int id_up = bu.getInt("Update_Id");
            String name_up = bu.getString("Update_Name");
            String phone_up = bu.getString("Update_Phone");
            String img= bu.getString("Update_Image");
            c.setId(id_up);
            c.setName(name_up);
            c.setPhone(phone_up);
            c.setImages(img);
            ListAdapter.notifyDataSetChanged();
        }

    }
}