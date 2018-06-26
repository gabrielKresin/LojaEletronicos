package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private final String url = "jdbc:mysql://localhost:3306/loja";
    private final String user = "root";
    private final String password = "";

    public Connection obterConexao() {

        Connection conexao = null;

        try {
            conexao = DriverManager.getConnection(url, user, password);
            System.out.println("Conexão realizada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao realizar conexão -> "+e.getMessage());
            throw new RuntimeException(e);
        }

        //Retornar a conexão
        return conexao;
    }
}
