/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhocasa3d;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

/**
 *
 * @author gabrielozaki
 */
public class Algoritmos {
    
    public static void Algoritmos() {
        
    }
    
    public static BufferedImage criaImagem(int width, int heigth) {
        BufferedImage img = new BufferedImage(width, heigth, BufferedImage.TYPE_INT_ARGB);
        Color cor = new Color(255, 255, 255);
        
        int[] data = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
        Arrays.fill(data, cor.getRGB());
        return img;
    }

    //Obtem angulo entre dois pontos
    public static double getAngulo(Point ini, Point fim) {
        //Armazena as coordenads
        int x1 = (int) ini.getX();
        int y1 = (int) ini.getY();
        int x2 = (int) fim.getX();
        int y2 = (int) fim.getY();
        //O angulo é calculado pelo arcotangente de x,y multiplicado por 180 e divido por pi(para passarmos de radianos para graus)
        double angulo = Math.atan2((x2 - x1), (y2 - y1)) * 180 / Math.PI;
        //Ajuste, o angulo zero é considerado o norte por essa formula, enquanto no nosso uso queremos o leste como angulo zero
        angulo -= 90;
        //SE o angulo der negativo, alteramos sua rotação
        if (angulo < 0) {
            return (360 + angulo);
        } else {
            return (angulo);
        }
    }
    
    public static double getDistancia(Point ini, Point fim) {
        //Armazena as coordenads
        int x1 = (int) ini.getX();
        int y1 = (int) ini.getY();
        int x2 = (int) fim.getX();
        int y2 = (int) fim.getY();
        int deltax, deltay;
        
        if (x1 >= x2) {
            deltax = x1 - x2;
        } else {
            deltax = x2 - x1;
        }
        
        if (y1 >= y2) {
            deltay = y1 - y2;
        } else {
            deltay = y2 - y1;
        }
        
        return Math.sqrt((deltax * deltax) + (deltay * deltay));
    }

    //Senha um ponto nas cordenadas de p
    public BufferedImage desenhaPonto(BufferedImage img, Point p) {
        //Pega as coordenadas
        int x = (int) p.getX();
        int y = (int) p.getY();

        //Cria a cor preta
        Color cor = new Color(0, 0, 0);

        //Um pixel seria muito dificil de ver
        //Então pintamos um quadrado de 3x3
        //Percorre na horizontal
        for (int i = x - 1; i <= x + 1; i++) {
            //percorre na vertical
            for (int j = y - 1; j <= y + 1; j++) {
                //pinta o pixel
                img.setRGB(i, j, cor.getRGB());
            }
        }
        return img;
    }

    //Devolve a imagem com a reta desenhada utilizando a forma parametrica para desenhar
    //NOTA: Esse é considerado um algoritmo ruim, pois dependendo da precisão ele pode pintar o mesmo pixel duas vezes ou nenhuma vez(falhado)
    public BufferedImage desenhaRetaParametrica(BufferedImage img, Point ini, Point fim, int precisao) {
        int xini, xfim, tmpx;
        int yini, yfim, tmpy;
        double teta;
        xini = (int) (ini.getX());
        yini = (int) (ini.getY());
        xfim = (int) (fim.getX());
        yfim = (int) (fim.getY());
        Color cor = new Color(0, 0, 0);
        //O numero de repetições será igual a precisão, pontanto uma reta com precisão 1000, terá o laço executado 1000 vezes
        for (int i = 0; i <= precisao; i++) {
            //Desenha reta usando a formula parametrica 
            teta = (double) i / precisao;
            //Formula parametrica
            tmpx = (int) ((teta * xini) + ((1 - teta) * xfim));
            tmpy = (int) ((teta * yini) + ((1 - teta) * yfim));
            
            img.setRGB(tmpx, tmpy, cor.getRGB());
            
        }
        return img;
    }

    //Devolve a imagem com a reta desenhada utilizando o algoritmo de Bresenham
    public BufferedImage desenhaRetaBresenham(BufferedImage img, Point ini, Point fim) {
        double angulo;
        int xini, yini, xfim, yfim;

        //Obtemos os pontos
        xini = (int) (ini.getX());
        yini = (int) (ini.getY());
        xfim = (int) (fim.getX());
        yfim = (int) (fim.getY());

        //Bresenham
        //Considerando um circulo trigonometrico invertido(o java mapeia a imagem de baixo pra cima, então o algoritmo funciona de 315 a 360 graus)
        angulo = getAngulo(ini, fim);
        /*
             Octantes:
             \5|6/
             4\|/7
            ---+---
             3/|\0
             /2|1\
            
         */

        //O bresenham só funciona normalmente no primeiro octante, todas as outras soluções requerem adaptações
        if (angulo > 315 && angulo <= 360) {
            //quadratne 0
            img = bresenhamOct0(img, xini, yini, xfim, yfim);
        } else if (angulo > 270 && angulo <= 315) {
            //Quadrante 1
            img = bresenhamOct1(img, xini, yini, xfim, yfim);
        } else if (angulo > 225 && angulo <= 270) {
            //Quadrantre 2
            img = bresenhamOct2(img, xini, yini, xfim, yfim);
        } else if (angulo > 180 && angulo <= 225) {
            //quadratne 3
            img = bresenhamOct3(img, xini, yini, xfim, yfim);
        } else if (angulo > 135 && angulo <= 180) {
            //quadratne 4
            //igual ao quadrante 0 so que invertendo ini e fim  
            img = bresenhamOct0(img, xfim, yfim, xini, yini);
        } else if (angulo > 90 && angulo <= 135) {
            //Quadrante 5
            //igual ao quadrante 0 so que invertendo ini e fim  
            img = bresenhamOct1(img, xfim, yfim, xini, yini);
        } else if (angulo > 45 && angulo <= 90) {
            //Quadrantre 6
            //igual ao quadrante 2 so que invertendo ini e fim
            img = bresenhamOct2(img, xfim, yfim, xini, yini);
        } else {
            //quadratne 7
            //Igual ao 3 so que inverte ini e fim
            img = bresenhamOct3(img, xfim, yfim, xini, yini);
        }
        return img;
    }
    
    private BufferedImage bresenhamOct0(BufferedImage img, int xini, int yini, int xfim, int yfim) {
        //O Bresenham utiliza os deltas para definir qual pixel pintar
        double deltax, deltay, deltaerr;
        //Definimos um valor inicial para o erro, ele será corrigido a cada laço
        double erro = -1.0;

        //Esse octante executa o bresenham puro
        int tmpx, tmpy;
        Color cor = new Color(0, 0, 0);
        //Os deltas são a diferençaentre as cordenadas entre dois pontos
        deltax = xfim - xini;
        deltay = yfim - yini;

        //Evitamos aqui uma divisão por zero
        if (deltax != 0) {
            deltaerr = Math.abs(deltay / deltax);
        } else {
            deltaerr = Math.abs(deltay / (deltax + 1));
        }

        //iniciamos com o y no y-inicial
        tmpy = yini;
        //Percorremos o eixo x
        for (tmpx = xini; tmpx < xfim - 1; tmpx++) {
            //pinta o ponto
            if (verificaDentro(img, tmpx, tmpy)) {
                img.setRGB(tmpx, tmpy, cor.getRGB());
            }
            //Soma ao erro o valor absoluto da divisão de deltay/deltax
            erro = erro + deltaerr;
            //Quando o erro se torna positivo, é sinal que devemos deslocar um pixel na direção y
            if (erro >= 0.0) {
                //desloca o pixel
                tmpy++;
                //atualizamos o erro
                erro = erro - 1.0;
            }
        }
        
        return img;
    }
    
    private BufferedImage bresenhamOct1(BufferedImage img, int xini, int yini, int xfim, int yfim) {
        //O Bresenham utiliza os deltas para definir qual pixel pintar
        double deltax, deltay, deltaerr;
        //Definimos um valor inicial para o erro, ele será corrigido a cada laço
        double erro = -1.0;

        //Esse octante executa o bresenham alterado
        int tmpx, tmpy;
        Color cor = new Color(0, 0, 0);

        //Será necessário inverter algumas veriaveis para que a linha base seja o eixo y e o algoritmo funcione
        deltax = xfim - xini;
        
        deltay = yfim - yini;

        //inverte a divisao
        //Agora tratamos a linha base como sendo o eixo y
        if (deltay != 0) {
            deltaerr = Math.abs(deltax / deltay);
        } else {
            deltaerr = Math.abs(deltax / (deltay + 1));
        }

        //invertemos aqui o tmpx e o tmp y
        //Nota-se que o comportamento deles são quase iguais, não fica na mesma função, mas alterando os parametros
        //Devido a essa inversão gerar problemas no desenho
        tmpx = xini;
        for (tmpy = yini; tmpy < yfim - 1; tmpy++) {
            img.setRGB(tmpx, tmpy, cor.getRGB());
            erro = erro + deltaerr;
            if (erro >= 0.0) {
                tmpx++;
                erro = erro - 1.0;
            }
        }
        return img;
    }
    
    private BufferedImage bresenhamOct2(BufferedImage img, int xini, int yini, int xfim, int yfim) {
        //O Bresenham utiliza os deltas para definir qual pixel pintar
        double deltax, deltay, deltaerr;
        //Definimos um valor inicial para o erro, ele será corrigido a cada laço
        double erro = -1.0;

        //Esse octante executa o bresenham alterado
        int tmpx, tmpy;
        Color cor = new Color(0, 0, 0);
        //Será necessário inverter algumas veriaveis para que a linha base seja o eixo y e o algoritmo funcione
        deltax = xini - xfim;
        
        deltay = yfim - yini;

        //inverte a divisao
        if (deltay != 0) {
            deltaerr = Math.abs(deltax / deltay);
        } else {
            deltaerr = Math.abs(deltax / (deltay + 1));
        }

        //invertemos aqui o tmpx e o tmp y
        tmpx = xini;
        for (tmpy = yini; tmpy < yfim - 1; tmpy++) {
            if (verificaDentro(img, tmpx, tmpy)) {
                img.setRGB(tmpx, tmpy, cor.getRGB());
            }
            erro = erro + deltaerr;
            if (erro >= 0.0) {
                //Devido ao deslocamenteto ser invertido
                //Devemos nos mover para a esquerda agora
                tmpx--;
                erro = erro - 1.0;
            }
        }
        return img;
    }
    
    private BufferedImage bresenhamOct3(BufferedImage img, int xini, int yini, int xfim, int yfim) {
        //O Bresenham utiliza os deltas para definir qual pixel pintar
        double deltax, deltay, deltaerr;
        //Definimos um valor inicial para o erro, ele será corrigido a cada laço
        double erro = -1.0;

        //Esse octante executa o bresenham alterado
        int tmpx, tmpy;
        Color cor = new Color(0, 0, 0);
        //inverte a coordenada X
        deltax = xini - xfim;
        deltay = yfim - yini;
        
        if (deltax != 0) {
            deltaerr = Math.abs(deltay / deltax);
        } else {
            deltaerr = Math.abs(deltay / (deltax + 1));
        }

        //começamos a desenhar a partir do fim
        //O desenho é praticamente espelhado ao octante 0
        tmpy = yfim;
        for (tmpx = xfim; tmpx < xini - 1; tmpx++) {
            if (verificaDentro(img, tmpx, tmpy)) {
                img.setRGB(tmpx, tmpy, cor.getRGB());
            }
            erro = erro + deltaerr;
            if (erro >= 0.0) {
                tmpy--;
                erro = erro - 1.0;
            }
        }
        
        return img;
    }

    //Desenha um circulo de centro xini,yini e de raio = raio
    public BufferedImage desenhaCirculoBresenham(BufferedImage img, int xini, int yini, double raio) {
        int x, y, err;
        int sizey = img.getHeight();
        int sizex = img.getWidth();
        System.out.println(sizex);
        System.out.println(sizey);
        Color cor = new Color(0, 0, 0);
        //O x inicial será = o raio
        x = (int) raio;
        //O y inicial será igual a zero
        y = 0;
        //erro será igual a zero
        err = 0;

        //Como funciona, no primeiro instante o y zerado e o x igual ao raio, ele fará o primeiro ponto no eixo x igual ao raio
        //No proximo laço ele irá deslocar um pouco para cima(no mesmo quadrante), porem atualizando o x para manter o raio constante
        while (x >= y) {
            //Aqui é utilizado uma otimização para desenhar os 8 quadrantes ao mesmo tempo
            //o numero ao lado representa o quadrante
            if (verificaDentro(img, xini + x, yini + y)) {
                img.setRGB(xini + x, yini + y, cor.getRGB());//0
            }
            if (verificaDentro(img, xini + y, yini + x)) {
                img.setRGB(xini + y, yini + x, cor.getRGB());//1
            }
            if (verificaDentro(img, xini - y, yini + x)) {
                img.setRGB(xini - y, yini + x, cor.getRGB());//2
            }
            if (verificaDentro(img, xini - x, yini + y)) {
                img.setRGB(xini - x, yini + y, cor.getRGB());//3
            }
            if (verificaDentro(img, xini - x, yini - y)) {
                img.setRGB(xini - x, yini - y, cor.getRGB());//4
            }
            if (verificaDentro(img, xini - y, yini - x)) {
                img.setRGB(xini - y, yini - x, cor.getRGB());//5

            }
            if (verificaDentro(img, xini + y, yini + x)) {
                img.setRGB(xini + y, yini - x, cor.getRGB());//6            
            }
            if (verificaDentro(img, xini + y, yini + x)) {
                img.setRGB(xini + x, yini - y, cor.getRGB());//7
            }
            //O y se desloca para cima(relativo ao quadrante) a todo laço
            y += 1;
            //O erro se atualiza
            err += 1 + 2 * y;
            //Se esta formula der maior que zero, é necessário deslocar o x
            if (2 * (err - x) + 1 > 0) {
                //Desloca o x para a esquerda para manter o raio constante
                x -= 1;
                err += 1 - 2 * x;
            }
        }
        
        return img;
    }
    
    private static boolean verificaDentro(BufferedImage img, int x, int y) {
        int xmax = img.getWidth();
        int ymax = img.getHeight();
        
        return x < xmax && x >= 0 && y < ymax && y >= 0;
        
    }
}
