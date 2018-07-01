package Main;

//Importações
import Connection.ConnectionFactory;
import Views.MainView;

public class Main {
    
    public static void main(String[] args) {
        
        ConnectionFactory cf = new ConnectionFactory();
        cf.obterConexao();
        MainView mv = new MainView();
        mv.setVisible(true);
        
    }
}
