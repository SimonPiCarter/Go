package engine;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
public class Main {

	public static void main(String[] args) {
		Board board =new Board(9);
		Window window =new Window(576,576,"Go!");
		Panel pane =new Panel(board);
		window.setContentPane(pane);
		Colors colorPlaying =Colors.WHITE;
		int moveX=0, moveY=0;
		MyMouseListener mouseListener = new MyMouseListener();
		pane.addMouseListener(mouseListener);
		boolean legalMove=false;
		boolean gameOn=true;
		boolean playSkip=false;
		try
		{
		Colors.WHITE.img=ImageIO.read(new File("white_token.png"));
		Colors.BLACK.img=ImageIO.read(new File("black_token.png"));
		}catch(IOException e)
		{e.printStackTrace();}
		
		while(gameOn){
			while(!legalMove)
			{
				///choix du coup et verif de la légalité
				while(!mouseListener.isEventClicked()&&!playSkip){}//Boucle pour attendre un clic souris
				if(!playSkip){
				moveX=mouseListener.getBoardX()/64;
				moveY=mouseListener.getBoardY()/64;
				mouseListener.resetEvent();
				legalMove=board.isLegal(moveX,moveY,colorPlaying);}
			}
			
			if(!playSkip)
			{
				board.setCell(moveX, moveY, colorPlaying);
				//board.getCell(moveX,moveY).killCell(board);
				colorPlaying=colorPlaying.oppositeColor();
				legalMove = false;
				moveX=0;
				moveY=0;
				pane.repaint();
			}
			
		}
	}
}
