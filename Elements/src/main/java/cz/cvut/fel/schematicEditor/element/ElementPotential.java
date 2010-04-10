package cz.cvut.fel.schematicEditor.element;

import java.util.Stack;

/**
 * 
 * @author Karel
 * 
 * The class concepted as static in GUI,
 * gives unique potential information
 * in system or readable format
 * 
 */

public class ElementPotential {
	
	private static Stack<Integer> lineOfNos; 	// queue of numbers
	private static Stack<String>  lineOfNames; 	// queue of text names
	private static Stack<Integer> lineOfIDs; 	// queue of IDs
	
	public ElementPotential() {
		/**
		 * @author Karel
		 * constructor
		 * 
		 **/
		
		lineOfNos   = new Stack<Integer>();
		lineOfNames = new Stack<String>();
		lineOfIDs 	= new Stack<Integer>();
	}
	
	public String getName(int ID) {
		
		// if there isn't already initialised array
		if(lineOfIDs.size()==0)
			{
			createNew(ID);
			return "1";
			}
		 else
		 {
			 int pozice = lineOfIDs.search(ID);
			 
			 if(pozice == -1)
				 {
				 createNew(ID);
				 return lineOfNos.lastElement().toString();
				 }
			 
			 
			 if(lineOfNames.get(pozice).length()>0)
				 return lineOfNames.get(pozice);
			 	else
			 	{
			 	 return lineOfNos.get(pozice).toString();	
			 	}
			 
		 }
			
		
		
	
	}
	
	public void createNew(int ID){
		
		if(lineOfIDs.size()==0)
			lineOfNos.add(1);
			else
				lineOfNos.add(((Integer) lineOfNos.lastElement()) + 1);
		
		lineOfIDs.add(ID);
		lineOfNames.add("");
	}
	
	public void setName(int ID, String name) {
		int itemNo = lineOfIDs.search(ID);
		lineOfNames.set(itemNo, name);
	}
	
	
}
