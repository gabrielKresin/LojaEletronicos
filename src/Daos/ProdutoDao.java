package Daos;

//Importações
import java.sql.Connection;
import Connection.ConnectionFactory;
import Beans.ProdutoBean;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ProdutoDao {

    private Connection conexao;

    public ProdutoDao() {

        this.conexao = new ConnectionFactory().obterConexao();

    }

    //Método para verificar se um produto já existe
    public boolean verificaProduto(String produto){
        
        boolean existente = false;

        //Pegar os nomes já cadastrados
        String sql = "SELECT nomeProduto FROM produtos ";

        try {

            Statement stmt = conexao.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                //Se o nome inserido for igual a um nome já cadastrado, o método irá retornar true, caso contrário
                //irá retornar false
                if (rs.getString("nomeProduto").equals(produto)) {

                    existente = true;
                    break;

                }
            }

            stmt.close();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Erro ao comparar produtos!");

        }

        return existente;
        
    }
    
    //Método para carregar uma ArrayList de produtos no banco
    public ArrayList carregaProdutos() {

        ArrayList<String> produtos = new ArrayList<>();

        String sql = "SELECT nomeProduto FROM produtos ";

        try {

            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                produtos.add(rs.getString("nomeProduto"));

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar produtos!");
        }

        return produtos;

    }

    //Método para cadastrar um produto no banco de dados
    public void cadastraProduto(ProdutoBean pb) {

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

    //Método para retornar uma TableModel de produtos que estão no banco de dados
    public DefaultTableModel listarProdutos() {

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

            while (rs.next()) {
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

    //Método para alterar um produto no banco de dados
    public void alterarProduto(ProdutoBean pb) {

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

        //Alterar estoque na tabela de estoque
        sql = "UPDATE estoque SET quantidadeEstoque = ? WHERE idEstoque = ? ";

        try {

            PreparedStatement pstmt = this.conexao.prepareStatement(sql);
            pstmt.setInt(1, pb.getEstoqueProduto());
            pstmt.setInt(2, pb.getIdProdutoAlterado());
            pstmt.execute();
            pstmt.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar estoque! " + e.getMessage());
        }

        //Alterar produto na tabela de produtos
        sql = "UPDATE produtos SET nomeProduto = ? , valor = ? WHERE idProduto = ? ";

        try {

            PreparedStatement pstmt = this.conexao.prepareStatement(sql);
            pstmt.setString(1, pb.getNomeProduto());
            pstmt.setDouble(2, pb.getValorProduto());
            pstmt.setInt(3, pb.getIdProdutoAlterado());
            pstmt.execute();
            pstmt.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar produto! " + e.getMessage());
        }

    }

    //Método para excluir um produto do banco
    public void excluirProduto(String produto) {

        int idProduto = 0;

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

            JOptionPane.showMessageDialog(null, "Erro ao buscar produtos!" + e.getMessage());

        }

        //excluir venda da tabela vendas
        sql = "DELETE FROM vendas WHERE idProduto = ? ";

        try {

            PreparedStatement pstmt = this.conexao.prepareStatement(sql);
            pstmt.setInt(1, idProduto);

            pstmt.execute();
            pstmt.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir produto!" + e.getMessage());
        }

        //excluir produto da tabela produtos
        sql = "DELETE FROM produtos WHERE idProduto = ? ";

        try {

            PreparedStatement pstmt = this.conexao.prepareStatement(sql);
            pstmt.setInt(1, idProduto);

            pstmt.execute();
            pstmt.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir produto!" + e.getMessage());
        }

        //excluir estoque do produto da tabela estoque
        sql = "DELETE FROM estoque WHERE idEstoque = ? ";

        try {

            PreparedStatement pstmt = this.conexao.prepareStatement(sql);
            pstmt.setInt(1, idProduto);

            pstmt.execute();
            pstmt.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir estoque!" + e.getMessage());
        }

    }

    //Método para carregarf uma TableModel de produtos do banco
    public DefaultTableModel listarParaAlterar() {

        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("Id");
        modelo.addColumn("Nome");

        String sql = "SELECT idProduto, nomeProduto FROM produtos";

        try {
            Statement stmt = conexao.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
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
