package p6.puzzle.View;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;
import p6.puzzle.P6Puzzle;

/**
 * @author Antoni Payeras
 */
public class PuzzleImage extends JPanel{
    
    private P6Puzzle p6;
    private TokenImage ti;
    private BufferedImage bima;
    private int dimension;
    private int img_size;
    private int[][] table;
    private int speed;
    private ArrayList<int[][]> steps;

    
    public PuzzleImage(P6Puzzle p6){
        this.p6 = p6;
        this.dimension = 3;
        this.speed = 800;
        this.ti = new TokenImage();
        img_size = TokenImage.getImgSize();
    }

    public void repaint() {
        if (this.getGraphics() != null) {
            paint(this.getGraphics());
        }
    }

    public void paint(Graphics gr) {
        if (steps == null){
            loadImage(table);
            gr.drawImage(bima, 0, 0, this);
        } else {
            for (int[][] step: steps){
                loadImage(step);
                gr.drawImage(bima, 0, 0, this);
                bima = null;
                espera(speed);
            }  
        }  
    }
    
    
    private void loadImage(int[][] table){
        if (bima == null) {
            if (this.getWidth() > 0) {
                bima = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
                Graphics2D bima_graphics = bima.createGraphics();
                bima_graphics.setColor(new Color(217,255,240));
                bima_graphics.fillRect(0, 0, bima.getWidth(), bima.getHeight());
                if (table == null){
                    for (int i=0; i< dimension*dimension; i++){
                        int[] imgLoc = ti.getImageLocation(i);
                        bima_graphics.drawImage(ti.getSubImatge(i), imgLoc[0]*img_size, imgLoc[1]*img_size, null);
                    }
                } else {
                    for (int y=0;y<table.length;y++){
                        for(int x=0;x<table.length;x++){
                            bima_graphics.drawImage(ti.getSubImatge(table[x][y]), y*img_size, x*img_size, null);
                        }
                    }
                }
            }
        }
    }
    
    public void setDimension(int x){
        TokenImage.setDimension(x);
        dimension = x;
        img_size = TokenImage.getImgSize();
        bima = null;
        this.repaint();
    }
    
    public void setSteps(ArrayList<int[][]> steps){
        this.steps = steps;
        bima = null;
        this.repaint();
        this.steps = null;
    }
    
    public void setTable(int[][] table){
        this.table = table;
        bima = null;
        this.repaint();
        this.table = null;
    }
    
    public void setSpeed(int t){
        this.speed = t;
    }
    
    private void espera(long t) {
        try {
            Thread.sleep(t);
        } catch (Exception e) {

        }
    }

}
