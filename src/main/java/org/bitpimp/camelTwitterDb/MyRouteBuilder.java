package org.bitpimp.camelTwitterDb;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.properties.PropertiesComponent;

import twitter4j.Status;

import com.google.gson.JsonObject;

/**
 * A Camel Java DSL Router
 */
public class MyRouteBuilder extends RouteBuilder {

	/**
     * Let's configure the Camel routing rules using Java code...
     */
    @Override
	public void configure() {

    	// Configure Properties component
    	PropertiesComponent pc = new PropertiesComponent();
    	pc.setLocation("application.properties");
    	getContext().addComponent("properties", pc);
    	
    			
    	// Setup route from Twitter endpoint using configured properties
    	from("twitter://search?type={{search.type}}" +
    			"&keywords={{search.keywords}}" +
    			"&delay={{search.delay}}" +
    			"&consumerKey={{consumerKey}}" +
    			"&consumerSecret={{consumerSecret}}" +
    			"&accessToken={{accessToken}}" +
    			"&accessTokenSecret={{accessTokenSecret}}")
        		.tracing()
        		// Processor to convert Twitter4j status to Gson payload for CouchDB
        		.process(new Processor() {
					@Override
					public void process(final Exchange exchange) throws Exception {
						final Status status = (Status) exchange.getIn().getBody();
						// CouchDB needs Gson object or String as a document base
						final JsonObject jsonStatus = new JsonObject();
						jsonStatus.addProperty("user", status.getUser().getName());
						jsonStatus.addProperty("timestamp", status.getCreatedAt().toString());
						jsonStatus.addProperty("text", status.getText());
						// push new message body
						exchange.getIn().setBody(jsonStatus);
						
						log.info("***************************************");
						log.info("User: " + status.getUser().getName());
						log.info("Date: " + status.getCreatedAt());
						log.info("Text: " + status.getText());
						log.info("***************************************");
					}
        		})
        		// Send to CouchDB endpoint
        		.to("{{url}}");
    }
}
