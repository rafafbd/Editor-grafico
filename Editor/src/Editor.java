import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;


public class Editor extends JFrame{ // Formulário GUI

    private JButton btnPonto, btnLinha, btnCirculo, btnElipse, btnCor, btnAbrir,
            btnSalvar, btnApagar, btnSair, btnRetangulo, btnPolilinha, btnSelecionar, btnMudarCor,
            btnLimpaSelecionados, btnApagaSelecionados, btnDeslocar;
    private JPanel pnlBotoes;

    static private Color corAtual = Color.BLACK; //Cor inicial

    static private MeuJPanel pnlDesenho;

    private static Ponto[] figuras = new Ponto[1000];
    private static int[] indicesSelecionados = new int[1000];
    static int qtasFiguras;
    static int qtosIndicesSelecionados;

    static private JInternalFrame frame;

    static private String figuraEsperada = ""; // se for "", eh porque nao se espera nenhuma
    static private boolean fezPolilinha = false; // variavel especifica para o funcionamento da polilinha

    static JLabel statusBar1, statusBar2;


    public Editor(){
        super("Editor Gráfico"); // super construtor

        btnAbrir = new JButton("Abrir");
        btnSalvar = new JButton("Salvar");
        btnPonto = new JButton("Ponto");
        btnLinha = new JButton("Linha");
        btnCirculo = new JButton("Circulo");
        btnElipse = new JButton("Elipse");
        btnRetangulo = new JButton("Retangulo");
        btnCor = new JButton("Cores");
        btnApagar = new JButton("Apagar");
        btnMudarCor = new JButton("Mudar Cor");
        btnSelecionar = new JButton("Selecionar");
        btnSair = new JButton("Sair");
        btnLimpaSelecionados = new JButton("Limpar Selecionados");
        btnApagaSelecionados = new JButton("Apagar Selecionados");
        btnPolilinha = new JButton("Polilinha");
        btnDeslocar = new JButton("Deslocar");


        pnlBotoes = new JPanel();
        pnlBotoes.setLayout(new GridLayout(2, 10));
        btnAbrir.addActionListener(new FazAbertura());
        btnSalvar.addActionListener(new FazSalvamento());
        btnPonto.addActionListener(new FazPonto());
        btnLinha.addActionListener(new FazLinha());
        btnCirculo.addActionListener(new FazCirculo());
        btnElipse.addActionListener(new FazOval());
        btnRetangulo.addActionListener(new FazRetangulo());
        btnPolilinha.addActionListener(new FazPolilinha());
        btnApagar.addActionListener(new ApagaTela());
        btnSelecionar.addActionListener(new FazSelecionar());
        btnCor.addActionListener(new EscolheCor());
        btnMudarCor.addActionListener(new FazMudarCor());
        btnDeslocar.addActionListener(new FazDeslocamento());

        btnLimpaSelecionados.addActionListener(new LimpaSelecionados());
        btnApagaSelecionados.addActionListener(new ApagaSelecionados());
        btnSair.addActionListener(new FazSair());
        pnlBotoes.add(btnAbrir);
        pnlBotoes.add(btnSalvar);
        pnlBotoes.add(btnPonto);
        pnlBotoes.add(btnLinha);
        pnlBotoes.add(btnCirculo);
        pnlBotoes.add(btnElipse);
        pnlBotoes.add(btnRetangulo);
        pnlBotoes.add(btnPolilinha);
        pnlBotoes.add(btnCor);
        pnlBotoes.add(btnApagar);
        pnlBotoes.add(btnSelecionar);
        pnlBotoes.add(btnMudarCor);
        pnlBotoes.add(btnDeslocar);
        pnlBotoes.add(btnLimpaSelecionados);
        pnlBotoes.add(btnApagaSelecionados);
        pnlBotoes.add(btnSair);

        Container cntForm = getContentPane(); // acessa o painel de conteúdo do frame
        cntForm.setLayout(new BorderLayout());
        cntForm.add(pnlBotoes , BorderLayout.NORTH); // Container cntForm recebe o layout pnlBotoes que tem os botoes
        JDesktopPane panDesenho = new JDesktopPane();
        cntForm.add(panDesenho);

        frame = new JInternalFrame("Nenhum arquivo aberto", true, true, true, true);
        panDesenho.add(frame);

        pnlDesenho = new MeuJPanel();
        panDesenho.add(pnlDesenho);

        setSize(1100,700);
        show();

        frame.setSize(this.getWidth() / 2,this.getHeight() / 2);
        frame.setOpaque(true);
        frame.show();

        pnlDesenho = new MeuJPanel();
        Container cntFrame = frame.getContentPane();
        cntFrame.add(pnlDesenho);

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


    private void Salvar(){
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

    private class FazSelecionar implements ActionListener{
        public void actionPerformed(ActionEvent e){
            try{
                String input = JOptionPane.showInputDialog(null,
                        "Digite o índice da figura geométrica:",
                        "Selecionar Figura",
                        JOptionPane.QUESTION_MESSAGE);

                if (input == null)
                    return;

                int indice = Integer.parseInt(input);

                if (indice < 0 || indice >= qtasFiguras){
                    JOptionPane.showMessageDialog(null,
                            "Índice inválido! Insira um valor entre 0 e " + (qtasFiguras - 1),
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                indicesSelecionados[qtosIndicesSelecionados++] = indice;

                repaint();
            }
            catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "Por favor, insira um número válido.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class FazSalvamento implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Salvar();
        }
    }

    private class ApagaSelecionados implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            Ponto[] temporario = new Ponto[1000];
            int quantosTemporarios = 0;
            for(int i = 0; i<qtasFiguras; i++){
                if (!pnlDesenho.contem(indicesSelecionados, i)){
                    temporario[quantosTemporarios++] = figuras[i];
                }
            }

            for (int ind = 0; ind < quantosTemporarios; ind++){
                figuras[ind] = temporario[ind];
            }
            qtasFiguras = quantosTemporarios;
            qtosIndicesSelecionados = 0;
            repaint();
        }
    }

    private class LimpaSelecionados implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            qtosIndicesSelecionados = 0;
            repaint();
        }
    }

    private class FazDeslocamento implements ActionListener{

        public void actionPerformed(ActionEvent e){
            int deltaX = Integer.parseInt(JOptionPane.showInputDialog(null, "Deslocamento para o lado: ", "Deslocamento de figuras", JOptionPane.PLAIN_MESSAGE));
            int deltaY = Integer.parseInt(JOptionPane.showInputDialog(null, "Deslocamento para o cima/baixo: ", "Deslocamento de figuras", JOptionPane.PLAIN_MESSAGE));
            for (int indice=0; indice<qtosIndicesSelecionados; indice++){
                Ponto figura = figuras[indicesSelecionados[indice]];
                figura.setX(figura.getX() + deltaX);
                figura.setY(figura.getY() + deltaY);
                if (figura instanceof Linha){
                    Linha linha = (Linha) figura;
                    linha.setX2(linha.getX2() + deltaX);
                    linha.setY2(linha.getY2() + deltaY);
                }
                else if (figura instanceof Polilinha){
                    Polilinha poly = (Polilinha) figura;
                    int[] posicoesX = poly.getxCods();
                    int[] posicoesY = poly.getyCods();
                    for (int i = 0; i<poly.getQtsPontos(); i++){
                        int novoX = posicoesX[i] + deltaX;
                        int novoY = posicoesY[i] + deltaY;
                        try {
                            poly.atualizarCoordanadas(i, novoX, novoY);
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }

            }
            pnlDesenho.repaint();
        }
    }

    private class FazMudarCor implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            Color novaCor = JColorChooser.showDialog(
                    null,
                    "Escolha uma Nova Cor",
                    corAtual // Cor padrão inicial
            );

            if (novaCor == null){
                return;
            }

            for (int indice = 0; indice<qtosIndicesSelecionados; indice++){
                figuras[indicesSelecionados[indice]].setCor(novaCor);
            }

            pnlDesenho.repaint();
        }
    }

    private class FazPonto implements ActionListener{


        public void actionPerformed(ActionEvent e) {
            figuraEsperada = "Ponto";
            pnlDesenho.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e){
                    if (figuraEsperada.equals("Ponto")){
                        int x = e.getX();
                        int y = e.getY();
                        Ponto ponto = new Ponto(x, y, corAtual);//Pode mudar a cor padrão

                        figuras[qtasFiguras++] = ponto;
                        pnlDesenho.repaint();
                    }
                }
            });
        }
    }

    private class EscolheCor implements ActionListener{
        public void actionPerformed(ActionEvent e){
            Color novaCor = JColorChooser.showDialog(
                    null,
                    "Escolha uma cor",
                    corAtual
            );

            if (novaCor != null){
                corAtual = novaCor;
                //Mudou de cor
            }

        }
    }

    private class FazSair implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            Object[] options = {"Salvar e Sair", "Sair Sem Salvar", "Cancelar"};
            int resposta = JOptionPane.showOptionDialog(
                    null,
                    "Deseja salvar antes de sair?",
                    "Sair",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[2] //Opcao padrao

            );

            if (resposta == JOptionPane.YES_OPTION) {
                Salvar();
                System.exit(0);
            } else if (resposta == JOptionPane.NO_OPTION) {
                System.exit(0);
            }
        }
    }

    private class FazLinha implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            figuraEsperada = "Linha";
            pnlDesenho.addMouseListener(new MouseAdapter() {
                private Ponto pontoInicial = null;
                @Override
                public void mousePressed(MouseEvent e){
                    if (figuraEsperada.equals("Linha")){
                        int x = e.getX();
                        int y = e.getY();
                        if (pontoInicial == null){
                            pontoInicial = new Ponto(x, y, corAtual);//Pode mudar a cor padrão
                        }
                        else{
                            //Ponto inicial já definido
                            Ponto pontoFinal = new Ponto(x, y, corAtual);

                            Linha linha = new Linha(pontoInicial.getX(), pontoInicial.getY(), pontoFinal.getX(), pontoFinal.getY(), corAtual);

                            figuras[qtasFiguras++] = linha;
                            pontoInicial = null; //Limpa o ponto inicial para permitir criar outra linha
                            pnlDesenho.repaint();
                        }
                    }
                }
            });
        }
    }

    private class FazPolilinha implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.out.println(figuraEsperada);
            if (figuraEsperada.equals("Polilinha")) {
                System.out.println("vai desenhar");
                figuraEsperada = "";
                fezPolilinha = false;
                pnlDesenho.repaint();
                statusBar1.setText("Mensagem: Nenhuma figura selecionada");
            }
            else {
                figuraEsperada = "Polilinha";
                pnlDesenho.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (figuraEsperada.equals("Polilinha")){
                        int x = e.getX();
                        int y = e.getY();
                        if (!fezPolilinha){
                            Polilinha poly = new Polilinha(x, y, corAtual);
                            figuras[qtasFiguras++] = poly;
                            fezPolilinha = true;
                            System.out.println("Inicializou a polilinha");
                        }
                        else {
                            System.out.println("Novo ponto da polilinha");
                            Polilinha poly = (Polilinha) figuras[qtasFiguras-1];
                            poly.setNovoPonto(x, y);
                        }
                    }
            }
        });
                statusBar1.setText("Mensagem: Desenhe pontos ou clique [Polilinha] para terminar");
            }
        }

    }

    private class FazRetangulo implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            figuraEsperada = "Retangulo";
            pnlDesenho.addMouseListener(new MouseAdapter() {
                private Ponto pontoInicial = null;
                @Override
                public void mousePressed(MouseEvent e){
                    if (figuraEsperada.equals("Retangulo")){
                        int x = e.getX();
                        int y = e.getY();
                        if (pontoInicial == null){
                            pontoInicial = new Ponto(x, y, corAtual);//Pode mudar a cor padrão
                        }
                        else{
                            //Ponto inicial já definido
                            Ponto pontoFinal = new Ponto(x, y, corAtual);
                            int xMin = Math.min(pontoInicial.getX(), pontoFinal.getX());
                            int yMin = Math.min(pontoInicial.getY(), pontoFinal.getY());
                            int largura = Math.abs(pontoFinal.getX() - pontoInicial.getX());
                            int altura = Math.abs(pontoFinal.getY() - pontoInicial.getY());
                            Retangulo retangulo = new Retangulo(xMin, yMin, largura, altura, corAtual);

                            figuras[qtasFiguras++] = retangulo;
                            pontoInicial = null; //Limpa o ponto inicial para permitir criar outra linha
                            pnlDesenho.repaint();
                        }
                    }
                }
            });
        }
    }

    private class FazCirculo implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            figuraEsperada = "Circulo";
            pnlDesenho.addMouseListener(new MouseAdapter() {
                private Ponto pontoCentro = null;
                private int raio = 0;
                @Override
                public void mousePressed(MouseEvent e){
                    if (figuraEsperada.equals("Circulo")){
                        int x = e.getX();
                        int y = e.getY();
                        if (pontoCentro == null){
                            pontoCentro = new Ponto(x, y, corAtual);//Pode mudar a cor padrão
                        }
                        else{
                            //Ponto inicial já definido

                            //O raio é a hipotenusa do triangulo cujos catetos são as distancias do centro em relacao ao outro ponto clicado em realçaõ a x a y
                            //Por pitagoras hipotenusa² = (x - xCentro)² + (y - yCentro)² --> hipotenusa = √(x - xCentro)² + (y - yCentro)
                            raio = (int) Math.sqrt(Math.pow(x - pontoCentro.getX(), 2) + Math.pow(y - pontoCentro.getY(), 2));

                            Circulo circulo = new Circulo(pontoCentro.getX(), pontoCentro.getY(), raio, corAtual);

                            figuras[qtasFiguras++] = circulo;
                            pontoCentro = null; //Limpa o ponto central para permitir criar outro circulo
                            pnlDesenho.repaint();
                        }
                    }
                }
            });
        }
    }

    private class FazOval implements ActionListener{
        public Ponto primeiroCentro = null;
        public void actionPerformed(ActionEvent e) {
            figuraEsperada = "Oval";
//            if (primeiroCentro == null){
//                statusBar1.setText("Mensagem: Clique no primeiro centro");
//            }
//            else {
//                statusBar1.setText("Mensagem: Clique no Segundo centro");
//            }
            pnlDesenho.addMouseListener(new MouseAdapter() {
                private int raioX = 0;
                private int raioY = 0;
                @Override
                public void mousePressed(MouseEvent e){
                    if (figuraEsperada.equals("Oval")){
                        int x = e.getX();
                        int y = e.getY();
                        if (primeiroCentro == null){
                            primeiroCentro = new Ponto(x, y, corAtual);//Pode mudar a cor padrão
                        }
                        else{
                            //Ponto inicial já definido

                            raioX = Math.abs(x - primeiroCentro.getX());
                            raioY = Math.abs(y - primeiroCentro.getY());

                            Oval elipse = new Oval(primeiroCentro.getX(), primeiroCentro.getY(), raioX, raioY, corAtual);

                            figuras[qtasFiguras++] = elipse;
                            primeiroCentro = null; //Limpa o ponto central para permitir criar outro circulo
                            pnlDesenho.repaint();
                        }
                    }
                }
            });
        }
    }

    private class ApagaTela implements ActionListener{
        public void actionPerformed(ActionEvent e){
            qtasFiguras = 0;
            qtosIndicesSelecionados = 0;
            figuras = new Ponto[1000];
            indicesSelecionados = new int[1000];
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
                System.out.println(arquivo);
            }
            try {
                assert arquivo != null;
                BufferedReader arqFiguras = new BufferedReader(
                        new FileReader(arquivo));
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
                            case 'p':
                                figuras[qtasFiguras++] = new Ponto(xBase, yBase, cor); break;
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
                                figuras[qtasFiguras++] = new Oval(xBase, yBase, raioA, raioB, cor); break;
                            case 'y':
                                Polilinha poly = new Polilinha(xBase, yBase, cor);
                                for (int i=30; i+10<=linha.length(); i+=10){
                                    poly.setNovoPonto(Integer.parseInt(linha.substring(i, i+5).trim()), Integer.parseInt(linha.substring(i+5, i+10).trim()));
                                }
                                figuras[qtasFiguras++] = poly;

                        }
                        linha = arqFiguras.readLine();
                    }
                    arqFiguras.close();
                    frame.setTitle(arquivo.getName());
                    pnlDesenho.repaint();
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


    private class MeuJPanel extends JPanel implements MouseListener, MouseMotionListener {

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

        public MeuJPanel(){
            super();
            Container frame = getContentPane();
            JPanel pnlStatus = new JPanel();
            pnlStatus.setLayout(new GridLayout(1,2));
            statusBar1 = new JLabel("Mensagem: Nenhuma figura selecionada");
            statusBar2 = new JLabel("Coordenada: ");
            pnlStatus.add(statusBar1);
            pnlStatus.add(statusBar2);

            frame.add(pnlStatus, BorderLayout.SOUTH);

            addMouseListener(this);
            addMouseMotionListener(this);
        }

        public boolean contem(int[] array, int valor) {
            for (int i = 0; i<qtosIndicesSelecionados; i++) {
                if (array[i] == valor) {
                    return true;
                }
            }
            return false;
        }

        public void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            super.paintComponent(g);
            for (int qualFigura =0 ; qualFigura < qtasFiguras; qualFigura++){
//                System.out.println(figuras[qualFigura].getClass());
//                System.out.println("Tentou desenhar");


                if (contem(indicesSelecionados, qualFigura)) {
                    g2d.setStroke(new BasicStroke(3)); // Espessura de 3 pixels
                }
                else {
                    g2d.setStroke(new BasicStroke(1)); // Espessura padrão
                }
                figuras[qualFigura].desenhar(g2d);
            }
        }
    }
}
