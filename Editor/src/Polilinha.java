import java.awt.*;

public class Polilinha  extends Ponto {

    private int[] xCods = new int[30];
    private int[] yCods = new int[30];
    private int qtsPontos = 0;

    public Polilinha(int x1, int y1, Color cor) {
        super(x1,y1, cor);
        setNovoPonto(x, y);
    }

    public int getQtsPontos(){
        return qtsPontos;
    }

    public int[] getxCods(){
        return xCods;
    }

    public int[] getyCods(){
        return  yCods;
    }

    public void setNovoPonto(int x, int y){
        xCods[qtsPontos] = x;
        yCods[qtsPontos] = y;
        qtsPontos++;
    }

    public String toString(){
        String saida = transformaString("y", 5) +
                transformaString(Integer.toString(x),5)+
                transformaString(Integer.toString(y),5)+
                transformaString(Integer.toString(getCor().getRed()),5)+
                transformaString(Integer.toString(getCor().getGreen()),5)+
                transformaString(Integer.toString(getCor().getBlue()),5);
        for (int i=0; i<qtsPontos; i++){
            saida += transformaString(Integer.toString(xCods[i]), 5);
            saida += transformaString(Integer.toString(yCods[i]), 5);
        }
        return saida;
    }

    public void desenhar(Graphics g){
        g.setColor(cor);
        g.drawPolyline(xCods, yCods, qtsPontos);
    }
}
