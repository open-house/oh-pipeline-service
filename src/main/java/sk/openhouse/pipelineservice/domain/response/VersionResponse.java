package sk.openhouse.pipelineservice.domain.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "version")
@XmlType(propOrder = {"versionNumber", "resources"})
public class VersionResponse {

    @XmlElement(name = "version-number")
    private String versionNumber;

    @XmlElement(name = "resources")
    private ResourcesResponse resources;

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
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
                .append(versionNumber)
                .toHashCode();
    }

    @Override
    public final boolean equals(Object object) {

        if (!(object instanceof VersionResponse)) {
            return false;
        }

        final VersionResponse other = (VersionResponse) object;
        return new EqualsBuilder()
                .append(versionNumber, other.versionNumber)
                .isEquals();
    }

    @Override
    public String toString() {

        return new ToStringBuilder(this)
                .append("number", versionNumber)
                .append("resources", resources)
                .toString();
    }
}
