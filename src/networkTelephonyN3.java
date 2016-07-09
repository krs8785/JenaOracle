
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

public class networkTelephonyN3 {

	final static String phoneLabIRI = "http://www.PhoneLab.com/ontologies/PhoneLab.owl";
	String user;
	static int count = 1;
	String folder;
	
	public networkTelephonyN3() {
		// TODO Auto-generated constructor stub
	}
	public networkTelephonyN3(String u, String f) {
		// TODO Auto-generated constructor stub
		user =u;
		folder = f;
	}
	
	public String getFolder() {
		return folder;
	}
	public String getUser() {
		return user;
	}
	
	public void startLoading() {
		OntModelImpl model = (OntModelImpl) ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
		model.createOntology(phoneLabIRI);
		
		populateInstances("C:\\Users\\karan\\Downloads\\Compressed\\building\\"+getFolder()+"\\tag\\Network-Telephony-PhoneLab\\2015\\03",model);
		 
		String fileName = "networkTelephony"+getUser()+".n3";
		
		try {
			FileWriter out = new FileWriter( fileName );
		    model.write( out, "N-TRIPLE" );
		    out.close();
		}catch(Exception e){
			
		}
		
	}


	private void populateInstances(String rootFolder, OntModelImpl model) {
		// TODO Auto-generated method stub
		 File folder = new File(rootFolder);
	     File[] listOfFiles = folder.listFiles();
	     for(int idx=0; idx<listOfFiles.length; ++idx){
	            File file = listOfFiles[idx];
	            System.out.println(file.toString());
	            
	            performJsonToRdfConvert(file,model);
	            
	     }

		
		
	}


	private void performJsonToRdfConvert(File file, OntModelImpl model) {
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
		    
		    
		    	Resource networkTelephony= model.createResource(phoneLabIRI+"#networkTelephonyPhoneLab"+count);
		        
		     	Property hasTimeStamp = model.createProperty(phoneLabIRI+"#hasTimeStamp");
		        Property hasUser  = model.createProperty(phoneLabIRI+"#hasUser");
		        networkTelephony.addProperty(hasTimeStamp,timestamp);
		        networkTelephony.addProperty(hasUser, getUser());
		       
		        Property hasActionValue = model.createProperty(phoneLabIRI+"#hasActionValue");
		        networkTelephony.addProperty(hasActionValue, instance.getString("Action"));
		       
		        Property hasCounter = model.createProperty(phoneLabIRI+"#hasCounter");
		        networkTelephony.addProperty(hasCounter, instance.get("Counter").toString());
		        
		        Property hasLogFormat = model.createProperty(phoneLabIRI+"#hasLogFormat");
		        networkTelephony.addProperty(hasLogFormat, instance.get("LogFormat").toString());
		        
		        if(instance.has("DataActivity")){
		        	Property hasDataActivity= model.createProperty(phoneLabIRI+"#hasDataActivity");
			        networkTelephony.addProperty(hasDataActivity, instance.get("DataActivity").toString());
			        
		        }
		        
		        if(instance.has("IsRoaming")){
		        	if(instance.get("IsRoaming") instanceof JSONObject){
		        		
		        	}else{
		        		Property hasIsRoaming= model.createProperty(phoneLabIRI+"#hasIsRoaming");
				        networkTelephony.addProperty(hasIsRoaming, instance.get("IsRoaming").toString());				        
		        	}
		        }
		        if(instance.has("Reason")){
		        	Property hasReason= model.createProperty(phoneLabIRI+"#hasReason");
			        networkTelephony.addProperty(hasReason, instance.get("Reason").toString());	
		        }
		        
		        if(instance.has("SignalStrength")){
		        	JSONObject instance1 = instance.getJSONObject("SignalStrength");
		        	Resource SignalStrength = model.createResource(phoneLabIRI+"#networkTelephony"+count+"SignalStrength");
		        	Property hasSignalStrength = model.createProperty(phoneLabIRI+"#hasSignalStrength");
		        	
		        	Property hasAsu = model.createProperty(phoneLabIRI+"#hasAsu");
		        	SignalStrength.addProperty(hasAsu, instance1.get("Asu").toString());
			        Property hasType = model.createProperty(phoneLabIRI+"#hasType");
			        SignalStrength.addProperty(hasType, instance1.get("Type").toString());
			        Property hasLevel = model.createProperty(phoneLabIRI+"#hasLevel");
			        SignalStrength.addProperty(hasLevel, instance1.get("Level").toString());
			        Property hasStrength = model.createProperty(phoneLabIRI+"#hasStrength");
			        SignalStrength.addProperty(hasStrength, instance1.get("Strength").toString());
			        
			        networkTelephony.addProperty(hasSignalStrength, SignalStrength);
			        
		        }
		        
		        if(instance.has("State")){		  
		        	Property hasState = model.createProperty(phoneLabIRI+"#hasState");     	
		        	if(instance.get("State") instanceof JSONObject){
		        		JSONObject instance2 = instance.getJSONObject("State");
		        		Resource state = model.createResource(phoneLabIRI+"#networkTelephony"+count+"State");
		        		

		        		Property hasIsRoaming = model.createProperty(phoneLabIRI+"#hasIsRoaming");
		        		state.addProperty(hasIsRoaming, instance2.get("IsRoaming").toString());

		        		Property hasSystemId = model.createProperty(phoneLabIRI+"#hasSystemId");
		        		state.addProperty(hasSystemId, instance2.get("SystemId").toString());
		        		Property hasIsManualNetworkSelection = model.createProperty(phoneLabIRI+"#hasIsManualNetworkSelection");
		        		state.addProperty(hasIsManualNetworkSelection, instance2.get("IsManualNetworkSelection").toString());
		        		Property hasVoiceRegState = model.createProperty(phoneLabIRI+"#hasVoiceRegState");
		        		state.addProperty(hasVoiceRegState, instance2.get("VoiceRegState").toString());
		        		Property hasCSSIndicator = model.createProperty(phoneLabIRI+"#hasCSSIndicator");
		        		state.addProperty(hasCSSIndicator, instance2.get("CSSIndicator").toString());
		        		Property hasDataRegState = model.createProperty(phoneLabIRI+"#hasDataRegState");
		        		state.addProperty(hasDataRegState, instance2.get("DataRegState").toString());
		        		Property hasOperatorNumeric = model.createProperty(phoneLabIRI+"#hasOperatorNumeric");
		        		state.addProperty(hasOperatorNumeric, instance2.get("OperatorNumeric").toString());
		        		Property hasCdmaRoamingIndicator = model.createProperty(phoneLabIRI+"#hasCdmaRoamingIndicator");
		        		state.addProperty(hasCdmaRoamingIndicator, instance2.get("CdmaRoamingIndicator").toString());
		        		Property hasNetworkId = model.createProperty(phoneLabIRI+"#hasNetworkId");
		        		state.addProperty(hasNetworkId, instance2.get("NetworkId").toString());
		        		Property hasRilDataRadioTechology = model.createProperty(phoneLabIRI+"#hasRilDataRadioTechology");
		        		state.addProperty(hasRilDataRadioTechology, instance2.get("RilDataRadioTechology").toString());
		        		Property hasOperatorAlphaShort = model.createProperty(phoneLabIRI+"#hasOperatorAlphaShort");
		        		state.addProperty(hasOperatorAlphaShort, instance2.get("OperatorAlphaShort").toString());
		        		Property hasOperatorAlphaLong = model.createProperty(phoneLabIRI+"#hasOperatorAlphaLong");
		        		state.addProperty(hasOperatorAlphaLong, instance2.get("OperatorAlphaLong").toString());

		        		Property hasCdmaDefaultRomaingIndicator = model.createProperty(phoneLabIRI+"#hasCdmaDefaultRomaingIndicator");
		        		state.addProperty(hasCdmaDefaultRomaingIndicator, instance2.get("CdmaDefaultRomaingIndicator").toString());
		        		Property hasRilVoiceRadioTechology = model.createProperty(phoneLabIRI+"#hasRilVoiceRadioTechology");
		        		state.addProperty(hasRilVoiceRadioTechology, instance2.get("RilVoiceRadioTechology").toString());
		        		Property hasIsEmergencyOnly = model.createProperty(phoneLabIRI+"#hasIsEmergencyOnly");
		        		state.addProperty(hasIsEmergencyOnly, instance2.get("IsEmergencyOnly").toString());
		        		Property hasCdmaEriIconIndex = model.createProperty(phoneLabIRI+"#hasCdmaEriIconIndex");
		        		state.addProperty(hasCdmaEriIconIndex, instance2.get("CdmaEriIconIndex").toString());

		        		Property hasCdmaEriIconMode = model.createProperty(phoneLabIRI+"#hasCdmaEriIconMode");
		        		state.addProperty(hasCdmaEriIconMode, instance2.get("CdmaEriIconMode").toString());
		        		
		        		networkTelephony.addProperty(hasState, state);
		        				
		        	}else{
		        		networkTelephony.addProperty(hasState, instance.get("State").toString());
		        	}
		        }
		        

		        count++;
		        
		    }
		    is.close();
		}catch(Exception e){
			System.out.println(e);
		}
		
	}
}

