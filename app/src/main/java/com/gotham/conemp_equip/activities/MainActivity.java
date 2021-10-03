package com.gotham.conemp_equip.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gotham.conemp_equip.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnGerenciarEquipamento = findViewById(R.id.botaoGerEquip);
        Button btnGerenciarEmprestimo = findViewById(R.id.botaoGerEmp);

        btnGerenciarEquipamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), EquipamentosActivity.class);
                startActivity(it);
            }
        });

        btnGerenciarEmprestimo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), EmprestimosActivity.class);
                startActivity(it);
            }
        });
    }
}
