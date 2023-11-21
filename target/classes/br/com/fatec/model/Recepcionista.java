package br.com.fatec.model;

import java.util.Objects;

public class Recepcionista {
    private int id;
    private String nome;
    private String email;
    private String sexo;
    private String telefone;
    private double salario;

    public Recepcionista() {
    }

    public Recepcionista(int id, String nome, String email, String sexo, String telefone, double salario) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.sexo = sexo;
        this.telefone = telefone;
        this.salario = salario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
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
        final Recepcionista other = (Recepcionista) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}