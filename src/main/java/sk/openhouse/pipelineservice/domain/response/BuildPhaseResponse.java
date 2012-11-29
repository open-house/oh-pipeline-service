package sk.openhouse.pipelineservice.domain.response;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "buildPhase")
@XmlType(propOrder = { "name", "states" })
public class BuildPhaseResponse {

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "states")
    private List<StateResponse> states = new ArrayList<StateResponse>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<StateResponse> getStates() {
        return states;
    }

    public void setStates(List<StateResponse> states) {
        this.states = states;
    }

    @Override
    public final int hashCode() {

        return new HashCodeBuilder()
                .append(name)
                .append(states)
                .toHashCode();
    }

    @Override
    public final boolean equals(Object object) {

        if (!(object instanceof BuildPhaseResponse)) {
            return false;
        }

        final BuildPhaseResponse other = (BuildPhaseResponse) object;
        return new EqualsBuilder()
                .append(name, other.name)
                .append(states, other.states)
                .isEquals();
    }

    @Override
    public String toString() {

        return new ToStringBuilder(this)
                .append("name", name)
                .append("states", states)
                .toString();
    }
}
