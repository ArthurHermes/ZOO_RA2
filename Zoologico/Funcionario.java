public class Funcionario {
    private String nome;
    private int hrEscala;
    private int idade;
    private float salario;
    private String email;

    public Funcionario(String nome, int hrEscala, int idade, float salario, String email) {
        this.nome = nome;
        this.hrEscala = hrEscala;
        this.idade = idade;
        this.salario = salario;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Nome: " + nome + ", Idade: " + idade + ", Hora Escala: " + hrEscala + ", Salario: " + salario + ", Email: " + email;
    }
}
