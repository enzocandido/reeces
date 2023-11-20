package br.com.fatec.model;

import java.sql.Timestamp; // Importe para manipulação de data e hora
import java.util.Objects;

public class Agendamento {
    private int id; // Adicionei o campo id, pois parece ser necessário para o DAO
    private String nomeCliente;
    private Timestamp dataHorario; // Alteração do tipo para Timestamp
    private String profissional;
    private String unidade;
    private float valor;
    private int servicoId;

    public Agendamento() {
    }

    public Agendamento(int id, String nomeCliente, Timestamp dataHorario, String profissional, String unidade, float valor, int servicoId) {
        this.id = id;
        this.nomeCliente = nomeCliente;
        this.dataHorario = dataHorario;
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

    public Timestamp getDataHorario() {
        return dataHorario;
    }

    public void setDataHorario(Timestamp dataHorario) {
        this.dataHorario = dataHorario;
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

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
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