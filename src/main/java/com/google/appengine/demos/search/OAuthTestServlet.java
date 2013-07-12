package com.google.appengine.demos.search;

import org.acme.sfdc.dto.SFDCAuthorization;
import org.apache.amber.oauth2.client.OAuthClient;
import org.apache.amber.oauth2.client.URLConnectionClient;
import org.apache.amber.oauth2.client.request.OAuthClientRequest;
import org.apache.amber.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.amber.oauth2.client.response.OAuthAuthzResponse;
import org.apache.amber.oauth2.common.message.types.GrantType;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStreamReader;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

public class OAuthTestServlet extends HttpServlet
{

   private static final Logger LOG = Logger.getLogger(OAuthTestServlet.class.getSimpleName());

   // TODO: Put these in a properties file
   private static String CALLBACK_URL = "https://codenvy-zazarie.appspot.com/authorize_sfdc";

   private static String SFDC_CLIENT_ID =
      "3MVG9A2kN3Bn17hvaearcHEUAs7jYVBcKsNQ8JUt1P.Z.i86xJspN_ijYiJ5pHD9Fj_ZfVAZxrueQ0USr_01m";

   private static String SFDC_CLIENT_SECRET = "6989414199582572543";

   private static String SFDC_SCOPE = "api id web refresh_token";

   @Override
   public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException
   {

      LOG.info("Landed inside /OAuthTestServlet servlet...");

      // Step 2 of the oauth dance
      if (req.getParameter("code") != null)
      {

         try
         {

            OAuthAuthzResponse oar = OAuthAuthzResponse.oauthCodeAuthzResponse(req);

            String code = oar.getCode();
            
            String body = buildOauthBody(CALLBACK_URL, code, SFDC_CLIENT_ID, SFDC_CLIENT_SECRET);
            
            LOG.info("body is: " + body);
            
            SFDCAuthorization auth = fetchAccessToken(body, "https://login.salesforce.com/services/oauth2/token");
            
            LOG.info("Auth token is: " + auth.getAccessToken());
            
            // Redirect back to index
        	  resp.sendRedirect("/search");

         }
         catch (Exception e)
         {
            LOG.severe("Error in processing oauth: " + e);
         }
      }

      try
      {

         // TODO: Add hardcoded values to property file
         OAuthClientRequest rRequest =
            OAuthClientRequest.authorizationLocation("https://login.salesforce.com/services/oauth2/authorize")
               .setClientId(SFDC_CLIENT_ID)
               .setRedirectURI(CALLBACK_URL).setResponseType("code")
               .setScope(SFDC_SCOPE).setParameter("duration", "permanent").setState("teststate").buildQueryMessage();

         LOG.info("URL for salesforce oauth is:: " + rRequest.getLocationUri());

         resp.sendRedirect(rRequest.getLocationUri());

      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
   }

   /*
    * Builds the HTTP OAuth body content for Salesforce
    */
   public static String buildOauthBody(String redirectUrl, String accessCode, String clientId, String clientPass)
   {

      URLCodec encoder = new URLCodec();

      try
      {
         // valid grant_types are refresh_token and authorization_code

         return "grant_type=authorization_code&redirect_uri=" + encoder.encode(redirectUrl) + "&code=" + accessCode
            + "&grant_type=" + GrantType.AUTHORIZATION_CODE + "&client_id=" + clientId + "&&client_secret="
            + clientPass;
      }
      catch (EncoderException e)
      {
         e.printStackTrace();
      }

      return null;
   }

   /*  
    * Calls step2 of the oauth dance and returns results as a string
    */
   public static SFDCAuthorization fetchAccessToken(String body, String endpointUrl) throws Exception
   {

      ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally

      URL url = new URL(endpointUrl);

      LOG.info("Endpoint URL is: " + endpointUrl);

      HttpURLConnection conn = (HttpURLConnection)url.openConnection();
      conn.setRequestMethod("POST");
      conn.setDoOutput(true);
      conn.setRequestProperty("Accept", "application/json");

      OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
      writer.write(body);
      writer.close();

      if (conn.getResponseCode() != 200)
      {
         throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
      }

      BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

      /*
      String output = "";

      String line;
      while ((line = br.readLine()) != null)
      {
         output += line + "\n";
      }
      br.close();

      LOG.info("Output from Server .... " + output);

      return output;
      */
      
      SFDCAuthorization auth = mapper.readValue(br, SFDCAuthorization.class);

		  conn.disconnect();

		  LOG.info("auth is: " + auth);

		  return auth;

   }

}