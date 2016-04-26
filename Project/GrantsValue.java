package examples;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;


/**
 * Simple Hello servlet.
 */

public final class GrantsValue extends HttpServlet {


	 public void doGet(HttpServletRequest request,
			 HttpServletResponse response)
throws IOException, ServletException {

final String infile="C:\\Users\\keshs\\Desktop\\M.S Data Science\\Data Semantics\\2012-05-23_vivo.iu.edu_export.rdf";
String name =request.getParameter("value");
//int value = Integer.parseInt(name);
Model model = ModelFactory.createDefaultModel();
InputStream in=FileManager.get().open(infile);
if(in==null)
{
throw new IllegalArgumentException("File: "+ infile +" not found");
}

model.read(in,"");
String queryString= " prefix dc: <http://purl.org/dc/elements/1.1/> "+
		" prefix rdfs:<http://www.w3.org/2000/01/rdf-schema#>" +
	" prefix j.10:<http://xmlns.com/foaf/0.1/>" +
	" prefix j.3:<http://vivo.iu.edu/ontology/iuvivo#>" +
	" prefix j.5:<http://vivoweb.org/ontology/core#>" +
	" prefix xsd: <http://www.w3.org/2001/XMLSchema#>" +
" SELECT DISTINCT ?x?p "+
" WHERE "+
" { "+
" ?y rdfs:label ?x ." +
" ?y j.5:totalAwardAmount ?p ." +
" FILTER(xsd:integer(?p) > "+"xsd:integer(\""+name+"\")"+")" +
	" } ";
Query query=QueryFactory.create(queryString);
QueryExecution qe= QueryExecutionFactory.create(query,model);
ResultSet results=qe.execSelect();
ByteArrayOutputStream bstrem = new ByteArrayOutputStream();
ResultSetFormatter.out(bstrem,results,query);
//System.out.println(new String(bstrem.toByteArray()));
String output = new String(bstrem.toByteArray());

output = output.replace("<", "");
output = output.replace(">", "");
//System.out.print(output);
//result=results;
request.setAttribute("out2", output);
request.getRequestDispatcher("/return3.jsp").forward(request, response);
qe.close();
}
}
