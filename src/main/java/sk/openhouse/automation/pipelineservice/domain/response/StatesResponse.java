package sk.openhouse.automation.pipelineservice.domain.response;

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
@XmlRootElement(name = "states")
@XmlType(propOrder = { "states" })
public class StatesResponse {

    @XmlElement(name = "states")
    private List<StateResponse> states = new ArrayList<StateResponse>();

    public List<StateResponse> getStates() {
        return states;
    }

    public void setStates(List<StateResponse> states) {
        this.states = states;
    }

    @Override
    public final int hashCode() {

        return new HashCodeBuilder()
                .append(states)
                .toHashCode();
    }

    @Override
    public final boolean equals(Object object) {

        if (!(object instanceof StatesResponse)) {
            return false;
        }

        final StatesResponse other = (StatesResponse) object;
        return new EqualsBuilder()
                .append(states, other.states)
                .isEquals();
    }

    @Override
    public String toString() {

        return new ToStringBuilder(this)
                .append("states", states)
                .toString();
    }
}
