

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

public class powerBatteryN3 {


	final static String phoneLabIRI = "http://www.PhoneLab.com/ontologies/PhoneLab.owl";
	static int count = 1;
	String user;
	String folder;
	
	
	public powerBatteryN3(String u, String f) {
		user = u;
		folder =f;
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
		
		populateInstances("C:\\Users\\karan\\Downloads\\Compressed\\building\\"+getFolder()+"\\tag\\Power-Battery-PhoneLab\\2015\\03",model);
		 
		
		String fileName = "powerBattery"+getUser()+".n3";
		
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
		    	
		    	Resource powerBattery = model.createResource(phoneLabIRI+"#powerBatteryPhoneLab"+count);
		        
		    	if(instance.has("Action")){
		    		if(!instance.getString("Action").equals("UNKNOWN")){
		    			Property hasTimeStamp = model.createProperty(phoneLabIRI+"#hasTimeStamp");
				        Property hasUser  = model.createProperty(phoneLabIRI+"#hasUser");
				        powerBattery.addProperty(hasTimeStamp,timestamp);
				        powerBattery.addProperty(hasUser, user);
				       
				        Property hasActionValue = model.createProperty(phoneLabIRI+"#hasActionValue");
				        powerBattery.addProperty(hasActionValue, instance.getString("Action"));
				       
				        Property hasCounter = model.createProperty(phoneLabIRI+"#hasCounter");
				        powerBattery.addProperty(hasCounter, instance.get("Counter").toString());
				        
				        Property hasLogFormat = model.createProperty(phoneLabIRI+"#hasLogFormat");
				        powerBattery.addProperty(hasLogFormat, instance.get("LogFormat").toString());
				        
				        Resource batteryProperty = model.createResource(phoneLabIRI+"#batteryProperty"+count);
				        Property hasProperty = model.createProperty(phoneLabIRI+"#hasProperty");
				        
				        JSONObject instance1 = new JSONObject();
				        instance1 = instance.getJSONObject("BatteryProperties");
				        Property hasStatus = model.createProperty(phoneLabIRI+"#hasStatus");
				        batteryProperty.addProperty(hasStatus, instance1.get("Status").toString());

				        Property hasPresent = model.createProperty(phoneLabIRI+"#hasPresent");
				        batteryProperty.addProperty(hasPresent, instance1.get("Present").toString());
				        Property hasVoltage = model.createProperty(phoneLabIRI+"#hasVoltage");
				        batteryProperty.addProperty(hasVoltage, instance1.get("Voltage").toString());
				        Property hasTemperature = model.createProperty(phoneLabIRI+"#hasTemperature");
				        batteryProperty.addProperty(hasTemperature, instance1.get("Temperature").toString());
				        Property hasCurrentNow = model.createProperty(phoneLabIRI+"#hasCurrentNow");
				        batteryProperty.addProperty(hasCurrentNow, instance1.get("CurrentNow").toString());
				        Property hasHealth = model.createProperty(phoneLabIRI+"#hasHealth");
				        batteryProperty.addProperty(hasHealth, instance1.get("Health").toString());
				        Property hasLevel = model.createProperty(phoneLabIRI+"#hasLevel");
				        batteryProperty.addProperty(hasLevel, instance1.get("Level").toString());
				        Property hasPlugType = model.createProperty(phoneLabIRI+"#hasPlugType");
				        batteryProperty.addProperty(hasPlugType, instance1.get("PlugType").toString());
				        Property hasChargeCounter = model.createProperty(phoneLabIRI+"#hasChargeCounter");
				        batteryProperty.addProperty(hasChargeCounter, instance1.get("ChargeCounter").toString());
				        Property hasTechnology = model.createProperty(phoneLabIRI+"#hasTechnology");
				        batteryProperty.addProperty(hasTechnology, instance1.get("Technology").toString());
				        
				        powerBattery.addProperty(hasProperty, batteryProperty);
				        count++;

		    		}
		    	}
		     	
		    }
		    is.close();
		}catch(Exception e){
			System.out.println(e);
		}
		    
		
	}
}
