import java.awt.*;
public class Reta  extends Ponto {

    private Ponto pontoFinal;

    public Reta(int x1, int y1, int x2, int y2, Color cor) {
        super(x1,y1, cor);
        pontoFinal = new Ponto(x2,y2, cor);
    }

    public void desenhar(Graphics g){
        g.setColor(cor);
        g.drawLine(super.x, super.y, pontoFinal.x, pontoFinal.y);
    }
}
