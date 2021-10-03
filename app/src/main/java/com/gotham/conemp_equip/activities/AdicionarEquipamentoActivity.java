package com.gotham.conemp_equip.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.gotham.conemp_equip.R;
import com.gotham.conemp_equip.helper.EquipamentoDAO;
import com.gotham.conemp_equip.model.Equipamento;

public class AdicionarEquipamentoActivity extends AppCompatActivity {

    //Atributos da GUI
    private TextInputEditText editNome;
    private TextInputEditText editMarca;

    private Equipamento equipAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_equipamento);
        getSupportActionBar().setTitle(R.string.titulo_activity_adicionar_equip);

        editNome = findViewById(R.id.textInputNomeEquip);
        editMarca = findViewById(R.id.textInputMarcaEquip);

        equipAtual = (Equipamento) getIntent().getSerializableExtra(
                String.valueOf(R.string.intent_nome_equip_selecionado)
        );

        //Verifica se há objeto no intent, caso afirmativo configura tela de edição
        if(equipAtual != null) {

            getSupportActionBar().setTitle(R.string.titulo_activity_atualizar_equip);

            editNome.setText(equipAtual.getNomeEquip());
            editMarca.setText(equipAtual.getMarca());
        }
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
                EquipamentoDAO dao = new EquipamentoDAO(getApplicationContext());

                String nomeEquip = editNome.getText().toString();
                String marcaEquip = editMarca.getText().toString();

                // Verifica se os campos estão vazios
                if(!nomeEquip.isEmpty() && !marcaEquip.isEmpty()) {

                    // Cria uma classe do modelo
                    Equipamento equip = new Equipamento();

                    if(equipAtual != null) { // Método para editar

                        equip.setId(equipAtual.getId());
                        equip.setNomeEquip(nomeEquip);
                        equip.setMarca(marcaEquip);

                        if(dao.atualizar(equip)) {
                            // Finaliza a atividade
                            finish();

                            Toast.makeText(AdicionarEquipamentoActivity.this,
                                    R.string.toast_sucesso_atualizacao_equip, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AdicionarEquipamentoActivity.this,
                                    R.string.toast_erro_atualizacao_equip, Toast.LENGTH_SHORT).show();
                        }

                    } else { // Método para inserir

                        // Popula os atributos com os valores das caixas de texto
                        equip.setNomeEquip(nomeEquip);
                        equip.setMarca(marcaEquip);

                        if(dao.inserir(equip)) {
                            // Finaliza a atividade
                            finish();

                            Toast.makeText(AdicionarEquipamentoActivity.this,
                                    R.string.toast_sucesso_insercao_equip, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AdicionarEquipamentoActivity.this,
                                    R.string.toast_erro_insercao_equip, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
