package br.com.fatec.DAO;

import br.com.fatec.model.Agendamento;
import br.com.fatec.banco.Banco;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class AgendamentoDAO implements DAO<Agendamento> {

    private Agendamento agendamento;
    private PreparedStatement pst;
    private ResultSet rs;
    
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    @Override
    public boolean insere(Agendamento dado) throws SQLException {
        boolean inseriu;

        Banco.conectar();

        String sql = "INSERT INTO agendamentos (nome_cliente, data_horario, profissional, unidade, valor, servico_id) VALUES (?, ?, ?, ?, ?, ?)";

        pst = Banco.obterConexao().prepareStatement(sql);

        pst.setString(1, dado.getNomeCliente());
        
        String formattedDate = dateFormat.format(dado.getDataHorario());
        
        pst.setString(2, formattedDate);
        pst.setString(3, dado.getProfissional());
        pst.setString(4, dado.getUnidade());
        pst.setFloat(5, dado.getValor());
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

        String sql = "UPDATE agendamentos SET nome_cliente = ?, data_horario = ?, profissional = ?, unidade = ?, valor = ?, servico_id = ? WHERE id = ?";

        pst = Banco.obterConexao().prepareStatement(sql);

        pst.setString(1, dado.getNomeCliente());
        String formattedDate = dateFormat.format(dado.getDataHorario());
        pst.setString(2, formattedDate);
        pst.setString(3, dado.getProfissional());
        pst.setString(4, dado.getUnidade());
        pst.setFloat(5, dado.getValor());
        pst.setInt(6, dado.getServicoId());
        pst.setInt(7, dado.getId());

        if (pst.executeUpdate() > 0)
            alterou = true;
        else
            alterou = false;

        Banco.desconectar();

        return alterou;
    }

    @Override
    public Agendamento buscaID(Agendamento dado) throws SQLException {
        // Implemente a busca por ID se necessário

        return null;
    }

    @Override
    public Collection<Agendamento> lista(String filtro) throws SQLException {
        Collection<Agendamento> listagem = new ArrayList<>();
        

        agendamento = null;

        String sql = "SELECT * FROM agendamentos ";

        if (filtro.length() != 0) {
            sql += "WHERE " + filtro;
        }

        Banco.conectar();

        pst = Banco.obterConexao().prepareStatement(sql);

        rs = pst.executeQuery();

        while (rs.next()) {
            
            agendamento = new Agendamento();
            agendamento.setId(rs.getInt("id"));
            agendamento.setNomeCliente(rs.getString("nome_cliente"));
            agendamento.setDataHorario(rs.getTimestamp("data_horario"));
            agendamento.setProfissional(rs.getString("profissional"));
            agendamento.setUnidade(rs.getString("unidade"));
            agendamento.setValor(rs.getFloat("valor"));
            agendamento.setServicoId(rs.getInt("servico_id"));

            listagem.add(agendamento);
        }

        Banco.desconectar();

        return listagem;
    }
}