import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AlimentoGUI extends JFrame {
    private JTextField nomeField;
    private JTextField fabricacaoField;
    private JTextField validadeField;
    private JTextField quantidadeField;
    private JButton cadastrarButton;
    private JButton backButton;

    private List<Alimento> alimentos;

    public AlimentoGUI() {
        setTitle("Cadastro de Alimentos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(820, 560);
        setLayout(new GridLayout(0, 2));
        setLocationRelativeTo(null);
        setResizable(false);
        
        alimentos = new ArrayList<>();

        nomeField = new JTextField();
        fabricacaoField = new JTextField();
        validadeField = new JTextField();
        quantidadeField = new JTextField();

        // Aplicando filtros aos campos
        ((AbstractDocument) nomeField.getDocument()).setDocumentFilter(new LetterFilter());
        ((AbstractDocument) quantidadeField.getDocument()).setDocumentFilter(new NumberFilter());

        cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(null, "Você deseja cadastrar este alimento?", "Confirmar Cadastro", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    cadastrarAlimento();
                }
            }
        });

        backButton = new JButton("Voltar");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new telaInicial(); // Supondo que essa classe exista
            }
        });

        add(new JLabel("Nome:"));
        add(nomeField);
        add(new JLabel("Fabricação (dd/mm/yyyy):"));
        add(fabricacaoField);
        add(new JLabel("Validade (dd/mm/yyyy):"));
        add(validadeField);
        add(new JLabel("Quantidade:"));
        add(quantidadeField);
        add(backButton);
        add(cadastrarButton);

        setVisible(true);
    }

    private void cadastrarAlimento() {
        String nome = nomeField.getText();
        String fabricacaoText = fabricacaoField.getText();
        String validadeText = validadeField.getText();
        int quantidade = 0;

        try {
            quantidade = Integer.parseInt(quantidadeField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "A quantidade deve ser um número inteiro.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Date fabricacao = parseDate(fabricacaoText);
        Date validade = parseDate(validadeText);

        if (nome.isEmpty() || fabricacao == null || validade == null) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos corretamente.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Alimento alimento = new Alimento(nome, fabricacao, validade, quantidade);
        alimentos.add(alimento);
        salvarAlimentos();

        // Limpar os campos após o cadastro
        nomeField.setText("");
        fabricacaoField.setText("");
        validadeField.setText("");
        quantidadeField.setText("");
    }

    private Date parseDate(String dateString) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Formato de data inválido. Use o formato dd/mm/yyyy.", "Erro", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private void salvarAlimentos() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("alimentos.txt", true))) {
            for (Alimento alimento : alimentos) {
                writer.write(alimento.toString());
                writer.newLine();
            }
            JOptionPane.showMessageDialog(this, "Alimento cadastrado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar alimentos: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AlimentoGUI();
            }
        });
    }
}

class Alimento {
    private String nome;
    private Date fabricacao;
    private Date validade;
    private int quantidade;

    public Alimento(String nome, Date fabricacao, Date validade, int quantidade) {
        this.nome = nome;
        this.fabricacao = fabricacao;
        this.validade = validade;
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return nome + "," + dateFormat.format(fabricacao) + "," + dateFormat.format(validade) + "," + quantidade;
    }
}

class NumberFilter extends DocumentFilter {
    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if (string.matches("\\d*")) {
            super.insertString(fb, offset, string, attr);
        } else {
            JOptionPane.showMessageDialog(null, "Somente números são permitidos.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (text.matches("\\d*")) {
            super.replace(fb, offset, length, text, attrs);
        } else {
            JOptionPane.showMessageDialog(null, "Somente números são permitidos.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
        }
    }
}

class LetterFilter extends DocumentFilter {
    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if (string.matches("[a-zA-Z]*")) {
            super.insertString(fb, offset, string, attr);
        } else {
            JOptionPane.showMessageDialog(null, "Somente letras são permitidas.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (text.matches("[a-zA-Z]*")) {
            super.replace(fb, offset, length, text, attrs);
        } else {
            JOptionPane.showMessageDialog(null, "Somente letras são permitidas.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
        }
    }
}
