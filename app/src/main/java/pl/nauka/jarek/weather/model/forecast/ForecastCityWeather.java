
package pl.nauka.jarek.weather.model.forecast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ForecastCityWeather {

    @SerializedName("cod")
    @Expose
    private String cod;
    @SerializedName("message")
    @Expose
    private Double message;
    @SerializedName("cnt")
    @Expose
    private Integer cnt;
    @SerializedName("list")
    @Expose
    private java.util.List<pl.nauka.jarek.weather.model.forecast.List> list = null;
    @SerializedName("city")
    @Expose
    private City city;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ForecastCityWeather() {
    }

    /**
     * 
     * @param message
     * @param cnt
     * @param cod
     * @param list
     * @param city
     */
    public ForecastCityWeather(String cod, Double message, Integer cnt, java.util.List<pl.nauka.jarek.weather.model.forecast.List> list, City city) {
        super();
        this.cod = cod;
        this.message = message;
        this.cnt = cnt;
        this.list = list;
        this.city = city;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Double getMessage() {
        return message;
    }

    public void setMessage(Double message) {
        this.message = message;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public java.util.List<pl.nauka.jarek.weather.model.forecast.List> getList() {
        return list;
    }

    public void setList(java.util.List<pl.nauka.jarek.weather.model.forecast.List> list) {
        this.list = list;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("cod", cod).append("message", message).append("cnt", cnt).append("list", list).append("city", city).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(message).append(cnt).append(cod).append(list).append(city).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ForecastCityWeather) == false) {
            return false;
        }
        ForecastCityWeather rhs = ((ForecastCityWeather) other);
        return new EqualsBuilder().append(message, rhs.message).append(cnt, rhs.cnt).append(cod, rhs.cod).append(list, rhs.list).append(city, rhs.city).isEquals();
    }

}
