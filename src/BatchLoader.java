import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;

import oracle.spatial.rdf.client.jena.GraphOracleSem;
import oracle.spatial.rdf.client.jena.Oracle;



public class BatchLoader {
	
	public static void main(String[] args) throws Exception {
		String szJdbcURL = "jdbc:oracle:thin:@localhost:1521/projowldone";
	    String szUser    = "rajesh";
	    String szPasswd  = "shah";
	    String szModelName = "thisIsNewLocation";

	    System.out.println("step 1");
	    Oracle oracle = new Oracle(szJdbcURL, szUser, szPasswd);
	    //Model model = ModelOracleSem.createOracleSemModel(oracle, szModelName);
	    System.out.println("step 2");
	    
	    GraphOracleSem graph = new GraphOracleSem(oracle, szModelName);
	    PrintStream psOut = System.out;
	    String dirname = "C:\\Users\\karan\\Desktop\\rdffiles\\lm";
	    File fileDir = new File(dirname);
	    String[] szAllFiles = fileDir.list();
	    
	    long start = System.currentTimeMillis();
	    for (int idx = 0; idx < szAllFiles.length; idx++) {
	    	  String szIndFileName = dirname + File.separator + szAllFiles[idx];
	    	  psOut.println("process to [ID = " + idx + " ] file " + szIndFileName);
	    	  psOut.flush();
	    	  
	    	  try{
	    		  InputStream is = new FileInputStream(szIndFileName);
	    		    graph.getBulkUpdateHandler().prepareBulk(
	    		        is,                    // input stream
	    		        "",  // base URI
	    		        "N-TRIPLE",             // data file type: can be RDF/XML, N-TRIPLE, etc.
	    		        "rdf_users",               // tablespace
	    		        null,                  // flags
	    		        null,                  // listener
	    		        null                   // staging table name.
	    		        );
	    		    is.close();
	    	  }catch(Throwable  t){
	    		  psOut.println("Hit exception " + t.getMessage());	    		  
	    	  }
	    	  System.out.println("done");
	    }
	    graph.getBulkUpdateHandler().completeBulk(
	    		  null,  // flags for invoking SEM_APIS.bulk_load_from_staging_table
	    		  null   // staging table name
	    );
	    long end = System.currentTimeMillis();

	    System.out.println("Took : " + ((end - start) / 1000));
	    //graph.commitTransaction();
	    graph.close();
	    //oracle.executeSQL("commit");
	    oracle.dispose();
	    
	    
	    
	}
}
