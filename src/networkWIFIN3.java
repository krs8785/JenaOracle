

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

public class networkWIFIN3 {


	final static String phoneLabIRI = "http://www.PhoneLab.com/ontologies/PhoneLab.owl";
	String user;
	static int count = 1;
	String folder;
	
	public networkWIFIN3() {
		// TODO Auto-generated constructor stub
	}
	public networkWIFIN3(String u, String f) {
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
	
	public void startLoading() {
		OntModelImpl model = (OntModelImpl) ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
		model.createOntology(phoneLabIRI);
		
		populateInstances("C:\\Users\\karan\\Downloads\\Compressed\\building\\"+getFolder()+"\\tag\\Network-Wifi-PhoneLab\\2015\\03",model);
		 
		
		String fileName = "networkWifi"+getUser()+".n3";
		
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
		    	
		    	Resource networkWifi = model.createResource(phoneLabIRI+"#networkWifiPhoneLab"+count);
		    	Property hasTimeStamp = model.createProperty(phoneLabIRI+"#hasTimeStamp");
		        Property hasUser  = model.createProperty(phoneLabIRI+"#hasUser");
		        networkWifi.addProperty(hasTimeStamp,timestamp);
		        networkWifi.addProperty(hasUser, getUser());
		       
		        Property hasActionValue = model.createProperty(phoneLabIRI+"#hasActionValue");
		        networkWifi.addProperty(hasActionValue, instance.getString("Action"));
		       
		        Property hasCounter = model.createProperty(phoneLabIRI+"#hasCounter");
		        networkWifi.addProperty(hasCounter, instance.get("Counter").toString());
		        
		        Property hasLogFormat = model.createProperty(phoneLabIRI+"#hasLogFormat");
		        networkWifi.addProperty(hasLogFormat, instance.get("LogFormat").toString());
		        
		        if(instance.has("LinkProperties")){
		        	JSONObject linkProp = instance.getJSONObject("LinkProperties");
		        	
		        	Resource linkProperties = model.createResource(phoneLabIRI+"#networkWifi"+count+"linkProperty");
		        	
		        	Property hasIfaceName = model.createProperty(phoneLabIRI+"#hasIfaceName");
		        	linkProperties.addLiteral(hasIfaceName, linkProp.get("IfaceName").toString());
		        	Property hasMtu = model.createProperty(phoneLabIRI+"#hasMtu");
		        	linkProperties.addLiteral(hasMtu, linkProp.get("Mtu").toString());
		        	Property hasDnses = model.createProperty(phoneLabIRI+"#hasDnses");
		        	linkProperties.addLiteral(hasDnses, linkProp.get("Dnses").toString());
		        	
		        	Property hasDomains= model.createProperty(phoneLabIRI+"#hasDomains");
		        	linkProperties.addLiteral(hasDomains, linkProp.get("Domains").toString());
		        	Property hasLinkAddresses = model.createProperty(phoneLabIRI+"#hasLinkAddresses");
		        	linkProperties.addLiteral(hasLinkAddresses, linkProp.get("LinkAddresses").toString());
		        	
		        	
		        	
		        	
		        	Property hasLinkProperty = model.createProperty(phoneLabIRI+"#hasLinkProperty");
		        	networkWifi.addLiteral(hasLinkProperty, linkProperties);
		        	
		        }
		        
		        count++;
		    }
		    is.close();
		}catch(Exception e){
			System.out.println(e);
		}
		
	}

}
