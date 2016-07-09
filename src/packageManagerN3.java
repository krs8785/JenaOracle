
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

import org.json.JSONObject;

import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.impl.OntModelImpl;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;


public class packageManagerN3 {
	final static String phoneLabIRI = "http://www.PhoneLab.com/ontologies/PhoneLab.owl";
	String user ;
	static int count = 1;
	String folder;
	
	public packageManagerN3() {
		// TODO Auto-generated constructor stub
	}
	public packageManagerN3(String u, String f) {
		// TODO Auto-generated constructor stub
		user = u;
		folder = f;
	}
	
	public String getUser() {
		return user;
	}
	
	public String getFolder() {
		return folder;
	}
	
	public  void startLoading() {
		
		OntModelImpl model = (OntModelImpl) ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
		model.createOntology(phoneLabIRI);
		
		populateInstances("C:\\Users\\karan\\Downloads\\Compressed\\building\\"+getFolder()+"\\tag\\PackageManager-Misc-PhoneLab\\2015\\03",model);
		 
		String fileName = "packageManager"+getUser()+".n3";
		
		try {
			FileWriter out = new FileWriter( fileName );
		    model.write( out, "N-TRIPLE" );
		    out.close();
		}catch(Exception e){
			
		}
	}

	private  void populateInstances(String rootFolder, OntModelImpl model) {
		// TODO Auto-generated method stub
		File folder = new File(rootFolder);
	     File[] listOfFiles = folder.listFiles();
	     for(int idx=0; idx<listOfFiles.length; ++idx){
	            File file = listOfFiles[idx];
	            System.out.println(file.toString());
	            
	            performJsonToRdfConvert(file,model);
	            
	     }
		
	}

	private  void performJsonToRdfConvert(File file, OntModelImpl model) {
		// TODO Auto-generated method stub

		try{
			
			FileInputStream fin = new FileInputStream(file);
		    GZIPInputStream gzis = new GZIPInputStream(fin);
		    InputStreamReader xover = new InputStreamReader(gzis);
		    BufferedReader is = new BufferedReader(xover);

		    String line;
		    // Now read lines of text: the BufferedReader puts them in lines,
		    // the InputStreamReader does Unicode conversion, and the
		    // GZipInputStream "gunzip"s the data from the FileInputStream.
		    while ((line = is.readLine()) != null) {
		    	JSONObject instance = new JSONObject(line.substring(line.indexOf("{"), line.length()));
                		    	
		    	//System.out.println("Read: " + line);
		    	String lhs = line.substring(0, line.indexOf("{"));
		    	String[] components = lhs.split("\\s+");
		    	String timestamp = components[3]+"T"+components[4].substring(0, components[4].indexOf("."));
		    	
		    	Resource packageManager = model.createResource(phoneLabIRI+"#packageManagerPhoneLab"+count);
		    	Property hasTimeStamp = model.createProperty(phoneLabIRI+"#hasTimeStamp");
		        Property hasUser  = model.createProperty(phoneLabIRI+"#hasUser");
		        packageManager.addProperty(hasTimeStamp,timestamp);
		        packageManager.addProperty(hasUser, getUser());
		       
		        Property hasActionValue = model.createProperty(phoneLabIRI+"#hasActionValue");
		        packageManager.addProperty(hasActionValue, instance.getString("Action"));
		       
		        Property hasCounter = model.createProperty(phoneLabIRI+"#hasCounter");
		        packageManager.addProperty(hasCounter, instance.get("Counter").toString());
		        
		        Property hasLogFormat = model.createProperty(phoneLabIRI+"#hasLogFormat");
		        packageManager.addProperty(hasLogFormat, instance.get("LogFormat").toString());
		    		
		        Property hasPackageName= model.createProperty(phoneLabIRI+"#hasPackageName");
		        packageManager.addProperty(hasPackageName, instance.get("PackageName").toString());
		    		
		    	count++;
		     	
		    }
		    is.close();
		}catch(Exception e){
			System.out.println(e);
		}
		    
		
	}
}
