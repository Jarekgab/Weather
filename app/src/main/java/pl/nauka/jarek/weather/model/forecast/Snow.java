
package pl.nauka.jarek.weather.model.forecast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Snow {

    @SerializedName("3h")
    @Expose
    private Double _3h;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Snow() {
    }

    /**
     * 
     * @param _3h
     */
    public Snow(Double _3h) {
        super();
        this._3h = _3h;
    }

    public Double get3h() {
        return _3h;
    }

    public void set3h(Double _3h) {
        this._3h = _3h;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("_3h", _3h).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(_3h).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Snow) == false) {
            return false;
        }
        Snow rhs = ((Snow) other);
        return new EqualsBuilder().append(_3h, rhs._3h).isEquals();
    }

}
