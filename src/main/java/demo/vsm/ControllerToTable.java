package demo.vsm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import demo.logToDb.AccessData;
import demo.logToDb.AccessDataService;
import demo.logToDb.LogPattern;
import demo.logToDb.LogService;

@Component
public class ControllerToTable {

	@Autowired
	private LogService logService;
	
	@Autowired
	private AccessDataService accessDataService;
	
	//找呼叫多少個服務端點,以URL當作Key,URL總共有幾筆資料當作Value
	public HashMap<String, List<LogPattern>> findSameRelation() {
		List<LogPattern> list = logService.listAllLogs();
		//System.out.println(list.get(0).getRequestUrl());
		//List<LogPattern> newList = new ArrayList<LogPattern>();
		//newList.add(list.get(0));
		/* 10 改成 25 */
		String[] appearedUrl = new String[50]; 
		appearedUrl[0] = list.get(0).getRequestUrl();
		int index = 0;
		for(int i = 0; i < list.size(); i++) {
			String url = list.get(i).getRequestUrl();
			Boolean isAppeared = false;
			for(int j = 0 ; j < 50 ; j++) {
				if(appearedUrl[j] == null)
					break;
				if(appearedUrl[j].equals(url)) {
					isAppeared = true;
					break;
				}
				else {
					isAppeared = false;
				}
			}		
			if(!isAppeared) {
				index++;
				appearedUrl[index] = url;
			}
		}
		
		for(int j = 0 ; j < appearedUrl.length ; j++) {
			System.out.println(appearedUrl[j]);
		}	
			
		HashMap<String, List<LogPattern>> relation = new HashMap<String, List<LogPattern>>();
		for(int j = 0 ; j < 45 ; j++) {
			List<LogPattern> newList = new ArrayList<LogPattern>();
			for(int i = 0 ; i < list.size() ; i++) {
				String url = list.get(i).getRequestUrl();
				if(appearedUrl[j] == null)
					break;
				if(appearedUrl[j].equals(url)) {
					newList.add(list.get(i));
				}
			}
			if(appearedUrl[j] == null) 
				continue;
			else
			relation.put(appearedUrl[j], newList);
		}
		System.out.println("relation1 size: " + relation.size());
		return relation;

	}
	
	//找使用多少個實體(entity),以entity當作Key,entity總共有幾筆資料當作Value
	public HashMap<String, List<AccessData>> findSameRelation2() {
		List<AccessData> list = accessDataService.listAllAccessDatas();
		//System.out.println(list.get(0).getRequestUrl());
		//List<LogPattern> newList = new ArrayList<LogPattern>();
		//newList.add(list.get(0));
		String[] appearedTable = new String[10]; 
		appearedTable[0] = list.get(0).getEntityTableName();
		int index = 0;
		for(int i = 0; i < list.size(); i++) {
			String table = list.get(i).getEntityTableName();
			Boolean isAppeared = false;
			for(int j = 0 ; j < 10 ; j++) {
				if(appearedTable[j] == null)
					break;
				if(appearedTable[j].equals(table)) {
					isAppeared = true;
					break;
				}
				else {
					isAppeared = false;
				}
			}		
			if(!isAppeared) {
				index++;
				appearedTable[index] = table;
				//index++;
			}
		}
		
		for(int j = 0 ; j < appearedTable.length ; j++) {
			System.out.println(appearedTable[j]);
		}	
			
		HashMap<String, List<AccessData>> relation = new HashMap<String, List<AccessData>>();
		for(int j = 0 ; j < 10 ; j++) {
			List<AccessData> newList = new ArrayList<AccessData>();
			for(int i = 0 ; i < list.size() ; i++) {
				String table = list.get(i).getEntityTableName();
				if(appearedTable[j] == null) {
					//System.out.println("bbb" + j);
					break;
				}
				if(appearedTable[j].equals(table)) {
					newList.add(list.get(i));
				}
			}
			if(appearedTable[j] == null) 
				continue;
			else
				relation.put(appearedTable[j], newList);
		}
		//System.out.println("2" + relation);
		return relation;

	}
	
	//找服務端點中使用到的entity,以請求時間當作主鍵,URL當作key,entity當作value
	public HashMap<String, String> ControllerTableRelation(){
		HashMap<String, String> relation = new HashMap<String, String>();
		List<LogPattern> list1 = logService.listAllLogs();
		List<AccessData> list2 = accessDataService.listAllAccessDatas();
		
		for(int i = 0; i < list1.size(); i++) {
			Boolean isAppeared = false;
			for(String s : relation.keySet()) {
				if(list1.get(i).getRequestUrl().equals(s)) {
					isAppeared = true;
					//System.out.println("s=" + s);
					break;
				}
			}
			if(isAppeared) {
				continue;
			}
			else {
				int count = 0;
				String[] tableName = new String[10];
				for(int j = 0; j < list2.size(); j++) {	
					if(list1.get(i).getRequestTime().equals(list2.get(j).getLogtime())) {
						//System.out.println("i="+ i + " j= "+ j + list1.get(i).getRequestUrl());
						/*if(relation.get(list1.get(i).getRequestUrl()) != null) {
							//System.out.println("Hi:"+ j + "  " + list1.get(i).getRequestUrl());
							relation.put(list1.get(i).getRequestUrl(), list2.get(j).getEntityTableName() + "/" + list2.get(j-1).getEntityTableName());
							break;
						}
						relation.put(list1.get(i).getRequestUrl(), list2.get(j).getEntityTableName());*/
						
						//new
						if(relation.get(list1.get(i).getRequestUrl()) == null){
							relation.put(list1.get(i).getRequestUrl(), list2.get(j).getEntityTableName());
							tableName[0] = list2.get(j).getEntityTableName();
							count++;
						}
						else{
							tableName[count] = list2.get(j).getEntityTableName();
							String str="";
							for(int k = 0; k <= count; k++) {
								str += tableName[k] + "/";
							}
							count++;
							relation.put(list1.get(i).getRequestUrl(), str);
						}
						//new
					}				
				}
			}
		}
		
		for(String s1 : relation.keySet()) {
			System.out.println(s1 + " : " + relation.get(s1));
		}
		return relation;
	}
}
