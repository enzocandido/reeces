package br.com.fatec.model;

import java.util.Date;
import java.util.Objects;

public class Agendamento {
    private int id;
    private String nomeCliente;
    private Date data;
    private String profissional;
    private String unidade;
    private double valor;
    private int servicoId;

    public Agendamento() {
    }

    public Agendamento(int id, String nomeCliente, Date data, String profissional, String unidade, double valor,
            int servicoId) {
        this.id = id;
        this.nomeCliente = nomeCliente;
        this.data = data;
        this.profissional = profissional;
        this.unidade = unidade;
        this.valor = valor;
        this.servicoId = servicoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getProfissional() {
        return profissional;
    }

    public void setProfissional(String profissional) {
        this.profissional = profissional;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getServicoId() {
        return servicoId;
    }

    public void setServicoId(int servicoId) {
        this.servicoId = servicoId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Agendamento other = (Agendamento) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}