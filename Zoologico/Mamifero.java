// classe mamifero que herda da classe aimal
class Mamifero extends Animal {
    private String pelagem;
    private int tempoGestacao;


    public Mamifero(String nome, int idade, double peso,String especie, boolean possuiNecessidades, String necessidades, String pelagem, int tempoGestacao) {
        super(nome, idade, peso, especie, possuiNecessidades, necessidades);
        this.pelagem = pelagem;
        this.tempoGestacao = tempoGestacao;
    }

    public String getPelagem() {
        return pelagem;
    }

    public int getTempoGestacao() {
        return tempoGestacao;
    }

    @Override
    public String toString() {
        return super.toString() + ", Pelagem: " + pelagem + ", Tempo de Gestação: " + tempoGestacao;
    }

    public void vacinar() {
        System.out.println("Vacinação do mamífero " + getNome());
        // Coloque aqui a lógica para vacinar o mamífero
    }
}