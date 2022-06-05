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
    
    public TokenImage(){
        dimension = 3;
        img_size = 720 / dimension;
        try {
            sprite = ImageIO.read(getClass().getResource("img-scaled.png"));
        } catch (IOException ex) {
            Logger.getLogger(TokenImage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static int getDimension(){
        return dimension;
    }
    
    public static void setDimension(int dim){
        dimension = dim;
        img_size = 720 / dimension;
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
        //System.out.println("X: "+location[0]+"  Y: "+location[1]+ " Size: "+location[2]);
        return sprite.getSubimage(location[0]*img_size, location[1]*img_size, img_size, img_size)
                .getScaledInstance(img_size-5, img_size-5, BufferedImage.SCALE_DEFAULT); 
        
    }
}
