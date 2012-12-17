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
@XmlRootElement(name = "build-phases")
@XmlType(propOrder = { "buildPhases" })
public class BuildPhasesResponse {

    @XmlElement(name = "build-phase")
    private List<BuildPhaseResponse> buildPhases = new ArrayList<BuildPhaseResponse>();

    public List<BuildPhaseResponse> getBuildPhases() {
        return buildPhases;
    }

    public void setBuildPhases(List<BuildPhaseResponse> buildPhases) {
        this.buildPhases = buildPhases;
    }

    @Override
    public final int hashCode() {

        return new HashCodeBuilder()
                .append(buildPhases)
                .toHashCode();
    }

    @Override
    public final boolean equals(Object object) {

        if (!(object instanceof BuildPhasesResponse)) {
            return false;
        }

        final BuildPhasesResponse other = (BuildPhasesResponse) object;
        return new EqualsBuilder()
                .append(buildPhases, other.buildPhases)
                .isEquals();
    }

    @Override
    public String toString() {

        return new ToStringBuilder(this)
                .append("buildPhases", buildPhases)
                .toString();
    }
}
