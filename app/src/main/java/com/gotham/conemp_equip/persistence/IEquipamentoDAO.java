package com.gotham.conemp_equip.persistence;

import com.gotham.conemp_equip.model.Equipamento;

import java.util.List;

public interface IEquipamentoDAO {

    public boolean inserir(Equipamento equip);
    public boolean atualizar(Equipamento equip);
    public boolean apagar(Equipamento equip);
    public List<Equipamento> listarTodos();
}
