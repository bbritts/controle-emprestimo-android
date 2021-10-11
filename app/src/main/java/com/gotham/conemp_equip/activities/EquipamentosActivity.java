package com.gotham.conemp_equip.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gotham.conemp_equip.R;
import com.gotham.conemp_equip.adapters.EquipamentosAdapter;
import com.gotham.conemp_equip.persistence.EquipamentoDAO;
import com.gotham.conemp_equip.util.RecyclerItemClickListener;
import com.gotham.conemp_equip.model.Equipamento;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EquipamentosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EquipamentosAdapter equipAdapter;
    private List<Equipamento> listaEquip = new ArrayList<>();
    private Equipamento equipSelecionado;

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

                        intent.putExtra(String.valueOf(R.string.intent_nome_equip_selecionado), equipSelecionado);

                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                        equipSelecionado = listaEquip.get(position);

                        AlertDialog.Builder janelaConfirmacao = new AlertDialog.Builder(EquipamentosActivity.this);
                        janelaConfirmacao.setTitle(R.string.dialog_titulo_exclusao);
                        janelaConfirmacao.setMessage(R.string.dialog_mensagem_exclusao_equip);

                        janelaConfirmacao.setPositiveButton(R.string.dialog_botao_positivo,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        EquipamentoDAO dao = new EquipamentoDAO(getApplicationContext());

                                        if (dao.apagar(equipSelecionado)) {

                                            carregaEquipamentos();

                                            Toast.makeText(EquipamentosActivity.this,
                                                    R.string.toast_sucesso_exclusao_equip, Toast.LENGTH_SHORT).show();

                                        } else {

                                            AlertDialog.Builder janelaErro = new AlertDialog.Builder(EquipamentosActivity.this);
                                            janelaErro.setTitle(R.string.dialog_titulo_erro_exclusao_equip);
                                            janelaErro.setMessage(R.string.dialog_mensagem_erro_exclusao_equip);

                                            janelaErro.setNeutralButton(R.string.dialog_botao_neutro, null);

                                            janelaErro.create();
                                            janelaErro.show();

                                            Toast.makeText(EquipamentosActivity.this,
                                                    R.string.toast_erro_exclusao_equip, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                        janelaConfirmacao.setNegativeButton(R.string.dialog_botao_negativo, null);

                        janelaConfirmacao.create();
                        janelaConfirmacao.show();
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
