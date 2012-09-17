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
@XmlRootElement(name = "versionDetails")
@XmlType(propOrder = {"version", "builds"})
public class VersionDetailsResponse {

    @XmlElement(name = "version")
    private VersionResponse version = new VersionResponse();

    @XmlElement(name = "builds")
    private List<BuildResponse> builds = new ArrayList<BuildResponse>();

    public VersionResponse getVersion() {
        return version;
    }

    public void setVersion(VersionResponse version) {
        this.version = version;
    }

    public List<BuildResponse> getBuilds() {
        return builds;
    }

    public void setBuilds(List<BuildResponse> builds) {
        this.builds = builds;
    }

    @Override
    public final int hashCode() {

        return new HashCodeBuilder()
                .append(version)
                .append(builds)
                .toHashCode();
    }

    @Override
    public final boolean equals(Object object) {

        if (!(object instanceof VersionDetailsResponse)) {
            return false;
        }

        final VersionDetailsResponse other = (VersionDetailsResponse) object;
        return new EqualsBuilder()
                .append(version, other.version)
                .append(builds, other.builds)
                .isEquals();
    }

    @Override
    public String toString() {

        return new ToStringBuilder(this)
                .append("version", version)
                .append("builds", builds)
                .toString();
    }
}
