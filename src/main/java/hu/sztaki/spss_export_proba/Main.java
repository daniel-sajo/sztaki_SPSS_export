/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.sztaki.spss_export_proba;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import spssexport.DBMain;

/**
 *
 * @author dani
 */
@Path("/hello")
public class Main {
    @GET
    @Path("/{param}")
    public Response getMsg(@PathParam("param") String message) {
        String output = "Hello " + message + "!";
        
        spssexport.DBMain csvExport = new DBMain();
        String csv = csvExport.createCSV();
        
        return Response.status(200).entity(csv).build();
    }
    
    
}
