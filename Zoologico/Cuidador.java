public class Cuidador extends Funcionario {
    private boolean temExperiencia;

    public Cuidador(String nome, int hrEscala, int idade, float salario, String email, boolean temExperiencia) {
        super(nome, hrEscala, idade, salario, email);
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