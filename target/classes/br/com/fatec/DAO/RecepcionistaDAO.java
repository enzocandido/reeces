package br.com.fatec.DAO;

import br.com.fatec.model.Recepcionista;
import br.com.fatec.banco.Banco;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class RecepcionistaDAO implements DAO<Recepcionista> {
    private Recepcionista recepcionista;
    private PreparedStatement pst;
    private ResultSet rs;

    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    @Override
    public boolean insere(Recepcionista dado) throws SQLException {
        boolean inseriu;

        Banco.conectar();

        String sql = "INSERT INTO recepcionistas (nome, email, sexo, telefone, salario) VALUES (?, ?, ?, ?, ?)";

        pst = Banco.obterConexao().prepareStatement(sql);

        pst.setString(1, dado.getNome());
        pst.setString(2, dado.getEmail());
        pst.setString(3, dado.getSexo());
        pst.setString(4, dado.getTelefone());
        pst.setDouble(5, dado.getSalario());

        if (pst.executeUpdate() > 0)
            inseriu = true;
        else
            inseriu = false;

        Banco.desconectar();

        return inseriu;
    }

    @Override
    public boolean remove(Recepcionista dado) throws SQLException {
        boolean removeu;

        Banco.conectar();

        String sql = "DELETE FROM recepcionistas WHERE id = ?";

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
    public boolean altera(Recepcionista dado) throws SQLException {
        boolean alterou;

        Banco.conectar();

        String sql = "UPDATE recepcionistas SET nome = ?, email = ?, sexo = ?, telefone = ?, salario = ? WHERE id = ?";

        pst = Banco.obterConexao().prepareStatement(sql);

        pst.setString(1, dado.getNome());
        pst.setString(2, dado.getEmail());
        pst.setString(3, dado.getSexo());
        pst.setString(4, dado.getTelefone());
        pst.setDouble(5, dado.getSalario());
        pst.setInt(6, dado.getId());

        if (pst.executeUpdate() > 0)
            alterou = true;
        else
            alterou = false;

        Banco.desconectar();

        return alterou;
    }

    @Override
    public Recepcionista buscaID(Recepcionista dado) throws SQLException {
        return null;
    }

    public Recepcionista pesquisa(String filtro) throws SQLException {
        Recepcionista recepcionista = null;

        String sql = "SELECT * FROM recepcionistas ";

        if (filtro.length() != 0) {
            sql += "WHERE " + filtro;
        }

        Banco.conectar();

        pst = Banco.obterConexao().prepareStatement(sql);

        rs = pst.executeQuery();

        if (rs.next()) {
            recepcionista = new Recepcionista();
            recepcionista.setId(rs.getInt("id"));
            recepcionista.setNome(rs.getString("nome"));
            recepcionista.setEmail(rs.getString("email"));
            recepcionista.setSexo(rs.getString("sexo"));
            recepcionista.setTelefone(rs.getString("telefone"));
            recepcionista.setSalario(rs.getDouble("salario"));
        }

        Banco.desconectar();

        return recepcionista;
    }

    @Override
    public Collection<Recepcionista> lista(String criterio) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}