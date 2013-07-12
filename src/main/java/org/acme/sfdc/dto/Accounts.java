package org.acme.sfdc.dto;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class Accounts implements Serializable
{
   private int totalSize;
   private boolean done;
   
   @JsonProperty("records")
   List<AccountRecord> records;

   /**
    * @return the done
    */
   public boolean isDone()
   {
      return done;
   }

   /**
    * @param done the done to set
    */
   public void setDone(boolean done)
   {
      this.done = done;
   }

   /**
    * @return the records
    */
   public List<AccountRecord> getRecords()
   {
      return records;
   }

   /**
    * @param records the records to set
    */
   public void setRecords(List<AccountRecord> records)
   {
      this.records = records;
   }

   /**
    * @return the totalSize
    */
   public int getTotalSize()
   {
      return totalSize;
   }

   /**
    * @param totalSize the totalSize to set
    */
   public void setTotalSize(int totalSize)
   {
      this.totalSize = totalSize;
   }
   
   
}