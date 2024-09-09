import java.awt.*;

public class Ponto {

    private int x, y;
    private Color cor;

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

    public void desenha(Color cor, Graphics g){
        g.setColor(cor);
        g.drawLine(getX(), getY(), getX(), getY());
    }
}
