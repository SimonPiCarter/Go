package engine;

import java.util.Arrays;


public class Board implements Cloneable{
	private final int size;
	private Cell[][] tabBoard;
	public Board(int size){
		this.size=size;
		this.tabBoard= new Cell[this.size][this.size];
		int y=0;
		
		for(int x=0;y<=this.size;x++){
			this.tabBoard[x][y]=new Cell(x,y);
			x++;
			if(x==this.size&&y<this.size){
				y++;
				x=0;
			}
		}
	}
	public Board newClone(){
		Board boardClone = new Board(this.size);
		boardClone.setTabBoard(this.getTabBoard());
		return boardClone;
	}
	public String toString(){//test
		String str = "";
		int i=0;
		int x=0,y=0;
		while (i==0){
			str+=this.tabBoard[x][y];
			x++;
			if(x==this.size&&y<this.size){
				y++;
				x=0;
			}
			if(y==this.size){
				i++;
			} 
		}
		return str;
	}
	private Cell[][] getTabBoard(){
		return this.tabBoard;}
	private void setTabBoard(Cell[][] tab){
		this.tabBoard=tab;
	}
	public Cell getCell(int x,int y){
		if(x>0||x<=this.size||y>0||y<=this.size){
			return tabBoard[x][y];
		}else return new Cell(Colors.BORDER);
	}
	public void setCell(int x, int y, Colors color)
	{
		this.getCell(x,y).setCellColor(color);
	}
	public boolean isLegal(int posX,int posY,Colors color){
		Board boardTemp=this.newClone();
		int compteur=0;
		int compteuA=0;
		Colors ennemy=null;	
		switch(color){
			case WHITE:
				ennemy=Colors.BLACK;
			break;
			case BLACK:
				ennemy=Colors.WHITE;
			break;
			case EMPTY:
				ennemy=null;
			default:return false;
		}
		boardTemp.getCell(posX, posY).setCellColor(color);//on creer un 2e tableau pour faire des essais 
		if(this.tabBoard[posX][posY].getCellColor()==Colors.EMPTY){//case vide? 1
			if( boardTemp.getCell(posX-1,posY).getCellColor()!=Colors.EMPTY&&
				boardTemp.getCell(posX+1,posY).getCellColor()!=Colors.EMPTY&&
				boardTemp.getCell(posX,posY-1).getCellColor()!=Colors.EMPTY&&
				boardTemp.getCell(posX,posY+1).getCellColor()!=Colors.EMPTY){//si la case est entouré de pierre?
				if(!boardTemp.getCell(posX, posY).survivalTest(boardTemp))//est-ce du suicide(brut) doit renvoyer false sinon on peut jouer
				{
					if( boardTemp.getCell(posX-1,posY).getCellColor()==ennemy&&
						boardTemp.getCell(posX+1,posY).getCellColor()==ennemy&&
						boardTemp.getCell(posX,posY-1).getCellColor()==ennemy&&
						boardTemp.getCell(posX,posY+1).getCellColor()==ennemy)
					{
						if(boardTemp.getCell(posX-1,posY).survivalTest(this))
						{
							if(color.getBoardKo().equals(boardTemp)){//situation de Ko? verif si le equals verifie toutes las cases du tableau
								return false;
							}else compteuA++;
						}else compteur++;// pour compter si toutes les cases autour sont toutes vivantes
						
						if(boardTemp.getCell(posX+1,posY).survivalTest(this))
						{
							if(color.getBoardKo().equals(boardTemp)){
								return false;
							}else compteuA++;
						}else compteur++;
						
						if(boardTemp.getCell(posX,posY-1).survivalTest(this))
						{
							if(color.getBoardKo().equals(boardTemp)){
								return false;
							}else compteuA++;
						}else compteur++;
						
						if(boardTemp.getCell(posX,posY+1).survivalTest(this))
						{
							if(color.getBoardKo().equals(boardTemp)){
								return false;
							}else compteuA++;
						}else compteur++;
						
						if(compteur==4)return false;// retourne faux si entuoré d'adversaire et n'en tue aucun
						if(compteuA>1)return true;
					}else return false;// si c'est un suicide et que c'est entouré de pierre allié c'est illegal
				}else return true;// si la piece ne meurt pas c'est authorisé
			}else return true;// si il y a une liberté disponible authorisé	
		}return false;//1 si la case est deja ocuppé ce n'est pas autorisé //2 c'est pas du suicide= c'est jouable

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
		int y=0;
		for(int x=0;y<=this.size;x++){
			
			if(this.tabBoard[x][y].isDead())
			{
				this.tabBoard[x][y].getCellColor().oppositeColor().scoreUp(1);
				this.tabBoard[x][y].setDead(false);
				this.tabBoard[x][y].setCellColor(Colors.EMPTY);
			}else this.tabBoard[x][y].setDone(false);
			x++;
			if(x==this.size&&y<this.size){
				y++;
				x=0;
			}
		}
	}
}
