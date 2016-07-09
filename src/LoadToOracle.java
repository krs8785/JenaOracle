
import oracle.spatial.rdf.client.jena.Oracle;
import oracle.spatial.rdf.client.jena.ModelOracleSem;
import oracle.spatial.rdf.client.BatchLoader;

import com.hp.hpl.jena.graph.NodeFactory;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;





public class LoadToOracle {
	public static void main(String[] args) throws Exception
	  {
	    String szJdbcURL = "jdbc:oracle:thin:@localhost:1521/PROJOWLFINAL";
	    String szUser    = "rajesh";
	    String szPasswd  = "shah";

	    String szModelName = "karan456";
	    Oracle oracle = new Oracle(szJdbcURL, szUser, szPasswd);
	    Model model = ModelOracleSem.createOracleSemModel(oracle, szModelName);

	    model.getGraph().add(Triple.create(
	          NodeFactory.createURI("http://example.com/superman"),
	          NodeFactory.createURI("http://example.com/fatherOf"),
	          NodeFactory.createURI("http://example.com/batman")));
	    

	    Query query = QueryFactory.create(
	        "select ?f ?k WHERE {?f <http://example.com/fatherOf> ?k .}");
	    QueryExecution qexec = QueryExecutionFactory.create(query, model);
	    ResultSet results = qexec.execSelect();
	    ResultSetFormatter.out(System.out, results, query);

	    model.close();
	    oracle.dispose();
	  }
}
