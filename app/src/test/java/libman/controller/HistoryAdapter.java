package libman.controller;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.libman.R;
import com.libman.api.endpointUrl;
import com.libman.model.history.HistoryData;
import com.libman.ui.detail_buku;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
private  final Context context;

    private List<HistoryData> historyDataList;

    public HistoryAdapter(Context context) {
        this.context = context;
        this.historyDataList = new ArrayList<>();
    }

    public void setData(List<HistoryData> historyDataList) {
        this.historyDataList = historyDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_buku, parent, false);


        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoryData historyData = historyDataList.get(position);
        String judul = historyDataList.get(position).getJudulBuku();
        String semester = historyDataList.get(position).getSemester();
        String penerbit = historyDataList.get(position).getPenerbit();
        String jumlah = historyDataList.get(position).getJumlah();
        holder.txtJudul.setText(historyData.getJudulBuku());
        holder.txtSemester.setText("Semester: " + historyData.getSemester());
        holder.txtTgl.setText(historyData.getTanggalPengembalian());
        String linkgbr = historyDataList.get(position).getGambar();

        // Load image using Glide or any other image loading library
        String imageUrl = endpointUrl.BASE_URL_IMAGE + linkgbr;
        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .override(1500, 1500)
                .into(holder.imgBuku);

        holder.imgBuku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the clicked book data

                // Create an intent to navigate to the detail book page
                Intent intent = new Intent(v.getContext(), detail_buku.class);
                intent.putExtra("id_buku", historyData.getId_buku());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageButton imgBuku;
        TextView txtJudul, txtSemester, txtTgl;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBuku = itemView.findViewById(R.id.img_buku);
            txtJudul = itemView.findViewById(R.id.txt_judul);
            txtSemester = itemView.findViewById(R.id.txt_semester);
            txtTgl = itemView.findViewById(R.id.txt_tgl);
        }
    }
}
