import java.awt.Graphics;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Esfera {
	public int x;
	public int y;
	public int z;
	public int raio;
	public BufferedImage textura;
	public String arquivo;

	public Esfera(int xc, int yc, int zc, int raioc, String arqc) {
		this.x = xc;
		this.y = yc;
		this.z = zc;
		this.raio = raioc;
		this.arquivo = arqc;

		try {
			textura = ImageIO.read(new File(this.arquivo));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

	}


	public BufferedImage gerar() {
		// Prepara versao menor da textura
		BufferedImage escala = new BufferedImage(this.raio * 2, this.raio * 2, BufferedImage.TYPE_INT_ARGB);
		// Aqui começamos a manipular a textura
		Graphics g = escala.getGraphics();
		// Recortamos um circulo
		g.setClip(new Ellipse2D.Float(0, 0, this.raio * 2, this.raio * 2));


		// Desenha a textura
		g.drawImage(this.textura, 0, 0, escala.getWidth(), escala.getHeight(), null);

		g.dispose();

		return escala;
	}

}
