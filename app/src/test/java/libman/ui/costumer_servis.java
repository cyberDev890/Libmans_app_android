

package libman.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.libman.R;


public class costumer_servis extends Fragment {
private TextView text;

    public costumer_servis() {
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
        View view = inflater.inflate(R.layout.fragment_customerservice_screen, container, false);

        text = view.findViewById(R.id.kata_cs);
        text.setText("Jika mengalami kendala dalam penggunaan aplikasi\n" +
                "anda dapat menghubungi beberapa CS berikut :\n\n" +
                "No :\n" +
                "+62 856 9555 0432 ( GG Project Company )\n" +
                " \n\n" +
                "Website :\n" +
                "www.ggproject.com\n" +
                "\n\n" +
                "Email :\n" +
                "ggproject@gmail.com");
        String[] para = text.getText().toString().split("\r\r ");
        return view;
    }
}
