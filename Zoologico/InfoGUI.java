import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class InfoGUI extends JFrame {
    private JButton infosFuncionarios;
    private JButton infosAnimais;
    private JButton infosVacinas;
    private JButton infosAlimentos;
    private JButton backButton;
    private JTextArea infoArea;

    public InfoGUI() {
        setTitle("Informações");
        setSize(820, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        infosFuncionarios = new JButton("Funcionarios");
        infosAnimais = new JButton("Animais");
        infosVacinas = new JButton("Vacinas");
        infosAlimentos = new JButton("Alimentos");
        backButton = new JButton("Voltar ao menu");

        JLabel txt = new JLabel("Selecione abaixo as informações que deseja visualizar", JLabel.CENTER);
        add(txt, BorderLayout.NORTH);

        JPanel btnMenu = new JPanel();
        btnMenu.setLayout(new GridLayout(1, 5));
        btnMenu.add(infosFuncionarios);
        btnMenu.add(infosAnimais);
        btnMenu.add(infosVacinas);
        btnMenu.add(infosAlimentos);
        btnMenu.add(backButton);

        add(btnMenu, BorderLayout.SOUTH);

        infoArea = new JTextArea();
        infoArea.setEditable(false);
        infoArea.setLineWrap(true); 
        infoArea.setWrapStyleWord(true); 
        infoArea.setFont(new Font("Arial", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(infoArea);
        add(scrollPane, BorderLayout.CENTER);

        infosFuncionarios.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayInfo("funcionarios.txt");
            }
        });

        infosAnimais.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayInfo("animais.txt");
            }
        });

        infosVacinas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayInfo("vacinas.txt");
            }
        });

        infosAlimentos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayInfo("alimentos.txt");
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new telaInicial();
            }
        });

        setVisible(true);
    }


    private void displayInfo(String fileName) {
        try {
            infoArea.setText(getFileContent(fileName));
        } catch (IOException e) {
            infoArea.setText("Erro ao ler o arquivo: " + fileName);
            e.printStackTrace();
        }
    }

    private String getFileContent(String fileName) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    public static void main(String[] args) {
        new InfoGUI();
    }
}
