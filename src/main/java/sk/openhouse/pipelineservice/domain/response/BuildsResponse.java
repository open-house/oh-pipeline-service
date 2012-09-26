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

/**
 * List of builds
 * 
 * @author pete
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "builds")
public class BuildsResponse {

    @XmlElement(name = "build")
    private List<BuildResponse> builds = new ArrayList<BuildResponse>();

    /**
     * @return list of builds
     */
    public List<BuildResponse> getBuilds() {
        return builds;
    }

    /**
     * @param builds list of builds
     */
    public void setBuilds(List<BuildResponse> builds) {
        this.builds = builds;
    }

    @Override
    public final int hashCode() {

        return new HashCodeBuilder()
                .append(builds)
                .toHashCode();
    }

    @Override
    public final boolean equals(Object object) {

        if (!(object instanceof BuildsResponse)) {
            return false;
        }

        final BuildsResponse other = (BuildsResponse) object;
        return new EqualsBuilder()
                .append(builds, other.builds)
                .isEquals();
    }

    @Override
    public String toString() {

        return new ToStringBuilder(this)
                .append("builds", builds)
                .toString();
    }
}
