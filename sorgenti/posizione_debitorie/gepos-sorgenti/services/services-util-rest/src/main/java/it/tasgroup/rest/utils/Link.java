package it.tasgroup.rest.utils;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.net.URI;

/**
 * Link model
 */
@XmlRootElement
public class Link {
    private URI extendedRel;
    private String rel;
    private URI href;


    public Link() {
    }

    public Link(String registeredRelation, URI href) {
        this.rel = registeredRelation;
        this.href = href;
    }


    public Link(URI extendedRelation, URI href) {
        this.extendedRel = extendedRelation;
        this.href = href;
    }

    public Link(URI extendedRel) {
        this.extendedRel = extendedRel;
    }

    @XmlAttribute
    public String getRel() {
        return extendedRel != null ? extendedRel.toString() : rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    @XmlAttribute
    public URI getHref() {
        return href;
    }

    public void setHref(URI href) {
        this.href = href;
    }
}
