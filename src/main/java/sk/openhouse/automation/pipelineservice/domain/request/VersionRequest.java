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
@XmlRootElement(name = "version")
@XmlType(propOrder = {"version-number"})
public class VersionRequest {

    @XmlElement(name = "version-number")
    private String versionNumber;

    /**
     * @return product version number
     */
    public String getVersionNumber() {
        return versionNumber;
    }

    /**
     * @param number product version number
     */
    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }


    @Override
    public final int hashCode() {

        return new HashCodeBuilder()
                .append(versionNumber)
                .toHashCode();
    }

    @Override
    public final boolean equals(Object object) {

        if (!(object instanceof VersionRequest)) {
            return false;
        }

        final VersionRequest other = (VersionRequest) object;
        return new EqualsBuilder()
                .append(versionNumber, other.versionNumber)
                .isEquals();
    }

    @Override
    public String toString() {

        return new ToStringBuilder(this)
                .append("versionNumber", versionNumber)
                .toString();
    }
}
