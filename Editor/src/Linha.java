import java.awt.*;
public class Linha  extends Ponto {

    private Ponto pontoFinal;

    public Linha(int x1, int y1, int x2, int y2, Color cor) {
        super(x1,y1, cor);
        pontoFinal = new Ponto(x2,y2, cor);
    }

    public String transformaString(String valor, int quantasPosicoes) {
        String cadeia = valor;
        while (cadeia.length() < quantasPosicoes)
            cadeia = cadeia + " ";
        return cadeia.substring(0,quantasPosicoes);
    }

    public String toString(){
        return transformaString("p", 5) +
                transformaString(Integer.toString(x),5)+
                transformaString(Integer.toString(y),5)+
                transformaString(Integer.toString(getCor().getRed()),5)+
                transformaString(Integer.toString(getCor().getGreen()),5)+
                transformaString(Integer.toString(getCor().getBlue()),5)+
                transformaString(Integer.toString(pontoFinal.x),5)+
                transformaString(Integer.toString(pontoFinal.y),5);
    }

    public void desenhar(Graphics g){
        g.setColor(cor);
        g.drawLine(super.x, super.y, pontoFinal.x, pontoFinal.y);
    }
}
