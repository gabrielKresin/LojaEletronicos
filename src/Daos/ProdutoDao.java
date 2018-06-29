package Daos;

import java.sql.Connection;
import Connection.ConnectionFactory;
import Beans.ProdutoBean;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ProdutoDao {
    
    private Connection conexao;
    
    public ProdutoDao(){
        
        this.conexao = new ConnectionFactory().obterConexao();
        
    }
    
    public void cadastraProduto(ProdutoBean pb){
        
        int idEstoque = 0;
        int idMarca = 0;
        
        //Verificar qual o marca selecionada
        String sql = "SELECT idMarca FROM marcas WHERE nomeMarca = ? ";

        try {

            PreparedStatement pstmt = this.conexao.prepareStatement(sql);
            pstmt.setString(1, pb.getMarcaProduto());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                idMarca = rs.getInt("idMarca");
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Erro ao buscar marcas!" + e.getMessage());

        }
        
        //Cadastrar estoque na tabela de estoque
        try {

            sql = "INSERT INTO estoque VALUES (null, ?);";

            PreparedStatement pstmt = this.conexao.prepareStatement(sql);

            pstmt.setInt(1, pb.getEstoqueProduto());
            pstmt.execute();
            pstmt.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar estoque!" + e.getMessage());
        }
        
        //Pegar o id do estoque cadastrado
        try {

            sql = "SELECT idEstoque FROM estoque ORDER BY idEstoque DESC LIMIT 1";

            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                idEstoque = rs.getInt("idEstoque");
            }
            
            stmt.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar dados do estoque!" + e.getMessage());
        }
        
        //Cadastrar produto na tabela produtos
        try {
            sql = "INSERT INTO produtos VALUES (null, ?, ?, ?, ?);";

            PreparedStatement pstmt = this.conexao.prepareStatement(sql);

            pstmt.setString(1, pb.getNomeProduto());
            pstmt.setInt(2, idMarca);
            pstmt.setDouble(3, pb.getValorProduto());
            pstmt.setInt(4, idEstoque);

            pstmt.execute();
            pstmt.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto!" + e.getMessage());
        }
        
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
    
    public DefaultTableModel listarParaAlterar(){
        
        DefaultTableModel modelo = new DefaultTableModel();
        
        modelo.addColumn("Id");
        modelo.addColumn("Nome");
        
        String sql = "SELECT idProduto, nomeProduto FROM produtos";
        
        try {
            Statement stmt = conexao.createStatement();

            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()){
                modelo.addRow(
                        new Object[]{rs.getInt("idProduto"), rs.getString("nomeProduto")}
                );
            }
            
            stmt.close();
            
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, "Erro ao carregar produtos!");
            
        }
        
        return modelo;
        
    }
    
}
