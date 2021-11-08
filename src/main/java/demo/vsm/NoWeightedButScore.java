package demo.vsm;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import demo.logToDb.AccessData;
import demo.logToDb.LogPattern;

@Component
public class NoWeightedButScore {
	
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
			
			HashMap<String, Integer> hash= new HashMap<String, Integer>();
			if(tableName.contains("/")) {
				String[] tableNameArray = tableName.split("/");				
				hash.put(tableNameArray[0], 1);
				for(int m = 1; m < tableNameArray.length; m++) {
					if(hash.containsKey(tableNameArray[m])) {
						hash.put(tableNameArray[m], hash.get(tableNameArray[m]) + 1);
					}
					else {
						hash.put(tableNameArray[m], 1);
					}
				}
				
				for(String s : hash.keySet()) {
					System.out.println("hash "  + " : " + s);
					for(int j = 0; j < tableToArray.length; j++) {
						if(s.equals(tableToArray[j])) {
								for(String s2 : relation2.keySet()) {
									if(s2.equals(tableToArray[j])) {
										String method = "";
										for(int m = 0; m < relation2.get(s2).size(); m++) {
											if(relation1.get(s1).get(0).getRequestTime().equals(relation2.get(s2).get(m).getLogtime())) {
												method = method + relation2.get(s2).get(m).getRequestMethod() + "/";
												System.out.println("!!!kkkkkkkk : " + method + s2);
												//break;
											}
										}
										String[] methodArray = null;
										if(method.contains("/")) {
											methodArray = method.split("/");
										}
										System.out.println("最後一步 " + methodArray.length);							
												
										double scores = 0;
										for(int n = 0; n < methodArray.length; n++) {
											System.out.println("最後 " + methodArray[n]);
											switch(methodArray[n]) {
											case "GET":
												scores += 1;
												break;
											case "POST":
												scores += 5;
												break;
											case "PUT":
												scores += 3;
												break;
											}
											emptyArray[j] += scores;		
											System.out.println("=======次數=========" + scores);
											scores = 0;
										}
										System.out.println("最 " + emptyArray[j]);
									}
								}
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
				
				String method = relation1.get(s1).get(0).getRequestMethod();							
				switch(method) {
				case "GET":
					emptyArray[position] = 1;
					break;
				case "POST":
					emptyArray[position] = 5;
					break;
				case "PUT":
					emptyArray[position] = 3;
					break;
				}			
				score3.put(s1, emptyArray);
			}
		}
		
		for(String ss : score3.keySet()) {
			System.out.println("77777777777777" + ss + " : " + Arrays.toString(score3.get(ss)));
		}

		return score3;
	}
}
