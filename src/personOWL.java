




import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.impl.OntModelImpl;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.vocabulary.XSD;




public class personOWL {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		OntModelImpl model = (OntModelImpl) ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
		String bookURI = "http://library/books/";
		String authorURI = "http://authors/";
		model.createOntology(bookURI);
		
		OntClass books = model.createClass(bookURI+"#books");
		OntClass author  = model.createClass(authorURI+"#author");
		

		ObjectProperty hasPublished = model.createObjectProperty(authorURI+"#hasPublished");
		
		
		ObjectProperty hasAuthor = model.createObjectProperty(authorURI+"#hasAuthor");
		hasAuthor.setDomain(author);
		hasAuthor.setRange(author);
		hasAuthor.setInverseOf(hasPublished);
		
		
		DatatypeProperty hasName = model.createDatatypeProperty(authorURI+"#hasName");
		hasName.setDomain(author);
		hasName.setRange(XSD.xstring);	

		DatatypeProperty hasBirthYear = model.createDatatypeProperty(authorURI+"#hasBirthYear");
		hasBirthYear.setDomain(author);
		hasBirthYear.setRange(XSD.integer);	
		
		
		Individual HarryPotter_Azkaban = model.createIndividual(bookURI+"#HarryPotter-Azkaban",books);
		Individual HarryPotter_HalfBlood = model.createIndividual(bookURI+"#HarryPotter-HalfBlood",books);
		Individual JKRowling = model.createIndividual(authorURI+"#JKRowling",author);		
		HarryPotter_Azkaban.setPropertyValue(hasAuthor, JKRowling);
		HarryPotter_HalfBlood.setPropertyValue(hasAuthor, JKRowling);
		JKRowling.addLiteral(hasName, "Joanne Rowling");
		JKRowling.addLiteral(hasBirthYear, 1881);		
		
		Individual Alchemist = model.createIndividual(bookURI+"#Alchemist",books);
		Individual PauloCoelho = model.createIndividual(authorURI+"#PauloCoelho",author);
		PauloCoelho.addLiteral(hasName, "Paulo Coelho de Souza");
		PauloCoelho.addLiteral(hasBirthYear, 1882);
		Alchemist.setPropertyValue(hasAuthor, PauloCoelho);				

		Individual NotAPennyLessNotAPennyMore = model.createIndividual(bookURI+"#NotAPennyLessNotAPennyMore",books);
		Individual JeffreyArcher = model.createIndividual(authorURI+"#JeffreyArcher",author);
		JeffreyArcher.addLiteral(hasName, "Jeffrey Howard Archer");
		JeffreyArcher.addLiteral(hasBirthYear, 1883);
		NotAPennyLessNotAPennyMore.setPropertyValue(hasAuthor, JeffreyArcher);
		
		Individual RichDadPoorDad = model.createIndividual(bookURI+"#RichDadPoorDad",books);
		Individual RobertKiyoski = model.createIndividual(authorURI+"#RobertKiyoski",author);
		RobertKiyoski.addLiteral(hasName, "Robert Toru Kiyosak");
		RobertKiyoski.addLiteral(hasBirthYear, 1884);
		RichDadPoorDad.setPropertyValue(hasAuthor, RobertKiyoski);
		
		Individual TheFirm = model.createIndividual(bookURI+"#TheFirm",books);
		Individual JohnGrisham = model.createIndividual(authorURI+"#JohnGrisham",author);
		JohnGrisham.addLiteral(hasName, "John Ray Grisham");
		JohnGrisham.addLiteral(hasBirthYear, 1885);
		TheFirm.setPropertyValue(hasAuthor, JohnGrisham);	
		
		
		model.write(System.out,"N-TRIPLE");
		
	}

}
