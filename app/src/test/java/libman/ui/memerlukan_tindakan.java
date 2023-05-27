package libman.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.libman.R;
import com.libman.api.ApiClient;
import com.libman.api.ApiInterface;
import com.libman.controller.tindakanAdapter;
import com.libman.model.memerlukan_tindakan.Tindakan;
import com.libman.model.memerlukan_tindakan.TindakanData;
import com.libman.sesion.SesionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class memerlukan_tindakan extends Fragment {

    private RecyclerView rvTindakan;
    SesionManager sesionManager;
    private String _Nis;
    private tindakanAdapter TindakanAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_memerlukan_tindakan, container, false);

        sesionManager = new SesionManager(getActivity());
        String NIS = sesionManager.getUserDetail().get(SesionManager.NIS);
        Toast.makeText(getActivity(), NIS, Toast.LENGTH_SHORT).show();
        rvTindakan = view.findViewById(R.id.rv_tindakan);
        rvTindakan.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        TindakanAdapter = new tindakanAdapter();
        rvTindakan.setAdapter(TindakanAdapter);
        // Fetch history data
        fetchHistoryData(NIS);
        return view;
    }
    private void fetchHistoryData(String nis ) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Tindakan> call = apiInterface.tindakan(nis);
        call.enqueue(new Callback<Tindakan>() {
            @Override
            public void onResponse(Call<Tindakan> call, Response<Tindakan> response) {
                if (response.isSuccessful()) {
                    Tindakan tindakan = response.body();
                    if (tindakan != null && tindakan.isStatus()) {
                        List<TindakanData> tindakanList = tindakan.getData();
                        TindakanAdapter.setData(tindakanList);
                        TindakanAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getActivity(), "Data Kosong", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Tindakan> call, Throwable t) {

            }
        });


    }

}