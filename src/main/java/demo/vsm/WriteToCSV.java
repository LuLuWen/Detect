package demo.vsm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;

import org.springframework.stereotype.Component;

@Component
public class WriteToCSV {

	public void writeToCSV(HashMap<String, double[]> controllerRelationDistance) {
		try {
			File CSV = new File("./src/main/resources/movie1.csv");
			PrintWriter writer = new PrintWriter(CSV);
			
			StringBuilder sb = new StringBuilder();
			//sb.append(" ");
			sb.append(",");
			for(String s : controllerRelationDistance.keySet()) {
				sb.append(s);
				sb.append(",");
			}
			sb.append("\n");
			
			for(String s1 : controllerRelationDistance.keySet()) {
				sb.append(s1);
				for(int i = 0; i < controllerRelationDistance.get(s1).length; i++) {					
					sb.append(",");
					sb.append(controllerRelationDistance.get(s1)[i]);
					//System.out.println(controllerRelationDistance.get(s1)[i]);
				}				
				sb.append("\n");
			}
			writer.write(sb.toString());
			System.out.println(sb.toString());
			System.out.println("done!");

			writer.flush();
			writer.close();
		} catch (FileNotFoundException e) {
            //log.info("File not found :{}", e.toString());
			System.out.println(e.getMessage());
        } catch (Exception e) {
            //log.info("Error :{}", e.toString());
        }
	}
}
