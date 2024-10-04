public class Vacina {
    private String nome;
    private String descricao;
    private String fabricante;
    private String validade;
    private String lote;
    private int quantidadeDisponivel;
    private VacinaStrategy strategy;

    public Vacina(String nome, String descricao, String fabricante, String validade, String lote, int quantidadeDisponivel, VacinaStrategy strategy) {
        this.nome = nome;
        this.descricao = descricao;
        this.fabricante = fabricante;
        this.validade = validade;
        this.lote = lote;
        this.quantidadeDisponivel = quantidadeDisponivel;
        this.strategy = strategy;
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

    public String getValidade() {
        return validade;
    }

    public int getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    // Método que diminui a dose
    public void diminuirDose() {
        quantidadeDisponivel--;
    }
    
    public String administrarDose() {
        return strategy.administrar(this); // Usando strategy para administrar a dose
    }

    public void vacinando() {
        System.out.println("Vacina: " + nome);
    }
}
