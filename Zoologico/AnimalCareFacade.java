import javax.swing.*;

public class AnimalCareFacade {
    private AlimentarAnimaisGUI alimentarAnimaisGUI;
    private Reabilitacao reabilitacaoGUI;

    public AnimalCareFacade() {
        alimentarAnimaisGUI = new AlimentarAnimaisGUI();
        reabilitacaoGUI = new Reabilitacao();
    }

    public void abrirAlimentacao() {
        SwingUtilities.invokeLater(() -> {
            alimentarAnimaisGUI.setVisible(true);
        });
    }

    public void abrirReabilitacao() {
        SwingUtilities.invokeLater(() -> {
            reabilitacaoGUI.setVisible(true);
        });
    }

    public static void main(String[] args) {
        AnimalCareFacade facade = new AnimalCareFacade();
        facade.abrirAlimentacao();  // ou facade.abrirReabilitacao(); para abrir a tela de reabilitação
    }
}
