package com.gotham.conemp_equip.model;

import java.io.Serializable;

public class Emprestimo implements Serializable {

    // Atributos

    private Integer id;
    private String nomePessoa;
    private String telefone;
    private String data;
    private boolean devolvido;
    private Equipamento equipamento;

    //Construtor padrão

    public Emprestimo() {
    }

    //Construtor com parâmetros


    public Emprestimo(Integer id, String nomePessoa, String telefone,
                      String data, boolean devolvido, Equipamento equipamento) {
        setId(id);
        setNomePessoa(nomePessoa);
        setTelefone(telefone);
        setData(data);
        setDevolvido(devolvido);
        setEquipamento(equipamento);
    }

    //Métodos getter e setter

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomePessoa() {
        return nomePessoa;
    }

    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa = nomePessoa;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isDevolvido() {
        return devolvido;
    }

    public void setDevolvido(boolean devolvido) {
        this.devolvido = devolvido;
    }

    public Equipamento getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
    }
}
