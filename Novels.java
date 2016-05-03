import java.util.*;
import java.io.*;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.util.FileManager;


public class Novels {

  static final String resUri = "http://resource/";
  static final String linkUri = "http://imdb.com/link/";
  
    public static void main(String args[]) {
		
    Model model = ModelFactory.createDefaultModel();
    Resource NAMESPACE = model.createResource( linkUri );
       	model.setNsPrefix( "link", linkUri);
    Property authorOf = model.createProperty(linkUri,"authorOf");
    //Property hasSequel = model.createProperty(linkUri,"hasSequel");
    Property isMovie = model.createProperty(linkUri,"isMovie");
    
    // Authors
    Resource JKR = model.createResource(resUri+"J.K.Rowling");
    Resource CP = model.createResource(resUri+"ChristopherPaolini");
    Resource JS = model.createResource(resUri+"JonathanStroud");
	//Books
    Resource HP = model.createResource(resUri+"HarryPotter");
	Resource ER = model.createResource(resUri+"Inheritance");
	Resource BM = model.createResource(resUri+"Bartimaeus");
	//Movies
    Resource HPM = model.createResource(resUri+"HarryPotterMovies");
    Resource ERM = model.createResource(resUri+"EragonMovies");

    JKR.addProperty(authorOf,HP);
	HP.addProperty(isMovie,HPM);
	
	CP.addProperty(authorOf,ER);
	ER.addProperty(isMovie,ERM);
	
	JS.addProperty(authorOf,BM);
	
	try{
	File file=new File("C:\\Jena\\Tutorial\\familytree\\novels.rdf");
   	FileOutputStream f1=new FileOutputStream(file);
   	RDFWriter d = model.getWriter("RDF/XML-ABBREV");
   			d.write(model,f1,null);
		}catch(Exception e) {}
  }
}
