package sk.openhouse.pipelineservice.domain.response;

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
@XmlType(propOrder = {"name", "callURI", "pollURI", "timeoutSeconds", "orderIndex"})
public class PhaseResponse {

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "callURI")
    private URI callURI;

    @XmlElement(name = "pollURI")
    private URI pollURI;

    @XmlElement(name = "timeoutSeconds")
    private int timeoutSeconds = 360;

    @XmlElement(name = "orderIndex")
    private int orderIndex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public URI getCallURI() {
        return callURI;
    }

    public void setCallURI(URI callURI) {
        this.callURI = callURI;
    }

    public URI getPollURI() {
        return pollURI;
    }

    public void setPollURI(URI pollURI) {
        this.pollURI = pollURI;
    }

    public int getTimeoutSeconds() {
        return timeoutSeconds;
    }

    public void setTimeoutSeconds(int timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }

    @Override
    public final int hashCode() {

        return new HashCodeBuilder()
                .append(name)
                .append(callURI)
                .append(pollURI)
                .append(timeoutSeconds)
                .append(orderIndex)
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
                .append(callURI, other.callURI)
                .append(pollURI, other.pollURI)
                .append(timeoutSeconds, other.timeoutSeconds)
                .append(orderIndex, other.orderIndex)
                .isEquals();
    }

    @Override
    public String toString() {

        return new ToStringBuilder(this)
                .append("name", name)
                .append("callURI", callURI)
                .append("pollURI", pollURI)
                .append("timeoutSeconds", timeoutSeconds)
                .append("orderIndex", orderIndex)
                .toString();
    }
}
