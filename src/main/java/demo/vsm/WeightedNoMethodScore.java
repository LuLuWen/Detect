package demo.vsm;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import demo.logToDb.AccessData;
import demo.logToDb.LogPattern;

@Component
public class WeightedNoMethodScore {
	
	@Autowired
	private ControllerToTable con;
	
	//9000-11000 random number
	public int randomNumber() {
		int random = 0;
		random = (int)(Math.random()* 2001 ) + 9000;
		
		return random;
	}
	
	
	public double[] init(int tableNumber) {
		double[] tN = new double[tableNumber]; 
		return tN;
	}
	
	public String[] TableToArray(HashMap<String, List<AccessData>> relation) {
		String[] tableToArray = new String[relation.keySet().size()];
		System.out.println("size=" + relation.keySet().size());
		int index = 0;
		for(String s : relation.keySet()) {
			tableToArray[index] = s;
			index++;
		}
		
		return tableToArray;
		
	}
	
	public HashMap<String, double[]> score3(HashMap<String, List<LogPattern>> relation1, HashMap<String, List<AccessData>> relation2) {
		HashMap<String, double[]> score3 = new HashMap<String, double[]>();
		
		HashMap<String, String> ControllerTableRelation= new HashMap<String, String>();
		ControllerTableRelation = con.ControllerTableRelation();		
		String[] tableToArray = TableToArray(relation2);
		for(String s : tableToArray) {
			 System.out.println("table name : " + s);
		}

		for(String s1 : relation1.keySet()) {
			System.out.println("test : " + s1);
			String tableName;
			tableName = ControllerTableRelation.get(s1);
			System.out.println(tableName);
			double[] emptyArray = init(9);
			
			if(tableName.contains("/")) {
				String[] tableNameArray = tableName.split("/");	
				
				int basetimes = 0;
				if(relation1.get(s1).get(0).getRequestMethod().equals("GET")) {
					basetimes = randomNumber();
				}
				else {
					basetimes = randomNumber() / 10;
				}				
				System.out.println("亂數: " + basetimes);
				
				for(String s : tableNameArray) {
					for(int j = 0; j < tableToArray.length; j++) {
						if(s.equals(tableToArray[j])) {
							int times = (int)(Math.random() * ((basetimes+20)-(basetimes-20)+1)) + basetimes;
							emptyArray[j] += times;
						}
					}
				}
				score3.put(s1, emptyArray);	
			}
			else {
				int position = 0;		
				for(int i = 0; i < tableToArray.length; i++) {
					if(tableToArray[i].equals(tableName)) {
						position = i;
						System.out.println("position = " + position);
					}
				}
				int basetimes = 0;
				if(relation1.get(s1).get(0).getRequestMethod().equals("GET")) {
					basetimes = randomNumber();
				}
				else {
					basetimes = randomNumber() / 10;
				}				
				System.out.println("亂數: " + basetimes);
				
				emptyArray[position] = basetimes;
				score3.put(s1, emptyArray);
			}
		}
		
		for(String ss : score3.keySet()) {
			System.out.println("77777777777777" + ss + " : " + Arrays.toString(score3.get(ss)));
		}

		return score3;
	}
}
