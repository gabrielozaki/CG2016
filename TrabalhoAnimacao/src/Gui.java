import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Gui extends JFrame {

	private JPanel contentPane;
	private Algoritmos a = new Algoritmos();
	private BufferedImage img = a.criaImagem(450, 300);

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui frame = new Gui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Gui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 480, 369);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAnimao = new JLabel("Animação");
		lblAnimao.setBounds(12, 12, 420, 313);
		contentPane.add(lblAnimao);
		
		
		//Gera um fundo
		lblAnimao.setIcon(new ImageIcon(this.img));
		lblAnimao.setSize(this.img.getWidth(), this.img.getHeight());
		
		//a.desenhaCirculoBresenham(img, terra.x, terra.y, terra.raio);
		//a.desenhaCirculoBresenham(img, lua.x, lua.y, lua.raio);
	
		//anima(lblAnimao);
		Animacao anima = new Animacao(lblAnimao);
		
		//a.animacao(lblAnimao, lua, terra);
		Thread t = new Thread(anima);
		t.start();
		
	}

}
