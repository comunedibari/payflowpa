package it.tasgroup.rest.utils;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Resource link model
 */
public abstract class RESTResource implements Serializable {
    private List<Link> links = new ArrayList<Link>();

    @XmlElement
    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
}
