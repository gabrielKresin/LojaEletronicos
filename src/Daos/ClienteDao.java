package Daos;

import java.sql.Connection;
import Connection.ConnectionFactory;
import Beans.ClienteBean;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ClienteDao {
    
    private Connection conexao;
    
    public ClienteDao(){
        
        this.conexao = new ConnectionFactory().obterConexao();
        
    }
    
    public ArrayList carregaClientes(){
        ArrayList<String> clientes = new ArrayList<>();
        
        String sql = "SELECT nomeCliente FROM clientes ";
        
        try {
            
            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()){
                
                clientes.add(rs.getString("nomeCliente"));
               
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar clientes!");
        }
        
        return clientes;
    }
    
    public void cadastraCliente(ClienteBean cb){
        
        int idBairro = 0;
        int idCadastro = 0;
        
        //Verificar qual o bairro selecionado
        String sql = "SELECT idBairros FROM bairros WHERE nomeBairro = ? ";

        try {

            PreparedStatement pstmt = this.conexao.prepareStatement(sql);
            pstmt.setString(1, cb.getBairroCliente());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                idBairro = rs.getInt("idBairros");
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Erro ao buscar bairros!" + e.getMessage());

        }
        
        //Cadastrar endereço na tabela de enderecofuncionario
        try {

            sql = "INSERT INTO enderecocliente VALUES (null, ?, ?, ?, ?);";

            PreparedStatement pstmt = this.conexao.prepareStatement(sql);

            pstmt.setString(1, cb.getRuaCliente());
            pstmt.setInt(2, idBairro);
            pstmt.setInt(3, cb.getNumeroCasaCliente());
            pstmt.setString(4, cb.getComplementoCliente());
            pstmt.execute();
            pstmt.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar endereço!" + e.getMessage());
        }
        
        //Pegar o id do endereço cadastrado
        try {

            sql = "SELECT idEnderecoC FROM enderecocliente ORDER BY idEnderecoC DESC LIMIT 1";

            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                idCadastro = rs.getInt("idEnderecoC");
            }
            
            stmt.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar dados do cliente!" + e.getMessage());
        }
        
        //cadastrar contato na tabela de contatocliente
        try {

            sql = "INSERT INTO contatocliente VALUES (null, ?, ?, ?);";

            PreparedStatement pstmt = this.conexao.prepareStatement(sql);

            pstmt.setString(1, cb.getEmailCliente());
            pstmt.setLong(2, cb.getCelularCliente());
            pstmt.setLong(3, cb.getTelefoneCliente());

            pstmt.execute();
            pstmt.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar contato!" + e.getMessage());
        }
        
        //Cadastrar informações na tabela de informacoesfuncionario
        try {

            sql = "INSERT INTO informacoescliente VALUES (null, ?, ?, ?)";

            PreparedStatement pstmt = this.conexao.prepareStatement(sql);

            pstmt.setInt(1, cb.getIdadeCliente());
            pstmt.setLong(2, cb.getCpfCliente());
            pstmt.setString(3, cb.getSexoCliente());

            pstmt.execute();
            pstmt.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar informações!" + e.getMessage());
        }
        
        //Cadastrar cliente na tabela clientes
        try {
            sql = "INSERT INTO clientes VALUES (null, ?, ?, ?, ?);";

            PreparedStatement pstmt = this.conexao.prepareStatement(sql);

            pstmt.setInt(1, idCadastro);
            pstmt.setInt(2, idCadastro);
            pstmt.setInt(3, idCadastro);
            pstmt.setString(4, cb.getNomeCliente());

            pstmt.execute();
            pstmt.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar cliente!" + e.getMessage());
        }
        
    }
    
    public DefaultTableModel listarClientes(){
        
        DefaultTableModel modelo = new DefaultTableModel();
        
        modelo.addColumn("Id");
        modelo.addColumn("Nome");
        modelo.addColumn("CPF");
        modelo.addColumn("Idade");
        modelo.addColumn("Sexo");
        modelo.addColumn("E-mail");
        modelo.addColumn("Celular");
        modelo.addColumn("Telefone");
        modelo.addColumn("Bairro");
        modelo.addColumn("Rua");
        modelo.addColumn("Número");
        modelo.addColumn("Complemento");
        
        String sql = "SELECT clientes.idCliente, clientes.nomeCliente, informacoescliente.CPFCliente, informacoescliente.idadeCliente, informacoescliente.SexoCliente, contatocliente.emailCliente, contatocliente.celularCliente, contatocliente.telefoneCliente, bairros.nomeBairro, enderecocliente.RuaCliente, enderecocliente.numeroCasaCliente, enderecocliente.complemento FROM clientes, informacoescliente, contatocliente, bairros, enderecocliente WHERE clientes.idInformacoes = informacoescliente.idInformacoes AND clientes.idContato = contatocliente.idContato AND ((clientes.idEnderecoC = enderecocliente.idEnderecoC) AND (enderecocliente.idBairros = bairros.idBairros))";
        
        try {
            Statement stmt = conexao.createStatement();
            
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()){
                modelo.addRow(
                        new Object[]{rs.getInt("idCliente"), rs.getString("nomeCliente"), rs.getLong("CPFCliente"), rs.getInt("idadeCliente"), rs.getString("SexoCliente"), rs.getString("emailCliente"), rs.getLong("celularCliente"), rs.getLong("telefoneCliente"), rs.getString("nomeBairro"), rs.getString("RuaCliente"), rs.getInt("numeroCasaCliente"), rs.getString("complemento")}
                );
            }
            
            stmt.close();
            
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, "Erro ao carregar os clientes! "+e.getMessage());
            
        }
        
        return modelo;
        
    }
    
    public void alterarCliente(ClienteBean cb){
        
        int idBairro = 0;
        
        String sql = "SELECT idBairros FROM bairros WHERE nomeBairro = ? ";

        try {

            PreparedStatement pstmt = this.conexao.prepareStatement(sql);
            pstmt.setString(1, cb.getBairroCliente());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                idBairro = rs.getInt("idBairros");
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Erro ao buscar bairros!" + e.getMessage());

        }
        
        /*
        UPDATE enderecofuncionario SET ruaFuncionario = "kaka", idBairros = 4, numeroCasaFuncionario = 666, complemento = "Nenhum" WHERE idEnderecoF = 1;
        UPDATE contatofuncionario SET emailFuncionario = "kaka@inferno.hell", numeroContato = 666666666 WHERE idContatoF = 1;
        UPDATE informacoesfuncionario SET idadeFuncionario = 69, CPFFuncionario = 66666666669, SexoFuncionario = "Masculino" WHERE idInformacoesFuncionario = 1;
        UPDATE funcionarios SET nomeFuncionario = "xd", idCargo = 7 WHERE idFuncionario = 1;
        */
        
        sql = "UPDATE enderecocliente SET RuaCliente = ? , idBairros = ? , numeroCasaCliente = ? , complemento = ? WHERE idEnderecoC = ? ";
        
        try {
            
            PreparedStatement pstmt = this.conexao.prepareStatement(sql);
            pstmt.setString(1, cb.getRuaCliente());
            pstmt.setInt(2, idBairro);
            pstmt.setInt(3, cb.getNumeroCasaCliente());
            pstmt.setString(4, cb.getComplementoCliente());
            pstmt.setInt(5, cb.getIdClienteAlterado());
            pstmt.execute();
            pstmt.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar endereço! "+e.getMessage());
        }
        
        sql = "UPDATE contatocliente SET emailCliente = ? , celularCliente = ? , telefoneCliente = ? WHERE idContato = ? ";
        
        try {
            
            PreparedStatement pstmt = this.conexao.prepareStatement(sql);
            pstmt.setString(1, cb.getEmailCliente());
            pstmt.setLong(2, cb.getCelularCliente());
            pstmt.setLong(3, cb.getTelefoneCliente());
            pstmt.setInt(4, cb.getIdClienteAlterado());
            pstmt.execute();
            pstmt.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar contato! "+e.getMessage());
        }
        
        sql = "UPDATE informacoescliente SET idadeCliente = ? , CPFCliente = ? , SexoCliente = ? WHERE idInformacoes = ? ";
        
        try {
            
            PreparedStatement pstmt = this.conexao.prepareStatement(sql);
            pstmt.setInt(1, cb.getIdadeCliente());
            pstmt.setLong(2, cb.getCpfCliente());
            pstmt.setString(3, cb.getSexoCliente());
            pstmt.setInt(4, cb.getIdClienteAlterado());
            pstmt.execute();
            pstmt.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar informações! "+e.getMessage());
        }
        
        sql = "UPDATE clientes SET nomeCliente = ? WHERE idCliente = ? ";
        
        try {
            
            PreparedStatement pstmt = this.conexao.prepareStatement(sql);
            pstmt.setString(1, cb.getNomeCliente());
            pstmt.setInt(2, cb.getIdClienteAlterado());
            pstmt.execute();
            pstmt.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar cliente! "+e.getMessage());
        }
        
    }
    
    public void excluirCliente(String cliente){
        
        int idCliente = 0;
        
        String sql = "SELECT idCliente FROM clientes WHERE nomeCliente = ? ";

        try {

            PreparedStatement pstmt = this.conexao.prepareStatement(sql);
            pstmt.setString(1, cliente);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                idCliente = rs.getInt("idCliente");
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Erro ao buscar clientes!" + e.getMessage());

        }
        
        //excluir cliente da tabela clientes
        sql = "DELETE FROM clientes WHERE idCliente = ? ";
        
        try {
            
            PreparedStatement pstmt = this.conexao.prepareStatement(sql);
            pstmt.setInt(1, idCliente);
            
            pstmt.execute();
            pstmt.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir cliente!" + e.getMessage());
        }
        
        //excluir endereco do cliente da tabela enderecocliente
        sql = "DELETE FROM enderecocliente WHERE idEnderecoC = ? ";
        
        try {
            
            PreparedStatement pstmt = this.conexao.prepareStatement(sql);
            pstmt.setInt(1, idCliente);
            
            pstmt.execute();
            pstmt.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir enderecocliente!" + e.getMessage());
        }
        
        //excluir informacoes do cliente da tabela informacoescliente
        sql = "DELETE FROM informacoescliente WHERE idInformacoes = ?";
        
        try {
            
            PreparedStatement pstmt = this.conexao.prepareStatement(sql);
            pstmt.setInt(1, idCliente);
            
            pstmt.execute();
            pstmt.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir informacoescliente!" + e.getMessage());
        }
        
        //excluir contato do cliente da tabela contatocliente
        sql = "DELETE FROM contatocliente WHERE idContato = ? ";
        
        try {
            
            PreparedStatement pstmt = this.conexao.prepareStatement(sql);
            pstmt.setInt(1, idCliente);
            
            pstmt.execute();
            pstmt.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir contatocliente!" + e.getMessage());
        }
        
        
    }
    
    public DefaultTableModel listarParaAlterar(){
        
        DefaultTableModel modelo = new DefaultTableModel();
        
        modelo.addColumn("Id");
        modelo.addColumn("Nome");
        
        String sql = "SELECT idCliente, nomeCliente FROM clientes";
        
        try {
            Statement stmt = conexao.createStatement();

            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()){
                modelo.addRow(
                        new Object[]{rs.getInt("idCliente"), rs.getString("nomeCliente")}
                );
            }
            
            stmt.close();
            
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, "Erro ao carregar clientes!");
            
        }
        
        return modelo;
        
    }
    
}
