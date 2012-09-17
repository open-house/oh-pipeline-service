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
 * List of resources
 * 
 * @author pete
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "resources")
public class ResourcesResponse {

    @XmlElement(name = "resource")
    private List<ResourceResponse> resources = new ArrayList<ResourceResponse>();

    /**
     * @return list of resources
     */
    public List<ResourceResponse> getResources() {
        return resources;
    }

    /**
     * @param resources list of resources
     */
    public void setResources(List<ResourceResponse> resources) {
        this.resources = resources;
    }

    @Override
    public final int hashCode() {

        return new HashCodeBuilder()
                .append(resources)
                .toHashCode();
    }

    @Override
    public final boolean equals(Object object) {

        if (!(object instanceof ResourcesResponse)) {
            return false;
        }

        final ResourcesResponse other = (ResourcesResponse) object;
        return new EqualsBuilder()
                .append(resources, other.resources)
                .isEquals();
    }

    @Override
    public String toString() {

        return new ToStringBuilder(this)
                .append("resources", resources)
                .toString();
    }
}
