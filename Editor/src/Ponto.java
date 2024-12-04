import java.awt.*;

public class Ponto {

    protected int x, y;
    protected Color cor;


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

    public String transformaString(String valor, int quantasPosicoes) {
        String cadeia = valor;
        while (cadeia.length() < quantasPosicoes)
            cadeia = cadeia + " ";
        return cadeia.substring(0,quantasPosicoes);
    }

    // para gravar em arquivo depois
    public String toString(){
        return transformaString("p", 5) +
                transformaString(Integer.toString(x),5)+
                transformaString(Integer.toString(y),5)+
                transformaString(Integer.toString(getCor().getRed()),5)+
                transformaString(Integer.toString(getCor().getGreen()),5)+
                transformaString(Integer.toString(getCor().getBlue()),5);
    }

    // aqui no metodo desenhar tem algo meio estranho eu achei
    // cada classe desenhavel tem uma cor como atributo, entao eu nao coloquei a cor como parametro
    // porque a classe ja eh pra ter ela
    // apaga esse comentario depois que voce ler
    public void desenhar(Graphics g){
        g.setColor(cor);
        g.drawLine(x, y, x, y);
    }
}
