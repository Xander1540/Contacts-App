package com.xander.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.widget.ActivityChooserView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity<Item> extends AppCompatActivity {
    private static final String CHANNEL_ID ="My Channel";
    private static final int NOTIFICATION_ID = 1000;
CardView cardView;
Toolbar toolbar;
ArrayList<ContactModel> arrContacts= new ArrayList<>();
Recycler adapter;
FloatingActionButton btnOpenDialog;
RecyclerView recyclerView;
Button btnDial, btnMsg, btnMail, btnShare;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardView = findViewById(R.id.card);

        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.xan, null);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap largeIcon = bitmapDrawable.getBitmap();

        NotificationManager nm= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
             notification = new Notification.Builder(this)
                    .setLargeIcon(largeIcon)
                    .setSmallIcon(R.drawable.xan)
                    .setContentText("New Message")
                    .setSubText("New message from Xander")
                    .setChannelId(CHANNEL_ID)
                    .build();
            nm.createNotificationChannel(new NotificationChannel(CHANNEL_ID, "New Channel", NotificationManager.IMPORTANCE_HIGH));


        }else{
            notification = new Notification.Builder(this)
            .setLargeIcon(largeIcon)
                    .setSmallIcon(R.drawable.xan)
                    .setContentText("New Message")
                    .setSubText("New message from Xander")
                    .build();
             }

        nm.notify(NOTIFICATION_ID, notification);

        recyclerView = findViewById(R.id.recyclerContact);
        btnOpenDialog = findViewById(R.id.fab);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("My ToolBar");
        }
        toolbar.setSubtitle("Sub Title");

        Dialog ddialog = new Dialog(MainActivity.this);
        ddialog.setContentView(R.layout.custom_dialog_layout);



        btnOpenDialog.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.add_update_layout);

                EditText edtName = dialog.findViewById(R.id.edtName);
                EditText edtNumber = dialog.findViewById(R.id.edtNumber);
                Button btnAction = dialog.findViewById(R.id.btnAction);

                btnAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name= "", number="";

                        if(!edtName.getText().toString().equals("")) {
                            name = edtName.getText().toString();
                        }else{
                            Toast.makeText(MainActivity.this, "Please Enter Contact Name", Toast.LENGTH_SHORT).show();

                        }
                        if (!edtNumber.getText().toString().equals("")) {
                            number = edtNumber.getText().toString();
                        }else{
                            Toast.makeText(MainActivity.this, "Please Enter Contact Number", Toast.LENGTH_SHORT).show();
                        }

                        arrContacts.add(new ContactModel(R.drawable.a, name, number));
                        adapter.notifyItemInserted(arrContacts.size()-1);

                        recyclerView.scrollToPosition(arrContacts.size()-1);
                        Dialog updateDialog = new Dialog(MainActivity.this);
                        updateDialog.setContentView(R.layout.custom_dialog_layout); // Changed this line
                        updateDialog.show();
                    }
                });
                dialog.show();
            }
        });

        arrContacts.add(new ContactModel(R.drawable.a, "A", "7703096052"));
        arrContacts.add(new ContactModel(R.drawable.b, "B", "2133096052"));
        arrContacts.add(new ContactModel(R.drawable.c, "C", "6603096052"));
        arrContacts.add(new ContactModel(R.drawable.d, "D", "6773096052"));
        arrContacts.add(new ContactModel(R.drawable.e, "E", "3563096052"));
        arrContacts.add(new ContactModel(R.drawable.a, "F", "8678096052"));
        arrContacts.add(new ContactModel(R.drawable.b, "G", "8677796052"));
        arrContacts.add(new ContactModel(R.drawable.c, "H", "9803096052"));
        arrContacts.add(new ContactModel(R.drawable.d, "I", "4603096052"));
        arrContacts.add(new ContactModel(R.drawable.e, "J", "5603096052"));
        arrContacts.add(new ContactModel(R.drawable.a, "K", "5603096052"));
        arrContacts.add(new ContactModel(R.drawable.b, "L", "8703096052"));
        arrContacts.add(new ContactModel(R.drawable.c, "M", "9083096052"));
        arrContacts.add(new ContactModel(R.drawable.d, "N", "8103096052"));
        arrContacts.add(new ContactModel(R.drawable.e, "O", "9464096052"));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Recycler(this,arrContacts);
        recyclerView.setAdapter(adapter);
        /*
        cardView.setRadius(5.0f);
        cardView.setCardElevation(11.0f);
        cardView.setUseCompatPadding(true);
        */

        btnDial = findViewById(R.id.btnDial);
        btnMsg = findViewById(R.id.btnMsg);
        btnMail = findViewById(R.id.btnMail);
        btnShare = findViewById(R.id.btnShare);

        btnDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iDial= new Intent(Intent.ACTION_DIAL);
                iDial.setData(Uri.parse("tel: +918707229260"));
                startActivity(iDial);
            }
        });

        btnMsg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent iMsg= new Intent(Intent.ACTION_SENDTO);
                iMsg.setData(Uri.parse("smsto:"+Uri.encode("+918708229260")));
                iMsg.putExtra("sms_body", "Please solve this issue.");
                startActivity(iMsg);
            }
        });

        btnMail.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent iEmail = new Intent(Intent.ACTION_SEND);
                iEmail.setType("message/rfc822");
                iEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{"adityaxxx2003@gmail.com"});
                iEmail.putExtra(Intent.EXTRA_SUBJECT, "Queries");
                iEmail.putExtra(Intent.EXTRA_TEXT, "Please ");
                startActivity(Intent.createChooser(iEmail, "Email via"));
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent iShare= new Intent(Intent.ACTION_SEND);
                iShare.setType("text/plain");
                iShare.putExtra(Intent.EXTRA_TEXT, "SHIT");
                startActivity(Intent.createChooser(iShare, "Share via"));
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.opt_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item){
            int itemId = item.getItemId();

            if(itemId==R.id.opt_new){
                Toast.makeText(this, "Contacts App", Toast.LENGTH_SHORT).show();
            }else if(itemId==R.id.opt_open){
                Toast.makeText(this, "Owner: Xander", Toast.LENGTH_SHORT).show();
            }else if(itemId==R.id.opt_save){
                Toast.makeText(this, "Contact No. : 8707229260", Toast.LENGTH_SHORT).show();
            }else{
                super.onBackPressed();
            }
                return super.onOptionsItemSelected(item);
    }
}