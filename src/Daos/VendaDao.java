package Daos;

import java.sql.Connection;
import Connection.ConnectionFactory;
import Beans.VendaBean;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class VendaDao {
    
    private Connection conexao;
    
    public VendaDao(){
        
        this.conexao = new ConnectionFactory().obterConexao();
        
    }
    
    public void cadastraVenda(VendaBean vb){
        
    }
    
    public DefaultTableModel listarVendas(){
        
        DefaultTableModel modelo = new DefaultTableModel();
        
        modelo.addColumn("Id");
        modelo.addColumn("Produto Vendido");
        modelo.addColumn("Quantidade");
        modelo.addColumn("Total");
        
        String sql = "SELECT vendas.idVendas, produtos.nomeProduto, vendas.quantidadeVenda, vendas.valorVenda FROM vendas, produtos WHERE vendas.idProduto = produtos.idProduto";
        
        try {
            Statement stmt = conexao.createStatement();
            
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()){
                modelo.addRow(
                        new Object[]{rs.getInt("idVendas"), rs.getString("nomeProduto"), rs.getInt("quantidadeVenda"), rs.getDouble("valorVenda")}
                );
            }
            
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, "Erro ao carregar vendas!");
            
        }
        
        return modelo;
        
    }
    
    public DefaultTableModel listarParaComprar(){
        
        DefaultTableModel modelo = new DefaultTableModel();
        
        modelo.addColumn("Id");
        modelo.addColumn("Nome");
        modelo.addColumn("Valor");
        modelo.addColumn("Quantidade");
        
        String sql = "SELECT produtos.idProduto, produtos.nomeProduto, produtos.valor, estoque.quantidadeEstoque FROM produtos, estoque WHERE produtos.idEstoque = estoque.idEstoque";
        
        try {
            Statement stmt = conexao.createStatement();

            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()){
                modelo.addRow(
                        new Object[]{rs.getInt("idProduto"), rs.getString("nomeProduto"), rs.getDouble("valor"), rs.getInt("quantidadeEstoque")}
                );
            }
            
            stmt.close();
            
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, "Erro ao carregar produtos!");
            
        }
        
        return modelo;
        
    }
    
}
