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
import com.gotham.conemp_equip.persistence.EmprestimoDAO;
import com.gotham.conemp_equip.persistence.EquipamentoDAO;
import com.gotham.conemp_equip.model.Emprestimo;
import com.gotham.conemp_equip.model.Equipamento;
import com.gotham.conemp_equip.util.MaskEditUtil;

import java.util.List;

public class AdicionarEmprestimoActivity extends AppCompatActivity {

    // Atributos da GUI

    private TextInputEditText editNomePessoa;
    private EditText editTelefone;
    private EditText editData;
    private Spinner spinnerEquip;
    private Switch switchDevolvido;

    private List<Equipamento> equipamentos;
    private Equipamento equip;
    private Emprestimo emprestimoAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_emprestimo);
        getSupportActionBar().setTitle(R.string.titulo_activity_adicionar_emp);

        editNomePessoa = findViewById(R.id.textInputNomePessoa);

        // Atribui o id o editText do frontend e adiciona máscara
        editTelefone = findViewById(R.id.textInputTelefone);
        editTelefone.addTextChangedListener(MaskEditUtil.mask(editTelefone, MaskEditUtil.FORMAT_FONE));

        editData = findViewById(R.id.textInputData);
        editData.addTextChangedListener(MaskEditUtil.mask(editData, MaskEditUtil.FORMAT_DATE));


        spinnerEquip = findViewById(R.id.spinnerEquip);
        switchDevolvido = findViewById(R.id.switchDevolvido);

        // Instancia um DAO
        EquipamentoDAO equipDao = new EquipamentoDAO(getApplicationContext());

        //Busca os equipamentos cadastrados que estão disponíveis
        equipamentos = equipDao.listarDisponiveis();

        // Cria um Adapter para o Spinner
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, equipamentos);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spinnerEquip.setAdapter(adapter);

        // Recupera o objeto empréstimo do Intent
        emprestimoAtual = (Emprestimo) getIntent().getSerializableExtra(
                String.valueOf(R.string.intent_nome_emp_selecionado)
        );

        // Verifica se há objeto enviado pelo Intent para saber se é pra edição
        if (emprestimoAtual != null) {

            getSupportActionBar().setTitle(R.string.titulo_activity_atualizar_emp);
            switchDevolvido.setClickable(true);

            editNomePessoa.setText(emprestimoAtual.getNomePessoa());
            editTelefone.setText(emprestimoAtual.getTelefone());
            editData.setText(emprestimoAtual.getData());

            // Resolve o problema do equipamento emprestado não aparecer no spinner por estar indisponível
            if (!emprestimoAtual.isDevolvido()) {
                adapter.insert(emprestimoAtual.getEquipamento(), 0);
            }

            // Recupera o nome do equipamento emprestado e descobre sua posição no Spinner
            String equipEmprestado = emprestimoAtual.getEquipamento().getNomeEquip();
            Integer posicaoEquipEmprestado = recuperaPosicao(spinnerEquip, equipEmprestado);

            // Seleciona o equipamento que veio do banco de dados
            if (equipEmprestado != null) {
                spinnerEquip.setSelection(posicaoEquipEmprestado);
            }

            switchDevolvido.setChecked(emprestimoAtual.isDevolvido());
        }

        //Listener para ficar escutando se algum item foi selecionado no Spinner

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
            case R.id.salvar:

                // Cria classe DAO para manipular o BD
                EmprestimoDAO dao = new EmprestimoDAO(getApplicationContext());

                String nomePessoa = editNomePessoa.getText().toString();
                String telefone = editTelefone.getText().toString();
                String data = editData.getText().toString();

                if (!nomePessoa.isEmpty() && !telefone.isEmpty() && !data.isEmpty()) {

                    // Cria uma classe do modelo
                    Emprestimo emp = new Emprestimo();

                    if (emprestimoAtual != null) { //Método para editar

                        emp.setId(emprestimoAtual.getId());
                        emp.setNomePessoa(nomePessoa);
                        emp.setTelefone(telefone);
                        emp.setData(data);
                        emp.setEquipamento(equip);
                        emp.setDevolvido(switchDevolvido.isChecked());

                        if(dao.atualizar(emp)) {
                            //Finaliza a atividade
                            finish();

                            Toast.makeText(AdicionarEmprestimoActivity.this,
                                    R.string.toast_sucesso_atualizacao_emp, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AdicionarEmprestimoActivity.this,
                                    R.string.toast_erro_atualizacao_emp, Toast.LENGTH_SHORT).show();
                        }

                    } else { //Método para inserir

                        // Popula os atributos com os valores das caixas de texto
                        emp.setNomePessoa(nomePessoa);
                        emp.setTelefone(telefone);
                        emp.setData(data);
                        emp.setEquipamento(equip);
                        emp.setDevolvido(switchDevolvido.isChecked());

                        if(dao.inserir(emp)) {

                            //Finaliza a atividade
                            finish();

                            Toast.makeText(AdicionarEmprestimoActivity.this,
                                    R.string.toast_sucesso_insercao_emp, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AdicionarEmprestimoActivity.this,
                                    R.string.toast_erro_insercao_emp, Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private Integer recuperaPosicao(Spinner spinner, String valor) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(valor)) {
                return i;
            }
        }

        return 0;
    }
}
