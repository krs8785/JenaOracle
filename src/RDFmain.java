import java.io.File;
import java.util.ArrayList;


public class RDFmain {

	
	public static void main(String[] args) {
		
		 ArrayList<String> folderName = new ArrayList<String>();
		
		
		 File folders = new File("C:\\Users\\karan\\Desktop\\phoneData\\logcat");
	     File[] listOfFiles = folders.listFiles();
	     for(int idx=0; idx<listOfFiles.length; ++idx){
	            File file = listOfFiles[idx];
	            folderName.add(file.getName());   
	            
	     }
	     
	     for(int i=0; i<folderName.size();i++){
	    	
	    	String user = "User"+i;
	    	String folder = folderName.get(i);
	    	
	    	locationMiscN3 locationN3 = new locationMiscN3(user,folder);
	 		locationN3.startLoading();		

	 		//networkMiscN3 networkN3 = new networkMiscN3(user,folder);
	 		//networkN3.startLoading();
	 		
	 		//networkTelephonyN3 nt = new networkTelephonyN3(user, folder);
	 		//nt.startLoading();
	 		
	 		//networkWIFIN3 nw = new networkWIFIN3(user, folder);
	 		//nw.startLoading();
	 		
	 		//powerBatteryN3 pb = new powerBatteryN3(user, folder);
	 		//pb.startLoading();
	 		
	 		//powerScreenN3 ps = new powerScreenN3(user, folder);
	 		//ps.startLoading();
	    	
	    	//packageManagerN3 pk = new packageManagerN3(user,folder);
	    	//pk.startLoading();
	     }
	}
}
