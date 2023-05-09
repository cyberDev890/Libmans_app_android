package com.libman;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class drawerAdapater extends RecyclerView.Adapter<drawerAdapater.ViewHolder> {

    Activity activity;
    ArrayList<String> arrayList;
    ArrayList<Integer> image;

    //constructor
    public drawerAdapater(Activity activity, ArrayList<String> arrayList, ArrayList<Integer> image) {
        this.activity = activity;
        this.arrayList = arrayList;
        this.image = image;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drawer_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ivMenu.setImageResource(image.get(position));
        holder.tvMenu.setText(arrayList.get(position));
        holder.tvMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int posisi = holder.getAdapterPosition();

                switch (posisi) {
                    case 0:
                        activity.startActivity(new Intent(activity, dashboard.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;
                    case 1:
                        activity.startActivity(new Intent(activity, memerlukan_tindakan.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;
                    case 2:
                        activity.startActivity(new Intent(activity, daftar_buku.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;

                    case 3:
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                        builder.setTitle("Keluar");

                        builder.setMessage("Kamu Yakin Ingin Keluar Dari Aplikasi?");

                        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                activity.finish();
                                System.exit(0);
                            }
                        });
                        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvMenu;

        private ImageView ivMenu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMenu = itemView.findViewById(R.id.tvMenu);

            ivMenu = itemView.findViewById(R.id.ivMenu);
        }
    }
}
