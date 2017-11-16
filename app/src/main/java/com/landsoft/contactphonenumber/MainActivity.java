package com.landsoft.contactphonenumber;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.landsoft.contactphonenumber.Adapter.ContactAdapter;
import com.landsoft.contactphonenumber.Model.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private EditText editName, editPhone;
    RadioButton rbtnMale, rbtnFemale;
    Button btnAdd;
    ListView lvContact;
    int position;

    ContactAdapter contactAdapter;
    List<Contact> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        xin quyen user-permission
        checkAndRequestPermission();
//        lay tham so wwidget
        initWidget();
//        tao danh sach getView
        contactList = new ArrayList<>();
        contactAdapter = new ContactAdapter(MainActivity.this,contactList);
        lvContact.setAdapter(contactAdapter);
//        xu ly su kien khi click item list view
        lvContact.setOnItemClickListener(this);
//        xu ly su kien khi click button add contact
        btnAdd.setOnClickListener(this);
    }

    //xin quyen nguoi dung
    private void checkAndRequestPermission(){
        String[] permissions = new String[]{android.Manifest.permission.CALL_PHONE,
                android.Manifest.permission.SEND_SMS
        };
        List<String> listPermissions = new ArrayList<>();
        for (String permission : permissions ){
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                listPermissions.add(permission);
            }
        }
        if (!listPermissions.isEmpty()){
            ActivityCompat.requestPermissions(this, listPermissions.toArray(new String[listPermissions.size()]), 1);
        }
    }

    // tao layout dialog cho nguoi dung
    private void ShowDialogSendAndCall(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_send_call);
        dialog.setCancelable(true);
        Button btnCall = dialog.findViewById(R.id.btn_call);
        Button btnSend = dialog.findViewById(R.id.btn_send);
        btnCall.setOnClickListener(this);
        btnSend.setOnClickListener(this);
        dialog.show();
    }

    private void initWidget(){
        editName = findViewById(R.id.edt_name);
        editPhone = findViewById(R.id.edt_phone);
        rbtnMale = findViewById(R.id.rbtn_male);
        rbtnFemale = findViewById(R.id.rbtn_female);
        btnAdd = findViewById(R.id.btn_add);
        lvContact = findViewById(R.id.lv_contact);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                actionAddContact(view);
                break;
            case R.id.btn_call:
                Toast.makeText(MainActivity.this," Btn Call action ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+contactList.get(position).getNumber()));
                startActivity(intent);
                break;
            case R.id.btn_send:
                Toast.makeText(MainActivity.this," Btn Send action ", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent();
                intent1.setAction(Intent.ACTION_VIEW);
                intent1.setData(Uri.parse("sms:"+contactList.get(position).getNumber()));
                startActivity(intent1);
                break;
        }

    }

    private void actionAddContact(View view) {
        if (view.getId() == R.id.btn_add){
            String name = editName.getText().toString().trim();
            String phome = editPhone.getText().toString().trim();
            boolean isMale = false;
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phome)){
                Toast.makeText(getApplicationContext()," Vui long nhap ten hoac so dien thoai",Toast.LENGTH_SHORT).show();
            }
            else
            {
                if (rbtnMale.isChecked()){
                    isMale = true;
                }
                Contact contact = new Contact(isMale,name,phome);
                contactList.add(contact);
            }
            contactAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getApplicationContext(),"Ban dang chon " + adapterView.getItemAtPosition(i)+" contact "+ contactList.get(i).getName(),Toast.LENGTH_SHORT).show();
        position = i;
        ShowDialogSendAndCall();
    }
}
