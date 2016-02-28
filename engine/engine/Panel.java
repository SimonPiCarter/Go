package engine;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
public class Panel extends JPanel{
	Board board;
	public Panel(Board board)
	{
		this.board=board;
	}
	public void paintComponent(Graphics g){

		
		try{
			g.drawImage(ImageIO.read(new File("background.png")),0,0,this.getWidth(),this.getHeight(),this);
			}
		catch(IOException e)
			{e.printStackTrace();}
		
		
		for ( int x = 0 ; x < board.getSize() ; ++ x ) {
			for ( int y = 0 ; y < board.getSize() ; ++ y ) {
				this.drawStone(g,board.getCell(x,y));
			}
		}
	}

	
	private void drawStone(Graphics g, Cell cell) {
		g.drawImage(
				cell.getCellColor().img, 
				cell.getCoordX()*64*this.getWidth()/576, 
				cell.getCoordY()*64*this.getHeight()/576, 
				64*this.getWidth()/576, 
				64*this.getHeight()/576, this);
	}

}
