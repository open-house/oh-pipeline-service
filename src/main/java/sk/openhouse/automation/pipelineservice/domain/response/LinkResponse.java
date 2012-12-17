package sk.openhouse.automation.pipelineservice.domain.response;

import java.net.URI;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Represents resource
 * 
 * @author pete
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "link")
@XmlType(propOrder = {"href", "method", "schemaLocation"})
public class LinkResponse {

    @XmlAttribute(name = "schemaLocation")
    private URI schemaLocation;

    @XmlAttribute(name = "method")
    private String method;

    @XmlAttribute(name = "href")
    private URI href;

    /**
     * @return resource xml schema location
     */
    public URI getSchemaLocation() {
        return schemaLocation;
    }

    /**
     * @param schemaLocation resource xml schema location
     */
    public void setSchemaLocation(URI schemaLocation) {
        this.schemaLocation = schemaLocation;
    }

    /**
     * @return http method
     */
    public String getMethod() {
        return method;
    }

    /**
     * @param method http method
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * @return uri of the resource
     */
    public URI getHref() {
        return href;
    }

    /**
     * @param link uri of the resource
     */
    public void setHref(URI href) {
        this.href = href;
    }

    @Override
    public final int hashCode() {

        return new HashCodeBuilder()
                .append(href)
                .append(method)
                .toHashCode();
    }

    @Override
    public final boolean equals(Object object) {

        if (!(object instanceof LinkResponse)) {
            return false;
        }

        final LinkResponse other = (LinkResponse) object;
        return new EqualsBuilder()
                .append(href, other.href)
                .append(method, other.method)
                .isEquals();
    }

    @Override
    public String toString() {

        return new ToStringBuilder(this)
                .append("schemaLocation", schemaLocation)
                .append("method", method)
                .append("href", href)
                .toString();
    }
}
