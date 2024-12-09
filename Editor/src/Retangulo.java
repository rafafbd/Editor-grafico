import java.awt.*;

public class Retangulo extends Ponto {
    private int largura, altura;
    // eu mudei os nomes para raioX e raioY porque fica mais claro que raioA e raioB
    // apaga esse comentario depois que voce ler


    public Retangulo(int x1, int y1, int largura, int altura, Color novaCor) {
        super(x1, y1, novaCor);
        setAltura(altura);
        setLargura(largura);
    }


    private void setLargura(int largura){this.largura = largura;}

    private void setAltura(int altura){this.altura = altura;}

    public String toString(){
        return transformaString("r", 5) +
                transformaString(Integer.toString(x),5)+
                transformaString(Integer.toString(y),5)+
                transformaString(Integer.toString(getCor().getRed()),5)+
                transformaString(Integer.toString(getCor().getGreen()),5)+
                transformaString(Integer.toString(getCor().getBlue()),5)+
                transformaString(Integer.toString(largura),5)+
                transformaString(Integer.toString(altura),5);
    }

    public void desenhar(Graphics g) {
        g.setColor(cor);
        g.drawRect(getX(), getY(), largura, altura);
    }
}
