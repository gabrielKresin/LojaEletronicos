package Beans;

public class FuncionarioBean {
    
    private int idFuncionario;
    private String nomeFuncionario;
    private int idadeFuncionario;
    private long cpfFuncionario;
    private String sexoFuncionario;
    private String emailFuncionario;
    private long numeroContatoFuncionario;
    private String cargoFuncionario;
    private double  salarioFuncionario;
    private String bairroFuncionario;
    private String ruaFuncionario;
    private int numeroCasaFuncionario;
    private String complementoFuncionario;
    static private int idFuncionarioAlterado;

    public static int getIdFuncionarioAlterado() {
        return idFuncionarioAlterado;
    }

    public static void setIdFuncionarioAlterado(int idFuncionarioAlterado) {
        FuncionarioBean.idFuncionarioAlterado = idFuncionarioAlterado;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }

    public int getIdadeFuncionario() {
        return idadeFuncionario;
    }

    public void setIdadeFuncionario(int idadeFuncionario) {
        this.idadeFuncionario = idadeFuncionario;
    }

    public long getCpfFuncionario() {
        return cpfFuncionario;
    }

    public void setCpfFuncionario(long cpfFuncionario) {
        this.cpfFuncionario = cpfFuncionario;
    }

    public String getSexoFuncionario() {
        return sexoFuncionario;
    }

    public void setSexoFuncionario(String sexoFuncionario) {
        this.sexoFuncionario = sexoFuncionario;
    }

    public String getEmailFuncionario() {
        return emailFuncionario;
    }

    public void setEmailFuncionario(String emailFuncionario) {
        this.emailFuncionario = emailFuncionario;
    }

    public long getNumeroContatoFuncionario() {
        return numeroContatoFuncionario;
    }

    public void setNumeroContatoFuncionario(long numeroContatoFuncionario) {
        this.numeroContatoFuncionario = numeroContatoFuncionario;
    }

    public String getCargoFuncionario() {
        return cargoFuncionario;
    }

    public void setCargoFuncionario(String cargoFuncionario) {
        this.cargoFuncionario = cargoFuncionario;
    }

    public double getSalarioFuncionario() {
        return salarioFuncionario;
    }

    public void setSalarioFuncionario(double salarioFuncionario) {
        this.salarioFuncionario = salarioFuncionario;
    }

    public String getBairroFuncionario() {
        return bairroFuncionario;
    }

    public void setBairroFuncionario(String bairroFuncionario) {
        this.bairroFuncionario = bairroFuncionario;
    }

    public String getRuaFuncionario() {
        return ruaFuncionario;
    }

    public void setRuaFuncionario(String ruaFuncionario) {
        this.ruaFuncionario = ruaFuncionario;
    }

    public int getNumeroCasaFuncionario() {
        return numeroCasaFuncionario;
    }

    public void setNumeroCasaFuncionario(int numeroCasaFuncionario) {
        this.numeroCasaFuncionario = numeroCasaFuncionario;
    }

    public String getComplementoFuncionario() {
        return complementoFuncionario;
    }

    public void setComplementoFuncionario(String complementoFuncionario) {
        this.complementoFuncionario = complementoFuncionario;
    }
}
