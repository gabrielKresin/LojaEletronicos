package Daos;

import java.sql.Connection;
import Connection.ConnectionFactory;
import Beans.ProdutoBean;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ProdutoDao {
    
    private Connection conexao;
    
    public ProdutoDao(){
        
        this.conexao = new ConnectionFactory().obterConexao();
        
    }
    
    public void cadastraFuncionario(ProdutoBean pb){
        
    }
    
    public DefaultTableModel listarProdutos(){
        
        DefaultTableModel modelo = new DefaultTableModel();
        
        modelo.addColumn("Id");
        modelo.addColumn("Nome");
        modelo.addColumn("Marca");
        modelo.addColumn("Valor");
        modelo.addColumn("Quantidade");
        
        String sql = "SELECT produtos.idProduto, produtos.nomeProduto, marcas.nomeMarca, produtos.valor, estoque.quantidadeEstoque FROM produtos, marcas, estoque WHERE produtos.idMarca = marcas.idMarca AND produtos.idEstoque = estoque.idEstoque";
        
        try {
            Statement stmt = conexao.createStatement();
            
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()){
                modelo.addRow(
                        new Object[]{rs.getInt("idProduto"), rs.getString("nomeProduto"), rs.getString("nomeMarca"), rs.getDouble("valor"), rs.getInt("quantidadeEstoque")}
                );
            }
            
            stmt.close();
            
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, "Erro ao carregar produtos!");
            
        }
     
        return modelo;
        
    }
    
    public void alterarProduto(ProdutoBean pb){
        
    }
    
    public void excluirProduto(int idProduto){
        
    }
    
}
