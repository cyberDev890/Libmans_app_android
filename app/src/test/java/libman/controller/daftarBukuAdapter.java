package libman.controller;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.libman.R;
import com.libman.api.endpointUrl;
import com.libman.model.daftarbuku.DaftarBukuData;
import com.libman.ui.detail_buku;

import java.util.ArrayList;
import java.util.List;

public class daftarBukuAdapter extends RecyclerView.Adapter<daftarBukuAdapter.ViewHolder> implements Filterable {

    private List<DaftarBukuData> daftarBukuDataList;
    private List<DaftarBukuData> filteredList;

    public daftarBukuAdapter() {
        this.daftarBukuDataList = new ArrayList<>();
        this.filteredList = new ArrayList<>();
    }

    public void setData(List<DaftarBukuData> daftarBukuDataList) {
        this.daftarBukuDataList = daftarBukuDataList;
        this.filteredList = daftarBukuDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_daftar_buku, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DaftarBukuData daftarBukuData = filteredList.get(position);
        holder.txtJudul.setText(daftarBukuData.getJudulBuku());
        holder.txtSemester.setText("Semester: " + daftarBukuData.getSemester());
        holder.txtPenerbit.setText("Penerbit: " + daftarBukuData.getPenerbit());
        holder.txtJumlah.setText("Tersedia: " + daftarBukuData.getJumlah());
        holder.txtTahun.setText("Tahun Terbit: " + daftarBukuData.getTahunTerima());

        // Load image using Glide or any other image loading library
        String imageUrl =  endpointUrl.BASE_URL_IMAGE + daftarBukuData.getGambar();
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
                intent.putExtra("id_buku", daftarBukuData.getIdBuku());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBuku;
        TextView txtJudul, txtSemester, txtTahun, txtPenerbit, txtJumlah;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBuku = itemView.findViewById(R.id.listImageDaftarBuku);
            txtJudul = itemView.findViewById(R.id.listJudulDaftarBuku);
            txtSemester = itemView.findViewById(R.id.txt_semesterDaftarBuku);
            txtTahun = itemView.findViewById(R.id.tahunDaftarbuku);
            txtPenerbit = itemView.findViewById(R.id.penerbitDaftarbuku);
            txtJumlah = itemView.findViewById(R.id.jumlahDaftarBuku);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String keyword = charSequence.toString().toLowerCase().trim();
                List<DaftarBukuData> filteredData = new ArrayList<>();

                if (keyword.isEmpty()) {
                    filteredData = daftarBukuDataList;
                } else {
                    for (DaftarBukuData data : daftarBukuDataList) {
                        if (data.getJudulBuku().toLowerCase().contains(keyword)) {
                            filteredData.add(data);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredData;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredList = (List<DaftarBukuData>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
