package demo.vsm;

import java.util.HashMap;

import org.springframework.stereotype.Component;

@Component
public class ConvertControllerRelationToDistance {

	public HashMap<String, double[]> convertControllerRelationToDistance(HashMap<String, double[]> controllerRelation) {
		HashMap<String, double[]> controllerDistance = new HashMap<String, double[]>();
		
		int index = 0;
		for(String s : controllerRelation.keySet()) {
			for(int i = 0; i < controllerRelation.get(s).length; i++) {
				//controllerRelation.get(s)[i] = 1.0 - controllerRelation.get(s)[i];
				if(index == i) {
					controllerRelation.get(s)[i] = 0.0;
				}
				else {
					controllerRelation.get(s)[i] = 1.0 - controllerRelation.get(s)[i];
				}
				System.out.println("RRRRRRRR " + controllerRelation.get(s)[i]);
			}
			index++;
			controllerDistance.put(s , controllerRelation.get(s));
		}
		
		return controllerDistance;
	}
}
