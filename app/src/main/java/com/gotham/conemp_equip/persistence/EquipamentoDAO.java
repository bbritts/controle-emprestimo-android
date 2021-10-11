package com.gotham.conemp_equip.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.gotham.conemp_equip.model.Equipamento;

import java.util.ArrayList;
import java.util.List;

public class EquipamentoDAO implements IEquipamentoDAO {

    private SQLiteDatabase salva;
    private SQLiteDatabase recupera;

    public EquipamentoDAO(Context context) {
        DbHelper db = new DbHelper(context);

        salva = db.getWritableDatabase();
        recupera = db.getReadableDatabase();
    }

    @Override
    public boolean inserir(Equipamento equip) {

        ContentValues valoresParaInserir = new ContentValues();
        valoresParaInserir.put(DbHelper.getClEquipamentosNome(), equip.getNomeEquip());
        valoresParaInserir.put(DbHelper.getClEquipamentosMarca(), equip.getMarca());

        try {
            salva.insert(DbHelper.getTbEquipamentos(), null, valoresParaInserir);
            Log.i("DATA", "Equipamento salvo com sucesso");
        } catch (Exception e) {
            Log.e("ERROR", "Erro ao salvar o equipamento mensagem" + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean atualizar(Equipamento equip) {

        ContentValues valoresParaInserir = new ContentValues();
        valoresParaInserir.put(DbHelper.getClEquipamentosNome(), equip.getNomeEquip());
        valoresParaInserir.put(DbHelper.getClEquipamentosMarca(), equip.getMarca());

        try {
            String[] argumentoWhereSQL = {equip.getId().toString()};
            salva.update(DbHelper.getTbEquipamentos(), valoresParaInserir, "id=?", argumentoWhereSQL);
            Log.i("DATA", "Equipamento atualizado com sucesso");
        } catch (Exception e) {
            Log.e("ERROR", "Erro ao atualizar o equipamento" + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean apagar(Equipamento equip) {

        List<Equipamento> equipEmprestados = listarEmprestados();

        for (Equipamento item: equipEmprestados) {

            if (equip.getId() == item.getId()) {
                return false;
            }
        }

        try {
            String[] argumentoWhereSQL = {equip.getId().toString()};
            salva.delete(DbHelper.getTbEquipamentos(), "id = ?", argumentoWhereSQL);
            Log.i("DATA", "Equipamento apagado com sucesso");
        } catch (Exception e) {
            Log.e("ERROR", "Erro ao apagar o equipamento " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public List<Equipamento> listarTodos() {

        List<Equipamento> lista = new ArrayList<>();

        String consulta = String.format("SELECT * FROM %s", DbHelper.getTbEquipamentos());

        Cursor cursor = recupera.rawQuery(consulta, null);

        preencheListaEquip(lista, cursor);

        return lista;
    }

    public List<Equipamento> listarDisponiveis() {

        List<Equipamento> lista = new ArrayList<>();

        String consulta = String.format("SELECT * FROM %s " +
                        "WHERE %s NOT IN " +
                        "(SELECT %s FROM %s " +
                        "WHERE %s = 0)",
                DbHelper.getTbEquipamentos(),
                DbHelper.getClEquipamentosId(),
                DbHelper.getClEmprestimosIdEquipFk(),
                DbHelper.getTbEmprestimos(),
                DbHelper.getClEmprestimosDevolvido());


        Cursor cursor = recupera.rawQuery(consulta, null);

        preencheListaEquip(lista, cursor);

        return lista;
    }

    private List<Equipamento> listarEmprestados() {

        List<Equipamento> lista = new ArrayList<>();

        String consulta = String.format("SELECT * FROM %s " +
                        "WHERE %s IN " +
                        "(SELECT %s FROM %s " +
                        "WHERE %s = 0)",
                        DbHelper.getTbEquipamentos(),
                        DbHelper.getClEquipamentosId(),
                        DbHelper.getClEmprestimosIdEquipFk(),
                        DbHelper.getTbEmprestimos(),
                        DbHelper.getClEmprestimosDevolvido());

        Cursor cursor = recupera.rawQuery(consulta, null);

        preencheListaEquip(lista, cursor);

        return lista;
    }

    private void preencheListaEquip(List<Equipamento> lista, Cursor cursor) {
        while (cursor.moveToNext()) {

            Equipamento equip = new Equipamento();

            Integer id = cursor.getInt(cursor.getColumnIndex(DbHelper.getClEquipamentosId()));
            String nomeEquip = cursor.getString(cursor.getColumnIndex(DbHelper.getClEquipamentosNome()));
            String marca = cursor.getString(cursor.getColumnIndex(DbHelper.getClEquipamentosMarca()));

            equip.setId(id);
            equip.setNomeEquip(nomeEquip);
            equip.setMarca(marca);

            lista.add(equip);
        }
    }
}
