
package com.example.renitto.ktu_master.Models;

import java.util.HashMap;
import java.util.Map;



public class Paging {

    private String previous;
    private String next;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The previous
     */
    public String getPrevious() {
        return previous;
    }

    /**
     * 
     * @param previous
     *     The previous
     */
    public void setPrevious(String previous) {
        this.previous = previous;
    }

    /**
     * 
     * @return
     *     The next
     */
    public String getNext() {
        return next;
    }

    /**
     * 
     * @param next
     *     The next
     */
    public void setNext(String next) {
        this.next = next;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
