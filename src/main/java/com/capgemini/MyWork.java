package com.capgemini;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/work")
public class MyWork {
  @GET
  public Response performWork() {
    // Perform business logic here
    // Replace this with your actual business logic
    try {
      Byte[] memory = new Byte[10024 * 10024]; // 100MB
      Thread.sleep(5000); // Simulating some work for 5 seconds
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity("Work interrupted")
          .build();
    }

    return Response.ok("Work completed").build();
  }
}