package engine;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
public class Main {

	public static void main(String[] args) {
		Board board =new Board(9);
		Window window =new Window(500,500,"Go!");
		Panel pane =new Panel();
		Colors colorPlaying =Colors.WHITE;
		int moveX=0, moveY=0;
		MyMouseListener mouseListener = new MyMouseListener();
		window.setContentPane(pane);
		pane.addMouseListener(mouseListener);
		boolean legalMove=false;
		boolean gameOn=true;
		boolean playSkip=false;
		try
		{
		Colors.WHITE.img=ImageIO.read(new File("white_token.jpg"));
		Colors.BLACK.img=ImageIO.read(new File("black_token.jpg"));
		}catch(IOException e)
		{e.printStackTrace();}
		
		
		while(gameOn){
			while(!legalMove)
			{
				///choix du coup et verif de la légalité
				while(!mouseListener.isEventClicked()&&!playSkip){}//Boucle pour attendre un clic souris
				if(!playSkip){
				moveX=mouseListener.getBoardX();
				moveY=mouseListener.getBoardY();
				mouseListener.resetEvent();
				legalMove=board.isLegal(moveX,moveY,colorPlaying);}
			}
			
			if(!playSkip)board.setCell(moveX, moveY, colorPlaying);
			board.getCell(moveX,moveY).killCell(board);
			colorPlaying=colorPlaying.oppositeColor();
			moveX=0;
			moveY=0;
			
		}
	}
}
