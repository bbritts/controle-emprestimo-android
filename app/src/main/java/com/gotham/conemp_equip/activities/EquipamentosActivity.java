package com.gotham.conemp_equip.activities;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gotham.conemp_equip.R;
import com.gotham.conemp_equip.adapters.EquipamentosAdapter;
import com.gotham.conemp_equip.helper.DbHelper;
import com.gotham.conemp_equip.helper.EquipamentoDAO;
import com.gotham.conemp_equip.helper.RecyclerItemClickListener;
import com.gotham.conemp_equip.model.Equipamento;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class EquipamentosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EquipamentosAdapter equipAdapter;
    private List<Equipamento> listaEquip = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipamentos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerEquip);

        //Cria um evento de clique com o auxílio da helper.RecyclerItemClickListener
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        //Recuperar o equipamento selecionado
                        Equipamento equipSelecionado = listaEquip.get(position);

                        //Enviar para a próxima Activity
                        Intent intent = new Intent(EquipamentosActivity.this,
                                                        AdicionarEquipamentoActivity.class);

                        intent.putExtra("equipamento selecionado", equipSelecionado);

                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        Log.i("onLongItemClick", "Clique longo");
                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }
        ));

        FloatingActionButton fab = findViewById(R.id.floatbutton_inserir);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(getApplicationContext(), AdicionarEquipamentoActivity.class);
                startActivity(it);
            }
        });
    }

    public void carregaEquipamentos() {

        //Lista os equipamentos

        EquipamentoDAO dao = new EquipamentoDAO(getApplicationContext());

        listaEquip = dao.listarTodos();

        //Exibe lista no RecyclerView

        //Configuração do Adapter
        equipAdapter = new EquipamentosAdapter(listaEquip);

        //Configuração do Layout
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(equipAdapter);
    }

    @Override
    protected void onStart() {
        carregaEquipamentos();
        super.onStart();
    }
}
