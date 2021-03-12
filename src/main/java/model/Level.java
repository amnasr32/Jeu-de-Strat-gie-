
package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Level  implements Serializable {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = -1092342944633849329L;
	private Grid grid;
	
	/*
	 * Le Constructeur
	 */
	Level() {
	  
	}
	
	
	public Grid GetGrid() {
		return this.grid;
	}
	
	
	public void SetGrid(Grid grid) {
		this.grid=grid;
	}
	
	
	public void createLevel() {
		//pour sérialiser la grille de départ dans un fichier level1 dans le dossier Levels
        try {
			FileOutputStream fos = new FileOutputStream("src/main/Levels/level1");
		    ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(this.GetGrid());
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * @return la grille (la tableau de cellules qu'on a sérialisé
	 */
	
	public Grid showLevel() {
		//desérialiser le niveau 
	    this.SetGrid(null);
		try {
			String str ="src/main/Levels/level1";
			FileInputStream fis = new FileInputStream(str);
			ObjectInputStream ois = new ObjectInputStream(fis);
			this.SetGrid((Grid )ois.readObject()); 
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return this.GetGrid();
		
	}
	
}

