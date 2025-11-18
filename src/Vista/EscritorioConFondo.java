/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
/**
 *
 * @author Toto
 */
public class EscritorioConFondo extends JDesktopPane{
     
    private Image imagen;
    
    
    public EscritorioConFondo() {
        imagen = new ImageIcon(getClass().getResource("/imagenes/fondo.jpg")).getImage();
    }
@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagen != null) {
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
