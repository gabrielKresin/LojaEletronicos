package Main;

import Connection.ConnectionFactory;

public class Main {
    
    public static void main(String[] args) {
        
        ConnectionFactory cf = new ConnectionFactory();
        cf.obterConexao();
        
    }
}
