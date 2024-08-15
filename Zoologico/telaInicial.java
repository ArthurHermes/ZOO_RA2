import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class telaInicial extends JFrame {
    private JButton cadFuncionario;
    private JButton cadAnimal;
    private JButton cadVacinas;
    private JButton cadAlimento;
    private JButton infoButton;
    private JButton reabilitarButton;
    private JButton alimentarButton;
    private JButton cativeiroButton;

    public telaInicial() {
        setTitle("ZooPUC");
        setSize(820, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);

        /* Criando o texto */
        JLabel txt = new JLabel("SEJA BEM VINDO(A)!", JLabel.CENTER);

        /* Criando o painel */
        JPanel btnmenu = new JPanel();
        btnmenu.setLayout(new GridLayout(2, 3, 5, 5));

        btnmenu.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        /* Criando os botões */
        cadFuncionario = new JButton("Cadastrar Funcionario");
        cadAnimal = new JButton("Cadastrar Animais");
        cadVacinas = new JButton("Cadastrar Vacinas");
        cadAlimento = new JButton("Cadastrar Alimentos");
        alimentarButton = new JButton("Alimentar");
        infoButton = new JButton("Informações");
        reabilitarButton = new JButton("Reabilitação");
        cativeiroButton = new JButton("Cativeiro");

        cadFuncionario.setBackground(new Color(200,230,255));
        cadAnimal.setBackground(new Color(200,230,255));
        cadVacinas.setBackground(new Color(200,230,255));
        cadAlimento.setBackground(new Color(200,230,255));
        alimentarButton.setBackground(new Color(200,230,255));
        infoButton.setBackground(new Color(200,230,255));
        reabilitarButton.setBackground(new Color(200,230,255));
        cativeiroButton.setBackground(new Color(200,230,255));


        /* Colocando os botões no painel */
        btnmenu.add(cadFuncionario);
        btnmenu.add(cadAnimal);
        btnmenu.add(cadVacinas);
        btnmenu.add(cadAlimento);
        btnmenu.add(reabilitarButton);
        btnmenu.add(alimentarButton);
        btnmenu.add(cativeiroButton);
        btnmenu.add(infoButton);

        /* colocando a imagem de fundo */
        JPanel painelCentral = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                URL imageURL = getClass().getResource("Images/ZooPuc.png");
                if (imageURL != null) {
                    ImageIcon imageIcon = new ImageIcon(imageURL);
                    Image image = imageIcon.getImage();
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                } else {
                    System.out.println("Image not found");
                }
            }
        };

        painelCentral.setLayout(new BorderLayout());

        /* Adicionando o texto e o menu de botões na tela */
        painelCentral.add(txt, BorderLayout.NORTH);
        painelCentral.add(btnmenu, BorderLayout.SOUTH);

        setContentPane(painelCentral);
        setVisible(true);

        /* Adicionando ação nos botões */
        cadFuncionario.addActionListener((ActionEvent e) -> {
            dispose();
            new FuncionarioGUI();
        });

        cadAnimal.addActionListener((ActionEvent e) -> {
            dispose();
            new CadastroAnimaisGUI();
        });

        cadVacinas.addActionListener((ActionEvent e) -> {
            dispose();
            new VacinaGUI();
        });

        cadAlimento.addActionListener((ActionEvent e) -> {
            dispose();
            new AlimentoGUI();
        });

        infoButton.addActionListener((ActionEvent e) -> {
            dispose();
            new InfoGUI();
        });

        reabilitarButton.addActionListener((ActionEvent e) -> {
            dispose();
            new Reabilitacao();
        });

        alimentarButton.addActionListener((ActionEvent e) -> {
            dispose();
            new AlimentarAnimaisGUI();
        });

        cativeiroButton.addActionListener((ActionEvent e) ->{
            dispose();
            new CativeiroGUI();
        });

    }
}