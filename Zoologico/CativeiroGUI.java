import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CativeiroGUI extends JFrame {
    private JTextField apelidoField;
    private JComboBox<String> areaComboBox;
    private JComboBox<String> localizacaoComboBox;
    private JComboBox<String> tamanhoComboBox;
    private JButton cadastrarButton;

    private List<Cativeiro> cativeiros;

    public CativeiroGUI() {
        setTitle("Cadastro de Cativeiros");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(820, 560);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(0, 2));

        cativeiros = new ArrayList<>();

        apelidoField = new JTextField();

        String[] areas = { "Exibições Naturalistas", "Exibições de Aves", "Exibições de Répteis e Anfíbios", 
                           "Exibições de Invertebrados", "Exibições de Mamíferos Aquáticos" };
        areaComboBox = new JComboBox<>(areas);

        String[] localizacoes = { "Zona Norte", "Zona Sul", "Zona Oeste", "Zona Leste" };
        localizacaoComboBox = new JComboBox<>(localizacoes);

        String[] tamanhos = { "Pequeno", "Médio", "Grande" };
        tamanhoComboBox = new JComboBox<>(tamanhos);

        JButton backButton = new JButton("Voltar");

        backButton.addActionListener((ActionEvent e) ->{
            dispose();
            new telaInicial();
        });


        cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cadastrarCativeiro();
            }
        });

        add(new JLabel("Apelido:"));
        add(apelidoField);
        add(new JLabel("Área:"));
        add(areaComboBox);
        add(new JLabel("Localização:"));
        add(localizacaoComboBox);
        add(new JLabel("Tamanho:"));
        add(tamanhoComboBox);
        add(backButton); 
        add(cadastrarButton);

        setVisible(true);
    }

    private void cadastrarCativeiro() {
        String apelido = apelidoField.getText();
        String area = (String) areaComboBox.getSelectedItem();
        String localizacao = (String) localizacaoComboBox.getSelectedItem();
        String tamanho = (String) tamanhoComboBox.getSelectedItem();

        int capacidadeAnimais;
        switch (tamanho) {
            case "Pequeno":
                capacidadeAnimais = 7;
                break;
            case "Médio":
                capacidadeAnimais = 5;
                break;
            case "Grande":
                capacidadeAnimais = 2;
                break;
            default:
                capacidadeAnimais = 0;
                break;
        }

        if (apelido.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos corretamente.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Cativeiro cativeiro = new Cativeiro(apelido, area, localizacao, tamanho, capacidadeAnimais);
        cativeiros.add(cativeiro);
        salvarCativeiros();
        JOptionPane.showMessageDialog(this, "Cativeiro cadastrado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        // Limpar os campos após o cadastro
        apelidoField.setText("");
        areaComboBox.setSelectedIndex(0);
        localizacaoComboBox.setSelectedIndex(0);
        tamanhoComboBox.setSelectedIndex(0);
    }

    private void salvarCativeiros() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("cativeiros.txt"))) {
            for (Cativeiro cativeiro : cativeiros) {
                writer.println(cativeiro.toFileString());
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar cativeiros.", "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void carregarCativeiros() {
        cativeiros = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("cativeiros.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                cativeiros.add(Cativeiro.fromFileString(line));
            }
        } catch (FileNotFoundException e) {
            // Arquivo não encontrado, iniciar lista vazia
            cativeiros = new ArrayList<>();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar cativeiros.", "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                CativeiroGUI gui = new CativeiroGUI();
                gui.carregarCativeiros();
            }
        });
    }
}

class Cativeiro implements Serializable {
    private static final long serialVersionUID = 1L;

    private String apelido;
    private String area;
    private String localizacao;
    private String tamanho;
    private int capacidadeAnimais;

    public Cativeiro(String apelido, String area, String localizacao, String tamanho, int capacidadeAnimais) {
        this.apelido = apelido;
        this.area = area;
        this.localizacao = localizacao;
        this.tamanho = tamanho;
        this.capacidadeAnimais = capacidadeAnimais;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public int getCapacidadeAnimais() {
        return capacidadeAnimais;
    }

    public void setCapacidadeAnimais(int capacidadeAnimais) {
        this.capacidadeAnimais = capacidadeAnimais;
    }

    public String toFileString() {
        return apelido + ";" + area + ";" + localizacao + ";" + tamanho + ";" + capacidadeAnimais;
    }

    public static Cativeiro fromFileString(String fileString) {
        String[] parts = fileString.split(";");
        String apelido = parts[0];
        String area = parts[1];
        String localizacao = parts[2];
        String tamanho = parts[3];
        int capacidadeAnimais = Integer.parseInt(parts[4]);
        return new Cativeiro(apelido, area, localizacao, tamanho, capacidadeAnimais);
    }
}
