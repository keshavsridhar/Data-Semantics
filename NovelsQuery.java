import java.io.*;
import java.util.Iterator;
import com.hp.hpl.jena.util.*;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.reasoner.*;
import com.hp.hpl.jena.reasoner.rulesys.*;

public class NovelsQuery {

	private static String fname = "novels.rdf";
	private static String NS = "http://resource/";
	
	public static void main(String args[]) {

	Model rawData = FileManager.get().loadModel(fname);

    String rules = "[r1: (?x http://imdb.com/link/authorOf ?y), (?y http://imdb.com/link/isMovie ?z)" + 
    				"-> (?x isCoProducer ?z)]";
	
    Reasoner reasoner = new GenericRuleReasoner(Rule.parseRules(rules));
	InfModel inf = ModelFactory.createInfModel(reasoner, rawData);
	Resource A = inf.getResource(NS + "J.K.Rowling");
	
	System.out.println("J.K Rowling * * =>");
	Iterator list = inf.listStatements(A, null, (RDFNode)null);
	while (list.hasNext()) {
    	System.out.println(" - " + list.next());
		}
 	}
}


