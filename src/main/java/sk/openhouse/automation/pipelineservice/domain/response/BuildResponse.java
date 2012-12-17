package sk.openhouse.automation.pipelineservice.domain.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "build")
@XmlType(propOrder = {"number", "buildPhases", "resources"})
public class BuildResponse {

    @XmlElement(name = "number")
    private int number;

    @XmlElement(name = "build-phases")
    private BuildPhasesResponse buildPhases = new BuildPhasesResponse();

    @XmlElement(name = "resources")
    private ResourcesResponse resources;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public BuildPhasesResponse getBuildPhases() {
        return buildPhases;
    }

    public void setBuildPhases(BuildPhasesResponse buildPhases) {
        this.buildPhases = buildPhases;
    }

    /**
     * @return list of resources for a project(s)
     */
    public ResourcesResponse getResources() {
        return resources;
    }

    /**
     * @param links list of resources for a project(s)
     */
    public void setResources(ResourcesResponse resources) {
        this.resources = resources;
    }

    @Override
    public final int hashCode() {

        return new HashCodeBuilder()
                .append(number)
                .toHashCode();
    }

    @Override
    public final boolean equals(Object object) {

        if (!(object instanceof BuildResponse)) {
            return false;
        }

        final BuildResponse other = (BuildResponse) object;
        return new EqualsBuilder()
                .append(number, other.number)
                .isEquals();
    }

    @Override
    public String toString() {

        return new ToStringBuilder(this)
                .append("number", number)
                .append("buildPhases", buildPhases)
                .append("resources", resources)
                .toString();
    }
}
