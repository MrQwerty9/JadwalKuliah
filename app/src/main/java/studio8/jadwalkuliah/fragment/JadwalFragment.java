package studio8.jadwalkuliah.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import studio8.jadwalkuliah.R;
import studio8.jadwalkuliah.adapter.AdapterMatkul;
import studio8.jadwalkuliah.data.dbHandler;
import studio8.jadwalkuliah.model.ModelMatkul;

public class JadwalFragment extends Fragment {
    private RecyclerView mRecycleMatkul;
    private LinearLayoutManager linear;
    private AdapterMatkul adapterMatkul;
    private List<ModelMatkul> modalList = new ArrayList<ModelMatkul>();

    private dbHandler db;
    public JadwalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_jadwal, container, false);
        mRecycleMatkul = (RecyclerView) rootView.findViewById(R.id.rv_matkul);

        adapterMatkul = new AdapterMatkul(getActivity(), modalList);

        linear = new LinearLayoutManager(getActivity());
        mRecycleMatkul.setLayoutManager(linear);
        mRecycleMatkul.setAdapter(adapterMatkul);
        db = new dbHandler(getActivity());

        List<ModelMatkul> contacts = db.ReadMatkul();
        for (ModelMatkul cn : contacts) {
            ModelMatkul modal = new ModelMatkul();
            modal.setId(cn.getId());
            modal.setMatkul(cn.getMatkul());
            modal.setKelas(cn.getKelas());
            modal.setDosen(cn.getDosen());
            modalList.add(modal);

            if (modalList.isEmpty())
                Toast.makeText(getActivity(), "Data tidak ada", Toast.LENGTH_SHORT).show();
        }
        // Inflate the layout for this fragment
        return rootView;
    }
}
