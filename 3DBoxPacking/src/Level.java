import java.util.ArrayList;


public class Level {

	public ArrayList<Box> packedBoxes;
	
	public Level(){
		
		packedBoxes = new ArrayList<Box>();
		
	}
	public void add(Box box){
		
		this.packedBoxes.add(box);
		
		
	}
	
}
