package com.developer.amien.spbu;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.amien.spbu.data.rest.ApiClient;
import com.developer.amien.spbu.data.rest.ApiInterface;
import com.developer.amien.spbu.data.retrofit.Fasilitas;
import com.developer.amien.spbu.data.retrofit.Fasilitas_ken;
import com.developer.amien.spbu.data.retrofit.GetFasilitas;
import com.developer.amien.spbu.data.retrofit.GetFasilitas_ken;
import com.developer.amien.spbu.data.retrofit.GetUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class fasilitas_tambah_adapter extends RecyclerView.Adapter<fasilitas_tambah_adapter.ViewHolder> {
    private int selectedPosition = 0;// no selection by default
    private List<Fasilitas_ken> items;
    private List<Fasilitas> F_items;
    Context a;
    String id_spbu;
    SharedPreferences pref ;

    public fasilitas_tambah_adapter(List<Fasilitas> F_items,List<Fasilitas_ken> items, Context a, String id_spbu) {
        this.F_items = F_items;
        this.items = items;
        this.a = a;
        this.id_spbu = id_spbu;
        this.pref =  a.getSharedPreferences("spbu", 0); // 0 - for private mode
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tambah_fasilitas_recycle,viewGroup,false);
        return new fasilitas_tambah_adapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        final Fasilitas model = F_items.get(i);

        final String Nama_fasilitas = model.getNama();
        viewHolder.chkfasilitas.setText(Nama_fasilitas);
        viewHolder.chkfasilitas.setTag(model.getId_fasilitas());
//        viewHolder.chkfasilitas.setChecked(model.isSelected());
//        Toast.makeText(a, "ken"+items.size(), Toast.LENGTH_SHORT).show();
        for (int f=0; f<items.size();f++){
//            Toast.makeText(a, "index "+i+":"+f+" ="+(model.getId_fasilitas().equalsIgnoreCase(items.get(f).getId_fasilitas())) , Toast.LENGTH_SHORT).show();
            if(model.getId_fasilitas().equalsIgnoreCase(items.get(f).getIdFasilitas()) ){
//                Toast.makeText(a, "index "+i+":"+f+" ="+(model.getId_fasilitas().equalsIgnoreCase(items.get(f).getId_fasilitas())) , Toast.LENGTH_SHORT).show();
                viewHolder.chkfasilitas.setChecked(true);
                continue;
            }

        }
        viewHolder.chkfasilitas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(a, "Position "+buttonView.getTag(), Toast.LENGTH_SHORT).show();
                Toast.makeText(a, "Position 2"+id_spbu, Toast.LENGTH_SHORT).show();
//            Toast.makeText(a, ""+buttonView.getText()+""+buttonView.getTag().toString(), Toast.LENGTH_SHORT).show();
                if(buttonView.isChecked()){
                    insert(buttonView.getTag().toString(),id_spbu,pref.getString("id_user",null));
                }else{
                    delete(buttonView.getTag().toString(),id_spbu);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return F_items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox chkfasilitas;
        public ViewHolder(View itemView) {
            super(itemView);
            this.setIsRecyclable(false);
            chkfasilitas = (CheckBox) itemView.findViewById(R.id.chkfasilitas);
        }
    }

    public void insert(String id_fasilitas, String id_spbu, String id_user){
        ApiInterface mApiInterface = ApiClient.GetFasilitas_ken().create(ApiInterface.class);
        final Call<GetFasilitas_ken> fasilitas_kenCall = mApiInterface.Fasilitasken(id_fasilitas,id_spbu,id_user, "POST");
        fasilitas_kenCall.enqueue(new Callback<GetFasilitas_ken>() {
            @Override
            public void onResponse(Call<GetFasilitas_ken> call, Response<GetFasilitas_ken> response) {
                Toast.makeText(a, "Data checked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<GetFasilitas_ken> call, Throwable throwable) {

            }
        });
    }
    public void delete(String id_fasilitas, String id_spbu){
        ApiInterface mApiInterface = ApiClient.GetFasilitas_ken().create(ApiInterface.class);
        final Call<GetFasilitas_ken> fasilitas_kenCall = mApiInterface.Fasilitasken_delete(id_fasilitas,id_spbu, "DELETE");
        fasilitas_kenCall.enqueue(new Callback<GetFasilitas_ken>() {
            @Override
            public void onResponse(Call<GetFasilitas_ken> call, Response<GetFasilitas_ken> response) {
                Toast.makeText(a, "Data unchecked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<GetFasilitas_ken> call, Throwable throwable) {

            }
        });
    }
}
