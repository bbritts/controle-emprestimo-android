package com.gotham.conemp_equip.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final String NOME_BD = "EmpresaX";
    private static final int VERSAO_BD = 1;

    // Tabelas
    private static final String TB_EQUIPAMENTOS = "equipamentos";
    private static final String TB_EMPRESTIMOS = "emprestimos";

    // Colunas Equipamentos
    private static final String CL_EQUIPAMENTOS_ID = "id";
    private static final String CL_EQUIPAMENTOS_NOME = "nome";
    private static final String CL_EQUIPAMENTOS_MARCA = "marca";

    // Colunas Emprestimos
    private static final String CL_EMPRESTIMOS_ID = "id";
    private static final String CL_EMPRESTIMOS_ID_EQUIP_FK = "id_equip";
    private static final String CL_EMPRESTIMOS_NOME_PESSOA = "nome_pessoa";
    private static final String CL_EMPRESTIMOS_TELEFONE = "telefone";
    private static final String CL_EMPRESTIMOS_DATA = "data";
    private static final String CL_EMPRESTIMOS_DEVOLVIDO = "devolvido";

    public DbHelper(@Nullable Context context) {
        super(context, NOME_BD, null, VERSAO_BD);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql_equip = "CREATE TABLE IF NOT EXISTS " + TB_EQUIPAMENTOS
                            + " ( " + CL_EQUIPAMENTOS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + CL_EQUIPAMENTOS_NOME + " TEXT NOT NULL, "
                            + CL_EQUIPAMENTOS_MARCA + " TEXT NOT NULL); ";

        String sql_emp = "CREATE TABLE IF NOT EXISTS " + TB_EMPRESTIMOS
                            + " ( "
                                + CL_EMPRESTIMOS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                + CL_EMPRESTIMOS_ID_EQUIP_FK + " INTEGER REFERENCES " + TB_EQUIPAMENTOS + ", "
                                + CL_EMPRESTIMOS_NOME_PESSOA + " TEXT NOT NULL, "
                                + CL_EMPRESTIMOS_TELEFONE + " TEXT NOT NULL, "
                                + CL_EMPRESTIMOS_DATA + " TEXT NOT NULL, "
                                + CL_EMPRESTIMOS_DEVOLVIDO + " INTEGER(1)"
                            + ");";

        try {
            db.execSQL(sql_equip);
            db.execSQL(sql_emp);

            Log.i("INFO DB", "A criação das tabelas foi bem sucedida");
        } catch (Exception e) {
            Log.i("INFO DB", "Erro ao criar as tabelas" + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TB_EQUIPAMENTOS);
            db.execSQL("DROP TABLE IF EXISTS " + TB_EMPRESTIMOS);
            onCreate(db);
        }
    }


    //Métodos getter para as constantes

    public static String getNomeBd() {
        return NOME_BD;
    }

    public static int getVersaoBd() {
        return VERSAO_BD;
    }

    public static String getTbEquipamentos() {
        return TB_EQUIPAMENTOS;
    }

    public static String getTbEmprestimos() {
        return TB_EMPRESTIMOS;
    }

    public static String getClEquipamentosId() {
        return CL_EQUIPAMENTOS_ID;
    }

    public static String getClEquipamentosNome() {
        return CL_EQUIPAMENTOS_NOME;
    }

    public static String getClEquipamentosMarca() {
        return CL_EQUIPAMENTOS_MARCA;
    }

    public static String getClEmprestimosId() {
        return CL_EMPRESTIMOS_ID;
    }

    public static String getClEmprestimosIdEquipFk() {
        return CL_EMPRESTIMOS_ID_EQUIP_FK;
    }

    public static String getClEmprestimosNomePessoa() {
        return CL_EMPRESTIMOS_NOME_PESSOA;
    }

    public static String getClEmprestimosTelefone() {
        return CL_EMPRESTIMOS_TELEFONE;
    }

    public static String getClEmprestimosData() {
        return CL_EMPRESTIMOS_DATA;
    }

    public static String getClEmprestimosDevolvido() {
        return CL_EMPRESTIMOS_DEVOLVIDO;
    }
}
