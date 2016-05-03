import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.util.FileManager.*;
import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.query.ARQ;
import com.hp.hpl.jena.sparql.*;

import java.io.*;

public class JenaSparql1 extends Object {
static final String infile="assignment4.rdf";
	public static void main(String args[])
	{
	Model model = ModelFactory.createDefaultModel();
	InputStream in=FileManager.get().open(infile);
	if(in==null)
	{
	throw new IllegalArgumentException("File: "+ infile +" not found");
	}
	
	model.read(in,"");
	String queryString= "prefix dc: <http://purl.org/dc/elements/1.1/>"+
"SELECT ?y?z"+
"WHERE"+
"{"+
"?y dc:title ?z ."+
"FILTER(REGEX(?z,'The','i'))"+
"}";
Query query=QueryFactory.create(queryString);
QueryExecution qe= QueryExecutionFactory.create(query,model);
ResultSet results=qe.execSelect();
ResultSetFormatter.out(System.out,results,query);
qe.close();
	}
}