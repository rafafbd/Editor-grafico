import java.awt.*;

public class Circulo extends Ponto {
    private int raio;

    public Circulo(int xCentro, int yCentro, int r, Color cor){
        super(xCentro, yCentro, cor);
        raio = r;
    }

    public int getRaio(){
        return raio;
    }
    public void setRaio(int r){
        raio = r;
    }

    public String toString(){
        return transformaString("c", 5) +
                transformaString(Integer.toString(x),5)+
                transformaString(Integer.toString(y),5)+
                transformaString(Integer.toString(getCor().getRed()),5)+
                transformaString(Integer.toString(getCor().getGreen()),5)+
                transformaString(Integer.toString(getCor().getBlue()),5)+
                transformaString(Integer.toString(raio),5);
    }

    public void desenhar(Graphics g){
        g.setColor(cor);
        g.drawOval(x-raio, y-raio, raio*2, raio*2);
    }
}
