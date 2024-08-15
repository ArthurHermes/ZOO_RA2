import java.util.ArrayList;
import java.util.List;

// Classe Animal
class Animal {
    private String nome;
    private String especie;

    public Animal(String nome, String especie) {
        this.nome = nome;
        this.especie = especie;
    }

    public String getNome() {
        return nome;
    }

    public String getEspecie() {
        return especie;
    }
}

// Classe Funcionário
class Funcionario {
    private String nome;
    private String cargo;

    public Funcionario(String nome, String cargo) {
        this.nome = nome;
        this.cargo = cargo;
    }

    public String getNome() {
        return nome;
    }

    public String getCargo() {
        return cargo;
    }
}

// Classe Zoológico
class Zoologico {
    private List<Animal> animais;
    private List<Funcionario> funcionarios;

    public Zoologico() {
        animais = new ArrayList<>();
        funcionarios = new ArrayList<>();
    }

    public void adicionarAnimal(Animal animal) {
        animais.add(animal);
    }

    public void adicionarFuncionario(Funcionario funcionario) {
        funcionarios.add(funcionario);
    }

    public void alimentarAnimais() {
        for (Animal animal : animais) {
            System.out.println("Alimentando " + animal.getNome() + "...");
            // Lógica para alimentar o animal
        }
        System.out.println("Todos os animais foram alimentados.");
    }

    public void exibirInformacoes() {
        System.out.println("Animais no zoológico:");
        for (Animal animal : animais) {
            System.out.println("Nome: " + animal.getNome() + ", Espécie: " + animal.getEspecie());
        }

        System.out.println("\nFuncionários do zoológico:");
        for (Funcionario funcionario : funcionarios) {
            System.out.println("Nome: " + funcionario.getNome() + ", Cargo: " + funcionario.getCargo());
        }
    }
}

// Classe principal
public class Main {
    public static void main(String[] args) {
        Zoologico zoo = new Zoologico();

        // Adicionando animais
        zoo.adicionarAnimal(new Animal("Leão", "Felino"));
        zoo.adicionarAnimal(new Animal("Elefante", "Mamífero"));

        // Adicionando funcionários
        zoo.adicionarFuncionario(new Funcionario("João", "Veterinário"));
        zoo.adicionarFuncionario(new Funcionario("Maria", "Tratador"));

        // Alimentando os animais
        zoo.alimentarAnimais();

        // Exibindo informações
        zoo.exibirInformacoes();
    }
}
