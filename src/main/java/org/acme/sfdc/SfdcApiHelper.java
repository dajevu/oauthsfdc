package org.acme.sfdc;

import org.acme.sfdc.SfdcApiHelper;
import org.acme.sfdc.dto.SFDCAuthorization;
import org.acme.sfdc.dto.Accounts;
import org.apache.amber.oauth2.common.message.types.GrantType;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

public class SfdcApiHelper
{

   private static final Logger LOG = Logger.getLogger(SfdcApiHelper.class.getSimpleName());
   private static final String ACCOUNT_QUERY = "/services/data/v28.0/query?q=SELECT+name,industry,description,type,NumberOfEmployees+from+Account+ORDER+BY+Name+ASC+NULLS+FIRST";

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
         LOG.severe(e.getMessage());
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

   public static Accounts getAccounts(String accessToken, String endpointUrl) throws Exception {

      ObjectMapper mapper = new ObjectMapper();
      
      URL url = new URL(endpointUrl + ACCOUNT_QUERY);

      HttpURLConnection conn = (HttpURLConnection)url.openConnection();
      conn.setRequestMethod("GET");
      conn.setDoOutput(true);
      conn.setRequestProperty("Accept", "application/json");
      conn.addRequestProperty("Authorization", "Bearer " + accessToken);

      conn.connect();

      if (conn.getResponseCode() != 200)
      {
         throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
      }

      BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
      
      Accounts accounts = mapper.readValue(br, Accounts.class);   
         
      br.close();

      LOG.info("Output from Server .... " + accounts.getRecords());

      conn.disconnect();
      
      return accounts;

   }
   
   public static void getUserId(String accessToken, String endpointUrl) throws Exception {
      URL url = new URL(endpointUrl);

      HttpURLConnection conn = (HttpURLConnection)url.openConnection();
      conn.setRequestMethod("GET");
      conn.setDoOutput(true);
      conn.setRequestProperty("Accept", "application/json");
      conn.addRequestProperty("Authorization", "Bearer " + accessToken);
		  conn.connect();

      if (conn.getResponseCode() != 200)
      {
         throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
      }

      String output = "";

      BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

      String line;
      while ((line = br.readLine()) != null)
      {
         output += line + "\n";
      }
      br.close();

      LOG.info("Output from Server .... " + output);

      conn.disconnect();

   }
}