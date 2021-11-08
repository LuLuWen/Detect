package demo.vsm;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import demo.logToDb.AccessData;
import demo.logToDb.LogPattern;

@Component
public class Score {
	
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
	
	// 產生 string陣列, 存資料表名稱
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
	
	/*public HashMap<String, int[]> score2(HashMap<String, List<LogPattern>> relation) {
		HashMap<String, int[]> score2 = new HashMap<String, int[]>();
		for(String s : relation.keySet()) {
			int score = 0;
			int[] emptyArray = init(5);
			String method = relation.get(s).get(0).getRequestMethod();
			int times = relation.get(s).size(); 
			
			switch(method) {
			case "GET":
				score += 1 * times;
				break;
			case "POST":
				score += 5 * times;
				break;
			case "PUT":
				score += 3 * times;
				break;
			}
			score2.put(s, emptyArray);
		}		
		
		return score2;
	}*/
	public HashMap<String, double[]> score3(HashMap<String, List<LogPattern>> relation1, HashMap<String, List<AccessData>> relation2) {
		HashMap<String, double[]> score3 = new HashMap<String, double[]>();
		/*int tableNumber = relation2.keySet().size();
		for(String s : relation2.keySet()) {
			for(int i = 0; i < tableNumber; i++) {
				if(s.equals(TableToArray(relation2)[i])) {
					int position = i ;
					init(relation2.keySet().size());
				}
			}
		}*/
		/*for(String s1 : relation1.keySet()) {
			for(String s2 : relation2.keySet()) {
				for(int i = 0; i < relation1.get(s1).size(); i++) {
					for(int j = 0; j < relation2.get(s2).size(); j++) {
						if(relation1.get(s1).get(i).getRequestTime().equals(relation2.get(s2).get(j).getLogtime())) {
							String tableName = relation2.get(s2).get(j).getEntityTableName();
							
							break;
						}
					}
				}
			}
		}*/
		HashMap<String, String> ControllerTableRelation= new HashMap<String, String>();
		ControllerTableRelation = con.ControllerTableRelation();		
		String[] tableToArray = TableToArray(relation2);
		for(String s : tableToArray) {
			 System.out.println("table name : " + s);
		}
		//System.out.println("hhh" + tableToArray[0])-;
		for(String s1 : relation1.keySet()) {
			System.out.println("test : " + s1);
			String tableName;
			tableName = ControllerTableRelation.get(s1);
			System.out.println(tableName);
			double score = 0;
			double[] emptyArray = init(9);
			
			HashMap<String, Integer> hash= new HashMap<String, Integer>();
			if(tableName.contains("/")) {
				String[] tableNameArray = tableName.split("/");				
				/*for(String s : tableNameArray) {
					 System.out.println("!!!!!!!!!!!!!!table name : " + s);
				}*/
				/*for(int m = 0; m < tableNameArray.length; m++) {
					System.out.println("!!!!!!!!!!!!!!table name : " + tableNameArray[m]);
				}*/
				hash.put(tableNameArray[0], 1);
				for(int m = 1; m < tableNameArray.length; m++) {
					if(hash.containsKey(tableNameArray[m])) {
						hash.put(tableNameArray[m], hash.get(tableNameArray[m]) + 1);
					}
					else {
						hash.put(tableNameArray[m], 1);
					}
				}
				
				/*for(String sss : hash.keySet()) {
					System.out.println("hash " + sss + " : " + hash.get(sss));
				}*/
				int basetimes = relation1.get(s1).size();
				if(basetimes == 10) {
					basetimes = randomNumber();
				}
				else if(basetimes == 1) {
					basetimes = randomNumber() / 10;
				}
				System.out.println("亂數: " + basetimes);
				//basetimes = (int)(Math.random() * ((basetimes+20)-(basetimes-20)+1)) + basetimes;
				
				for(String s : hash.keySet()) {
					System.out.println("hash "  + " : " + s);
					/*int times = (int)(Math.random() * ((basetimes+20)-(basetimes-20)+1)) + basetimes;
					System.out.println("最終亂數: " + times);*/
					for(int j = 0; j < tableToArray.length; j++) {
						if(s.equals(tableToArray[j])) {
							//for(int k = hash.get(tableToArray[j]); k > 0; k--) {
								//double scores = 0;
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
										//int times = relation1.get(s1).size();
										
										/*if(times == 10) {
											times = randomNumber();
										}
										else if(times == 1) {
											times = randomNumber() / 10;
										}
										System.out.println("亂數: " + times);*/
												
										double scores = 0;
										for(int n = 0; n < methodArray.length; n++) {
											System.out.println("最後 " + methodArray[n]);
											int times = (int)(Math.random() * ((basetimes+20)-(basetimes-20)+1)) + basetimes;
											System.out.println("最終亂數: " + times);
											switch(methodArray[n]) {
											case "GET":
												scores += 1 * times;
												break;
											case "POST":
												scores += 5 * times;
												break;
											case "PUT":
												scores += 3 * times;
												break;
											}
											emptyArray[j] += scores;		
											System.out.println("=======次數=========" + scores);
											scores = 0;
										}
										System.out.println("最 " + emptyArray[j]);
									}
								}
							//}
						}
					}
				}
				score3.put(s1, emptyArray);
				
				/*for(int i = 0; i < tableNameArray.length; i++) {
					double scores = 0;
					for(int j = 0; j < tableToArray.length; j++) {
						if(tableNameArray[i].equals(tableToArray[j])) {
							//System.out.println("!!!!!!!!!!table name : " + tableToArray[j]);
							for(String s2 : relation2.keySet()) {
								if(s2.equals(tableToArray[j])) {
									//System.out.println("!!!!!!!table name : " + tableToArray[j]);
									//
									String method = "";
									for(int k = 0; k < relation2.get(s2).size(); k++) {
										if(relation1.get(s1).get(0).getRequestTime().equals(relation2.get(s2).get(k).getLogtime())) {
											method = relation2.get(s2).get(k).getRequestMethod();
											//System.out.println("!!!kkkkkkkk : " + k);
											break;
										}
									}
									//String method = relation2.get(s2).get(1).getRequestMethod();
									
									//System.out.println("!!!table name : " + method);
									int times = relation1.get(s1).size();
									
									switch(method) {
									case "GET":
										scores += 1 * times;
										break;
									case "POST":
										scores += 5 * times;
										break;
									case "PUT":
										scores += 3 * times;
										break;
									}
									emptyArray[j] += scores;									
								}
							}							
						}
					}
				}											
				score3.put(s1, emptyArray);
				*/								
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
				int times = relation1.get(s1).size(); 
				
				if(times == 10) {
					times = randomNumber();
				}
				else if(times == 1) {
					times = randomNumber() / 10;
				}
				System.out.println("亂數: " + times);
				
				
				switch(method) {
				case "GET":
					score += 1 * times;
					break;
				case "POST":
					score += 5 * times;
					break;
				case "PUT":
					score += 3 * times;
					break;
				}
				emptyArray[position] = score;
				score3.put(s1, emptyArray);
			}
			
			/*String method = relation1.get(s1).get(0).getRequestMethod();
			int times = relation1.get(s1).size(); 
			
			switch(method) {
			case "GET":
				score += 1 * times;
				break;
			case "POST":
				score += 5 * times;
				break;
			case "PUT":
				score += 3 * times;
				break;
			}
			emptyArray[position] = score;
			score3.put(s1, emptyArray);*/
		}
		
		for(String ss : score3.keySet()) {
			System.out.println("77777777777777" + ss + " : " + Arrays.toString(score3.get(ss)));
		}

		return score3;
	}
	
	public int[] createScoreArray(int score, String controller, String[] table) {
		int[] scoreArray = new int[5];
		
		return scoreArray;		
	}
}
