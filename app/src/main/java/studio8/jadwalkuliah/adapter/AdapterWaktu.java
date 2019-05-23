package studio8.jadwalkuliah.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import studio8.jadwalkuliah.view.WaktuActivity;
import studio8.jadwalkuliah.R;
import studio8.jadwalkuliah.model.ModelWaktu;

public class AdapterWaktu extends RecyclerView.Adapter<AdapterWaktu.WaktuHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<ModelWaktu> list;
    private String[] hari;

    public AdapterWaktu(Context context, List<ModelWaktu> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public WaktuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_waktu, null);
        return new WaktuHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final WaktuHolder holder, final int position) {
        ModelWaktu m = list.get(position);
        hari = new String[7];
        hari =  m.getHari().split(",");
        for (int j=0; j<hari.length; j++) {
            if (hari[j].equals("minggu")) {
//                holder.mMin.setTextColor(R.color.putih);
                holder.mMin.setBackgroundResource(R.drawable.corner_colorprimary);
            }
            if (hari[j].equals("senin")) {
                holder.mSen.setBackgroundResource(R.drawable.corner_colorprimary);
//                holder.mSen.setTextColor(R.color.putih);
            }
            if (hari[j].equals("selasa")) {
                holder.mSel.setBackgroundResource(R.drawable.corner_colorprimary);
//                holder.mSel.setTextColor(R.color.putih);
            }
            if (hari[j].equals("rabu")) {
                holder.mRab.setBackgroundResource(R.drawable.corner_colorprimary);
//                holder.mRab.setTextColor(R.color.putih);
            }
            if (hari[j].equals("kamis")) {
                holder.mKam.setBackgroundResource(R.drawable.corner_colorprimary);
//                holder.mKam.setTextColor(R.color.putih);
            }
            if (hari[j].equals("jumat")) {
                holder.mJum.setBackgroundResource(R.drawable.corner_colorprimary);
//                holder.mJum.setTextColor(R.color.putih);
            }
            if (hari[j].equals("sabtu")) {
                holder.mSab.setBackgroundResource(R.drawable.corner_colorprimary);
//                holder.mSab.setTextColor(R.color.putih);
            }
        }
        holder.mRuang.setText(String.valueOf(m.getRuang()));
        holder.mWaktu.setText(String.valueOf(m.getMulai()+" - "+m.getSelesai()));

        holder.cardViewWaktu.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ((WaktuActivity) context).OnLongClick(position, list);
//                Log.d("mytag", "adapterwaktu - onLongClick");
                return true;
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

    public class WaktuHolder extends RecyclerView.ViewHolder {
        TextView mMin, mSen, mSel, mRab, mKam,mJum,mSab, mRuang, mWaktu;
        CardView cardViewWaktu;
        public WaktuHolder(@NonNull View itemView) {
            super(itemView);
            mMin = (TextView) itemView.findViewById(R.id.tv_min);
            mSen = (TextView) itemView.findViewById(R.id.tv_sen);
            mSel = (TextView) itemView.findViewById(R.id.tv_sel);
            mRab = (TextView) itemView.findViewById(R.id.tv_rab);
            mKam = (TextView) itemView.findViewById(R.id.tv_kam);
            mJum = (TextView) itemView.findViewById(R.id.tv_jum);
            mSab = (TextView) itemView.findViewById(R.id.tv_sab);
            mRuang = (TextView) itemView.findViewById(R.id.tv_ruang);
            mWaktu = (TextView) itemView.findViewById(R.id.tv_waktu);
            cardViewWaktu = (CardView) itemView.findViewById(R.id.cv_waktu);
        }
    }
}
