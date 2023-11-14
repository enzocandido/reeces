/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fatec.bd;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Viotti
 */
public class Banco {

    //criar atributos
    public static String bancoDados, usuario, senha, servidor;
    public static int porta;

    //variavel de conexao
    public static java.sql.Connection conexao = null;

    //define valores padrão
    static {
        //mysql e mariaDB
        bancoDados = "reeces";
        usuario = "root";
        senha = "";
        servidor = "localhost";
        porta = 3306;
    }

    //métodos
    public static void conectar() throws SQLException {
        //mysql workbench
        String url = "jdbc:mysql://" + servidor +
                     ":" + porta + "/" + bancoDados;

        //MariaDB xampp
        //String url = "jdbc:mariadb://" + servidor +
        //             ":" + porta + "/" + bancoDados;
        
        //sqlServer
        //String url = "jdbc:sqlserver://" + servidor
        //        + ":" + porta + ";databaseName=" + bancoDados;

        conexao = DriverManager.getConnection(url, usuario, senha);
    }

    public static void desconectar() throws SQLException {
        conexao.close();
    }

    public static java.sql.Connection obterConexao() 
                throws SQLException {
        if (conexao == null) {
            throw new SQLException("Conexão está fechada..");
        } else {
            return conexao;
        }
    }

}