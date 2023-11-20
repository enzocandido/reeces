package br.com.fatec.DAO;

import br.com.fatec.model.Clientes;
import br.com.fatec.banco.Banco;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClienteDAO implements DAO<Clientes> {

    private Clientes cliente;
    private PreparedStatement pst;
    private ResultSet rs;

    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    @Override
    public boolean insere(Clientes dado) throws SQLException {
        boolean inseriu;

        Banco.conectar();

        String sql = "INSERT INTO clientes (nome, email, sexo, telefone, data_nascimento, endereco) VALUES (?, ?, ?, ?, ?, ?)";

        pst = Banco.obterConexao().prepareStatement(sql);

        pst.setString(1, dado.getNome());
        pst.setString(2, dado.getEmail());
        pst.setString(3, dado.getSexo());
        pst.setString(4, dado.getTelefone());
        pst.setDate(5, new java.sql.Date(dado.getDataNascimento().getTime()));
        pst.setString(6, dado.getEndereco());


        if (pst.executeUpdate() > 0)
            inseriu = true;
        else
            inseriu = false;

        Banco.desconectar();

        return inseriu;
    }


    @Override
    public boolean remove(Clientes dado) throws SQLException {
        boolean removeu;

        Banco.conectar();

        String sql = "DELETE FROM clientes WHERE id = ?";

        pst = Banco.obterConexao().prepareStatement(sql);

        pst.setInt(1, dado.getId());

        if (pst.executeUpdate() > 0)
            removeu = true;
        else
            removeu = false;

        Banco.desconectar();

        return removeu;
    }

    @Override
    public boolean altera(Clientes dado) throws SQLException {
        boolean alterou;

        Banco.conectar();

        String sql = "UPDATE clientes SET nome = ?, email = ?, sexo = ?, telefone = ?, data_nascimento = ?, endereco = ? WHERE id = ?";

        pst = Banco.obterConexao().prepareStatement(sql);

        pst.setString(1, dado.getNome());
        pst.setString(2, dado.getEmail());
        pst.setString(3, dado.getSexo());
        pst.setString(4, dado.getTelefone());
        pst.setDate(5, new java.sql.Date(dado.getDataNascimento().getTime()));
        pst.setString(6, dado.getEndereco());
        pst.setInt(7, dado.getId());

        if (pst.executeUpdate() > 0)
            alterou = true;
        else
            alterou = false;

        Banco.desconectar();

        return alterou;
    }

    @Override
    public Clientes buscaID(Clientes dado) throws SQLException {
        // Implemente a busca por ID se necessário

        return null;
    }

    @Override
    public Collection<Clientes> lista(String filtro) throws SQLException {
        Collection<Clientes> listagem = new ArrayList<>();

        cliente = null;

        String sql = "SELECT * FROM clientes ";

        if (filtro.length() != 0) {
            sql += "WHERE " + filtro;
        }

        Banco.conectar();

        pst = Banco.obterConexao().prepareStatement(sql);

        rs = pst.executeQuery();

        while (rs.next()) {
            cliente = new Clientes();
            cliente.setId(rs.getInt("id"));
            cliente.setNome(rs.getString("nome"));
            cliente.setEmail(rs.getString("email"));
            cliente.setSexo(rs.getString("sexo"));
            cliente.setTelefone(rs.getString("telefone"));

            java.sql.Date sqlDate = rs.getDate("data_nascimento");
            Date dataNascimento = new Date(sqlDate.getTime());

            cliente.setEndereco(rs.getString("endereco"));

            listagem.add(cliente);
        }

        Banco.desconectar();

        return listagem;
    }

}
