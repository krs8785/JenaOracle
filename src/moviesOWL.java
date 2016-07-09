
import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.impl.OntModelImpl;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.vocabulary.XSD;

public class moviesOWL {

	public static void main(String[] args) {
		OntModelImpl model = (OntModelImpl) ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
		String movieURI = "http://imdb/movies/";
		model.createOntology(movieURI);
		
		OntClass movie = model.createClass(movieURI+"#movie");
		
		
		DatatypeProperty hasName = model.createDatatypeProperty(movieURI+"#hasName");
		hasName.setDomain(movie);
		hasName.setRange(XSD.xstring);
		
		DatatypeProperty releaseYear = model.createDatatypeProperty(movieURI+"#releaseYear");
		releaseYear.setDomain(movie);
		releaseYear.setRange(XSD.integer);
		
		DatatypeProperty storyWriter = model.createDatatypeProperty(movieURI+"#storyWriter");
		storyWriter.setDomain(movie);
		storyWriter.setRange(XSD.xstring);
		
		Individual HarryPotterAzkaban = model.createIndividual(movieURI+"#ABC",movie);
		HarryPotterAzkaban.addLiteral(hasName, "ABC");
		HarryPotterAzkaban.addLiteral(releaseYear, 1881);
		HarryPotterAzkaban.addLiteral(storyWriter, "Joanne Rowling");
		
		Individual LordOfRings = model.createIndividual(movieURI+"#Terminatr",movie);
		LordOfRings.addLiteral(hasName, "Terminatr");
		LordOfRings.addLiteral(releaseYear, 1882);
		LordOfRings.addLiteral(storyWriter, "Jeffrey Howard Archer");
		
		Individual HouseOfCards = model.createIndividual(movieURI+"#Narcos",movie);
		HouseOfCards.addLiteral(hasName, "Narcos");
		HouseOfCards.addLiteral(releaseYear, 1882);
		HouseOfCards.addLiteral(storyWriter, "Paulo Coelho de Souza");
		

		Individual ForestGump = model.createIndividual(movieURI+"#pulpFictin",movie);
		ForestGump.addLiteral(hasName, "pulpFictin");
		ForestGump.addLiteral(releaseYear, 1883);
		ForestGump.addLiteral(storyWriter, "Robert Toru Kiyosak");
		

		Individual IpMan = model.createIndividual(movieURI+"#IpMan",movie);
		IpMan.addLiteral(hasName, "IpMan");
		IpMan.addLiteral(releaseYear, 1884);
		IpMan.addLiteral(storyWriter, "Robert Toru Kiyosak");
		
		Individual Superman = model.createIndividual(movieURI+"#batman",movie);
		Superman.addLiteral(hasName, "batman");
		Superman.addLiteral(releaseYear, 1886);
		Superman.addLiteral(storyWriter, "Steven Spielberg");
		
		
		

		model.write(System.out,"N-TRIPLE");
	}
}
