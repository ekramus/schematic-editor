package cz.cvut.fel.schematicEditor.element;

import java.util.Stack;

import cz.cvut.fel.schematicEditor.element.element.Element;

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
	private static Stack<String>  lineOfTypes;	
	static Element touchedObject;
	private static int orderNumber;
	
	public ElementPotential() {
		/**
		 * @author Karel
		 * constructor
		 * 
		 **/
		
		if(lineOfNos == null)
		{
		lineOfNos   = new Stack<Integer>();
		lineOfNames = new Stack<String>();
		lineOfIDs 	= new Stack<Integer>();
		lineOfTypes = new Stack<String>();
		orderNumber = 0;
		}
		//getName(0);
	}
	
	public String findName(int ID){
		String output = "";
		int	out = 0;
		
		int i = 0;
		for(i = 0; i < lineOfIDs.size(); i++){
			if(lineOfIDs.get(i)==ID)
				{
				 output = lineOfNames.get(i);
				 if(output !="")
						return output;
					else
						{
						return Integer.toString(lineOfIDs.size()-i);
						}
				}
		}
		
		createNew(ID);
		return findName(ID);
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
				nakonec = (Integer) (lineOfNos.size());
				lineOfNos.insertElementAt( nakonec, 0);
				
			}
		
				
		//lineOfIDs.add(ID);
		lineOfIDs.insertElementAt(ID, 0);
		
		//lineOfNames.add("");
		lineOfNames.insertElementAt("", 0);
		//lineOfTypes.insertElementAt(ID + "", 0);
		return (nakonec);
	}
	
	public void setName(int ID, String name) {
		int i = 0;
		for(i = 0; i < lineOfIDs.size(); i++){
				if(lineOfIDs.get(i)==ID)
					{
					 lineOfNames.set(i, name);
					 return;
					}
		}
		createNew(ID);
		
		setName(ID, name);
		int mrkvicka = 5;
	}
	
	public static Element getHitObject()
	{
		return touchedObject;
	}
	
	public static void setHitObject(Element object)
	{
		touchedObject = object;
	}
	
	
	
}
