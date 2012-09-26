package sk.openhouse.pipelineservice.domain.response;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "phases")
public class PhasesResponse {

    @XmlElement(name = "phases")
    private List<PhaseResponse> phases = new ArrayList<PhaseResponse>();

    public List<PhaseResponse> getPhases() {
        return phases;
    }

    public void setPhases(List<PhaseResponse> phases) {
        this.phases = phases;
    }

    @Override
    public final int hashCode() {

        return new HashCodeBuilder()
                .append(phases)
                .toHashCode();
    }

    @Override
    public final boolean equals(Object object) {

        if (!(object instanceof PhasesResponse)) {
            return false;
        }

        final PhasesResponse other = (PhasesResponse) object;
        return new EqualsBuilder()
                .append(phases, other.phases)
                .isEquals();
    }

    @Override
    public String toString() {

        return new ToStringBuilder(this)
                .append("phases", phases)
                .toString();
    }
}
