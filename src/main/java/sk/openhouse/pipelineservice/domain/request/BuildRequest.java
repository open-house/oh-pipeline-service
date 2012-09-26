package sk.openhouse.pipelineservice.domain.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "build")
@XmlType(propOrder = {"number"})
public class BuildRequest {

    @XmlElement(name = "number")
    private int number;

    /**
     * @return product build number
     */
    public int getNumber() {
        return number;
    }

    /**
     * @param number product build number
     */
    public void setNumber(int number) {
        this.number = number;
    }


    @Override
    public final int hashCode() {

        return new HashCodeBuilder()
                .append(number)
                .toHashCode();
    }

    @Override
    public final boolean equals(Object object) {

        if (!(object instanceof BuildRequest)) {
            return false;
        }

        final BuildRequest other = (BuildRequest) object;
        return new EqualsBuilder()
                .append(number, other.number)
                .isEquals();
    }

    @Override
    public String toString() {

        return new ToStringBuilder(this)
                .append("number", number)
                .toString();
    }
}
