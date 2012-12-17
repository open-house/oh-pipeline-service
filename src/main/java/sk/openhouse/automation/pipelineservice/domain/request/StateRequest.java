package sk.openhouse.automation.pipelineservice.domain.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import sk.openhouse.automation.pipelineservice.domain.PhaseState;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "state")
@XmlType(propOrder = { "name" })
public class StateRequest {

    @XmlElement(name = "name")
    private PhaseState name;

    public PhaseState getName() {
        return name;
    }

    public void setName(PhaseState name) {
        this.name = name;
    }

    @Override
    public final int hashCode() {

        return new HashCodeBuilder()
                .append(name)
                .toHashCode();
    }

    @Override
    public final boolean equals(Object object) {

        if (!(object instanceof StateRequest)) {
            return false;
        }

        final StateRequest other = (StateRequest) object;
        return new EqualsBuilder()
                .append(name, other.name)
                .isEquals();
    }

    @Override
    public String toString() {

        return new ToStringBuilder(this)
                .append("name", name)
                .toString();
    }
}