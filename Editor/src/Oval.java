import java.awt.*;

public class Oval extends Ponto {

    int raioX, raioY;
    // eu mudei os nomes para raioX e raioY porque fica mais claro que raioA e raioB
    // apaga esse comentario depois que voce ler


    public Oval(int xCentro, int yCentro, int novoRaioX, int novoRaioY, Color novaCor) {
        super(xCentro, yCentro, novaCor);
        setRaioX(novoRaioX);
        setRaioY(novoRaioY);
    }

    public void setRaioX(int novoRaio) {
        raioX = novoRaio;
    }

    public void setRaioY(int novoRaio) {
        raioY = novoRaio;
    }

    public String toString(){
        return transformaString("o", 5) +
                transformaString(Integer.toString(x),5)+
                transformaString(Integer.toString(y),5)+
                transformaString(Integer.toString(getCor().getRed()),5)+
                transformaString(Integer.toString(getCor().getGreen()),5)+
                transformaString(Integer.toString(getCor().getBlue()),5)+
                transformaString(Integer.toString(raioX),5)+
                transformaString(Integer.toString(raioY),5);
    }

    public void desenhar(Graphics g) {
        g.setColor(cor);
        g.drawOval(x- raioX, y- raioY, 2* raioX,2* raioY);
    }
}
