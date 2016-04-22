package examples;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.FileManager.*;
import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.sparql.*;

import java.io.*;

/**
 * Simple Hello servlet.
 */

public final class Hello1 extends HttpServlet {
    /**
     * Respond to a GET request for the content produced by
     * this servlet.
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are producing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
      throws IOException, ServletException {
    	String name =request.getParameter("fname");
    //    ResultSet result;
    	final String infile="C:\\Jena\\Tutorial\\arq\\assignment4.rdf";
    	Model model = ModelFactory.createDefaultModel();
		InputStream in=FileManager.get().open(infile);
		if(in==null)
		{
		throw new IllegalArgumentException("File: "+ infile +" not found");
		}
		
		model.read(in,"");
		String queryString= " prefix dc: <http://purl.org/dc/elements/1.1/> "+
	" SELECT ?y?z "+
	" WHERE "+
	" { "+
	" ?y dc:title ?z . "+
	" FILTER(REGEX(?z," +"\""+ name +"\"" + ")) " +
	" } ORDER BY ?z ";
	Query query=QueryFactory.create(queryString);
	QueryExecution qe= QueryExecutionFactory.create(query,model);
	ResultSet results=qe.execSelect();
	ByteArrayOutputStream bstrem = new ByteArrayOutputStream();
	ResultSetFormatter.out(bstrem,results,query);
//	System.out.println(new String(bstrem.toByteArray()));
	String output = new String(bstrem.toByteArray());
	
	output = output.replace("<", "");
	output = output.replace(">", "");
	System.out.print(output);
	//result=results;
    request.setAttribute("out", output);
    request.getRequestDispatcher("/return.jsp").forward(request, response);
	qe.close();
    }
    
    //public class JenaSparql1 extends Object {
    	
    	//}
    
} 