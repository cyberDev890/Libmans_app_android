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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tentangaplikasi_screen, container, false);

        TextView kata = view.findViewById(R.id.txt_kata);
        kata.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        kata.setText("Libman School merupakan sebuah aplikasi manajemen perpustakaan yang bertujuan untuk memudahkan pengguna dalam mengatur segala aktivitas di dalam perpustakaan secara cepat dan efisien. Libman School diambil dari kata-kata 'Library' yang berarti perpustakaan dan 'Management' yang berarti pengelolaan.\n\n" +
                "Aplikasi ini dirancang dalam dua versi, yaitu versi mobile dan versi website. Kedua versi tersebut memiliki fungsi yang berbeda namun saling melengkapi. Versi website digunakan oleh admin untuk melakukan manajemen dan pengolahan data perpustakaan, sedangkan versi mobile ditujukan untuk pengguna umum agar dapat melakukan transaksi peminjaman dan pengembalian buku.\n\n" +
                "Selain fitur utama yang telah disebutkan, website ini juga menyediakan berbagai fitur lainnya yang dapat memudahkan admin dalam mengelola data, seperti ekspor dan impor data dari file PDF, Excel, atau TXT. Terdapat juga fitur Scan Barcode yang cerdas menggunakan kartu perpustakaan untuk melakukan transaksi. Admin juga dapat mengedit database secara real-time dengan mudah dan cepat.\n\n" +
                "Pada versi mobile, terdapat berbagai fitur menarik yang mudah digunakan oleh pengguna. Misalnya, fitur pemberitahuan untuk memperlihatkan pengguna mengenai status batas waktu peminjaman buku agar tidak melewati batas waktu tersebut. Terdapat juga fitur tambah favorit yang memungkinkan pengguna untuk menandai buku sebagai favorit dan mengingatinya untuk peminjaman di masa depan tanpa perlu mencarinya kembali.");

        return view;
    }
}