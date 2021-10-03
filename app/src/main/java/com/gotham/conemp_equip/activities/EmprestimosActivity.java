package com.gotham.conemp_equip.activities;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gotham.conemp_equip.R;
import com.gotham.conemp_equip.adapters.EmprestimosAdapter;
import com.gotham.conemp_equip.helper.EmprestimoDAO;
import com.gotham.conemp_equip.model.Emprestimo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import static com.gotham.conemp_equip.R.layout.activity_emprestimos;

public class EmprestimosActivity extends AppCompatActivity {

    // Atributos
    private RecyclerView recyclerView;
    private EmprestimosAdapter empAdapter;
    private List<Emprestimo> listaEmp = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_emprestimos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerEmprestimo);

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
        empAdapter = new EmprestimosAdapter(listaEmp);

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
