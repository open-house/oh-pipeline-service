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
@XmlRootElement(name = "project")
@XmlType(propOrder = {"name", "resources"})
public class ProjectResponse {

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "resources")
    private ResourcesResponse resources;

    /**
     * @return unique product name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name product name
     */
    public void setName(String name) {
        this.name = name;
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
                .append(name)
                .toHashCode();
    }

    @Override
    public final boolean equals(Object object) {

        if (!(object instanceof ProjectResponse)) {
            return false;
        }

        final ProjectResponse other = (ProjectResponse) object;
        return new EqualsBuilder()
                .append(name, other.name)
                .isEquals();
    }

    @Override
    public String toString() {

        return new ToStringBuilder(this)
                .append("name", name)
                .append("resources", resources)
                .toString();
    }
}