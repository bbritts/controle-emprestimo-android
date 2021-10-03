package com.gotham.conemp_equip.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gotham.conemp_equip.R;
import com.gotham.conemp_equip.model.Emprestimo;

import java.util.List;

public class EmprestimosAdapter extends RecyclerView.Adapter<EmprestimosAdapter.MyViewHolder> {

    // Atributos
    private List<Emprestimo> lista;
    private Context context;

    // Construtor
    public EmprestimosAdapter(List<Emprestimo> lista, Context context) {

        this.lista = lista;
        this.context = context;
    }

    // Métodos implementados da RecyclerView.Adapter
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemCadastrado = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.lista_adapter, parent, false);

        return new MyViewHolder(itemCadastrado);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Emprestimo emp = lista.get(position);

        // Converte o valor de devolvido para sim ou não, ao invés de true ou false
        String devolvido = emp.isDevolvido() ? "Sim" : "Não";

        //Define o modo de apresentação no RecyclerView

        String string = context.getString(R.string.holder_rv_emprestimo,
                emp.getId(),
                emp.getNomePessoa(),
                emp.getTelefone(),
                emp.getData(),
                emp.getEquipamento().getNomeEquip(),
                devolvido);

        holder.emprestimo.setText(string);
    }

    @Override
    public int getItemCount() {
        return this.lista.size();
    }

    //Classe interior responsável pelo ViewHolder
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView emprestimo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            emprestimo = itemView.findViewById(R.id.textViewLista);
        }
    }
}
