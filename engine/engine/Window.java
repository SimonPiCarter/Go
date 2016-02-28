package engine;

import java.awt.*;

import javax.swing.*;


@SuppressWarnings("serial")
public class Window extends JFrame{

	public Window(int sizeX,int sizeY,String name){
		
		JPanel pan=new Panel();
		this.setSize(sizeX,sizeY);
		this.setName(name);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		pan.setBackground(Color.BLUE);
		
	}
}
