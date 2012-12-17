package sk.openhouse.automation.pipelineservice.domain.request;

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
@XmlType(propOrder = {"name"})
public class ProjectRequest {

    @XmlElement(name = "name")
    private String name;

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


    @Override
    public final int hashCode() {

        return new HashCodeBuilder()
                .append(name)
                .toHashCode();
    }

    @Override
    public final boolean equals(Object object) {

        if (!(object instanceof ProjectRequest)) {
            return false;
        }

        final ProjectRequest other = (ProjectRequest) object;
        return new EqualsBuilder()
                .append(name, other.name)
                .isEquals();
    }

    @Override
    public String toString() {

        return new ToStringBuilder(this)
                .append("name", name)
                .toString();
    }
}
