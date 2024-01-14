package com.xander.test;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Recycler extends RecyclerView.Adapter<Recycler.ViewHolder> {


    Context context;
    ArrayList<ContactModel> arrContact;

    private int lastPosition= -1;

    public Recycler(Context context, ArrayList<ContactModel> arrContact){
        this.context= context;
        this.arrContact=arrContact;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.contact_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.imgContact.setImageResource(arrContact.get(position).img);
        holder.txtName.setText(arrContact.get(position).name);
        holder.txtNumber.setText(arrContact.get(position).number);

        setAnimation(holder.itemView, position);

        holder.llRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.custom_dialog_layout);

                EditText edtName = dialog.findViewById(R.id.edtName);
                EditText edtNumber = dialog.findViewById(R.id.edtNumber);
                Button btnAction = dialog.findViewById(R.id.btnAction);
                TextView txtTitle = dialog.findViewById(R.id.txtTitle);

                txtTitle.setText("Update Contact");

                btnAction.setText("Save");

                edtName.setText((arrContact.get(position)).name);
                edtNumber.setText((arrContact.get(position)).number);

                btnAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name="", number="";

                        if(!edtName.getText().toString().equals("")) {
                            name = edtName.getText().toString();
                        }else{
                            Toast.makeText(context, "Please Enter Contact Name", Toast.LENGTH_SHORT).show();
                        }
                        if (!edtNumber.getText().toString().equals("")) {
                            number = edtNumber.getText().toString();
                        }else{
                            Toast.makeText(context, "Please Enter Contact Number", Toast.LENGTH_SHORT).show();
                        }

                        arrContact.set(position, new ContactModel(arrContact.get(position).img, name, number));
                        notifyItemChanged(position);

                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        holder.llRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setMessage("Confirm")
                        .setIcon(R.drawable.delete)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                arrContact.remove(position);
                                notifyItemRemoved(position);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                builder.show();
            }
        });
    }



    @Override
    public int getItemCount() {
        return arrContact.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtName, txtNumber;
        ImageView imgContact;
        LinearLayout llRow;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtNumber = itemView.findViewById(R.id.txtNumber);
            imgContact = itemView.findViewById(R.id.imgContact);
            llRow= itemView.findViewById(R.id.llRow);

        }
    }

    private void setAnimation(View viewToAnimate, int position){

        if(position>-1){
            Animation slideIn= AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(slideIn);
            lastPosition=position;
        }

    }
}
