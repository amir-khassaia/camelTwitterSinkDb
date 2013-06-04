package org.bitpimp.camelTwitterDb;


import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.json.DataObjectFactory;

public class CamelTwitterDbTest extends CamelTestSupport{
	@Produce(uri = "direct:start")
    protected ProducerTemplate template;
	
	@EndpointInject(uri = "mock:to")
	protected MockEndpoint toEndpoint;

	private final MyRouteBuilder routeBuilder = new MyRouteBuilder() {
		@Override
		public void configure() {
			from("direct:start")
				.tracing()
				.bean(getRouteProcessor())
				.to(toEndpoint);
		}
	};
	
	@Test
	public void testTwitterStatus() throws InterruptedException, TwitterException {
		final String rawJSON = "{\r\n" + 
				"	\"text\": \"RT @PostGradProblem: In preparation for the NFL lockout, I will be spending twice as much time analyzing my fantasy baseball team during ...\",\r\n" + 
				"	\"truncated\": true,\r\n" + 
				"	\"favorited\": false,\r\n" + 
				"	\"id_str\": \"54691802283900928\",\r\n" + 
				"	\"retweeted\": false,\r\n" + 
				"	\"place\": null,\r\n" + 
				"	\"retweet_count\": 4,\r\n" + 
				"	\"created_at\": \"Sun Apr 03 23:48:36 +0000 2011\",\r\n" + 
				"	\"user\": {\r\n" + 
				"		\"profile_use_background_image\": true,\r\n" + 
				"		\"statuses_count\": 351,\r\n" + 
				"		\"profile_background_color\": \"C0DEED\",\r\n" + 
				"		\"followers_count\": 48,\r\n" + 
				"		\"listed_count\": 0,\r\n" + 
				"		\"description\": \"watcha doin in my waters?\",\r\n" + 
				"		\"screen_name\": \"OldGREG85\",\r\n" + 
				"		\"default_profile\": true,\r\n" + 
				"		\"verified\": false,\r\n" + 
				"		\"time_zone\": \"Hawaii\",\r\n" + 
				"		\"profile_text_color\": \"333333\",\r\n" + 
				"		\"is_translator\": false,\r\n" + 
				"		\"profile_sidebar_fill_color\": \"DDEEF6\",\r\n" + 
				"		\"location\": \"Texas\",\r\n" + 
				"		\"id_str\": \"80177619\",\r\n" + 
				"		\"default_profile_image\": false,\r\n" + 
				"		\"profile_background_tile\": false,\r\n" + 
				"		\"lang\": \"en\",\r\n" + 
				"		\"friends_count\": 81,\r\n" + 
				"		\"protected\": false,\r\n" + 
				"		\"favourites_count\": 0,\r\n" + 
				"		\"created_at\": \"Tue Oct 06 01:13:17 +0000 2009\",\r\n" + 
				"		\"profile_link_color\": \"0084B4\",\r\n" + 
				"		\"name\": \"GG\",\r\n" + 
				"		\"show_all_inline_media\": false,\r\n" + 
				"		\"follow_request_sent\": null,\r\n" + 
				"		\"geo_enabled\": false,\r\n" + 
				"		\"profile_sidebar_border_color\": \"C0DEED\",\r\n" + 
				"		\"id\": 80177619,\r\n" + 
				"		\"contributors_enabled\": false,\r\n" + 
				"		\"utc_offset\": -36000\r\n" + 
				"	},\r\n" + 
				"	\"id\": 54691802283900930\r\n" + 
				"}";
		Status status = DataObjectFactory.createStatus(rawJSON);

        toEndpoint.expectedBodiesReceived(routeBuilder.stautsToJson(status));

        template.sendBodyAndHeader(status, "foo", "bar");

        toEndpoint.assertIsSatisfied();
	}
	
	@Override
	protected RouteBuilder createRouteBuilder() throws Exception {
		return routeBuilder ;
	}
	
}
