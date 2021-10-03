package com.gotham.conemp_equip.model;

import java.io.Serializable;

public class Equipamento implements Serializable {

    // Atributos

    private Integer id;
    private String nomeEquip;
    private String marca;

    //Construtor

    public Equipamento() {
    }

    public Equipamento(Integer id, String nomeEquip, String marca) {
        setId(id);
        setNomeEquip(nomeEquip);
        setMarca(marca);
    }

    //Métodos getter e setter

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeEquip() {
        return nomeEquip;
    }

    public void setNomeEquip(String nomeEquip) {
        this.nomeEquip = nomeEquip;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    @Override
    public String toString() {

        return nomeEquip;
    }
}
