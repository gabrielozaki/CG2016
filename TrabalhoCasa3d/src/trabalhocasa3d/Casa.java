/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhocasa3d;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author gabrielozaki
 */
public class Casa {

    ArrayList<Ponto3d> Pontos = new ArrayList<>();
    Algoritmos a = new Algoritmos();

    public Casa(ArrayList<Ponto3d> Pontos) {
        for (Ponto3d ponto : Pontos) {
            this.Pontos.add(ponto);
        }
    }

    public BufferedImage projecaoFrontal() {
        BufferedImage img = Algoritmos.criaImagem(500, 500);
        ArrayList<Point> p = new ArrayList<>();
        for (Ponto3d ponto : this.Pontos) {
            p.add(new Point(ponto.x, ponto.y));
        }

        img = desenhaCasa(img, p);

        return img;
    }

    public BufferedImage projecaoCaveira( int alfa) {
        BufferedImage img = Algoritmos.criaImagem(500, 500);
        ArrayList<Point> p = new ArrayList<>();
        //novox = x + (z * coseno alfa )
        //novoy = y + (z * seno alfa)
        int novox, novoy;

        for (Ponto3d ponto : this.Pontos) {
            novox = (int) (ponto.x + (ponto.z * Math.cos(alfa)));
            novoy = (int) (ponto.y + (ponto.z * Math.sin(alfa)));
            p.add(new Point(novox,novoy));
        }
        
        img = desenhaCasa(img, p);
        return img;
    }

    private BufferedImage desenhaCasa(BufferedImage img, ArrayList<Point> p) {
        // 0 -> 1,2,4
        img = a.desenhaRetaBresenham(img, p.get(0), p.get(1));
        img = a.desenhaRetaBresenham(img, p.get(0), p.get(2));
        img = a.desenhaRetaBresenham(img, p.get(0), p.get(4));

        // 1 -> 3,5
        img = a.desenhaRetaBresenham(img, p.get(1), p.get(3));
        img = a.desenhaRetaBresenham(img, p.get(1), p.get(5));

        // 2 -> 3,6
        img = a.desenhaRetaBresenham(img, p.get(2), p.get(3));
        img = a.desenhaRetaBresenham(img, p.get(2), p.get(6));

        //3 -> 7
        img = a.desenhaRetaBresenham(img, p.get(3), p.get(7));

        //4 -> 5,6,8
        img = a.desenhaRetaBresenham(img, p.get(4), p.get(5));
        img = a.desenhaRetaBresenham(img, p.get(4), p.get(6));
        img = a.desenhaRetaBresenham(img, p.get(4), p.get(8));

        //5 -> 7,8
        img = a.desenhaRetaBresenham(img, p.get(5), p.get(7));
        img = a.desenhaRetaBresenham(img, p.get(5), p.get(8));

        //6 -> 7,9
        img = a.desenhaRetaBresenham(img, p.get(6), p.get(7));
        img = a.desenhaRetaBresenham(img, p.get(6), p.get(9));

        //7 -> 9
        img = a.desenhaRetaBresenham(img, p.get(7), p.get(9));

        //8 -> 9
        img = a.desenhaRetaBresenham(img, p.get(8), p.get(9));

        return img;
    }
    
    public void escala(double x, double y, double z){
        int novox,novoy,novoz;
        ArrayList<Ponto3d> p = new ArrayList<>();
        for (Ponto3d ponto : this.Pontos) {
            novox = Math.round((float) (ponto.x * x));
            novoy = Math.round((float) (ponto.y * y));
            novoz = Math.round((float) (ponto.z * z));
            p.add(new Ponto3d(novox,novoy,novoz));
        }
        Pontos.clear();
        for (Ponto3d ponto : p) {
            this.Pontos.add(ponto);
        }
        
        
    }
    
    public void rotacionaX(double alfa){
        int novox,novoy,novoz;
        ArrayList<Ponto3d> p = new ArrayList<>();
        for (Ponto3d ponto : this.Pontos) {
            novox = ponto.x;
            novoy = (int) ((ponto.y * Math.cos(alfa))-(ponto.z * Math.sin(alfa)));
            novoz = (int) ((ponto.y * Math.sin(alfa))+(ponto.z * Math.cos(alfa)));;
            p.add(new Ponto3d(novox,novoy,novoz));
        }
        Pontos.clear();
        for (Ponto3d ponto : p) {
            this.Pontos.add(ponto);
        }        
    }
    
    public void rotacionaY(double alfa){
        int novox,novoy,novoz;
        ArrayList<Ponto3d> p = new ArrayList<>();
        for (Ponto3d ponto : this.Pontos) {
            novoy = ponto.y;
            novox = (int) ((ponto.x * Math.cos(alfa))+(ponto.z * Math.sin(alfa)));
            novoz = (int) (-1 * (ponto.x * Math.sin(alfa))+(ponto.z * Math.cos(alfa)));
            p.add(new Ponto3d(novox,novoy,novoz));
        }
        Pontos.clear();
        for (Ponto3d ponto : p) {
            this.Pontos.add(ponto);
        }        
    }
    
    public void rotacionaZ(double alfa){
        int novox,novoy,novoz;
        ArrayList<Ponto3d> p = new ArrayList<>();
        for (Ponto3d ponto : this.Pontos) {
            novoz = ponto.z;
            novox = (int) ((ponto.x * Math.cos(alfa))-(ponto.y * Math.sin(alfa)));
            novoy = (int) ((ponto.x * Math.sin(alfa))+(ponto.y * Math.cos(alfa)));
            p.add(new Ponto3d(novox,novoy,novoz));
        }
        Pontos.clear();
        for (Ponto3d ponto : p) {
            this.Pontos.add(ponto);
        }        
    }
}
