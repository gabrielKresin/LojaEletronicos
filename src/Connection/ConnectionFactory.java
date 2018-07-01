package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Classe que irá realizar as conexões entre o banco e o programa
public class ConnectionFactory {

    private final String url = "jdbc:mysql://localhost:3306/lojaeletronicos";
    private final String user = "root";
    private final String password = "";

    //Método para retornar uma conexão com o banco
    public Connection obterConexao() {

        Connection conexao = null;

        try {
            conexao = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return conexao;
    }
}
