package engine;

import java.util.ArrayList;

public class Cell {
	int coordX;
	int coordY;
	boolean dead;
	boolean done;
	boolean empty;
	Colors cellColor;
	
	//setters and getters
	public int getCoordX(){return this.coordX;}
	public int getCoordY(){return this.coordY;}
	public boolean isDead(){return this.dead;}
	public boolean isDone(){return this.done;}
	public Colors getCellColor(){return this.cellColor;}
	public void setDead(boolean dead){this.dead=dead;}
	public void setDone(boolean done){this.done=done;}
	public void setCellColor(Colors color){this.cellColor=color;}	
	
	
	public Cell(int x, int y){
		coordX=x;
		coordY=y;
		dead=false;
		done=false;
		cellColor=Colors.EMPTY;
		}
	public Cell(int x, int y,Colors color){
		coordX=x;
		coordY=y;
		dead=false;
		done=false;
		cellColor=color;
		}
	public Cell(Colors color){
		cellColor=color;
		}
	public Cell(Cell cell)
		{
			coordX=cell.getCoordX();
			coordY=cell.getCoordY();
			dead=false;
			done=false;
			cellColor=cell.getCellColor();
		}
	
	public String toString(){
		return "Cell[coordinate=("+this.coordX+";"+this.coordY+"), "+ 
		"color="+cellColor+"]\n";
	}

	//test the survivibility of the stone true=alive false=dead
	
	public boolean survivalTest(Board board){
		
		if(this.cellColor!=Colors.WHITE&&this.cellColor!=Colors.BLACK)
		{
			return true;
		}
		
		if(done){return true;}
		if(dead){return false;}
		
		//empty space aroud the stone?
		 if(this.cellNearby(board, Directions.EAST).getCellColor()!=Colors.EMPTY&&
			this.cellNearby(board, Directions.WEST).getCellColor()!=Colors.EMPTY&&
			this.cellNearby(board, Directions.SOUTH).getCellColor()!=Colors.EMPTY&&
			this.cellNearby(board, Directions.NORTH).getCellColor()!=Colors.EMPTY)
		 {
			if(this.cellColor.isEnemy(this.cellNearby(board, Directions.EAST).getCellColor())&&
				this.cellColor.isEnemy(this.cellNearby(board, Directions.WEST).getCellColor())&&
				this.cellColor.isEnemy(this.cellNearby(board, Directions.SOUTH).getCellColor())&&
				this.cellColor.isEnemy(this.cellNearby(board, Directions.NORTH).getCellColor()))
			{
				this.dead=true;
				 return false;//si entouré de pierre ennemie celle ci meurt
				 
			}else
			{
				return this.group(board,this.cellColor);
			}
		 }else
		 {
			 this.done=true;
			 return true;
		 }
	}
	
	
	//permet de savoir si la pierre dans la direction donnée
	//Si c'est vrai elle renvoye vrai (boolean version)
	
	public Cell cellNearby(Board board,Directions dir)
	{
		switch(dir){
		
			case NORTH:
				if(this.coordX-1>=0){
					return board.getCell(coordX-1, coordY);
				}else return new Cell(Colors.BORDER);//gestion des bords
			case SOUTH:
				if(this.coordX+1<board.getSize()){
					return board.getCell(coordX+1, coordY);
				}else return new Cell(Colors.BORDER);
			case WEST:
				if(this.coordY-1>=0){
					return board.getCell(coordX, coordY-1);
				}else return new Cell(Colors.BORDER);
			case EAST:
				if(this.coordY+1<board.getSize()){
					return board.getCell(coordX, coordY+1);
				}else return new Cell(Colors.BORDER);
			default: return null;
		}
	}
	private boolean group(Board board,Colors color){
		ArrayList<Cell> groupList=new ArrayList<Cell>();//curseur de lecture
		int liberties=0;
		groupList.add(this);
		if(done){return true;}
		if(dead){return false;}
		for(int t=0;t<groupList.size();t++)
		{
			if( groupList.get(t).cellNearby(board,Directions.WEST).getCellColor()==this.cellColor&&
				!groupList.contains(groupList.get(t).cellNearby(board,Directions.WEST)))
			{
				groupList.add(groupList.get(t).cellNearby(board,Directions.WEST));
			}
			
			if( groupList.get(t).cellNearby(board,Directions.EAST).getCellColor()==this.cellColor&&
				!groupList.contains(groupList.get(t).cellNearby(board,Directions.EAST)))
			{
				groupList.add(groupList.get(t).cellNearby(board,Directions.EAST));
			}
			
			if( groupList.get(t).cellNearby(board,Directions.SOUTH).getCellColor()==this.cellColor&&
				!groupList.contains(groupList.get(t).cellNearby(board,Directions.SOUTH)))
			{
				groupList.add(groupList.get(t).cellNearby(board,Directions.SOUTH));
			}
			
			if( groupList.get(t).cellNearby(board,Directions.NORTH).getCellColor()==this.cellColor&&
				!groupList.contains(groupList.get(t).cellNearby(board,Directions.NORTH)))
			{
				groupList.add(groupList.get(t).cellNearby(board,Directions.NORTH));
			}
			
		}
		
		for(int t=0;liberties==0&&t<groupList.size();t++)
		{
			if(groupList.get(t).cellNearby(board,Directions.WEST).getCellColor()==Colors.EMPTY){liberties++;}
			if(groupList.get(t).cellNearby(board,Directions.EAST).getCellColor()==Colors.EMPTY){liberties++;}
			if(groupList.get(t).cellNearby(board,Directions.NORTH).getCellColor()==Colors.EMPTY){liberties++;}
			if(groupList.get(t).cellNearby(board,Directions.SOUTH).getCellColor()==Colors.EMPTY){liberties++;}
		}
		
		if(liberties==0)
		{
			for(int t=0;t<groupList.size();t++)
			{
				groupList.get(t).setDead(true);//on met les pierres qui sont morte en état "mort"
			}
			return false;
		}
		else
		{
			for(int t=0;t+1<groupList.size();t++)
			{
				groupList.get(t).setDone(true);
			}
			return true;
		}
	}
	
	public void killCell(Board board)
	{
		this.cellNearby(board, Directions.SOUTH).survivalTest(board);
		this.cellNearby(board, Directions.NORTH).survivalTest(board);
		this.cellNearby(board, Directions.EAST).survivalTest(board);
		this.cellNearby(board, Directions.WEST).survivalTest(board);
		board.kill();
	}
}

	
