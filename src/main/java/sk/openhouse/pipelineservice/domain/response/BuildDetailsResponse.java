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
@XmlRootElement(name = "buildDetails")
@XmlType(propOrder = {"build"})
public class BuildDetailsResponse {

    @XmlElement(name = "build")
    private BuildResponse build = new BuildResponse();

    public BuildResponse getBuild() {
        return build;
    }

    public void setBuilds(BuildResponse build) {
        this.build = build;
    }

    @Override
    public final int hashCode() {

        return new HashCodeBuilder()
                .append(build)
                .toHashCode();
    }

    @Override
    public final boolean equals(Object object) {

        if (!(object instanceof VersionDetailsResponse)) {
            return false;
        }

        final BuildDetailsResponse other = (BuildDetailsResponse) object;
        return new EqualsBuilder()
                .append(build, other.build)
                .isEquals();
    }

    @Override
    public String toString() {

        return new ToStringBuilder(this)
                .append("build", build)
                .toString();
    }
}
