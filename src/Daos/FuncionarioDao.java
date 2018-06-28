package Daos;

import java.sql.Connection;
import Connection.ConnectionFactory;
import Beans.FuncionarioBean;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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

            sql = "SELECT idInformacoesFuncionario FROM funcionarios ORDER BY idInformacoesFuncionario DESC LIMIT 1";

            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                idCadastro = rs.getInt("idInformacoesFuncionario");
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

        //MUDAR A VARIAVEL DE CPF NO BD PARA LONG
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

    public boolean verificaCpf(long cpf) {
        boolean existente = false;

        String sql = "SELECT informacoesfuncionario.CPFFuncionario, informacoescliente.CPFCliente FROM informacoesfuncionario, informacoescliente";

        try {

            Statement stmt = conexao.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                if ((rs.getInt("CPFFuncionario") == cpf) || (rs.getInt("CPFCliente") == cpf)) {

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
                        new Object[]{rs.getInt("idFuncionario"), rs.getString("nomeFuncionario"), rs.getInt("CPFFuncionario"), rs.getInt("idadeFuncionario"), rs.getString("SexoFuncionario"), rs.getString("emailFuncionario"), rs.getInt("numeroContato"), rs.getString("nomeCargo"), rs.getDouble("salario"), rs.getString("nomeBairro"), rs.getString("ruaFuncionario"), rs.getInt("numeroCasaFuncionario"), rs.getString("complemento")}
                );
            }

            stmt.close();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Erro ao carregar funcionários!");

        }

        return modelo;

    }

    public void alterarFuncionario(FuncionarioBean fb) {

    }

    public void excluirFuncionario(int idFuncionario) {

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
