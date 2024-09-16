import java.awt.*;

public class Circulo extends Ponto {
    private int raio;

    public Circulo(int xCentro, int yCentro, int r, Color cor){
        super(xCentro, yCentro, cor);
        raio = r;
    }
    // getters e setters
    public int getRaio(){
        return raio;
    }
    public void setRaio(int r){
        raio = r;
    }
    // ---------------------------
    public void desenhar(Graphics g){
        g.setColor(cor);
        g.drawOval(x - raio, y - raio, raio, raio);
    }
}
