

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


public class locationMiscN3 {
	
	final static String phoneLabIRI = "http://www.PhoneLab.com/ontologies/PhoneLab.owl";
	String user;
	static int count = 1;
	String folder;
	
	public locationMiscN3() {
		// TODO Auto-generated constructor stub
	}
	
	public locationMiscN3(String _user,String _folder) {
		// TODO Auto-generated constructor stub
		this.user = _user;
		this.folder = _folder;
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
		populateInstances("C:\\Users\\karan\\Downloads\\Compressed\\building\\"+getFolder()+"\\tag\\Location-Misc-PhoneLab\\2015\\03",model);

		String fileName = "locationMisc"+getUser()+".n3";
		
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

	private void performJsonToRdfConvert(File file,OntModelImpl model) {
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
		    	
		    	
		    	Resource locationMisc = model.createResource(phoneLabIRI+"#LocationMiscPhoneLab"+count);
		        
		    	Property hasTimeStamp = model.createProperty(phoneLabIRI+"#hasTimeStamp");
		        Property hasUser  = model.createProperty(phoneLabIRI+"#hasUser");
		        locationMisc.addProperty(hasTimeStamp,timestamp);
		        locationMisc.addProperty(hasUser, getUser());
		       
		        Property hasActionValue = model.createProperty(phoneLabIRI+"#hasActionValue");
		        locationMisc.addProperty(hasActionValue, instance.getString("Action"));
		       
		        Property hasCounter = model.createProperty(phoneLabIRI+"#hasCounter");
		        locationMisc.addProperty(hasCounter, instance.get("Counter").toString());
		        
		        Property hasLogFormat = model.createProperty(phoneLabIRI+"#hasLogFormat");
		        locationMisc.addProperty(hasLogFormat, instance.get("LogFormat").toString());
		        
		        Resource location  = model.createResource(phoneLabIRI+"#location"+count);
		    
		        Property hasElapsedRealTimeNanosValue = model.createProperty(phoneLabIRI+"#hasElapsedRealTimeNanosValue");
		        Property hasHasSpeedValue = model.createProperty(phoneLabIRI+"#hasHasSpeedValue");
		        Property hasIsFromMockProviderValue = model.createProperty(phoneLabIRI+"#hasIsFromMockProviderValue");
		        Property hasHasBearingValue = model.createProperty(phoneLabIRI+"#hasHasBearingValue");
		        Property hasAccuracyValue = model.createProperty(phoneLabIRI+"#hasAccuracyValue");
		        Property hasAltitudeValue = model.createProperty(phoneLabIRI+"#hasAltitudeValue");
		        Property hasSpeedValue = model.createProperty(phoneLabIRI+"#hasSpeedValue");
		        Property hasTimeValue = model.createProperty(phoneLabIRI+"#hasTimeValue");
		        Property hasHasAltitudeValue = model.createProperty(phoneLabIRI+"#hasHasAltitudeValue");
		        Property hasLatitudeValue = model.createProperty(phoneLabIRI+"#hasLatitudeValue");
		        Property hasLongitudeValue = model.createProperty(phoneLabIRI+"#hasLongitudeValue");
		        Property hasBearingValue = model.createProperty(phoneLabIRI+"#hasBearingValue");
		        Property hasProviderValue = model.createProperty(phoneLabIRI+"#hasProviderValue");
		        Property hasHasAccuracyValue = model.createProperty(phoneLabIRI+"#hasHasAccuracyValue");
		        
		        
		        JSONObject instance1 = new JSONObject();
		        instance1 = instance.getJSONObject("Location");
		        location.addProperty(hasElapsedRealTimeNanosValue, instance1.get("ElapsedRealtimeNanos").toString());

		        location.addProperty(hasHasSpeedValue, instance1.get("HasSpeed").toString());
		        location.addProperty(hasIsFromMockProviderValue, instance1.get("IsFromMockProvider").toString());
		        location.addProperty(hasHasBearingValue, instance1.get("HasBearing").toString());
		        location.addProperty(hasAccuracyValue, instance1.get("Accuracy").toString());
		        location.addProperty(hasAltitudeValue, instance1.get("Altitude").toString());
		        location.addProperty(hasSpeedValue, instance1.get("Speed").toString());
		        location.addProperty(hasHasAltitudeValue, instance1.get("HasAltitude").toString());
		        location.addProperty(hasTimeValue, instance1.get("Time").toString());
		        location.addProperty(hasLatitudeValue, instance1.get("Latitude").toString());
		        location.addProperty(hasLongitudeValue, instance1.get("Longitude").toString());

		        location.addProperty(hasBearingValue, instance1.get("Bearing").toString());
		        location.addProperty(hasProviderValue, instance1.get("Provider").toString());
		        location.addProperty(hasHasAccuracyValue, instance1.get("HasAccuracy").toString());
		        
		        Property hasLocation = model.createProperty(phoneLabIRI+"#hasLocation");
		        locationMisc.addProperty(hasLocation, location);
		    	count++;
		    	
		  	}
		    is.close();
		}catch(Exception e){
			System.out.println(e);
		}
		
	}
}
