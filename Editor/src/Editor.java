import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class Editor extends JFrame{ // Formulário GUI

    private JButton btnPonto, btnLinha, btnCirculo, btnElipse, btnCor, btnAbrir,
            btnSalvar, btnApagar, btnSair;
    private JPanel pnlBotoes;


    static private MeuJPanel pnlDesenho;
    private static Ponto[] figuras = new Ponto[1000];
    static int qtasFiguras;

    static boolean esperandoPonto;

    static JLabel statusBar1, statusBar2;


    public Editor(){
        super("Editor Gráfico"); // super construtor

        btnAbrir = new JButton("Abrir"/*, new ImageIcon("abrir.jpg")*/);
        btnSalvar = new JButton("Salvar"/*, new ImageIcon("salvar.bmp")*/);
        btnPonto = new JButton("Ponto"/*, new ImageIcon("ponto.bmp")*/);
        btnLinha = new JButton("Linha"/*, new ImageIcon("linha.bmp")*/);
        btnCirculo = new JButton("Circulo"/*, new ImageIcon("circulo.bmp")*/);
        btnElipse = new JButton("Elipse"/*, new ImageIcon("elipse.bmp")*/);
        btnCor = new JButton("Cores"/*, new ImageIcon("cores.bmp")*/);
        btnApagar = new JButton("Apagar"/*, new ImageIcon("apagar.bmp")*/);
        btnSair = new JButton("Sair"/*, new ImageIcon("sair.bmp")*/);

        pnlBotoes = new JPanel();
        FlowLayout flwBotoes = new FlowLayout();
        pnlBotoes.setLayout(flwBotoes);
        btnAbrir.addActionListener(new FazAbertura());
        btnSalvar.addActionListener(new FazSalvamento());
        btnPonto.addActionListener(new FazPonto());
        btnLinha.addActionListener(new FazLinha());
        btnCirculo.addActionListener(new FazCirculo());
        btnElipse.addActionListener(new FazOval());
        btnApagar.addActionListener(new ApagaTela());
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

        pnlDesenho = new MeuJPanel("Nenhum arquivo aberto", true, true, true, true);
        panDesenho.add(pnlDesenho);

        setSize(900,700);
        show();

        pnlDesenho.setSize(this.getWidth() / 2,this.getHeight() / 2);
        pnlDesenho.setOpaque(true);
        pnlDesenho.show();

        cntForm.add(panDesenho, BorderLayout.CENTER);
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


    private class FazSalvamento implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser arq = new JFileChooser();
            arq.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int resultado = arq.showSaveDialog(Editor.this);
            if (resultado == JFileChooser.APPROVE_OPTION){
                File arquivo = arq.getSelectedFile();
                try(BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivo))){
                    for (int i = 0; i<qtasFiguras; i++){
                        escritor.write(figuras[i].toString());
                        escritor.newLine();
                    }
                    JOptionPane.showMessageDialog(Editor.this,
                            "Figuras salvas com sucesso!",
                            "Salvar",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(Editor.this,
                            "Erro ao salvar o arquivo!",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private class FazPonto implements ActionListener{


        public void actionPerformed(ActionEvent e) {
            pnlDesenho.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e){
                    int x = e.getX();
                    int y = e.getY();
                    Ponto ponto = new Ponto(x, y, Color.BLACK);//Pode mudar a cor padrão

                    figuras[qtasFiguras++] = ponto;
                    pnlDesenho.repaint();
                }
            });
        }
    }

    private class FazLinha implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            pnlDesenho.addMouseListener(new MouseAdapter() {
                private Ponto pontoInicial = null;
                @Override
                public void mousePressed(MouseEvent e){
                    int x = e.getX();
                    int y = e.getY();
                    if (pontoInicial == null){
                        pontoInicial = new Ponto(x, y, Color.BLACK);//Pode mudar a cor padrão
                    }
                    else{
                        //Ponto inicial já definido
                        Ponto pontoFinal = new Ponto(x, y, Color.BLACK);

                    Linha linha = new Linha(pontoInicial.getX(), pontoInicial.getY(), pontoFinal.getX(), pontoFinal.getY(), Color.BLACK);

                    figuras[qtasFiguras++] = linha;
                    pontoInicial = null; //Limpa o ponto inicial para permitir criar outra linha
                    pnlDesenho.repaint();
                    }


                }
            });
        }
    }

    private class FazCirculo implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            pnlDesenho.addMouseListener(new MouseAdapter() {
                private Ponto pontoCentro = null;
                private int raio = 0;
                @Override
                public void mousePressed(MouseEvent e){
                    int x = e.getX();
                    int y = e.getY();
                    if (pontoCentro == null){
                        pontoCentro = new Ponto(x, y, Color.BLACK);//Pode mudar a cor padrão
                    }
                    else{
                        //Ponto inicial já definido

                        //O raio é a hipotenusa do triangulo cujos catetos são as distancias do centro em relacao ao outro ponto clicado em realçaõ a x a y
                        //Por pitagoras hipotenusa² = (x - xCentro)² + (y - yCentro)² --> hipotenusa = √(x - xCentro)² + (y - yCentro)
                        raio = (int) Math.sqrt(Math.pow(x - pontoCentro.getX(), 2) + Math.pow(y - pontoCentro.getY(), 2));


                        Circulo circulo = new Circulo(pontoCentro.getX(), pontoCentro.getY(), raio, Color.BLACK);

                        figuras[qtasFiguras++] = circulo;
                        pontoCentro = null; //Limpa o ponto central para permitir criar outro circulo
                        pnlDesenho.repaint();
                    }


                }
            });
        }
    }

    private class FazOval implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            pnlDesenho.addMouseListener(new MouseAdapter() {
                private Ponto primeiroCentro = null;
                private int raioX = 0;
                private int raioY = 0;
                @Override
                public void mousePressed(MouseEvent e){
                    int x = e.getX();
                    int y = e.getY();
                    if (primeiroCentro == null){
                        primeiroCentro = new Ponto(x, y, Color.BLACK);//Pode mudar a cor padrão
                    }
                    else{
                        //Ponto inicial já definido

                        raioX = Math.abs(x - primeiroCentro.getX());
                        raioY = Math.abs(y - primeiroCentro.getY());

                        Oval elipse = new Oval(primeiroCentro.getX(), primeiroCentro.getY(), raioX, raioY, Color.BLACK);

                        figuras[qtasFiguras++] = elipse;
                        primeiroCentro = null; //Limpa o ponto central para permitir criar outro circulo
                        pnlDesenho.repaint();
                    }
                }
            });
        }
    }

    private class ApagaTela implements ActionListener{
        public void actionPerformed(ActionEvent e){
            qtasFiguras = 0;
            repaint();
        }
    }
    private class FazAbertura implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser arqEscolhido = new JFileChooser();
            arqEscolhido.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int result = arqEscolhido.showOpenDialog(Editor.this);
            File arquivo = null;
            if (result == JFileChooser.APPROVE_OPTION) {
                arquivo = arqEscolhido.getSelectedFile();
                System.out.println("Processando "+arquivo.getName());
            }
            try {
                BufferedReader arqFiguras = new BufferedReader(
                        new FileReader(arquivo.getName()));
                try {
                    qtasFiguras = 0;
                    String linha = arqFiguras.readLine();
                    while (linha != null)
                    {
                        String tipo = linha.substring(0,5).trim();
                        int xBase = Integer.parseInt(linha.substring(5,10).trim());
                        int yBase = Integer.parseInt(linha.substring(10,15).trim());
                        int corR  = Integer.parseInt(linha.substring(15,20).trim());
                        int corG  = Integer.parseInt(linha.substring(20,25).trim());
                        int corB  = Integer.parseInt(linha.substring(25,30).trim());
                        Color cor = new Color(corR, corG, corB);
                        switch (tipo.charAt(0)) // verificar qual tipo de figura
                        {
                            case 'p': figuras[qtasFiguras++] = new Ponto(xBase, yBase, cor); break;
                            case 'l':
                                int xFinal =Integer.parseInt(linha.substring(30,35).trim());
                                int yFinal =Integer.parseInt(linha.substring(35,40).trim());
                                figuras[qtasFiguras++] = new Linha(xBase, yBase, xFinal, yFinal, cor); break;
                            case 'c':
                                int raio =Integer.parseInt(linha.substring(30,35).trim());
                                figuras[qtasFiguras++] = new Circulo(xBase, yBase, raio, cor); break;
                            case 'o':
                                int raioA =Integer.parseInt(linha.substring(30,35).trim());
                                int raioB =Integer.parseInt(linha.substring(35,40).trim());
                                figuras[qtasFiguras++] = new Oval(xBase, yBase, raioA, raioB, cor);
                        }
                        linha = arqFiguras.readLine();
                    }
                    arqFiguras.close();
                    pnlDesenho.setTitle(arquivo.getName());
                    repaint();
                }
                catch (IOException ioe){
                    System.out.println("Erro de leitura no arquivo");
                }
            }
            catch (FileNotFoundException er) {
                System.out.println("Arquivo não pôde ser aberto");
            }
        }
    }

    

    private class MeuJPanel extends JInternalFrame implements MouseListener, MouseMotionListener {

        public void mouseMoved(MouseEvent e) {
            statusBar2.setText("Coordenada: "+e.getX()+","+e.getY());
        }
        public void mouseDragged(MouseEvent e) {
        }
        public void mouseClicked (MouseEvent e) {
        }
        public void mousePressed (MouseEvent e) {
        }
        public void mouseEntered (MouseEvent e) {
        }
        public void mouseExited (MouseEvent e)
        {
        }
        public void mouseReleased (MouseEvent e) {
        }
        //"Nenhum arquivo aberto", true, true, true, true
        public MeuJPanel(String titulo, boolean resizable, boolean closable, boolean maximixable, boolean iconifiable){
            super(titulo, resizable, closable, maximixable, iconifiable);
            Container frame = getContentPane();
            JPanel pnlStatus = new JPanel();
            pnlStatus.setLayout(new GridLayout(1,2));
            statusBar1 = new JLabel("Mensagem: ");
            statusBar2 = new JLabel("Coordenada: ");
            pnlStatus.add(statusBar1);
            pnlStatus.add(statusBar2);

            frame.add(pnlStatus, BorderLayout.SOUTH);

            addMouseListener(this);
            addMouseMotionListener(this);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (int qualFigura =0 ; qualFigura < qtasFiguras; qualFigura++){
                System.out.println(figuras[qualFigura].getClass());
                System.out.println("Tentou desenhar");
                figuras[qualFigura].desenhar(g);
            }
        }
    }
}
