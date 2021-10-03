package com.gotham.conemp_equip.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.gotham.conemp_equip.model.Emprestimo;
import com.gotham.conemp_equip.model.Equipamento;

import java.util.ArrayList;
import java.util.List;

public class EmprestimoDAO implements IEmprestimoDAO{

    private SQLiteDatabase salva;
    private SQLiteDatabase recupera;

    public EmprestimoDAO(Context context) {

        DbHelper db = new DbHelper(context);

        salva = db.getWritableDatabase();
        recupera = db.getReadableDatabase();
    }

    @Override
    public boolean inserir(Emprestimo emp) {

        ContentValues valoresParaInserir = new ContentValues();
        valoresParaInserir.put(DbHelper.getClEmprestimosIdEquipFk(), emp.getEquipamento().getId());
        valoresParaInserir.put(DbHelper.getClEmprestimosNomePessoa(), emp.getNomePessoa());
        valoresParaInserir.put(DbHelper.getClEmprestimosTelefone(), emp.getTelefone());
        valoresParaInserir.put(DbHelper.getClEmprestimosData(), emp.getData());
        valoresParaInserir.put(DbHelper.getClEmprestimosDevolvido(), emp.isDevolvido());

        try {
            salva.insert(DbHelper.getTbEmprestimos(), null, valoresParaInserir);
            Log.i("DATA", "Emprestimo registrado com sucesso");
        } catch(Exception e) {
            Log.e("ERROR", "Erro ao salvar o empréstimo " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean atualizar(Emprestimo emp) {

        ContentValues valoresParaInserir = new ContentValues();
        valoresParaInserir.put(DbHelper.getClEmprestimosIdEquipFk(), emp.getEquipamento().getId());
        valoresParaInserir.put(DbHelper.getClEmprestimosNomePessoa(), emp.getNomePessoa());
        valoresParaInserir.put(DbHelper.getClEmprestimosTelefone(), emp.getTelefone());
        valoresParaInserir.put(DbHelper.getClEmprestimosData(), emp.getData());
        valoresParaInserir.put(DbHelper.getClEmprestimosDevolvido(), emp.isDevolvido());

        try {
            String[] argumentoWhereSQL = {emp.getId().toString()};
            salva.update(DbHelper.getTbEmprestimos(), valoresParaInserir, "id=?", argumentoWhereSQL);
            Log.i("DATA", "Empréstimo atualizado com sucesso");
        } catch(Exception e) {
            Log.e("ERROR", "Erro ao atualizar o empréstimo " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean apagar(Emprestimo emp) {
        return false;
    }

    @Override
    public List<Emprestimo> listarTodos() {

        List<Emprestimo> lista = new ArrayList<>();

        String consulta =
                String.format("SELECT * FROM %s a JOIN %s b ON a.%s = b.%s",
                        DbHelper.getTbEquipamentos(),
                        DbHelper.getTbEmprestimos(),
                        DbHelper.getClEquipamentosId(),
                        DbHelper.getClEmprestimosIdEquipFk());

        Cursor cursor = recupera.rawQuery(consulta, null);

        while(cursor.moveToNext()) {

            // Cursor recupera os valores da tabela devolvida na Query

            Integer id = cursor.getInt(cursor.getColumnIndex(DbHelper.getClEmprestimosId()));
            String nomePessoa = cursor.getString(cursor.getColumnIndex(DbHelper.getClEmprestimosNomePessoa()));
            String telefone = cursor.getString(cursor.getColumnIndex(DbHelper.getClEmprestimosTelefone()));
            String data = cursor.getString(cursor.getColumnIndex(DbHelper.getClEmprestimosData()));
            boolean devolvido = cursor.getInt(cursor.getColumnIndex(DbHelper.getClEmprestimosDevolvido())) > 0;
            Equipamento equip = new Equipamento(
                    cursor.getInt(cursor.getColumnIndex(DbHelper.getClEmprestimosIdEquipFk())),
                    cursor.getString(cursor.getColumnIndex(DbHelper.getClEquipamentosNome())),
                    cursor.getString(cursor.getColumnIndex(DbHelper.getClEquipamentosMarca()))
            );

            Emprestimo emp = new Emprestimo(id, nomePessoa, telefone, data, devolvido, equip);

            lista.add(emp);
        }

        return lista;
    }
}
