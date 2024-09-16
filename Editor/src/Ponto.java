import java.awt.*;

public class Ponto {

    protected int x, y;
    protected Color cor;
    // protected faz com que seja acessível às heranças mas não a outra classes não relacionadas

    public Ponto(int cx, int cy, Color qualCor){
        x = cx;
        y = cy;
        cor = qualCor;
    }

    public void setX(int cx){
        x = cx;
    }

    public void setY(int cy){
        y = cy;
    }

    public void setCor(Color novaCor){
        cor = novaCor;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public Color getCor(){
        return cor;
    }

    public void desenhar(Graphics g){
        g.setColor(cor);
        g.drawLine(x, y, x, y);
    }
}
