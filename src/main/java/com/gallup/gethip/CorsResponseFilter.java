package com.gallup.gethip;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

public class CorsResponseFilter implements ContainerResponseFilter {

	@Override
	public ContainerResponse filter(ContainerRequest requestContext, ContainerResponse responseContext) {
		System.err.println("Filtering for cors");
		responseContext.getHttpHeaders().add("Access-Control-Allow-Origin", "*");
		responseContext.getHttpHeaders().add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
		responseContext.getHttpHeaders().add("Access-COntrol-Allow-Headers", "X-Requested-With, Content-Type");

		return responseContext;
	}

}