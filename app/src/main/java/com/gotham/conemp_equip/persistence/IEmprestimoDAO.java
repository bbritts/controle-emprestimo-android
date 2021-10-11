package com.gotham.conemp_equip.persistence;

import com.gotham.conemp_equip.model.Emprestimo;

import java.util.List;

public interface IEmprestimoDAO {

    public boolean inserir(Emprestimo emp);
    public boolean atualizar(Emprestimo emp);
    public boolean apagar(Emprestimo emp);
    public List<Emprestimo> listarTodos();
}
