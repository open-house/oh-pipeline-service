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
@XmlRootElement(name = "projectDetails")
@XmlType(propOrder = {"project", "versions"})
public class ProjectDetailsResponse {

    @XmlElement(name = "project")
    private ProjectResponse project = new ProjectResponse();

    @XmlElement(name = "versions")
    private VersionsResponse versions = new VersionsResponse();

    public ProjectResponse getProject() {
        return project;
    }

    public void setProject(ProjectResponse project) {
        this.project = project;
    }

    public VersionsResponse getVersions() {
        return versions;
    }

    public void setVersions(VersionsResponse versions) {
        this.versions = versions;
    }

    @Override
    public final int hashCode() {

        return new HashCodeBuilder()
                .append(project)
                .toHashCode();
    }

    @Override
    public final boolean equals(Object object) {

        if (!(object instanceof ProjectResponse)) {
            return false;
        }

        final ProjectDetailsResponse other = (ProjectDetailsResponse) object;
        return new EqualsBuilder()
                .append(project, other.project)
                .isEquals();
    }

    @Override
    public String toString() {

        return new ToStringBuilder(this)
                .append("project", project)
                .append("versions", versions)
                .toString();
    }
}
