import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Editor extends JFrame{ // Formulário GUI

    private JButton btnPonto, btnLinha, btnCirculo, btnElipse, btnCor, btnAbrir,
            btnSalvar,btnApagar, btnSair;
    private JPanel pnlBotoes;
    static private JInternalFrame frame;

    static private MeuJPanel pnlDesenho;
    private static Ponto[] figuras = new Ponto[1000];
    static int qtasFiguras;


    public Editor(){
        super("Editor Gráfico"); // super construtor

        btnAbrir = new JButton("Abrir", new ImageIcon("abrir.jpg"));
        btnSalvar = new JButton("Salvar", new ImageIcon("salvar.bmp"));
        btnPonto = new JButton("Ponto", new ImageIcon("ponto.bmp"));
        btnLinha = new JButton("Linha", new ImageIcon("linha.bmp"));
        btnCirculo = new JButton("Circulo", new ImageIcon("circulo.bmp"));
        btnElipse = new JButton("Elipse", new ImageIcon("elipse.bmp"));
        btnCor = new JButton("Cores", new ImageIcon("cores.bmp"));
        btnApagar = new JButton("Apagar", new ImageIcon("apagar.bmp"));
        btnSair = new JButton("Sair", new ImageIcon("sair.bmp"));

        pnlBotoes = new JPanel();
        FlowLayout flwBotoes = new FlowLayout();
        pnlBotoes.setLayout(flwBotoes);
        btnAbrir.addActionListener(new FazAbertura());
        pnlBotoes.add(btnAbrir);
        pnlBotoes.add(btnSalvar);
        pnlBotoes.add(btnPonto);
        pnlBotoes.add(btnLinha);
        pnlBotoes.add(btnCirculo);
        pnlBotoes.add(btnElipse);
        pnlBotoes.add(btnCor);
        pnlBotoes.add(btnApagar);
        pnlBotoes.add(btnSair);

        Container cntForm = getContentPane(); // acessa o painel de conteúdo do frame
        cntForm.setLayout(new BorderLayout());
        cntForm.add(pnlBotoes , BorderLayout.NORTH); // Container cntForm recebe o layout pnlBotoes que tem os botoes
        JDesktopPane panDesenho = new JDesktopPane();
        cntForm.add(panDesenho);

        frame = new JInternalFrame("Nenhum arquivo aberto", true, true, true, true);
        panDesenho.add(frame);

        setSize(700,500);
        show();

        frame.setSize(this.getWidth() / 2,this.getHeight() / 2);
        frame.setOpaque(true);
        frame.show();

        pnlDesenho = new MeuJPanel();
        Container cntFrame = frame.getContentPane();
        cntFrame.add(pnlDesenho);
    }

    public static void main(String[] args) {
        Editor aplicacao = new Editor();
        aplicacao.addWindowListener(
                        new WindowAdapter(){
                            public void windowClosing(WindowEvent e) {
                                System.exit(0);
                            }
                        }
                );
    }

    private class FazAbertura implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser arqEscolhido = new JFileChooser();
            arqEscolhido.setFileSelectionMode(JFileChooser.FILES_ONLY);

            int result = arqEscolhido.showOpenDialog(Editor.this);
        }
    }

    private class MeuJPanel extends JPanel {
        public void paintComponent(Graphics g) {
            for (int qualFigura =0 ; qualFigura < qtasFiguras; qualFigura++)
                figuras[qualFigura].desenhar(g);
        }
    }
}
