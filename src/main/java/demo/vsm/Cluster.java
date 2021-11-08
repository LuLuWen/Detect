package demo.vsm;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Component;

@Component
public class Cluster {

	public void cluster(HashMap<String, double[]> controllerRelation) {
		int index = 0;
		for(String s1 : controllerRelation.keySet()) {
			System.out.println("s1 : " +  s1);
			ArrayList<String> finishCluster = new ArrayList<>();
			finishCluster.add(s1);
			for(int i = 0; i < controllerRelation.get(s1).length ; i++) {			
				//System.out.println(controllerRelation.get(s1)[i]);
				if(controllerRelation.get(s1)[i] > 0.7) {
					if(index == i) {						
					}
					else {						
						finishCluster.add((String) controllerRelation.keySet().toArray()[i]);
						System.out.println((String) controllerRelation.keySet().toArray()[i]);
					}
				}
			}
			index++;
		}
	}
}
