package com.libman.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.libman.R;

public class tentang_aplikasi extends Fragment {

    private TextView kata;

    public tentang_aplikasi() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate layout untuk fragment ini
        View view = inflater.inflate(R.layout.fragment_tentangaplikasi_screen, container, false);

        TextView kata = view.findViewById(R.id.txt_kata);
        kata.setText("Libman School merupakan sebuah aplikasi manajemen perpustakaan yang bertujuan untuk memudahkan penggunanya mengatur segala aktivitas di dalam perpustakaan secara cepat dan efisien.\n\n" +
                "Nama 'Libman' diambil dari kata 'Library' yang berarti perpustakaan dan 'Man' yang berarti pengelolaan.Aplikasi ini memiliki dua versi, yaitu versi mobile dan website. Keduanya memiliki fungsi yang berbeda namun saling melengkapi. Website digunakan oleh admin untuk melakukan manajemen dan pengolahan data perpustakaan, sedangkan versi mobile diperuntukkan bagi pengguna umum untuk melakukan transaksi peminjaman dan pengembalian buku.\n\n" +
                "Selain fitur utama dalam website yang telah disebutkan, kami juga menyediakan berbagai fitur lain yang dapat memudahkan admin dalam mengelola data, seperti ekspor dan impor file dalam format PDF, Excel, atau TXT. Kami juga menyediakan fitur scan barcode menggunakan kartu perpustakaan untuk transaksi. Admin juga dapat mengedit database secara real-time dengan mudah dan cepat.\n\n" +
                "Versi mobile juga dilengkapi dengan berbagai fitur menarik dan mudah digunakan oleh pengguna, seperti fitur pemberitahuan yang memberikan informasi mengenai batas waktu peminjaman agar pengguna tidak melewati batas waktu. Terdapat juga fitur tambah favorit yang memungkinkan pengguna untuk menyimpan buku favorit sebagai pengingat untuk dipinjam di lain waktu tanpa perlu mencarinya kembali.");

        return view;
    }
}