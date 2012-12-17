package sk.openhouse.automation.pipelineservice.domain.request;

import java.net.URI;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "phase")
@XmlType(propOrder = {"name", "uri"})
public class PhaseRequest {

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
                .append(uri)
                .toHashCode();
    }

    @Override
    public final boolean equals(Object object) {

        if (!(object instanceof PhaseRequest)) {
            return false;
        }

        final PhaseRequest other = (PhaseRequest) object;
        return new EqualsBuilder()
                .append(name, other.name)
                .append(uri, other.uri)
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