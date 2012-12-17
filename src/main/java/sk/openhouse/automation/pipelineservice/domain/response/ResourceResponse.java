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
@XmlRootElement(name = "resource")
@XmlType(propOrder = {"link", "description"})
public class ResourceResponse {

    @XmlElement(name = "link")
    private LinkResponse link;

    @XmlElement(name = "description")
    private String description;

    public LinkResponse getLink() {
        return link;
    }

    public void setLink(LinkResponse link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public final int hashCode() {

        return new HashCodeBuilder()
                .append(link)
                .toHashCode();
    }

    @Override
    public final boolean equals(Object object) {

        if (!(object instanceof ResourceResponse)) {
            return false;
        }

        final ResourceResponse other = (ResourceResponse) object;
        return new EqualsBuilder()
                .append(link, other.link)
                .isEquals();
    }

    @Override
    public String toString() {

        return new ToStringBuilder(this)
                .append("link", link)
                .append("description", description)
                .toString();
    }
}
