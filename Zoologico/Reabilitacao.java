import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Reabilitacao extends JFrame {
    private JTextField TempoNescessario;
    private JComboBox<String> especieComboBox;
    private JComboBox<String> tratamentoComboBox;
    private JButton reabilitarButton;
    private JButton backButton;

    public Reabilitacao() {
        super("Reabilitação de Animais");

        TempoNescessario = new JTextField(20);
        ((AbstractDocument) TempoNescessario.getDocument()).setDocumentFilter(new NumericDocumentFilter());

        reabilitarButton = new JButton("Reabilitar");
        backButton = new JButton("Voltar");

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new telaInicial();
            }
        });

        List<String> especies = carregarEspecies("animais.txt");
        especieComboBox = new JComboBox<>(especies.toArray(new String[0]));

        String[] tratamentos = {"Fisioterapia", "Psicoterapia", "Terapia Ocupacional"};
        tratamentoComboBox = new JComboBox<>(tratamentos);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));
        panel.add(new JLabel("Tratamento:"));
        panel.add(tratamentoComboBox);
        panel.add(new JLabel("Espécie:"));
        panel.add(especieComboBox);
        panel.add(new JLabel("Tempo Necessário (minutos):"));
        panel.add(TempoNescessario);
        panel.add(backButton);
        panel.add(reabilitarButton);

        getContentPane().add(panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 500);
        setLocationRelativeTo(null);
        setVisible(true);

        reabilitarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(null, "Você deseja cadastrar esta reabilitação?", "Confirmar Cadastro", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    cadastrarReabilitacao();
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

    private void cadastrarReabilitacao() {
        String tratamentoSelecionado = (String) tratamentoComboBox.getSelectedItem();
        String especieSelecionada = (String) especieComboBox.getSelectedItem();
        String tempo = TempoNescessario.getText();

        if (tempo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha o campo de tempo necessário.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        SalvarReabilitacao("reabilitacoes.txt", tratamentoSelecionado, especieSelecionada, tempo);

        startTimer(Integer.parseInt(tempo));
        TempoNescessario.setText("");
    }

    private List<String> carregarEspecies(String arquivo) {
        List<String> especies = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(arquivo), "UTF-8"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                System.out.println("Linha lida: " + linha);
                String[] partes = linha.split(",");
                if (partes.length >= 4) {
                    String especie = partes[0].trim() + ", " + partes[2].trim() + "KG " + partes[3].trim();
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

    private void SalvarReabilitacao(String arquivo, String tratamento, String especie, String tempo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo, true))) {
            writer.write("Tratamento: " + tratamento);
            writer.newLine();
            writer.write("Espécie: " + especie);
            writer.newLine();
            writer.write("Tempo Necessário: " + tempo + " minutos");
            writer.newLine();
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startTimer(int durationMinutes) {
        JFrame timerFrame = new JFrame("Temporizador");
        JLabel label = new JLabel("Temporizador: 00:00:00");
        timerFrame.add(label);

        timerFrame.setSize(300, 150);

        Timer timer = new Timer(1000, new ActionListener() {
            int count = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                int hours = count / 3600;
                int minutes = (count % 3600) / 60;
                int seconds = count % 60;
                label.setText(String.format("Temporizador: %02d:%02d:%02d", hours, minutes, seconds));
                count++;
                if (count >= durationMinutes * 60) {
                    ((Timer) e.getSource()).stop();
                    JOptionPane.showMessageDialog(timerFrame, tratamentoComboBox.getSelectedItem() + " concluída");
                    timerFrame.dispose();
                }
            }
        });

        timer.start();
        timerFrame.setLocationRelativeTo(null);
        timerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        timerFrame.setVisible(true);
    }

    private static class NumericDocumentFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (string != null && string.matches("\\d*")) {
                super.insertString(fb, offset, string, attr);
            } else {
                JOptionPane.showMessageDialog(null, "Somente números são permitidos");
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (text != null && text.matches("\\d*")) {
                super.replace(fb, offset, length, text, attrs);
            } else {
                JOptionPane.showMessageDialog(null, "Somente números são permitidos");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Reabilitacao();
            }
        });
    }
}
