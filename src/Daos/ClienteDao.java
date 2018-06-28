package Daos;

import java.sql.Connection;
import Connection.ConnectionFactory;
import Beans.ClienteBean;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ClienteDao {
    
    private Connection conexao;
    
    public ClienteDao(){
        
        this.conexao = new ConnectionFactory().obterConexao();
        
    }
    
    public void cadastraCliente(ClienteBean cb){
        
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
                        new Object[]{rs.getInt("idCliente"), rs.getString("nomeCliente"), rs.getInt("CPFCliente"), rs.getInt("idadeCliente"), rs.getString("SexoCliente"), rs.getString("emailCliente"), rs.getString("celularCliente"), rs.getInt("telefoneCliente"), rs.getString("nomeBairro"), rs.getString("RuaCliente"), rs.getInt("numeroCasaCliente"), rs.getString("complemento")}
                );
            }
            
            stmt.close();
            
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, "Erro ao carregar os clientes!");
            
        }
        
        return modelo;
        
    }
    
    public void alterarCliente(ClienteBean cb){
        
    }
    
    public void excluirCliente(int idCliente){
        
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
