package Beans;

public class ProdutoBean {
    
    private int idProduto;
    private String nomeProduto;
    private double valorProduto;
    private String marcaProduto;
    private int estoqueProduto;
    static private int idProdutoAlterado;

    public static int getIdProdutoAlterado() {
        return idProdutoAlterado;
    }

    public static void setIdProdutoAlterado(int idProdutoAlterado) {
        ProdutoBean.idProdutoAlterado = idProdutoAlterado;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public double getValorProduto() {
        return valorProduto;
    }

    public void setValorProduto(double valorProduto) {
        this.valorProduto = valorProduto;
    }

    public String getMarcaProduto() {
        return marcaProduto;
    }

    public void setMarcaProduto(String marcaProduto) {
        this.marcaProduto = marcaProduto;
    }

    public int getEstoqueProduto() {
        return estoqueProduto;
    }

    public void setEstoqueProduto(int estoqueProduto) {
        this.estoqueProduto = estoqueProduto;
    }
    
}
