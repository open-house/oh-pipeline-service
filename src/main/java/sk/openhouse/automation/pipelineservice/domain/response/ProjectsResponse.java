package sk.openhouse.automation.pipelineservice.domain.response;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "projects")
public class ProjectsResponse {

    @XmlElement(name = "project")
    List<ProjectResponse> projects = new ArrayList<ProjectResponse>();

    public List<ProjectResponse> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectResponse> projects) {
        this.projects = projects;
    }

    @Override
    public final int hashCode() {

        return new HashCodeBuilder()
                .append(projects)
                .toHashCode();
    }

    @Override
    public final boolean equals(Object object) {

        if (!(object instanceof ProjectsResponse)) {
            return false;
        }

        final ProjectsResponse other = (ProjectsResponse) object;
        return new EqualsBuilder()
                .append(projects, other.projects)
                .isEquals();
    }

    @Override
    public String toString() {

        return new ToStringBuilder(this)
                .append("projects", projects)
                .toString();
    }
}
