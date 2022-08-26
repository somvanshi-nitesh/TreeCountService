package com.holidu.interview.assignment.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class DepartmentInfoController
{
    public DepartmentInfoController()
    {
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("departmentInfo/{departmentId}")
    public Response fetchDepartmentData(@PathParam("departmentId") String departmentId)
    {

        if (departmentId == null)
        {
            return Response.serverError()
                .entity("DepartmentId is null. Please provide departmentId")
                .build();
        }

        // TODO: As of now few sample department Json strings are used as reference,
        // in real-world scenarios read the value from Db or another endpoint/webservice
        String s1 = "{\"name\":\"ComputerScience\",\"DepartmentId\":1,\"NoofEmployees\":27}";
        String s2 = "{\"name\":\"Electronics\",\"DepartmentId\":2,\"NoofEmployees\":17}";
        String s3 = "{\"name\":\"Mechanical\",\"DepartmentId\":3,\"NoofEmployees\":19}";
        String s4 = "{\"name\":\"Civil\",\"DepartmentId\":4,\"NoofEmployees\":29}";
        String s5 = "{\"name\":\"Electrical\",\"DepartmentId\":5,\"NoofEmployees\":30}";
        String s6 = "{\"name\":\"Default\",\"DepartmentId\":6,\"NoofEmployees\":69}";

        if (departmentId != null && departmentId.equals("1"))
        {
            return Response.status(200).entity(s1).build();
        }
        if (departmentId != null && departmentId.equals("2"))
        {
            return Response.status(200).entity(s2).build();
        }

        if (departmentId != null && departmentId.equals("3"))
        {
            return Response.status(200).entity(s3).build();
        }
        if (departmentId != null && departmentId.equals("4"))
        {
            return Response.status(200).entity(s4).build();
        }
        if (departmentId != null && departmentId.equals("5"))
        {
            return Response.status(200).entity(s5).build();
        }
        
        return Response.status(200).entity(s6).build();

    }
}
