import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CadastroAnimaisGUI extends JFrame {
    private JComboBox<String> tipoAnimalComboBox;

    private JTextField nomeField;
    private JTextField idadeField;
    private JTextField pesoField;
    private JTextField especieField;

    private JComboBox<String> pelagemComboBox;
    private JTextField tempoGestacaoField;
    private JCheckBox temExoesqueletoCheckBox;

    private JCheckBox possuiNecessidadesCheckBox;
    private JTextField necessidadesField;
    private JLabel necessidadesLabel;

    private JButton cadastrarButton;
    private JButton voltarButton;

    private Map<String, Animal> animais;

    public CadastroAnimaisGUI() {
        setTitle("Cadastro de Animais");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(820, 560);
        setLayout(new GridLayout(0, 2));
        setLocationRelativeTo(null);

        animais = new HashMap<>();

        String[] tipoAnimalOptions = { "Mamífero", "Invertebrado" };
        tipoAnimalComboBox = new JComboBox<>(tipoAnimalOptions);

        tipoAnimalComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tipoAnimalComboBox.getSelectedItem().equals("Mamífero")) {
                    pelagemComboBox.setEnabled(true);
                    tempoGestacaoField.setEnabled(true);
                    temExoesqueletoCheckBox.setEnabled(false);
                    possuiNecessidadesCheckBox.setEnabled(true);
                } else {
                    pelagemComboBox.setEnabled(false);
                    tempoGestacaoField.setEnabled(false);
                    temExoesqueletoCheckBox.setEnabled(true);
                    possuiNecessidadesCheckBox.setEnabled(true);
                }
                checkPossuiNecessidades();
            }
        });

        nomeField = new JTextField();
        idadeField = new JTextField();
        pesoField = new JTextField();
        especieField = new JTextField();

        String[] pelagemOptions = { "Pequeno", "Médio", "Grande" };
        pelagemComboBox = new JComboBox<>(pelagemOptions);
        
        tempoGestacaoField = new JTextField();
        temExoesqueletoCheckBox = new JCheckBox("Tem exoesqueleto?");

        possuiNecessidadesCheckBox = new JCheckBox("Possui necessidades?");
        necessidadesField = new JTextField();
        necessidadesLabel = new JLabel("Necessidades:");

        possuiNecessidadesCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                checkPossuiNecessidades();
            }
        });

        cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int response = JOptionPane.showConfirmDialog(null, "Você deseja cadastrar este animal?", "Confirmar Cadastro", JOptionPane.YES_NO_OPTION);
                    if (response != JOptionPane.YES_OPTION) {
                        return;
                    }
                    cadastrarAnimal();
                    JOptionPane.showMessageDialog(null, "Animal cadastrado com sucesso!");
                    limparCampos();
                } catch (CadastroException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        voltarButton = new JButton("Voltar");
        voltarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new telaInicial();
            }
        });

        add(new JLabel("Nome:"));
        add(nomeField);
        add(new JLabel("Espécie:"));
        add(especieField);
        add(new JLabel("Idade:"));
        add(idadeField);
        add(new JLabel("Peso:"));
        add(pesoField);
        add(new JLabel("Tipo de Animal:"));
        add(tipoAnimalComboBox);
        add(new JLabel("Pelagem:"));
        add(pelagemComboBox);
        add(new JLabel("Tempo de Gestação:"));
        add(tempoGestacaoField);
        add(new JLabel(""));
        add(temExoesqueletoCheckBox);
        add(new JLabel("Possui necessidades?"));
        add(possuiNecessidadesCheckBox);
        add(necessidadesLabel);
        add(necessidadesField);
        add(voltarButton);
        add(cadastrarButton);

        pelagemComboBox.setEnabled(false);
        tempoGestacaoField.setEnabled(false);
        temExoesqueletoCheckBox.setEnabled(false);
        possuiNecessidadesCheckBox.setEnabled(false);
        checkPossuiNecessidades();

        ((AbstractDocument) idadeField.getDocument()).setDocumentFilter(new NumberFilter());
        ((AbstractDocument) pesoField.getDocument()).setDocumentFilter(new NumberFilter());
        ((AbstractDocument) tempoGestacaoField.getDocument()).setDocumentFilter(new NumberFilter());
        ((AbstractDocument) nomeField.getDocument()).setDocumentFilter(new LetterFilter());
        ((AbstractDocument) especieField.getDocument()).setDocumentFilter(new LetterFilter());
        ((AbstractDocument) necessidadesField.getDocument()).setDocumentFilter(new LetterFilter());

        setVisible(true);
    }

    private void cadastrarAnimal() throws CadastroException {
        String nome = nomeField.getText();
        String especie = especieField.getText();
        int idade;
        double peso;

        if (nome.isEmpty() || especie.isEmpty()) {
            throw new CadastroException("Os campos Nome e Espécie não podem estar vazios.");
        }

        try {
            idade = Integer.parseInt(idadeField.getText());
            peso = Double.parseDouble(pesoField.getText());
        } catch (NumberFormatException ex) {
            throw new CadastroException("Por favor, insira valores válidos para Idade e Peso.");
        }

        Animal animal = null;
        if (tipoAnimalComboBox.getSelectedItem().equals("Mamífero")) {
            String pelagem = (String) pelagemComboBox.getSelectedItem();
            int tempoGestacao;

            try {
                tempoGestacao = Integer.parseInt(tempoGestacaoField.getText());
            } catch (NumberFormatException ex) {
                throw new CadastroException("Por favor, insira um valor válido para Tempo de Gestação.");
            }

            boolean possuiNecessidades = possuiNecessidadesCheckBox.isSelected();
            String necessidades = possuiNecessidades ? necessidadesField.getText() : "";
            animal = new Mamifero(nome, idade, peso, pelagem, tempoGestacao, especie, possuiNecessidades, necessidades);
        } else if (tipoAnimalComboBox.getSelectedItem().equals("Invertebrado")) {
            boolean temExoesqueleto = temExoesqueletoCheckBox.isSelected();
            boolean possuiNecessidades = possuiNecessidadesCheckBox.isSelected();
            String necessidades = possuiNecessidades ? necessidadesField.getText() : "";
            animal = new Invertebrado(nome, idade, peso, temExoesqueleto, especie, possuiNecessidades, necessidades);
        }

        if (animal != null) {
            animais.put(animal.getNome(), animal);
            salvarAnimal(animal);
        }
    }

    private void salvarAnimal(Animal animal) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("animais.txt", true))) {
            writer.write(animal.toString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace(); // Print the stack trace for debugging
            JOptionPane.showMessageDialog(null, "Erro ao salvar o animal.", "Erro de Escrita", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void checkPossuiNecessidades() {
        boolean selecionado = possuiNecessidadesCheckBox.isSelected();
        necessidadesField.setEnabled(selecionado);
        necessidadesLabel.setEnabled(selecionado);
    }

    private void limparCampos() {
        nomeField.setText("");
        especieField.setText("");
        idadeField.setText("");
        pesoField.setText("");
        pelagemComboBox.setSelectedIndex(0);
        tempoGestacaoField.setText("");
        temExoesqueletoCheckBox.setSelected(false);
        possuiNecessidadesCheckBox.setSelected(false);
        necessidadesField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CadastroAnimaisGUI();
            }
        });
    }
}

class NumberFilter extends DocumentFilter {
    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if (string.matches("\\d*")) {
            super.insertString(fb, offset, string, attr);
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, insira apenas números.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (text.matches("\\d*")) {
            super.replace(fb, offset, length, text, attrs);
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, insira apenas números.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
        }
    }
}

class LetterFilter extends DocumentFilter {
    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if (string.matches("[a-zA-Z]*")) {
            super.insertString(fb, offset, string, attr);
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, insira apenas letras.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (text.matches("[a-zA-Z]*")) {
            super.replace(fb, offset, length, text, attrs);
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, insira apenas letras.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
        }
    }
}

abstract class Animal {
    private String nome;
    private int idade;
    private double peso;
    private String especie;
    private boolean possuiNecessidades;
    private String necessidades;

    public Animal(String nome, int idade, double peso, String especie, boolean possuiNecessidades, String necessidades) {
        this.nome = nome;
        this.idade = idade;
        this.peso = peso;
        this.especie = especie;
        this.possuiNecessidades = possuiNecessidades;
        this.necessidades = necessidades;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return nome + "," + idade + "," + peso + "," + especie + "," + possuiNecessidades + "," + necessidades;
    }
}

class Mamifero extends Animal {
    private String pelagem;
    private int tempoGestacao;

    public Mamifero(String nome, int idade, double peso, String pelagem, int tempoGestacao, String especie, boolean possuiNecessidades, String necessidades) {
        super(nome, idade, peso, especie, possuiNecessidades, necessidades);
        this.pelagem = pelagem;
        this.tempoGestacao = tempoGestacao;
    }

    @Override
    public String toString() {
        return super.toString() + "," + pelagem + "," + tempoGestacao;
    }
}

class Invertebrado extends Animal {
    private boolean temExoesqueleto;

    public Invertebrado(String nome, int idade, double peso, boolean temExoesqueleto, String especie, boolean possuiNecessidades, String necessidades) {
        super(nome, idade, peso, especie, possuiNecessidades, necessidades);
        this.temExoesqueleto = temExoesqueleto;
    }

    @Override
    public String toString() {
        return super.toString() + "," + temExoesqueleto;
    }
}

class CadastroException extends Exception {
    public CadastroException(String message) {
        super(message);
    }
}
