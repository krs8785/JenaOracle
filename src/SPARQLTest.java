import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;










import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;

import oracle.spatial.rdf.client.jena.GraphOracleSem;
import oracle.spatial.rdf.client.jena.ModelOracleSem;
import oracle.spatial.rdf.client.jena.Oracle;



public class SPARQLTest {
	
	public static void main(String[] args) throws Exception {
		String szJdbcURL = "jdbc:oracle:thin:@localhost:1521/PROJOWL2";
	    String szUser    = "rajesh";
	    String szPasswd  = "shah";
	    String szModelName = "locationMisc";

	    Oracle oracle = new Oracle(szJdbcURL, szUser, szPasswd);
	    Model model = ModelOracleSem.createOracleSemModel(oracle, szModelName);
	    
	    
	    //GraphOracleSem graph = new GraphOracleSem(oracle, szModelName);
	    
	    Query query = QueryFactory.create(
	            "select ?f ?k WHERE {?f <http://www.PhoneLab.com/ontologies/PhoneLab.owl#hasLongitudeValue> ?k .}");
	        QueryExecution qexec = QueryExecutionFactory.create(query, model);
	        ResultSet results = qexec.execSelect();
	        ResultSetFormatter.out(System.out, results, query);
	    model.close();
	    oracle.dispose();
	    
	    
	    
	}
}
