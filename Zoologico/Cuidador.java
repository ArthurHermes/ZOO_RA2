public class Cuidador extends Funcionario {
    private boolean temExperiencia;

    public Cuidador(String nome, int hrEscala, int idade, float salario, boolean temExperiencia) {
        super(nome, hrEscala, idade, salario);
        this.temExperiencia = temExperiencia;
    }

    public boolean isTemExperiencia() {
        return temExperiencia;
    }

    @Override
    public String toString() {
        return super.toString() + ", Experiência: " + (temExperiencia ? "Sim" : "Não");
    }
}