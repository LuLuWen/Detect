package demo.vsm;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CalculateControllerRelation {

	@Autowired
	private CosineSimilarity consineSimilarity;
	
	public HashMap<String, double[]> calculateControllerRelation(HashMap<String, double[]> score) {
		HashMap<String, double[]> calculateControllerRelation = new HashMap<String, double[]>();
		
		for(String s1 : score.keySet()) {
			double[] controllerRelation = new double[score.keySet().size()];
			int index = 0;
			for(String s2 : score.keySet()) {
				controllerRelation[index] = consineSimilarity.cosineSimilarity(score.get(s1),score.get(s2));
				index++;
			}
			calculateControllerRelation.put(s1, controllerRelation);
		}
		
			
		return calculateControllerRelation;
		
	}
}
