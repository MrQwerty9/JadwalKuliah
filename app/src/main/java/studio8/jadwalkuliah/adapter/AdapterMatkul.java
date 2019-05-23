package studio8.jadwalkuliah.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import studio8.jadwalkuliah.view.MainActivity;
import studio8.jadwalkuliah.R;
import studio8.jadwalkuliah.model.ModelMatkul;

public class AdapterMatkul extends RecyclerView.Adapter<AdapterMatkul.MatkulHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<ModelMatkul> list;

    public AdapterMatkul(Context context, List<ModelMatkul> list) {
        this.context = context;
        this.list = list;
//        Log.d("myTag", "adapter construct");
    }

    @NonNull
    @Override
    public MatkulHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_matkul, null);
        return new MatkulHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatkulHolder holder, final int position) {

        holder.mMatkul.setText(list.get(position).getMatkul());
        holder.mKelas.setText(list.get(position).getKelas());
        holder.mDosen.setText(list.get(position).getDosen());

        holder.cardViewMatkul.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View view) {
                ((MainActivity) context).OnLongClick(position, list);
                return true;
            }
        });
        holder.cardViewMatkul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) context).OnClick(position, list);
            }
        });
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MatkulHolder extends RecyclerView.ViewHolder{
        TextView mMatkul, mKelas, mDosen;
        CardView cardViewMatkul;
        public MatkulHolder(@NonNull View itemView) {
            super(itemView);
            cardViewMatkul = (CardView) itemView.findViewById(R.id.cv_matkul);
            mMatkul = (TextView) itemView.findViewById(R.id.tv_matkul);
            mKelas = (TextView) itemView.findViewById(R.id.tv_ruang);
            mDosen = (TextView) itemView.findViewById(R.id.tv_waktu);
        }
    }
}
