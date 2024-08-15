public class Vacina {
    private String nome;
    private String descricao;
    private String fabricante;
    private String validade;
    private String lote;
    private int quantidadeDisponivel;
    


    public Vacina(String nome, String descricao, String fabricante, String validade, String lote, int quantidadeDisponivel) {
        this.nome = nome;
        this.descricao = descricao;
        this.fabricante = fabricante;
        this.validade = validade;
        this.lote = lote;
        this.quantidadeDisponivel = quantidadeDisponivel;   
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getFabricante() {
        return fabricante;
    }


    public String getLote() {
        return lote;
    }
    
    public void vacinando(){
        System.out.println("Vacina: " + nome );
        
    }

    public String Validade(){
        return  validade = "6 meses";
    }

    public String administrarDose() {
        if (quantidadeDisponivel > 0) {
            quantidadeDisponivel--;
            return "Uma dose de " + nome + " foi administrada. Restam " + quantidadeDisponivel + " doses.";
        } else {
            return "Não há vacinas disponíveis para " + nome + " no momento.";
        }
    }

    public int getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    


}
