package com.example.lab3;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Contact> ContactList;
    private Adapter ListAdapter;
    private EditText etSearch;
    private ListView lvContact;
    private Button btnThem, btnSua, btnXoa;
    private int SelectedItemId;
    private Contact c;
    private int selectItemId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Yêu cầu quyền truy cập vào bộ nhớ
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    300);
        }
        ContactList = new ArrayList<>();
        ContactList.add(new Contact(1,"https://i.pinimg.com/originals/89/4f/15/894f156accbcbff8a5a406a1ba504bb8.jpg","Mot","mot@gmail.com","123",false));
        ContactList.add(new Contact(2,"https://i.pinimg.com/originals/c1/61/c8/c161c8eb9755205f66a499c50d807ead.jpg","Hai","hai@gmail.com","234",false));
        ContactList.add(new Contact(3,"https://i.pinimg.com/originals/ce/43/c3/ce43c38ad60db6bbda8f74279887105c.jpg","Ba","ba@gmail.com","345",true));
        ListAdapter = new Adapter(ContactList,this);
        lvContact = findViewById(R.id.main_lview);
        lvContact.setAdapter(ListAdapter);

        etSearch = findViewById(R.id.main_edSearch);
        btnThem = findViewById(R.id.main_btnAdd);
        btnSua = findViewById(R.id.main_btnEdit);
        btnXoa = findViewById(R.id.main_btnDelete);

        lvContact.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                SelectedItemId = position;
                Log.d("long", "onItemLongClick: " );
                return false;
            }
        });

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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        Log.d("MainActivity", "onCreateContextMenu called");
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contextmenu,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        Contact c = ContactList.get(SelectedItemId);
        if(item.getItemId()== R.id.mnuEdit){
            Intent intent = new Intent(MainActivity.this, SubActivity.class);
            Bundle b = new Bundle();
            b.putInt("Id",c.getId());
            b.putString("Name",c.getName());
            b.putString("Phone",c.getPhone());
            b.putString("Email",c.getEmail());
            b.putString("Image",c.getImages());
            b.putBoolean("Status",c.getStatus());
            intent.putExtras(b);
            startActivityForResult(intent,200);
        } else if (item.getItemId()== R.id.mnuCall) {
            Intent in = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + c.getPhone()));
            startActivity(in);
        }
        else if(item.getItemId()==R.id.mnuSms){
            Intent in = new Intent(Intent.ACTION_SENDTO, Uri.parse("sms:" + c.getPhone()));
            startActivity(in);
        } else if (item.getItemId()==R.id.mnuEmail) {
            Intent in = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + c.getEmail()));
            startActivity(in);
        } else if (item.getItemId()==R.id.mnuFacebook) {
            Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/"+ c.getName()));
            startActivity(in);
        }
        return super.onContextItemSelected(item);

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
            String email = b.getString("Email");
            Contact newcontact = new Contact(id,image,name,email,phone,false);
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