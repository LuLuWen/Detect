package demo.vsm;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class TableNumber {

	/** 
	 *  application.properties中配置的屬性 
	 */	
	@Value("${spring.datasource.url}")
	private String url;
	
	@Value("${spring.datasource.username}")
	private String username;
	
	@Value("${spring.datasource.password}")
	private String password;
	
	@Value("${spring.datasource.driver-class-name}")
	private String driver;
	
	@Autowired
	private ControllerToTable con;
	
	@Autowired
	private CosineSimilarity consine;
	
	@Autowired
	private Score score;
		
	@Autowired
	private CalculateControllerRelation calculateControllerRelation;
	
	@Autowired
	private ConvertControllerRelationToDistance convertControllerRelationToDistance;
	
	@Autowired
	private WriteToCSV writeToCSV;
	
	@Autowired
	private Cluster cluster;
	
	@Autowired
	private NoWeightedNoScore noWeightedNoScore;
	
	@Autowired
	private NoWeightedButScore noWeightedButScore;
	
	@Autowired
	private WeightedNoMethodScore weightedNoMethodScore;
	
	@Autowired
	private WeightedMethodScore weightedMethodScore;
	
	@RequestMapping("/table")
	public int getTableNumber() {
		int tableNumber = 0;
        try { 
            Class.forName(driver); 
            Connection conn = DriverManager.getConnection(url, username, password);
 
            if(conn != null && !conn.isClosed()) {
                System.out.println("資料庫連線測試成功！"); 
                DatabaseMetaData md = conn.getMetaData();
                ResultSet rs = md.getTables("movie", null, "%", null);
                while (rs.next()) {
                  System.out.println(rs.getString(3));
                  tableNumber++;
                }
                conn.close();
            }
            
        } 
        catch(ClassNotFoundException e) { 
            System.out.println("找不到驅動程式類別"); 
            e.printStackTrace(); 
        } 
        catch(SQLException e) { 
            e.printStackTrace(); 
        } 
        //con.findSameRelation2();
        //con.ControllerTableRelation();
        double[] docVector1 = {911.0, 0.0, 4555.0, 911.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        double[] docVector2 = {951.0, 0.0, 0.0, 4755.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        consine.cosineSimilarity(docVector1, docVector2);
        //score.score3(con.findSameRelation(), con.findSameRelation2());
        noWeightedNoScore.score3(con.findSameRelation(), con.findSameRelation2());
        //noWeightedButScore.score3(con.findSameRelation(), con.findSameRelation2());
        //weightedNoMethodScore.score3(con.findSameRelation(), con.findSameRelation2());
        //weightedMethodScore.score3(con.findSameRelation(), con.findSameRelation2());
        //calculateControllerRelation.calculateControllerRelation(score.score3(con.findSameRelation(), con.findSameRelation2()));
        //convertControllerRelationToDistance.convertControllerRelationToDistance(calculateControllerRelation.calculateControllerRelation(score.score3(con.findSameRelation(), con.findSameRelation2())));
        writeToCSV.writeToCSV(convertControllerRelationToDistance.convertControllerRelationToDistance(calculateControllerRelation.calculateControllerRelation(noWeightedNoScore.score3(con.findSameRelation(), con.findSameRelation2()))));
        //cluster.cluster(calculateControllerRelation.calculateControllerRelation(score.score3(con.findSameRelation(), con.findSameRelation2())));*/
        
        return tableNumber;
	}
}
