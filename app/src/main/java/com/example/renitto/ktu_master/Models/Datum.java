
package com.example.renitto.ktu_master.Models;

import java.util.HashMap;
import java.util.Map;



public class Datum {
    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    private String created_time;
    private String id;
    private Attachments attachments;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The createdTime
     */


    /**
     * 
     * @param createdTime
     *     The created_time
     */


    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The attachments
     */
    public Attachments getAttachments() {
        return attachments;
    }

    /**
     * 
     * @param attachments
     *     The attachments
     */
    public void setAttachments(Attachments attachments) {
        this.attachments = attachments;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
