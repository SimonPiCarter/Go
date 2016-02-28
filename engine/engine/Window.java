package engine;

import javax.swing.*;


@SuppressWarnings("serial")
public class Window extends JFrame{

	public Window(int sizeX,int sizeY,String name){
		this.setVisible(true);
		this.setSize(sizeX,sizeY);
		this.setName(name);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
}
