package com.example.tufunze;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.ViewHolder {
   public TextView txtName1, txteEmail1,txtPhone1;
   public ImageView imgbackground;
    public MyAdapter(@NonNull View itemView) {
        super(itemView);
        txtPhone1 = itemView.findViewById(R.id.txtPhone);
        txteEmail1 = itemView.findViewById(R.id.txtEmail);
        txtName1 = itemView.findViewById(R.id.txtName);
        imgbackground = itemView.findViewById(R.id.img_user);
    }
}
