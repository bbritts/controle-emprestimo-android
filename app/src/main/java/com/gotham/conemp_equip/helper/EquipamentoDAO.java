package com.gotham.conemp_equip.helper;

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
        } catch(Exception e) {
            Log.e("ERROR", "Erro ao salvar a mensagem");
            return false;
        }
        return true;
    }

    @Override
    public boolean atualizar(Equipamento equip) {

        String[] argumentoWhereSQL = {equip.getId().toString()};

        ContentValues valoresParaInserir = new ContentValues();
        valoresParaInserir.put(DbHelper.getClEquipamentosNome(), equip.getNomeEquip());
        valoresParaInserir.put(DbHelper.getClEquipamentosMarca(), equip.getMarca());

        salva.update(DbHelper.getTbEquipamentos(), valoresParaInserir, "id=?", argumentoWhereSQL);

        try {
            Log.i("DATA", "Equipamento atualizado com sucesso");
        } catch(Exception e) {
            Log.e("ERROR", "Erro ao atualizar a mensagem");
            return false;
        }

        return true;
    }

    @Override
    public boolean apagar(Equipamento equip) {
        return false;
    }

    @Override
    public List<Equipamento> listarTodos() {

        List<Equipamento> lista = new ArrayList<>();

        String consulta = String.format("SELECT * FROM %s", DbHelper.getTbEquipamentos());

        Cursor cursor = recupera.rawQuery(consulta, null);

        while(cursor.moveToNext()) {

            Equipamento equip = new Equipamento();

            Integer id = cursor.getInt(cursor.getColumnIndex(DbHelper.getClEquipamentosId()));
            String nomeEquip = cursor.getString(cursor.getColumnIndex(DbHelper.getClEquipamentosNome()));
            String marca = cursor.getString(cursor.getColumnIndex(DbHelper.getClEquipamentosMarca()));

            equip.setId(id);
            equip.setNomeEquip(nomeEquip);
            equip.setMarca(marca);

            lista.add(equip);
        }

        return lista;
    }

    public List<Equipamento> listarDisponiveis() {

        List<Equipamento> lista = new ArrayList<>();

        String consulta = String.format("SELECT * FROM %s a" +
                                        "LEFT JOIN %s b ON a.%s = b.%s" +
                                        "WHERE %s = 1 OR" +
                                        "%s IS NULL",
                                        DbHelper.getTbEquipamentos(),
                                        DbHelper.getTbEmprestimos(),
                                        DbHelper.getClEquipamentosId(),
                                        DbHelper.getClEmprestimosIdEquipFk(),
                                        DbHelper.getClEmprestimosDevolvido());

        Cursor cursor = recupera.rawQuery(consulta, null);

        //TODO

        return lista;
    }
}
