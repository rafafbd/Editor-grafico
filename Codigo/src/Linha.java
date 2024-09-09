import java.awt.*;
public class Linha  extends Ponto {

    private Ponto pontoFinal;

    public Linha(int x1, int y1, int x2, int y2, Color novaCor)
    {
        super(x1,y1, novaCor);
        pontoFinal = new Ponto(x2,y2, novaCor);
    }
}
