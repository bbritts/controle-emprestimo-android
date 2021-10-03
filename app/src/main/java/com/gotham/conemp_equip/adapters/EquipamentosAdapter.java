package com.gotham.conemp_equip.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gotham.conemp_equip.R;
import com.gotham.conemp_equip.model.Equipamento;

import java.util.List;

public class EquipamentosAdapter extends RecyclerView.Adapter<EquipamentosAdapter.MyViewHolder> {

    // Atributos
    private List<Equipamento> lista;

    // Construtor
    public EquipamentosAdapter(List<Equipamento> lista) {

        this.lista = lista;
    }

    // Métodos implementados da RecyclerView.Adapter
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemCadastrado = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_adapter, parent, false);

        return new MyViewHolder(itemCadastrado);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Equipamento equip = lista.get(position);

        holder.equipamento.setText(equip.getId() + ": " + equip.getMarca() + " - "  + equip.getNomeEquip());
    }

    @Override
    public int getItemCount() {
        return this.lista.size();
    }

    //Classe interior responsável pelo ViewHolder
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView equipamento;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            equipamento = itemView.findViewById(R.id.textViewLista);
        }
    }
}
