package Daos;

import java.sql.Connection;
import Connection.ConnectionFactory;
import Beans.FuncionarioBean;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FuncionarioDao {

    private Connection conexao;

    public FuncionarioDao() {

        this.conexao = new ConnectionFactory().obterConexao();

    }

    public void cadastraFuncionario(FuncionarioBean fb) {

        int idBairro = 0;
        int idCadastro = 0;
        int idCargo = 0;

        //Verificar qual o bairro selecionado
        String sql = "SELECT idBairros FROM bairros WHERE nomeBairro = ? ";

        try {

            PreparedStatement pstmt = this.conexao.prepareStatement(sql);
            pstmt.setString(1, fb.getBairroFuncionario());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                idBairro = rs.getInt("idBairros");
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Erro ao buscar bairros!" + e.getMessage());

        }

        //Cadastrar endereço na tabela de enderecofuncionario
        try {

            sql = "INSERT INTO enderecofuncionario VALUES (null, ?, ?, ?, ?);";

            PreparedStatement pstmt = this.conexao.prepareStatement(sql);

            pstmt.setString(1, fb.getRuaFuncionario());
            pstmt.setInt(2, idBairro);
            pstmt.setInt(3, fb.getNumeroCasaFuncionario());
            pstmt.setString(4, fb.getComplementoFuncionario());
            pstmt.execute();
            pstmt.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar endereço!" + e.getMessage());
        }

        //Pegar o id do endereço cadastrado
        try {

            sql = "SELECT idEnderecoF FROM enderecoFuncionario ORDER BY idEnderecoF DESC LIMIT 1";

            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                idCadastro = rs.getInt("idEnderecoF");
            }
            
            stmt.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar dados do funcionário!" + e.getMessage());
        }

        //cadastrar contato na tabela de contatofuncionario
        try {

            sql = "INSERT INTO contatofuncionario VALUES (null, ?, ?);";

            PreparedStatement pstmt = this.conexao.prepareStatement(sql);

            pstmt.setString(1, fb.getEmailFuncionario());
            pstmt.setLong(2, fb.getNumeroContatoFuncionario());

            pstmt.execute();
            pstmt.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar contato!" + e.getMessage());
        }

        //Cadastrar informações na tabela de informacoesfuncionario
        try {

            sql = "INSERT INTO informacoesfuncionario VALUES (null, ?, ?, ?)";

            PreparedStatement pstmt = this.conexao.prepareStatement(sql);

            pstmt.setInt(1, fb.getIdadeFuncionario());
            pstmt.setLong(2, fb.getCpfFuncionario());
            pstmt.setString(3, fb.getSexoFuncionario());

            pstmt.execute();
            pstmt.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar informações!" + e.getMessage());
        }

        //Pegar id do cargo a ser cadastrado
        try {
            sql = "SELECT idCargo FROM cargos WHERE nomeCargo = ? ";
            
            PreparedStatement pstmt = this.conexao.prepareStatement(sql);
            System.out.println(sql);
            pstmt.setString(1, fb.getCargoFuncionario());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                idCargo = rs.getInt("idCargo");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar cargo "+e.getMessage());
        }
        
        //Cadastrar funcionário na tabela funcionarios
        try {
            sql = "INSERT INTO funcionarios VALUES (null, ?, ?, ?, ?, ?);";

            PreparedStatement pstmt = this.conexao.prepareStatement(sql);

            pstmt.setString(1, fb.getNomeFuncionario());
            pstmt.setInt(2, idCadastro);
            pstmt.setInt(3, idCadastro);
            pstmt.setInt(4, idCadastro);
            pstmt.setInt(5, idCargo);

            pstmt.execute();
            pstmt.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar funcionário!" + e.getMessage());
        }

    }

    public ArrayList carregaFuncionario(){
        
        ArrayList<String> funcionarios = new ArrayList<>();
        
        String sql = "SELECT nomeFuncionario FROM funcionarios ";
        
        try {
            
            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()){
                
                funcionarios.add(rs.getString("nomeFuncionario"));
               
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar funcionários!");
        }
        
        return funcionarios;
        
    }
    
    public boolean verificaCpf(long cpf) {
        boolean existente = false;

        String sql = "SELECT informacoesfuncionario.CPFFuncionario, informacoescliente.CPFCliente FROM informacoesfuncionario, informacoescliente";

        try {

            Statement stmt = conexao.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                if ((rs.getLong("CPFFuncionario") == cpf) || (rs.getLong("CPFCliente") == cpf)) {

                    existente = true;
                    break;

                }
            }

            stmt.close();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Erro ao comparar CPF's!");

        }

        System.out.println(existente);
        return existente;

    }

    public DefaultTableModel listarFuncionarios() {

        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("Id");
        modelo.addColumn("Nome");
        modelo.addColumn("CPF");
        modelo.addColumn("Idade");
        modelo.addColumn("Sexo");
        modelo.addColumn("E-mail");
        modelo.addColumn("Telefone");
        modelo.addColumn("Cargo");
        modelo.addColumn("Salário");
        modelo.addColumn("Bairro");
        modelo.addColumn("Rua");
        modelo.addColumn("Número");
        modelo.addColumn("Complemento");

        String sql = "SELECT funcionarios.idFuncionario, funcionarios.nomeFuncionario, informacoesfuncionario.CPFFuncionario, informacoesfuncionario.idadeFuncionario, informacoesfuncionario.SexoFuncionario, contatofuncionario.emailFuncionario, contatofuncionario.numeroContato, cargos.nomeCargo, cargos.salario, bairros.nomeBairro, enderecofuncionario.ruaFuncionario, enderecofuncionario.numeroCasaFuncionario, enderecofuncionario.complemento FROM funcionarios, informacoesfuncionario, contatofuncionario, cargos, bairros, enderecofuncionario WHERE funcionarios.idContatoF = contatofuncionario.idContatoF AND funcionarios.idInformacoesFuncionario = informacoesfuncionario.idInformacoesFuncionario AND funcionarios.idCargo = cargos.idCargo AND ((funcionarios.idEnderecoF = enderecofuncionario.idEnderecoF) AND (enderecofuncionario.idBairros = bairros.idBairros))";

        try {

            Statement stmt = conexao.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                modelo.addRow(
                        new Object[]{rs.getInt("idFuncionario"), rs.getString("nomeFuncionario"), rs.getLong("CPFFuncionario"), rs.getInt("idadeFuncionario"), rs.getString("SexoFuncionario"), rs.getString("emailFuncionario"), rs.getInt("numeroContato"), rs.getString("nomeCargo"), rs.getDouble("salario"), rs.getString("nomeBairro"), rs.getString("ruaFuncionario"), rs.getInt("numeroCasaFuncionario"), rs.getString("complemento")}
                );
            }

            stmt.close();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Erro ao carregar funcionários!" + e.getMessage());

        }

        return modelo;

    }

    public void alterarFuncionario(FuncionarioBean fb) {

        /*
        UPDATE enderecofuncionario SET ruaFuncionario = "kaka", idBairros = 4, numeroCasaFuncionario = 666, complemento = "Nenhum" WHERE idEnderecoF = 1;
        UPDATE contatofuncionario SET emailFuncionario = "kaka@inferno.hell", numeroContato = 666666666 WHERE idContatoF = 1;
        UPDATE informacoesfuncionario SET idadeFuncionario = 69, CPFFuncionario = 66666666669, SexoFuncionario = "Masculino" WHERE idInformacoesFuncionario = 1;
        UPDATE funcionarios SET nomeFuncionario = "xd", idCargo = 7 WHERE idFuncionario = 1;
        */
        
        String sql = "";
        
    }

    public void excluirFuncionario(String funcionario) {

        int idFuncionario = 0;
        
        String sql = "SELECT idFuncionario FROM funcionarios WHERE nomeFuncionario = ? ";

        try {

            PreparedStatement pstmt = this.conexao.prepareStatement(sql);
            pstmt.setString(1, funcionario);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                idFuncionario = rs.getInt("idFuncionario");
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Erro ao buscar funcionários!" + e.getMessage());

        }
        
        //excluir funcionário da tabela funcionários
        sql = "DELETE FROM funcionarios WHERE idFuncionario = ? ";
        
        try {
            
            PreparedStatement pstmt = this.conexao.prepareStatement(sql);
            pstmt.setInt(1, idFuncionario);
            
            pstmt.execute();
            pstmt.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir funcionário!" + e.getMessage());
        }
        
        //excluir endereco do funcionario da tabela enderecofuncionario
        sql = "DELETE FROM enderecofuncionario WHERE idEnderecoF = ? ";
        
        try {
            
            PreparedStatement pstmt = this.conexao.prepareStatement(sql);
            pstmt.setInt(1, idFuncionario);
            
            pstmt.execute();
            pstmt.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir enderecofuncionario!" + e.getMessage());
        }
        
        //excluir informacoes do funcionario da tabela informacoesfuncionario
        sql = "DELETE FROM informacoesfuncionario WHERE idInformacoesfuncionario = ?";
        
        try {
            
            PreparedStatement pstmt = this.conexao.prepareStatement(sql);
            pstmt.setInt(1, idFuncionario);
            
            pstmt.execute();
            pstmt.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir informacoesfuncionario!" + e.getMessage());
        }
        
        //excluir contato do funcionario da tabela contatofuncionario
        sql = "DELETE FROM contatofuncionario WHERE idContatoF = ? ";
        
        try {
            
            PreparedStatement pstmt = this.conexao.prepareStatement(sql);
            pstmt.setInt(1, idFuncionario);
            
            pstmt.execute();
            pstmt.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir contatofuncionario!" + e.getMessage());
        }
        
    }

    public DefaultTableModel listarParaAlterar() {

        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("Id");
        modelo.addColumn("Nome");

        String sql = "SELECT idFuncionario, nomeFuncionario FROM funcionarios";

        try {
            Statement stmt = conexao.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                modelo.addRow(
                        new Object[]{rs.getInt("idFuncionario"), rs.getString("nomeFuncionario")}
                );
            }

            stmt.close();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Erro ao carregar funcionários!");

        }

        return modelo;

    }

}
