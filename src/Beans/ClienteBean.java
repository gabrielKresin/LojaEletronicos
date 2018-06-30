package Beans;

public class ClienteBean {
    
    private int idCliente;
    private String nomeCliente;
    private String ruaCliente;
    private String bairroCliente;
    private int numeroCasaCliente;
    private String complementoCliente;
    private int idadeCliente;
    private long cpfCliente;
    private String sexoCliente;
    private String emailCliente;
    private long celularCliente;
    private long telefoneCliente;
    static private int idClienteAlterado;

    public static int getIdClienteAlterado() {
        return idClienteAlterado;
    }

    public static void setIdClienteAlterado(int idClienteAlterado) {
        ClienteBean.idClienteAlterado = idClienteAlterado;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getRuaCliente() {
        return ruaCliente;
    }

    public void setRuaCliente(String ruaCliente) {
        this.ruaCliente = ruaCliente;
    }

    public String getBairroCliente() {
        return bairroCliente;
    }

    public void setBairroCliente(String bairroCliente) {
        this.bairroCliente = bairroCliente;
    }

    public int getNumeroCasaCliente() {
        return numeroCasaCliente;
    }

    public void setNumeroCasaCliente(int numeroCasaCliente) {
        this.numeroCasaCliente = numeroCasaCliente;
    }

    public String getComplementoCliente() {
        return complementoCliente;
    }

    public void setComplementoCliente(String complementoCliente) {
        this.complementoCliente = complementoCliente;
    }

    public int getIdadeCliente() {
        return idadeCliente;
    }

    public void setIdadeCliente(int idadeCliente) {
        this.idadeCliente = idadeCliente;
    }

    public long getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(long cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public String getSexoCliente() {
        return sexoCliente;
    }

    public void setSexoCliente(String sexoCliente) {
        this.sexoCliente = sexoCliente;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public long getCelularCliente() {
        return celularCliente;
    }

    public void setCelularCliente(long celularCliente) {
        this.celularCliente = celularCliente;
    }

    public long getTelefoneCliente() {
        return telefoneCliente;
    }

    public void setTelefoneCliente(long telefoneCliente) {
        this.telefoneCliente = telefoneCliente;
    }
    
}
