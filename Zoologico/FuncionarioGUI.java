import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class FuncionarioGUI extends JFrame {
    private JTextField nomeField;
    private JTextField idadeField;
    private JTextField salarioField;
    private JTextField emailField;
    private JCheckBox veterinarioCheckBox;
    private JTextField especializacaoField;
    private JCheckBox cuidadorCheckBox;
    private JRadioButton simRadioButton;
    private JRadioButton naoRadioButton;
    private ButtonGroup experienciaGroup;
    private JButton cadastrarButton;
    private JButton backButton;

    public FuncionarioGUI() {
        super("Cadastro de Funcionário");

        // Criação dos componentes
        nomeField = new JTextField(20);
        idadeField = new JTextField(20);
        salarioField = new JTextField(20);
        emailField = new JTextField(20);
        cadastrarButton = new JButton("Cadastrar");
        backButton = new JButton("Voltar");

        // Configuração do CheckBox para o cargo de Veterinário
        veterinarioCheckBox = new JCheckBox("Veterinário");
        especializacaoField = new JTextField(20);
        especializacaoField.setEnabled(false);

        // Configuração do CheckBox para o cargo de Cuidador
        cuidadorCheckBox = new JCheckBox("Cuidador");

        // Adicionar listener para o CheckBox de Veterinário
        veterinarioCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    cuidadorCheckBox.setSelected(false);
                    simRadioButton.setEnabled(false);
                    naoRadioButton.setEnabled(false);
                }
            }
        });

        // Adicionar listener para o CheckBox de Cuidador
        cuidadorCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    veterinarioCheckBox.setSelected(false);
                    simRadioButton.setEnabled(true);
                    naoRadioButton.setEnabled(true);
                }
            }
        });

        // Configuração dos RadioButtons para a experiência de Cuidador
        simRadioButton = new JRadioButton("Sim");
        naoRadioButton = new JRadioButton("Não");
        experienciaGroup = new ButtonGroup();
        experienciaGroup.add(simRadioButton);
        experienciaGroup.add(naoRadioButton);
        simRadioButton.setEnabled(false);
        naoRadioButton.setEnabled(false);

        // Adicionar listener para o botão
        cadastrarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nome = nomeField.getText();
                String idade = idadeField.getText();
                String salario = salarioField.getText();
                String email = emailField.getText();
                String cargo = "";

                ValidadorEmailAdapter regexValidator = new RegexEmailValidator();
                EmailValidationService validationService = new EmailValidationService(regexValidator);
                if (!validationService.validateEmail(email)) {
                    JOptionPane.showMessageDialog(FuncionarioGUI.this, "Por favor, preencha com um email válido.");
                } else {
                    // Determina o cargo selecionado
                    if (veterinarioCheckBox.isSelected()) {
                        cargo = "Veterinário";
                    } else if (cuidadorCheckBox.isSelected()) {
                        cargo = "Cuidador";
                    }

                    // Se o cargo for Veterinário, pega a especialização
                    String especializacao = "";
                    if (veterinarioCheckBox.isSelected()) {
                        especializacao = especializacaoField.getText();
                    }

                    // Se o cargo for Cuidador, determina a experiência
                    String experiencia = "";
                    if (cuidadorCheckBox.isSelected()) {
                        experiencia = simRadioButton.isSelected() ? "Sim" : "Não";
                    }

                    // Verifica se todos os campos obrigatórios foram preenchidos
                    if (nome.isEmpty() || idade.isEmpty() || salario.isEmpty() || cargo.isEmpty()) {
                        JOptionPane.showMessageDialog(FuncionarioGUI.this, "Por favor, preencha todos os campos obrigatórios.");
                        return;
                    }

                    // Salva os dados do funcionário
                    salvarFuncionario("funcionarios.txt", nome, idade, salario, cargo, especializacao, experiencia, email);

                    // Limpa os campos
                    nomeField.setText("");
                    idadeField.setText("");
                    salarioField.setText("");
                    emailField.setText("");
                    veterinarioCheckBox.setSelected(false);
                    cuidadorCheckBox.setSelected(false);
                    especializacaoField.setText("");
                    especializacaoField.setEnabled(false);
                    simRadioButton.setSelected(false);
                    naoRadioButton.setSelected(false);
                    simRadioButton.setEnabled(false);
                    naoRadioButton.setEnabled(false);

                    // Exibir mensagem de cadastro bem-sucedido
                    JOptionPane.showMessageDialog(FuncionarioGUI.this, "Funcionário cadastrado com sucesso!");
                }
            }
        });

        // Configuração da tela
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));
        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);
        panel.add(new JLabel("Idade:"));
        panel.add(idadeField);
        panel.add(new JLabel("Salário:"));
        panel.add(salarioField);
        panel.add(new JLabel("Email: "));
        panel.add(emailField);
        panel.add(new JLabel("Cargo:"));
        panel.add(veterinarioCheckBox);
        panel.add(new JLabel(""));
        panel.add(cuidadorCheckBox);
        panel.add(new JLabel("Especialização:"));
        panel.add(especializacaoField);
        panel.add(new JLabel("Experiência (Sim/Não):"));
        panel.add(new JLabel(""));
        panel.add(simRadioButton);
        panel.add(naoRadioButton);
        panel.add(backButton);
        panel.add(cadastrarButton);

        getContentPane().add(panel);

        // Configuração do frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(820, 560);
        setLocationRelativeTo(null);
        setVisible(true);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new telaInicial();  
            }
        });
    }

    private void salvarFuncionario(String arquivo, String nome, String idade, String salario, String cargo, String especializacao, String experiencia, String email) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo, true))) {
            writer.write("Nome: " + nome);
            writer.newLine();
            writer.write("Idade: " + idade);
            writer.newLine();
            writer.write("Salário: " + salario);
            writer.newLine();
            writer.write("Email: " + email);
            writer.newLine();
            writer.write("Cargo: " + cargo);
            writer.newLine();
            if (cargo.equals("Veterinário")) {
                writer.write("Especialização: " + especializacao);
                writer.newLine();
            } else if (cargo.equals("Cuidador")) {
                writer.write("Experiência: " + experiencia);
                writer.newLine();
            }
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new FuncionarioGUI();
            }
        });
    }
}
