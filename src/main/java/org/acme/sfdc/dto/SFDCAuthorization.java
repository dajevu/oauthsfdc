package org.acme.sfdc.dto;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/*
 * Represents JSON authorization object returned by SFDC.
 */
public class SFDCAuthorization implements Serializable
{
   private String id;
   
   @JsonProperty("issued_at")
   private String issuedAt;
   
   private String scope;
   
   @JsonProperty("instance_url")
   private String instanceUrl;
   
   @JsonProperty("refresh_token")
   private String refreshToken;
   
   private String signature;
   
   @JsonProperty("access_token")
   private String accessToken;
   
   /**
    * @return the accessToken
    */
   public String getAccessToken()
   {
      return accessToken;
   }
   /**
    * @param accessToken the accessToken to set
    */
   public void setAccessToken(String accessToken)
   {
      this.accessToken = accessToken;
   }
   /**
    * @return the id
    */
   public String getId()
   {
      return id;
   }
   /**
    * @param id the id to set
    */
   public void setId(String id)
   {
      this.id = id;
   }
   /**
    * @return the instanceUrl
    */
   public String getInstanceUrl()
   {
      return instanceUrl;
   }
   /**
    * @param instanceUrl the instanceUrl to set
    */
   public void setInstanceUrl(String instanceUrl)
   {
      this.instanceUrl = instanceUrl;
   }
   /**
    * @return the issuedAt
    */
   public String getIssuedAt()
   {
      return issuedAt;
   }
   /**
    * @param issuedAt the issuedAt to set
    */
   public void setIssuedAt(String issuedAt)
   {
      this.issuedAt = issuedAt;
   }
   /**
    * @return the refreshToken
    */
   public String getRefreshToken()
   {
      return refreshToken;
   }
   /**
    * @param refreshToken the refreshToken to set
    */
   public void setRefreshToken(String refreshToken)
   {
      this.refreshToken = refreshToken;
   }
   /**
    * @return the scope
    */
   public String getScope()
   {
      return scope;
   }
   /**
    * @param scope the scope to set
    */
   public void setScope(String scope)
   {
      this.scope = scope;
   }
   /**
    * @return the signature
    */
   public String getSignature()
   {
      return signature;
   }
   /**
    * @param signature the signature to set
    */
   public void setSignature(String signature)
   {
      this.signature = signature;
   }
   
   
}