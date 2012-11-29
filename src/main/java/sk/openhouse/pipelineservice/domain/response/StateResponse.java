package sk.openhouse.pipelineservice.domain.response;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "state")
@XmlType(propOrder = { "name", "date" })
public class StateResponse {

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "date")
    private Date date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public final int hashCode() {

        return new HashCodeBuilder()
                .append(name)
                .append(date)
                .toHashCode();
    }

    @Override
    public final boolean equals(Object object) {

        if (!(object instanceof StateResponse)) {
            return false;
        }

        final StateResponse other = (StateResponse) object;
        return new EqualsBuilder()
                .append(name, other.name)
                .append(date, other.date)
                .isEquals();
    }

    @Override
    public String toString() {

        return new ToStringBuilder(this)
                .append("name", name)
                .append("date", date)
                .toString();
    }
}
