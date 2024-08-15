public class Invertebrado extends Animal {
    private boolean temExoesqueleto;

    public Invertebrado(String nome, int idade, double peso, boolean temExoesqueleto, String especie, boolean possuiNecessidades, String necessidades) {
        super(nome, idade, peso, especie, possuiNecessidades, necessidades);
        this.temExoesqueleto = temExoesqueleto;
    }

    public boolean isTemExoesqueleto() {
        return temExoesqueleto;
    }

    @Override
    public String toString() {
        return super.toString() + ", Tem Exoesqueleto: " + temExoesqueleto;
    }
}
