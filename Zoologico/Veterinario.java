public class Veterinario extends Funcionario {
    private String especializacao;

    public Veterinario(String nome, int hrEscala, int idade, float salario, String email, String especializacao) {
        super(nome, hrEscala, idade, salario, email);
        this.especializacao = especializacao;
    }

    public String getEspecializacao() {
        return especializacao;
    }

    @Override
    public String toString() {
        return super.toString() + ", Especialização: " + especializacao;
    }
}