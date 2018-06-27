package Daos;

import java.sql.Connection;
import Connection.ConnectionFactory;
import Beans.FuncionarioBean;
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

}
