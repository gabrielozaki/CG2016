import java.awt.Graphics;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Animacao implements Runnable {
	private Esfera terra = new Esfera(145, 80, 100, 70, "img/terra.jpg");
	private Esfera lua = new Esfera(40, 100, 0, 40, "img/lua.jpg");
	private Algoritmos a = new Algoritmos();
	public JLabel area;

	public Animacao(JLabel label) {
		area = label;
	}

	@Override
	public void run() {
		int i = 0;
		boolean frente = true;

		// TODO Auto-generated method stub
		BufferedImage img = a.criaImagem(450, 300);
		BufferedImage textura = null;
		// carrega textura do espaco
		try {
			textura = ImageIO.read(new File("img/espaco.jpg"));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		// Prepara versao menor da textura
		BufferedImage escala = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);

		// Aqui começamos a manipular a textura
		Graphics g = escala.getGraphics();

		// Desenha a textura
		g.drawImage(textura, 0, 0, escala.getWidth(), escala.getHeight(), null);

		g.dispose();


		while (true) {

			if (i == 270) {
				frente = false;
			}
			if (i == 0) {
				frente = true;
			}

			if (frente) {
				i++;
			} else {
				i--;
			}

			img = a.criaImagem(450, 300);

			if (i < 135) {
				if (i % 10 == 0) {
					lua.raio++;
				}
			} else {
				if (i % 10 == 0) {
					lua.raio--;
				}
			}
			g = img.getGraphics();
			// g.drawImage( lua.gerar(), lua.x+i, lua.y, null );
			g.drawImage(escala, 0, 0, null);
			if (frente) {
				g.drawImage(terra.gerar(), terra.x, terra.y, null);
				g.drawImage(lua.gerar(), lua.x + i, lua.y, null);
			} else {
				g.drawImage(lua.gerar(), lua.x + i, lua.y, null);
				g.drawImage(terra.gerar(), terra.x, terra.y, null);
			}
			g.dispose();

			area.setIcon(new ImageIcon(img));
			try {
				Thread.sleep(33); // 1000 milliseconds is one second.
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}

		}
	}

}
