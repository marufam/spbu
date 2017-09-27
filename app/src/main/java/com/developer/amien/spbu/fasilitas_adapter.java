package com.developer.amien.spbu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.developer.amien.spbu.data.retrofit.Fasilitas_ken;

import java.util.List;


public class fasilitas_adapter extends RecyclerView.Adapter<fasilitas_adapter.ViewHolder> {

    private List<Fasilitas_ken> items;
    Context a;

    public fasilitas_adapter(List<Fasilitas_ken> items, Context a) {
        this.items = items;
        this.a = a;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fasilitas_recycle,viewGroup,false);
        return new fasilitas_adapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final Fasilitas_ken model = items.get(i);
        final String Nama_fasilitas = model.getNamaFasilitas();
        viewHolder.nama_fasilitas.setText(Nama_fasilitas);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama_fasilitas;
        public ViewHolder(View itemView) {
            super(itemView);
            nama_fasilitas = (TextView) itemView.findViewById(R.id.lsfasilitas);
        }
    }
}
