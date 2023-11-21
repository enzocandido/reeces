package br.com.fatec.DAO;

import br.com.fatec.model.Agendamento;
import br.com.fatec.banco.Banco;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class AgendamentoDAO implements DAO<Agendamento> {
    private PreparedStatement pst;
    private ResultSet rs;

    @Override
    public boolean insere(Agendamento dado) throws SQLException {
        boolean inseriu;

        Banco.conectar();

        String sql = "INSERT INTO agendamentos (nome_cliente, data, profissional, unidade, valor, servico_id) VALUES (?, ?, ?, ?, ?, ?)";

        pst = Banco.obterConexao().prepareStatement(sql);

        pst.setString(1, dado.getNomeCliente());
        pst.setDate(2, new java.sql.Date(dado.getData().getTime()));
        pst.setString(3, dado.getProfissional());
        pst.setString(4, dado.getUnidade());
        pst.setDouble(5, dado.getValor());
        pst.setInt(6, dado.getServicoId());

        if (pst.executeUpdate() > 0)
            inseriu = true;
        else
            inseriu = false;

        Banco.desconectar();

        return inseriu;
    }

    @Override
    public boolean remove(Agendamento dado) throws SQLException {
        boolean removeu;

        Banco.conectar();

        String sql = "DELETE FROM agendamentos WHERE id = ?";

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
    public boolean altera(Agendamento dado) throws SQLException {
        boolean alterou;

        Banco.conectar();

        String sql = "UPDATE agendamentos SET nome_cliente = ?, data = ?, profissional = ?, unidade = ?, valor = ?, servico_id = ? WHERE id = ?";

        pst = Banco.obterConexao().prepareStatement(sql);

        pst.setString(1, dado.getNomeCliente());
        pst.setDate(2, new java.sql.Date(dado.getData().getTime()));
        pst.setString(3, dado.getProfissional());
        pst.setString(4, dado.getUnidade());
        pst.setDouble(5, dado.getValor());
        pst.setInt(6, dado.getServicoId());
        pst.setInt(7, dado.getId());

        if (pst.executeUpdate() > 0)
            alterou = true;
        else
            alterou = false;

        Banco.desconectar();

        return alterou;
    }
    
    public Agendamento pesquisa(String filtro) throws SQLException {
        Agendamento agendamento = null;

        String sql = "SELECT * FROM agendamentos ";

        if (filtro.length() != 0) {
            sql += "WHERE " + filtro;
        }

        Banco.conectar();

        pst = Banco.obterConexao().prepareStatement(sql);

        rs = pst.executeQuery();

        if (rs.next()) {
            agendamento = new Agendamento();
            agendamento.setId(rs.getInt("id"));
            agendamento.setNomeCliente(rs.getString("nome_cliente"));
            java.util.Date utilDate = new java.util.Date(rs.getDate("data").getTime());
            agendamento.setData(utilDate);
            agendamento.setProfissional(rs.getString("profissional"));
            agendamento.setUnidade(rs.getString("unidade"));
            agendamento.setValor(rs.getDouble("valor"));
            agendamento.setServicoId(rs.getInt("servico_id"));
        }

        Banco.desconectar();

        return agendamento;
    }


    @Override
    public Collection<Agendamento> lista(String criterio) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Agendamento buscaID(Agendamento model) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}