package com.gotham.conemp_equip.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gotham.conemp_equip.R;
import com.gotham.conemp_equip.adapters.EmprestimosAdapter;
import com.gotham.conemp_equip.helper.EmprestimoDAO;
import com.gotham.conemp_equip.helper.RecyclerItemClickListener;
import com.gotham.conemp_equip.model.Emprestimo;

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

import static com.gotham.conemp_equip.R.layout.activity_emprestimos;

public class EmprestimosActivity extends AppCompatActivity {

    // Atributos
    private RecyclerView recyclerView;
    private EmprestimosAdapter empAdapter;
    private List<Emprestimo> listaEmp = new ArrayList<>();
    private Emprestimo empSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_emprestimos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerEmprestimo);

        //Cria um evento de clique com o auxílio da helper.RecyclerItemClickListener
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        //Recuperar o emprestimo selecionado
                        empSelecionado = listaEmp.get(position);

                        //Enviar para a próxima Activity
                        Intent intent = new Intent(EmprestimosActivity.this,
                                                        AdicionarEmprestimoActivity.class);

                        intent.putExtra(String.valueOf(R.string.intent_nome_emp_selecionado), empSelecionado);

                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                        empSelecionado = listaEmp.get(position);

                        AlertDialog.Builder janelaConfirmacao = new AlertDialog.Builder(EmprestimosActivity.this);
                        janelaConfirmacao.setTitle(R.string.dialog_titulo_exclusao);
                        janelaConfirmacao.setMessage(R.string.dialog_mensagem_exclusao_emp);

                        janelaConfirmacao.setPositiveButton(R.string.dialog_botao_positivo, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                EmprestimoDAO dao = new EmprestimoDAO(getApplicationContext());

                                if(dao.apagar(empSelecionado)) {
                                    carregaEmprestimos();

                                    Toast.makeText(EmprestimosActivity.this,
                                            R.string.toast_sucesso_exclusao_emp, Toast.LENGTH_SHORT).show();
                                } else {

                                    Toast.makeText(EmprestimosActivity.this,
                                            R.string.toast_erro_exclusao_emp, Toast.LENGTH_SHORT).show();
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


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(getApplicationContext(), AdicionarEmprestimoActivity.class);
                startActivity(it);
            }
        });
    }

    public void carregaEmprestimos() {

        //Lista os empréstimos

        EmprestimoDAO dao = new EmprestimoDAO(getApplicationContext());
        listaEmp = dao.listarTodos();

        //Exibe lista no RecyclerView

        //Configuração do Adapter
        empAdapter = new EmprestimosAdapter(listaEmp, EmprestimosActivity.this);

        //Configuração do Layout
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(empAdapter);
    }

    @Override
    protected void onStart() {
        carregaEmprestimos();
        super.onStart();
    }
}
