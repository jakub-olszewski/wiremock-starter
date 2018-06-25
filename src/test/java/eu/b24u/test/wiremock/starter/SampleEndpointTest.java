package eu.b24u.test.wiremock.starter;

import com.github.tomakehurst.wiremock.WireMockServer;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class SampleEndpointTest {

	int port = 7892;

	String path = "/test";
	String baseURI = "http://127.0.0.1";

	String content = "<HTML><HEAD><TITLE>title</TITLE></HEAD><BODY><P>Hello World!</BODY></HTML>";

	private WireMockServer wireMockServer;

	@Test
	public void test() {
		// @formatter:off
		stubFor(get(urlEqualTo("/test")).
				withHeader("Accept", equalTo("text/xml")).
				willReturn(aResponse()
				.withStatus(200).
				withHeader("Content-Type", "text/xml").
				withBody(content)));
		
		RestAssured.given().
        	header("Accept", "text/xml"). 
        	//  param("Key", "NuDVhdsfYmNkDLOZQ").
        	//  param("ConId", "xyz").
		    when().
		        get("/test").
		    then().
		        contentType(ContentType.XML).
		        statusCode(200);
		// @formatter:on
	}

	@BeforeClass
	public void before() {
		RestAssured.baseURI = baseURI;
		RestAssured.port = port;
		wireMockServer = new WireMockServer(port);
		wireMockServer.start();
		configureFor("localhost", port);
	}

	@AfterClass
	public void after() {
		wireMockServer.stop();
	}

}
