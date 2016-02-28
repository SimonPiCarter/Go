package engine;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
public class Panel extends JPanel{
	
	public void paintComponent(Graphics g){
		try{
		g.drawImage(ImageIO.read(new File("background.jpg")),0,0,this.getWidth(),this.getHeight(),this);}
		catch(IOException e){e.printStackTrace();}
		
	}
}
