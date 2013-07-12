package org.acme.sfdc.dto;

import org.acme.sfdc.dto.Attributes;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

public class AccountRecord implements Serializable
{

   private Attributes attributes;

   @JsonProperty("Name")
   private String name;

   @JsonProperty("Industry")
   private String industry;

   @JsonProperty("Description")
   private String description;

   @JsonProperty("Type")
   private String type;

   @JsonProperty("NumberOfEmployees")
   private int numberOfEmployees;

   /**
   * @return the attributes
   */
   public Attributes getAttributes()
   {
      return attributes;
   }

   /**
    * @param attributes the attributes to set
    */
   public void setAttributes(Attributes attributes)
   {
      this.attributes = attributes;
   }

   /**
    * @return the description
    */
   public String getDescription()
   {
      return description;
   }

   /**
    * @param description the description to set
    */
   public void setDescription(String description)
   {
      this.description = description;
   }

   /**
    * @return the industry
    */
   public String getIndustry()
   {
      return industry;
   }

   /**
    * @param industry the industry to set
    */
   public void setIndustry(String industry)
   {
      this.industry = industry;
   }

   /**
    * @return the name
    */
   public String getName()
   {
      return name;
   }

   /**
    * @param name the name to set
    */
   public void setName(String name)
   {
      this.name = name;
   }

   /**
    * @return the numberOfEmployees
    */
   public int getNumberOfEmployees()
   {
      return numberOfEmployees;
   }

   /**
    * @param numberOfEmployees the numberOfEmployees to set
    */
   public void setNumberOfEmployees(int numberOfEmployees)
   {
      this.numberOfEmployees = numberOfEmployees;
   }

   /**
    * @return the type
    */
   public String getType()
   {
      return type;
   }

   /**
    * @param type the type to set
    */
   public void setType(String type)
   {
      this.type = type;
   }

}