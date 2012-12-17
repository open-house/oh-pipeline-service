package sk.openhouse.automation.pipelineservice.domain.response;

import java.net.URI;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Represents a single project phase
 * 
 * @author pete
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "phase")
@XmlType(propOrder = {"name", "uri"})
public class PhaseResponse {

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "uri")
    private URI uri;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    @Override
    public final int hashCode() {

        return new HashCodeBuilder()
                .append(name)
                .toHashCode();
    }

    @Override
    public final boolean equals(Object object) {

        if (!(object instanceof PhaseResponse)) {
            return false;
        }

        final PhaseResponse other = (PhaseResponse) object;
        return new EqualsBuilder()
                .append(name, other.name)
                .isEquals();
    }

    @Override
    public String toString() {

        return new ToStringBuilder(this)
                .append("name", name)
                .append("uri", uri)
                .toString();
    }
}
