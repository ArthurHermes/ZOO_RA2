public class Funcionario {
    private String nome;
    private int hrEscala;
    private int idade;
    private float salario;

    public Funcionario(String nome, int hrEscala, int idade, float salario){
        this.nome = nome;
        this.hrEscala = hrEscala;
        this.idade = idade;
        this.salario = salario;
    }

    public String getNome() {
        return nome;
    }

    public float getSalario() {
        return salario;
    }

    public int getHrEscala() {
        return hrEscala;
    }

    public int getIdade() {
        return idade;
    }

    @Override
    public String toString() {
        return "Nome: " + nome + ", Idade: " + idade + ", Hora Escala: " + hrEscala + ", Salario: " + salario;
    }

}
