package com.gotham.conemp_equip.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.gotham.conemp_equip.R;
import com.gotham.conemp_equip.helper.EmprestimoDAO;
import com.gotham.conemp_equip.helper.EquipamentoDAO;
import com.gotham.conemp_equip.model.Emprestimo;
import com.gotham.conemp_equip.model.Equipamento;

import java.util.List;

public class AdicionarEmprestimoActivity extends AppCompatActivity {

    // Atributos da GUI

    TextInputEditText editNomePessoa;
    EditText editTelefone;
    EditText editData;
    Spinner spinnerEquip;
    Switch switchDevolvido;

    List<Equipamento> equipamentos;
    Equipamento equip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_emprestimo);
        getSupportActionBar().setTitle("Adicionar Empréstimo");

        editNomePessoa = findViewById(R.id.textInputNomePessoa);
        editTelefone = findViewById(R.id.textInputTelefone);
        editData = findViewById(R.id.textInputData);
        spinnerEquip = findViewById(R.id.spinnerEquip);
        switchDevolvido = findViewById(R.id.switchDevolvido);

        EquipamentoDAO equipDao = new EquipamentoDAO(getApplicationContext());

        //Busca os equipamentos cadastrados TODO disponíveis
        equipamentos = equipDao.listarTodos();

        //Spinner Adapter
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, equipamentos);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spinnerEquip.setAdapter(adapter);

        AdapterView.OnItemSelectedListener escolha = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                equip = (Equipamento) spinnerEquip.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        spinnerEquip.setOnItemSelectedListener(escolha);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adicionar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.salvar :

                // Cria classe DAO para manipular o BD
                EmprestimoDAO dao = new EmprestimoDAO(getApplicationContext());

                String nomePessoa = editNomePessoa.getText().toString();
                String telefone = editTelefone.getText().toString();
                String data = editData.getText().toString();

                if (!nomePessoa.isEmpty() && !telefone.isEmpty() && !data.isEmpty()) {

                    // Cria uma classe do modelo
                    Emprestimo emp = new Emprestimo();

                    // Popula os atributos com os valores das caixas de texto
                    emp.setNomePessoa(nomePessoa);
                    emp.setTelefone(telefone);
                    emp.setData(data);
                    emp.setEquipamento(equip);
                    emp.setDevolvido(switchDevolvido.isChecked());

                    dao.inserir(emp);

                    //Finaliza a atividade
                    finish();

                    Toast.makeText(AdicionarEmprestimoActivity.this, "Item Salvo", Toast.LENGTH_SHORT).show();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
