import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Interface de estratégia
interface VacinaStrategy {
    String administrar(Vacina vacina);
}

// Classe principal da GUI
public class VacinaGUI extends JFrame {
    private JTextField nomeField;
    private JComboBox<String> especieComboBox;
    private JButton vacinarButton;
    private JTextField dosesField;
    private JButton backButton;
    private JComboBox<VacinaStrategy> strategyComboBox; // ComboBox para as estratégias

    public VacinaGUI() {
        super("Vacinação");

        // Criação dos componentes
        nomeField = new JTextField(20);
        vacinarButton = new JButton("Vacinando");
        dosesField = new JTextField(20);
        backButton = new JButton("Voltar");

        // Adicionar validação ao nomeField para não aceitar números
        nomeField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { validateNomeField(); }
            public void removeUpdate(DocumentEvent e) { validateNomeField(); }
            public void changedUpdate(DocumentEvent e) { validateNomeField(); }

            private void validateNomeField() {
                SwingUtilities.invokeLater(() -> {
                    try {
                        String text = nomeField.getText(0, nomeField.getDocument().getLength());
                        if (text.matches(".*\\d.*")) {
                            JOptionPane.showMessageDialog(VacinaGUI.this, "Entrada inválida. Por favor, não inclua números.");
                            nomeField.setText(text.replaceAll("\\d", ""));
                        }
                    } catch (BadLocationException e) {
                        e.printStackTrace();
                    }
                });
            }
        });

        // Adicionar validação ao dosesField para aceitar apenas números
        dosesField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { validateDosesField(); }
            public void removeUpdate(DocumentEvent e) { validateDosesField(); }
            public void changedUpdate(DocumentEvent e) { validateDosesField(); }

            private void validateDosesField() {
                SwingUtilities.invokeLater(() -> {
                    try {
                        String text = dosesField.getText(0, dosesField.getDocument().getLength());
                        if (!text.matches("\\d*")) {
                            JOptionPane.showMessageDialog(VacinaGUI.this, "Entrada inválida. Por favor, insira apenas números.");
                            dosesField.setText(text.replaceAll("[^\\d]", ""));
                        }
                    } catch (BadLocationException e) {
                        e.printStackTrace();
                    }
                });
            }
        });

        // Carregar as opções dos animais a partir do arquivo txt
        List<String> especies = carregarEspecies("animais.txt");
        especieComboBox = new JComboBox<>(especies.toArray(new String[0]));

        // Criar o ComboBox para selecionar a estratégia
        strategyComboBox = new JComboBox<>();
        strategyComboBox.addItem(new VacinaPadraoStrategy()); // Adiciona estratégia padrão
        strategyComboBox.addItem(new VacinaEspecialStrategy()); // Adiciona estratégia especial

        // Adicionar listener para o botão
        vacinarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(null, "Você deseja cadastrar esta vacina?", "Confirmar Cadastro", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    cadastrarVacina();
                }
            }
        });

        // Configuração da tela
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));
        panel.add(new JLabel("Nome da Vacina:"));
        panel.add(nomeField);
        panel.add(new JLabel("Doses:"));
        panel.add(dosesField);
        panel.add(new JLabel("Espécie:"));
        panel.add(especieComboBox);
        panel.add(new JLabel("Estratégia de Administração:"));
        panel.add(strategyComboBox); // Adiciona o combo box de estratégia à GUI
        panel.add(backButton);
        panel.add(vacinarButton);

        getContentPane().add(panel);

        // Configuração do frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(820, 560);
        setLocationRelativeTo(null);
        setVisible(true);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new telaInicial(); // Substitua com a tela inicial apropriada
            }
        });
    }

    private void cadastrarVacina() {
        String nomeVacina = nomeField.getText();
        String especieSelecionada = (String) especieComboBox.getSelectedItem();
        String doses = dosesField.getText();
        VacinaStrategy selectedStrategy = (VacinaStrategy) strategyComboBox.getSelectedItem(); // Obtém a estratégia selecionada

        if (nomeVacina.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha o campo de nome.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (doses.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha o campo de doses.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int numeroDeDoses = Integer.parseInt(doses); // Converte doses para inteiro

        // Cria a vacina com a estratégia selecionada
        Vacina vacina = new Vacina(nomeVacina, "descrição", "fabricante", "validade", "lote", numeroDeDoses, selectedStrategy);

        // Usar a estratégia para administrar a dose
        String resultado = vacina.administrarDose();
        String validade = vacina.Validade();
        JOptionPane.showMessageDialog(null, resultado);
        JOptionPane.showMessageDialog(null, validade);

        salvarVacina("vacinas.txt", nomeVacina, especieSelecionada, doses);

        // Limpar os campos
        nomeField.setText("");
        dosesField.setText("");
        especieComboBox.setSelectedIndex(0);
    }

    private List<String> carregarEspecies(String arquivo) {
        List<String> especies = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(arquivo), "UTF-8"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                System.out.println("Linha lida: " + linha);
                String[] partes = linha.split(",");
                if (partes.length >= 4) {
                    String especie = partes[0].trim() + ", " + partes[3].trim();
                    especies.add(especie);
                } else {
                    System.out.println("Aviso: linha incompleta - " + linha);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return especies;
    }

    private void salvarVacina(String arquivo, String nomeVacina, String especie, String doses) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo, true))) {
            writer.write("Nome da Vacina: " + nomeVacina);
            writer.newLine();
            writer.write("Espécie: " + especie);
            writer.newLine();
            writer.write("Doses: " + doses);
            writer.newLine();
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new VacinaGUI();
            }
        });
    }

    // Estratégia para vacinas padrão
    public static class VacinaPadraoStrategy implements VacinaStrategy {
        @Override
        public String administrar(Vacina vacina) {
            if (vacina.getQuantidadeDisponivel() > 0) {
                vacina.diminuirDose();
                return "Uma dose de " + vacina.getNome() + " foi administrada. Restam " + vacina.getQuantidadeDisponivel() + " doses.";
            } else {
                return "Não há vacinas disponíveis para " + vacina.getNome() + " no momento.";
            }
        }

        @Override
        public String toString() {
            return "Vacina Padrão";  // Nome exibido no JComboBox
        }
    }

    // Estratégia para vacinas com procedimento especial
    public static class VacinaEspecialStrategy implements VacinaStrategy {
        @Override
        public String administrar(Vacina vacina) {
            if (vacina.getQuantidadeDisponivel() > 0) {
                vacina.diminuirDose();
                return "Uma dose especial de " + vacina.getNome() + " foi administrada com procedimento especial. Restam " + vacina.getQuantidadeDisponivel() + " doses.";
            } else {
                return "Não há vacinas especiais disponíveis para " + vacina.getNome() + " no momento.";
            }
        }

        @Override
        public String toString() {
            return "Vacina Especial";  // Nome exibido no JComboBox
        }
    }
}
