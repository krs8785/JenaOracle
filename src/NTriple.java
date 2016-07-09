import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.ProfileRegistry;
import com.hp.hpl.jena.rdf.model.ModelFactory;



public class NTriple {

    static String xmlbase = "http://www.soctrace.org/ontologies/#";
    static String ontopath = "PhoneLabProgram.owl";

    public static void main(String[] args) throws IOException {

        manageOntologies();
    }

    public static void manageOntologies() throws IOException{
        OntModel domainModel =ModelFactory.createOntologyModel(ProfileRegistry.OWL_DL_LANG);
        domainModel.read((new FileInputStream(ontopath)), null,"RDF/XML");        
        //domainModel.write(System.out,"TURTLE");
        System.out.println("start");
        String fileName = "networkTelephonyMisc10.n3";
        FileWriter out = new FileWriter( fileName );
        try {
        	domainModel.write( out, "N-TRIPLE" );
        }
        finally {
        	System.out.println("done");
           try {
               out.close();
           }
           catch (IOException closeException) {
               // ignore
           }
        }
    }   
}