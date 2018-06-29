package Daos;

import java.sql.Connection;
import Connection.ConnectionFactory;
import Beans.VendaBean;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class VendaDao {

    private Connection conexao;

    public VendaDao() {

        this.conexao = new ConnectionFactory().obterConexao();

    }

    public void cadastraVenda(VendaBean vb) {

        int idProduto = 0;
        
        //Verificar qual o produto selecionado
        String sql = "SELECT idProduto FROM produtos WHERE nomeProduto = ? ";

        try {

            PreparedStatement pstmt = this.conexao.prepareStatement(sql);
            pstmt.setString(1, vb.getProduto());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                idProduto = rs.getInt("idProduto");
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Erro ao buscar produto!" + e.getMessage());

        }
        
        try {
            sql = "INSERT INTO vendas VALUES (null, ?, ?, ?)";
            
            PreparedStatement pstmt = this.conexao.prepareStatement(sql);
            pstmt.setInt(1, idProduto);
            pstmt.setDouble(2, vb.getTotal());
            pstmt.setInt(3, vb.getQuantidadeVenda());
            
            pstmt.execute();
            pstmt.close();
            
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar venda! ");
            System.out.println( e.getMessage());
            
        }
        
    }

    public boolean verificaEstoque(int quantidade, String produto) {

        boolean produtosSuficiente = true;
        int idProduto = 0;
        int novoEstoque = 0;

        //Verificar qual o produto selecionado
        String sql = "SELECT idProduto FROM produtos WHERE nomeProduto = ? ";

        try {

            PreparedStatement pstmt = this.conexao.prepareStatement(sql);
            pstmt.setString(1, produto);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                idProduto = rs.getInt("idProduto");
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Erro ao buscar produto!" + e.getMessage());

        }

        //Verificar a quantidade no estoque
        sql = "SELECT estoque.quantidadeEstoque FROM estoque, produtos WHERE produtos.idEstoque = estoque.idEstoque AND produtos.idProduto = ? ";

        try {

            PreparedStatement pstmt = this.conexao.prepareStatement(sql);
            pstmt.setInt(1, idProduto);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                if (rs.getInt("quantidadeEstoque") < quantidade) {

                    produtosSuficiente = false;
                    break;

                }
            }

            pstmt.close();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Erro ao comparar quantidades!");

        }

        return produtosSuficiente;

    }
    
    //Descontar produtos do estoque
    public void atualizaEstoque(int quantidade, String produto) {

        int idProduto = 0;
        int novoEstoque = 0;
        
        String sql = "SELECT idProduto FROM produtos WHERE nomeProduto = ? ";

        try {

            PreparedStatement pstmt = this.conexao.prepareStatement(sql);
            pstmt.setString(1, produto);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                idProduto = rs.getInt("idProduto");
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Erro ao buscar produto!" + e.getMessage());

        }

        try {
            sql = "SELECT quantidadeEstoque FROM estoque WHERE idEstoque = ?";
            
            PreparedStatement pstmt2 = this.conexao.prepareStatement(sql);
            pstmt2.setInt(1, idProduto);
            ResultSet rs = pstmt2.executeQuery();
            
            while(rs.next()){
                novoEstoque = (rs.getInt("quantidadeEstoque") - quantidade);
            }
            
            pstmt2.execute();
            pstmt2.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar estoque!" + e.getMessage());
        }
        
        try {
 
            sql = "UPDATE estoque SET quantidadeEstoque = ? WHERE idEstoque = ?";
        
            PreparedStatement pstmt3 = this.conexao.prepareStatement(sql);
            pstmt3.setInt(1, novoEstoque);
            pstmt3.setInt(2, idProduto);

            pstmt3.execute();

            pstmt3.close();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Falha ao atualizar estoque!" + e.getMessage());

        }
    }

    public double carregaPreco(String produto) {

        double valorProduto = 0;
        int idProduto = 0;

        String sql = "SELECT idProduto FROM produtos WHERE nomeProduto = ? ";

        try {

            PreparedStatement pstmt = this.conexao.prepareStatement(sql);
            pstmt.setString(1, produto);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                idProduto = rs.getInt("idProduto");
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Erro ao buscar produto!" + e.getMessage());

        }

        sql = "SELECT valor FROM produtos WHERE idProduto = ? ";

        try {

            PreparedStatement pstmt = this.conexao.prepareStatement(sql);
            pstmt.setInt(1, idProduto);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                valorProduto = rs.getDouble("valor");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar valor!" + e.getMessage());
        }

        return valorProduto;

    }

    public DefaultTableModel listarVendas() {

        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("Id");
        modelo.addColumn("Produto Vendido");
        modelo.addColumn("Quantidade");
        modelo.addColumn("Total");

        String sql = "SELECT vendas.idVendas, produtos.nomeProduto, vendas.quantidadeVenda, vendas.valorVenda FROM vendas, produtos WHERE vendas.idProduto = produtos.idProduto";

        try {
            Statement stmt = conexao.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                modelo.addRow(
                        new Object[]{rs.getInt("idVendas"), rs.getString("nomeProduto"), rs.getInt("quantidadeVenda"), rs.getDouble("valorVenda")}
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Erro ao carregar vendas!");

        }

        return modelo;

    }

    public DefaultTableModel listarParaComprar() {

        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("Id");
        modelo.addColumn("Nome");
        modelo.addColumn("Valor");
        modelo.addColumn("Quantidade");

        String sql = "SELECT produtos.idProduto, produtos.nomeProduto, produtos.valor, estoque.quantidadeEstoque FROM produtos, estoque WHERE produtos.idEstoque = estoque.idEstoque";

        try {
            Statement stmt = conexao.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
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
