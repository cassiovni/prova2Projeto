package proxy_dao;

import java.awt.Dimension;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;


public class JButtonImage extends JButton{
    private ImagemProxy imagemProxy;

    public JButtonImage(ImagemProxy imagemProxy) {
        this.imagemProxy = imagemProxy;
        
        Image img = this.imagemProxy.getMiniatura();
        ImageIcon icon = new ImageIcon(img);
        icon.setImage(icon.getImage().getScaledInstance(100, 80, 100));
        setIcon(icon);
        setPreferredSize(new Dimension(100, 100));        
    }

    public ImagemProxy getImagemProxy() {
        return imagemProxy;
    }

    public void setImagemProxy(ImagemProxy imagemProxy) {
        this.imagemProxy = imagemProxy;
    }
    
}
