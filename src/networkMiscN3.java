

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

import org.json.JSONArray;
import org.json.JSONObject;

import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.impl.OntModelImpl;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class networkMiscN3 {

	
	final static String phoneLabIRI = "http://www.PhoneLab.com/ontologies/PhoneLab.owl";
	String user;
	static int count = 1;
	String folder;
	
	public networkMiscN3() {
		// TODO Auto-generated constructor stub
	}
	public networkMiscN3(String u, String f) {
		// TODO Auto-generated constructor stub
		user = u;
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
		
		populateInstances("C:\\Users\\karan\\Downloads\\Compressed\\building\\"+getFolder()+"\\tag\\Network-Misc-PhoneLab\\2015\\03",model);
		 
		//File size too large. May be break down upto certain threshold
		String fileName = "networkMisc"+getUser()+".n3";
		
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
	            System.out.println("done");
	            
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
		    
		    
		    	Resource networkMisc = model.createResource(phoneLabIRI+"#NetworkMiscPhoneLab"+count);
		        
		     	Property hasTimeStamp = model.createProperty(phoneLabIRI+"#hasTimeStamp");
		        Property hasUser  = model.createProperty(phoneLabIRI+"#hasUser");
		        networkMisc.addProperty(hasTimeStamp,timestamp);
		        networkMisc.addProperty(hasUser, getUser());
		       
		        Property hasActionValue = model.createProperty(phoneLabIRI+"#hasActionValue");
		        networkMisc.addProperty(hasActionValue, instance.getString("Action"));
		       
		        Property hasCounter = model.createProperty(phoneLabIRI+"#hasCounter");
		        networkMisc.addProperty(hasCounter, instance.get("Counter").toString());
		        
		        Property hasLogFormat = model.createProperty(phoneLabIRI+"#hasLogFormat");
		        networkMisc.addProperty(hasLogFormat, instance.get("LogFormat").toString());

		        
		        if(instance.has("Samples")){
		        	JSONArray samples = instance.getJSONArray("Samples"); 
		        	for(int i=0;i<samples.length();i++){
		        		int sampleCount = 1;
		        		JSONObject obj = samples.getJSONObject(i);
		        		JSONObject innerObj = obj.getJSONObject("wlan0");
		        		

				    	Resource Sample = model.createResource(phoneLabIRI+"#network"+count+"Sample"+sampleCount);
				        

				        Property hasTxPacketCount = model.createProperty(phoneLabIRI+"#hasTxPacketCount");
				        Sample.addProperty(hasTxPacketCount, innerObj.get("TxPacketCount").toString());

				        Property hasRxByteCount = model.createProperty(phoneLabIRI+"#hasRxByteCount");
				        Sample.addProperty(hasRxByteCount,  innerObj.get("RxByteCount").toString());

				        Property hasRxPacketErrorCount = model.createProperty(phoneLabIRI+"#hasRxPacketErrorCount");
				        Sample.addProperty(hasRxPacketErrorCount,  innerObj.get("RxPacketErrorCount").toString());

				        Property hasTimetamp = model.createProperty(phoneLabIRI+"#hasTimetamp");
				        Sample.addProperty(hasTimetamp,  innerObj.get("Timetamp").toString());


				        Property hasTxByteCount= model.createProperty(phoneLabIRI+"#hasTxByteCount");
				        Sample.addProperty(hasTxByteCount,  innerObj.get("TxByteCount").toString());


				        Property hasTxPacketErrorCount = model.createProperty(phoneLabIRI+"#hasTxPacketErrorCount");
				        Sample.addProperty(hasTxPacketErrorCount,  innerObj.get("TxPacketErrorCount").toString());
				        
				        Property hasRxPacketCount = model.createProperty(phoneLabIRI+"#hasRxPacketCount");
				        Sample.addProperty(hasRxPacketCount, innerObj.get("RxPacketCount").toString());
				    	
				    	
				    	
				        Property hasSample = model.createProperty(phoneLabIRI+"#hasSample");
				        networkMisc.addProperty(hasSample, Sample);

				    	
				    	sampleCount++;
				        
		        		
		        	}
		        }else {
		        	if(instance.has("XtSnapshot")){
		        		JSONObject obj = instance.getJSONObject("XtSnapshot");
		        		JSONArray entries = obj.getJSONArray("Entries"); 
		        		
		        		Resource XtSnapShot = model.createResource(phoneLabIRI+"#network"+count+"XtSnapShot");
		        		
		        		int entryCount = 1;
		        		
		        		for (int i = 0; i < entries.length(); i++) {
		        			
							JSONObject innerObj = entries.getJSONObject(i);
							
							
							Resource entry = model.createResource(phoneLabIRI+"#network"+count+"XtSnapShot"+"Entry"+entryCount);
							
							Property hasTxBytes = model.createProperty(phoneLabIRI+"#hasTxBytes");
							entry.addProperty(hasTxBytes, innerObj.get("TxBytes").toString());
							Property hasOperations = model.createProperty(phoneLabIRI+"#hasOperations");
							entry.addProperty(hasOperations, innerObj.get("Operations").toString());
							Property hasTag = model.createProperty(phoneLabIRI+"#hasTag");
							entry.addProperty(hasTag, innerObj.get("Tag").toString());
							Property hasRxBytes = model.createProperty(phoneLabIRI+"#hasRxBytes");
							entry.addProperty(hasRxBytes, innerObj.get("RxBytes").toString());
							Property hasSet= model.createProperty(phoneLabIRI+"#hasSet");
							entry.addProperty(hasSet, innerObj.get("Set").toString());
							Property hasRxPackets= model.createProperty(phoneLabIRI+"#hasRxPackets");
							entry.addProperty(hasRxPackets, innerObj.get("RxPackets").toString());
							Property hasTxPackets = model.createProperty(phoneLabIRI+"#hasTxPackets");
							entry.addProperty(hasTxPackets, innerObj.get("TxPackets").toString());
							Property hasIface = model.createProperty(phoneLabIRI+"#hasIface");
							entry.addProperty(hasIface, innerObj.get("Iface").toString());
							Property hasUid = model.createProperty(phoneLabIRI+"#hasUid");
							entry.addProperty(hasUid, innerObj.get("Uid").toString());
							
							Property hasEntry = model.createProperty(phoneLabIRI+"#hasEntry");							
							XtSnapShot.addProperty(hasEntry, entry);
							entryCount++;
							
						}
		        		Property hasXtSnapShot = model.createProperty(phoneLabIRI+"#hasXtSnapShot");
		        		networkMisc.addProperty(hasXtSnapShot, XtSnapShot);
		        	}
		        	
		        	
		        	if(instance.has("UidSnapshot")){
		        		JSONObject obj = instance.getJSONObject("UidSnapshot");
		        		JSONArray entries = obj.getJSONArray("Entries"); 
		        		
		        		Resource UidSnapshot = model.createResource(phoneLabIRI+"#network"+count+"UidSnapshot");
		        		
		        		int entryCount = 1;
		        		
		        		for (int i = 0; i < entries.length(); i++) {
		        			
							JSONObject innerObj = entries.getJSONObject(i);
							
							
							Resource entry = model.createResource(phoneLabIRI+"#network"+count+"UidSnapshot"+"Entry"+entryCount);
							
							Property hasTxBytes = model.createProperty(phoneLabIRI+"#hasTxBytes");
							entry.addProperty(hasTxBytes, innerObj.get("TxBytes").toString());
							Property hasOperations = model.createProperty(phoneLabIRI+"#hasOperations");
							entry.addProperty(hasOperations, innerObj.get("Operations").toString());
							Property hasTag = model.createProperty(phoneLabIRI+"#hasTag");
							entry.addProperty(hasTag, innerObj.get("Tag").toString());
							Property hasRxBytes = model.createProperty(phoneLabIRI+"#hasRxBytes");
							entry.addProperty(hasRxBytes, innerObj.get("RxBytes").toString());
							Property hasSet= model.createProperty(phoneLabIRI+"#hasSet");
							entry.addProperty(hasSet, innerObj.get("Set").toString());
							Property hasRxPackets= model.createProperty(phoneLabIRI+"#hasRxPackets");
							entry.addProperty(hasRxPackets, innerObj.get("RxPackets").toString());
							Property hasTxPackets = model.createProperty(phoneLabIRI+"#hasTxPackets");
							entry.addProperty(hasTxPackets, innerObj.get("TxPackets").toString());
							Property hasIface = model.createProperty(phoneLabIRI+"#hasIface");
							entry.addProperty(hasIface, innerObj.get("Iface").toString());
							Property hasUid = model.createProperty(phoneLabIRI+"#hasUid");
							entry.addProperty(hasUid, innerObj.get("Uid").toString());
							
							Property hasEntry = model.createProperty(phoneLabIRI+"#hasEntry");							
							UidSnapshot.addProperty(hasEntry, entry);
							entryCount++;
							
						}
		        		Property hasUidSnapshot = model.createProperty(phoneLabIRI+"#hasUidSnapshot");
		        		networkMisc.addProperty(hasUidSnapshot, UidSnapshot);
		        	}
		        	
		        	if(instance.has("DevSnapshot")){
		        		JSONObject obj = instance.getJSONObject("DevSnapshot");
		        		JSONArray entries = obj.getJSONArray("Entries"); 
		        		
		        		Resource DevSnapshot = model.createResource(phoneLabIRI+"#network"+count+"DevSnapshot");
		        		
		        		int entryCount = 1;
		        		
		        		for (int i = 0; i < entries.length(); i++) {
		        			
							JSONObject innerObj = entries.getJSONObject(i);
							
							
							Resource entry = model.createResource(phoneLabIRI+"#network"+count+"DevSnapshot"+"Entry"+entryCount);
							
							Property hasTxBytes = model.createProperty(phoneLabIRI+"#hasTxBytes");
							entry.addProperty(hasTxBytes, innerObj.get("TxBytes").toString());
							Property hasOperations = model.createProperty(phoneLabIRI+"#hasOperations");
							entry.addProperty(hasOperations, innerObj.get("Operations").toString());
							Property hasTag = model.createProperty(phoneLabIRI+"#hasTag");
							entry.addProperty(hasTag, innerObj.get("Tag").toString());
							Property hasRxBytes = model.createProperty(phoneLabIRI+"#hasRxBytes");
							entry.addProperty(hasRxBytes, innerObj.get("RxBytes").toString());
							Property hasSet= model.createProperty(phoneLabIRI+"#hasSet");
							entry.addProperty(hasSet, innerObj.get("Set").toString());
							Property hasRxPackets= model.createProperty(phoneLabIRI+"#hasRxPackets");
							entry.addProperty(hasRxPackets, innerObj.get("RxPackets").toString());
							Property hasTxPackets = model.createProperty(phoneLabIRI+"#hasTxPackets");
							entry.addProperty(hasTxPackets, innerObj.get("TxPackets").toString());
							Property hasIface = model.createProperty(phoneLabIRI+"#hasIface");
							entry.addProperty(hasIface, innerObj.get("Iface").toString());
							Property hasUid = model.createProperty(phoneLabIRI+"#hasUid");
							entry.addProperty(hasUid, innerObj.get("Uid").toString());
							
							Property hasEntry = model.createProperty(phoneLabIRI+"#hasEntry");							
							DevSnapshot.addProperty(hasEntry, entry);
							entryCount++;
							
						}
		        		Property hasDevSnapshot = model.createProperty(phoneLabIRI+"#hasDevSnapshot");
		        		networkMisc.addProperty(hasDevSnapshot, DevSnapshot);
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

