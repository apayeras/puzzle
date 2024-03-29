package p6.puzzle.View;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * @author Antoni Payeras
 */
public  class TokenImage {
    
    private BufferedImage sprite;
    private static int dimension;
    private static int img_size;
    private static final int panelSize = 650;
    
    public TokenImage(){
        dimension = 3;
        img_size = panelSize / dimension;
        try {
            sprite = ImageIO.read(getClass().getResource("perro.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(TokenImage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static int getDimension(){
        return dimension;
    }
    
    public static void setDimension(int dim){
        dimension = dim;
        img_size = panelSize / dimension;
    }
    
    public static int getImgSize(){
        return img_size;
    }
    
    public int[] getImageLocation(int num_img){
        
        int posX = num_img / dimension;
        int posY = num_img % dimension;
        
        return new int[]{posX, posY};
    }
    
    public Image getSubImatge(int num_img){
        if (num_img == dimension*dimension-1) return null;
        
        int [] location = getImageLocation(num_img);
        return sprite.getSubimage(location[0]*img_size, location[1]*img_size, img_size, img_size)
                .getScaledInstance(img_size-5, img_size-5, BufferedImage.SCALE_DEFAULT); 
        
    }
}
