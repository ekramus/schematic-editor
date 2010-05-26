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
		if(lineOfIDs.isEmpty())
			{
			createNew(ID);
			return "";
			}
		 else
		 {
			 int pozice = lineOfIDs.search(ID);
			 
			 if(pozice == -1)
				 {
				 
				 return "" + createNew(ID);
				 }
			 
			 
			 if(!lineOfNames.get(pozice - 1).isEmpty())
				 return lineOfNames.get(pozice - 1).toString();
			 	else
			 	{
			 	 return  "" +  pozice; // lineOfIDs.get(pozice - 1).toString();	
			 	}
			 
		 }
			
		
		
	
	}
	
	public int createNew(int ID){
		
		int nakonec = 0;
		
		if(lineOfIDs.size()==0)
			{
			lineOfNos.add(1);
			//lineOfIDs.add(ID);
			//lineOfNames.add("");
			}
			else
			//  lineOfNos.add(((Integer) lineOfNos.lastElement()) + 1);
			{
				nakonec = (Integer) (lineOfNos.lastElement()) + 1;
				lineOfNos.insertElementAt( nakonec, 0);
				
			}
		
				
		//lineOfIDs.add(ID);
		lineOfIDs.insertElementAt(ID, 0);
		
		//lineOfNames.add("");
		lineOfNames.insertElementAt("", 0);
		
		return nakonec;
	}
	
	public void setName(int ID, String name) {
		getName(ID);
		int pozice = lineOfIDs.search(ID);
		//lineOfNames.set(pozice, name);
	}
	
	
}
