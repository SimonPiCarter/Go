package engine;

import java.util.ArrayList;
import java.util.Arrays;


public class Board implements Cloneable{
	private final int size;
	private Cell[][] tabBoard;
	private ArrayList<Action> playList= new ArrayList<Action>();
	public Board(int size){
		this.size=size;
		this.tabBoard= new Cell[this.size][this.size];
		
		for ( int x = 0 ; x < this.size ; ++ x ) {
			for ( int y = 0 ; y < this.size ; ++ y ) {
				this.tabBoard[x][y]=new Cell(x,y,Colors.EMPTY);
			}
		}
	}
	
	public Board(Board other){
		this.size=other.size;
		
		this.tabBoard= new Cell[this.size][this.size];
		
		for ( int x = 0 ; x < this.size ; ++ x ) {
			for ( int y = 0 ; y < this.size ; ++ y ) {
				this.tabBoard[x][y]=new Cell(x,y,other.getCell(x, y).getCellColor());
			}
		}
	}
	
	public void play(Action act)
	{
		if(!act.isSkip())
		{
			int y=act.getCoordY();
			int x=act.getCoordX();
			Colors color= act.getColorPlay();
			this.setCell(x, y, color);
			this.getCell(x,y).killCell(this);
			color.setBoardKo(this.clone());
		}
		playList.add(act);
	}
	
	public void replay(int offset)//offset must be <0
	{
		if(offset<0)
		{
			for ( int x = 0 ; x < this.size ; ++ x ) {
				for ( int y = 0 ; y < this.size ; ++ y ) {
					this.tabBoard[x][y]=new Cell(x,y,Colors.EMPTY);
				}
			}
			ArrayList<Action> tempList =new ArrayList<Action>();
			tempList=this.playList;
			this.playList=new ArrayList<Action>();
			for(int i=0;tempList.size()+offset>i;i++)
			{
				this.play(tempList.get(i));
			}
		}
		
	}
	
	public Board clone(){
		return new Board(this);
	}
	public String toString(){//test
		String str = "";
		int i=0;
		int x=0,y=0;
		while (i==0){
			str+=this.tabBoard[x][y].getCellColor()+", ";
			x++;
			if(x==this.size&&y<this.size){
				y++;
				x=0;
				str+="\n";
			}
			if(y==this.size){
				i++;
			} 
		}
		return str;
	}
	
	private boolean koEquals(Board board)
	{
		if(board.getSize()==this.size)
		{
			for(int x=0;x<this.size;x++)
			{
				for(int y=0;y<this.size;y++)
				{
					if(board.getCell(x, y).getCellColor()!=this.getCell(x, y).getCellColor())
					{
						return false;
					}
				}
			}
		}
		return true;
		
	}
	
	public Cell getCell(int x,int y){
		if(x>=0&&x<this.size&&y>=0&&y<this.size){
			return tabBoard[x][y];
		}else return new Cell(Colors.BORDER);
	}
	public void setCell(int x, int y, Colors color)
	{
		this.getCell(x,y).setCellColor(color);
	}
	public boolean isLegal(Action act){
		
		int posX=act.getCoordX();
		int posY=act.getCoordY();
		Colors color=act.getColorPlay(); 
		Board boardTemp=this.clone();
		int compteur=0;
		Cell currentCell=boardTemp.getCell(posX, posY);
		currentCell.setCellColor(color);
		
		
		if(currentCell.getCellColor()!=Colors.WHITE&&currentCell.getCellColor()!=Colors.BLACK)
		{
			return true;
		}
		
		Colors ennemy=currentCell.getCellColor().oppositeColor();
		//on creer un 2e tableau pour faire des essais 
		if(this.tabBoard[posX][posY].getCellColor()==Colors.EMPTY){//case vide? 1
			if( currentCell.cellNearby(boardTemp, Directions.WEST).getCellColor()!=Colors.EMPTY&&
				currentCell.cellNearby(boardTemp, Directions.EAST).getCellColor()!=Colors.EMPTY&&
				currentCell.cellNearby(boardTemp, Directions.SOUTH).getCellColor()!=Colors.EMPTY&&
				currentCell.cellNearby(boardTemp, Directions.NORTH).getCellColor()!=Colors.EMPTY){//si la case est entouré de pierre?
				if(!boardTemp.getCell(posX, posY).survivalTest(boardTemp))//est-ce du suicide(brut) doit renvoyer false sinon on peut jouer
				{
					if( currentCell.cellNearby(boardTemp, Directions.WEST).getCellColor()==ennemy&&
						currentCell.cellNearby(boardTemp, Directions.EAST).getCellColor()==ennemy&&
						currentCell.cellNearby(boardTemp, Directions.SOUTH).getCellColor()==ennemy&&
						currentCell.cellNearby(boardTemp, Directions.NORTH).getCellColor()==ennemy)
					{
						if(!currentCell.cellNearby(boardTemp, Directions.WEST).survivalTest(boardTemp))
						{
							compteur++;// pour compter si toutes les cases autour sont toutes vivantes
						}
						
						if(!currentCell.cellNearby(boardTemp, Directions.EAST).survivalTest(boardTemp))
						{
							compteur++;
						}
						
						if(!currentCell.cellNearby(boardTemp, Directions.SOUTH).survivalTest(boardTemp))
						{
							compteur++;
						}
						
						if(!currentCell.cellNearby(boardTemp, Directions.NORTH).survivalTest(boardTemp))
						{
							compteur++;
						}
						
						if(compteur!=0)
						{
							boardTemp.getCell(posX,posY).setDead(false);
							boardTemp.getCell(posX,posY).killCell(boardTemp);
							if(color.getBoardKo().koEquals(boardTemp))
							{
								return false;
							}
							return true;
						}
						else return false;// retourne faux si entuoré d'adversaire et n'en tue aucun
					}else return false;// si c'est un suicide et que c'est entouré de pierre allié c'est illegal C'EST FAUX CA
				}else return true;// si la piece ne meurt pas c'est authorisé
			}else return true;// si il y a une liberté disponible authorisé	
		}else return false;//1 si la case est deja ocuppé ce n'est pas autorisé //2 c'est pas du suicide= c'est jouable
		
		}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Board other = (Board) obj;
		if (!Arrays.deepEquals(tabBoard, other.tabBoard))
			return false;
		return true;
	}
	public int getSize()
	{
		return this.size;
	}
	public void kill()
	{
		for(int x=0;x<this.size;x++){
			for(int y=0;y<this.size;y++){
				if(this.tabBoard[x][y].isDead())
				{
					this.tabBoard[x][y].getCellColor().oppositeColor().scoreUp(1);
					this.tabBoard[x][y].setDead(false);
					this.tabBoard[x][y].setCellColor(Colors.EMPTY);
					
				}else
					this.tabBoard[x][y].setDone(false);
			}
		}
	}

}
