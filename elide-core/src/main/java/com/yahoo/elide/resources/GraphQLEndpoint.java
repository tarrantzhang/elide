/*
 * Copyright 2017, Yahoo Inc.
 * Licensed under the Apache License, Version 2.0
 * See LICENSE file in project root for terms.
 */
package com.yahoo.elide.resources;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yahoo.elide.Elide;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Default endpoint for using Elide and GraphQL.
 */
@Singleton
@Produces("application/vnd.api+json")
@Path("/graphql")
public class GraphQLEndpoint {
    protected static final ObjectMapper MAPPER = new ObjectMapper();
    protected final Elide elide;
    protected final Function<SecurityContext, Object> getUser;
//    protected final GraphQL graphQL;

    @Inject
    public GraphQLEndpoint(@Named("elide") Elide elide,
                           @Named("elideUserExtractionFunction") DefaultOpaqueUserFunction getUser) {
        this.elide = elide;
        this.getUser = getUser == null ? v -> null : getUser;

        // configure the schema
    }

    @GET
    public Response get(@PathParam("path") String path,
                        @Context SecurityContext securityContext) {
        String query = "";
        return executeRequest(query);
    }

    @POST
    public Response post(@PathParam("path") String path,
                        @Context SecurityContext securityContext,
                         String body) {
        String query = "";
        return executeRequest(query);
    }

    protected Response executeRequest(String request) {
        JsonNode json = parseRequest(request);
        String query = json.get("query").asText();
        Map<String, Object> variables = new HashMap<>();

        if (json.has("variables") && !json.get("variables").isNull()) {
            variables = MAPPER.convertValue(json.get("variables"), HashMap.class);
        }

//        ExecutionResult result = graphQL.execute(query, (Object) null, variables);
//        return Response.ok()
//                .entity(buildJson(result))
//                .type(MediaType.APPLICATION_JSON_TYPE).build();
        return Response.noContent().build();
    }

    protected JsonNode parseRequest(String request) {
        try {
            JsonNode json = MAPPER.readTree(request);
            if (!json.has("query")) {
                throw new WebApplicationException(400);
            }

            return json;
        } catch (IOException e) {
            throw new WebApplicationException(400);
        }
    }

//    protected String buildJson(ExecutionResult result) {
//        return "";
//    }
}
