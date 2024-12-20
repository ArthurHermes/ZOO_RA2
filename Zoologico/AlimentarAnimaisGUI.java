import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Interface Observer
interface Observer {
    void update(String alimento, String especie, int quantidade, String funcionario);
}

// Interface Subject
interface Subject {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(String alimento, String especie, int quantidade, String funcionario);
}

// Classe para registrar a alimentação
class RegistroAlimentacaoObserver implements Observer {
    @Override
    public void update(String alimento, String especie, int quantidade, String funcionario) {
        System.out.println("Registro: " + alimento + " foi dado para " + especie + " em " + quantidade + "Kg pelo funcionário " + funcionario);
        // Aqui você poderia adicionar lógica para gravar em um arquivo ou atualizar uma interface, por exemplo.
    }
}

class NotificacaoEmailObserver implements Observer {
    @Override
    public void update(String alimento, String especie, int quantidade, String funcionario) {
        System.out.println("Enviando e-mail: " + funcionario + " alimentou " + quantidade + "Kg de " + alimento + " para " + especie + ".");
    }
}   


public class AlimentarAnimaisGUI extends JFrame implements Subject {
    private JTextField quantidadeField;
    private JComboBox<String> especieComboBox;
    private JComboBox<String> alimentoComboBox;
    private JComboBox<String> funcionarioCombobox;
    private JButton alimentarButton;
    private JButton backButton;
    
    private List<Observer> observers = new ArrayList<>();

    public AlimentarAnimaisGUI() {
        super("Alimentar Animais");

        quantidadeField = new JTextField(20);
        ((AbstractDocument) quantidadeField.getDocument()).setDocumentFilter(new NumberFilter());

        alimentarButton = new JButton("Alimentar");
        backButton = new JButton("Voltar");

        List<String> especies = carregarEspecies("animais.txt");
        especieComboBox = new JComboBox<>(especies.toArray(new String[0]));

        List<String> alimentos = carregarAlimentos("alimentos.txt");
        alimentoComboBox = new JComboBox<>(alimentos.toArray(new String[0]));

        List<String> funcionarios = carregarFuncionarios("funcionarios.txt");
        funcionarioCombobox = new JComboBox<>(funcionarios.toArray(new String[0]));

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));
        panel.add(new JLabel("Alimento:"));
        panel.add(alimentoComboBox);
        panel.add(new JLabel("Espécie:"));
        panel.add(especieComboBox);
        panel.add(new JLabel("Funcionário:"));
        panel.add(funcionarioCombobox);
        panel.add(new JLabel("Quantidade em Quilos:"));
        panel.add(quantidadeField);
        panel.add(backButton);
        panel.add(alimentarButton);

        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(820, 560);
        setLocationRelativeTo(null);
        setVisible(true);

        // Registra o observer
        RegistroAlimentacaoObserver observer = new RegistroAlimentacaoObserver();
        registerObserver(observer);

        NotificacaoEmailObserver observer2 = new NotificacaoEmailObserver();
        registerObserver(observer2);
        

        alimentarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String alimentoSelecionado = (String) alimentoComboBox.getSelectedItem();
                String especieSelecionada = (String) especieComboBox.getSelectedItem();
                String funcionario = (String) funcionarioCombobox.getSelectedItem();
                String quantidade = quantidadeField.getText();

                try {
                    int response = JOptionPane.showConfirmDialog(null, "Você deseja alimentar os animais?", "Confirmar Alimentação", JOptionPane.YES_NO_OPTION);
                    if (response != JOptionPane.YES_OPTION) {
                        return;
                    }

                    int quantidadeInt = Integer.parseInt(quantidade);
                    salvarAlimentacaoAnimais("registroAlimentacao.txt", alimentoSelecionado, especieSelecionada, quantidade, funcionario);

                    // Notifica os observadores
                    notifyObservers(alimentoSelecionado, especieSelecionada, quantidadeInt, funcionario);

                    int diasEspera = Math.round(quantidadeInt / 10.0f * 2);
                    JOptionPane.showMessageDialog(null, "Recomendado alimentar novamente daqui " + diasEspera + " dias.");

                    alimentoComboBox.setSelectedIndex(0);
                    especieComboBox.setSelectedIndex(0);
                    quantidadeField.setText("");

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, insira um número válido para a quantidade.");
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new telaInicial();
            }
        });
    }

    // Os arquivos carregarAlimentos e carregarEspecies estão em formato simples CSV
    private List<String> carregarAlimentos(String arquivo) {
        List<String> alimentos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(arquivo), "UTF-8"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length >= 4) {
                    String alimento = partes[0].trim();
                    alimentos.add(alimento);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return alimentos;
    }

    private List<String> carregarEspecies(String arquivo) {
        List<String> especies = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(arquivo), "UTF-8"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length >= 4) {
                    String especie = partes[0].trim() + ", " + partes[2].trim() + "KG " + partes[3].trim();
                    especies.add(especie);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return especies;
    }

    private List<String> carregarFuncionarios(String arquivo) {
        List<String> funcionarios = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(arquivo), "UTF-8"))) {
            String linha;
            String nome = null;
            String cargo = null;

            while ((linha = br.readLine()) != null) {
                if (linha.startsWith("Nome: ")) {
                    nome = linha.substring(6).trim();
                } else if (linha.startsWith("Cargo: ")) {
                    cargo = linha.substring(7).trim();
                }

                if (nome != null && cargo != null) {
                    funcionarios.add(nome + ", " + cargo);
                    nome = null;
                    cargo = null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return funcionarios;
    }

    private void salvarAlimentacaoAnimais(String arquivo, String alimento, String especie, String quantidade, String funcionarios) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo, true))) {
            writer.write("Alimento: " + alimento);
            writer.newLine();
            writer.write("Espécie: " + especie);
            writer.newLine();
            writer.write("Funcionário: " + funcionarios);
            writer.newLine();
            writer.write("Quantidade: " + quantidade + "Kg");
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String alimento, String especie, int quantidade, String funcionario) {
        for (Observer observer : observers) {
            observer.update(alimento, especie, quantidade, funcionario);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AlimentarAnimaisGUI();
            }
        });
    }
}
