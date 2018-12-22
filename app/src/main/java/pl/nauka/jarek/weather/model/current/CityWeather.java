
package pl.nauka.jarek.weather.model.current;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class CityWeather {

    @SerializedName("coord")
    @Expose
    private Coord coord;
    @SerializedName("weather")
    @Expose
    private Weather weather;
    @SerializedName("base")
    @Expose
    private String base;
    @SerializedName("main")
    @Expose
    private Main main;
    @SerializedName("visibility")
    @Expose
    private Integer visibility;
    @SerializedName("wind")
    @Expose
    private Wind wind;
    @SerializedName("clouds")
    @Expose
    private Clouds clouds;
    @SerializedName("dt")
    @Expose
    private Integer dt;
    @SerializedName("sys")
    @Expose
    private Sys sys;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("cod")
    @Expose
    private Integer cod;

    /**
     * No args constructor for use in serialization
     *
     */
    public CityWeather() {
    }

    /**
     * 
     * @param clouds
     * @param coord
     * @param wind
     * @param sys
     * @param weather
     * @param main
     */
    public CityWeather(Coord coord, Weather weather, String base, Main main, Integer visibility, Wind wind, Clouds clouds, Integer dt, Sys sys, Integer id, String name, Integer cod) {
        super();
        this.coord = coord;
        this.weather = weather;
        this.base = base;
        this.main = main;
        this.visibility = visibility;
        this.wind = wind;
        this.clouds = clouds;
        this.dt = dt;
        this.sys = sys;
        this.id = id;
        this.name = name;
        this.cod = cod;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public Weather getWeather() {
        return (Weather) weather;
    }

    public void setWeather(Weather weather) {
        this.weather =  weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("coord", coord).append("weather", weather).append("base", base).append("main", main).append("visibility", visibility).append("wind", wind).append("clouds", clouds).append("dt", dt).append("sys", sys).append("id", id).append("name", name).append("cod", cod).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(dt).append(clouds).append(wind).append(visibility).append(sys).append(main).append(id).append(coord).append(cod).append(name).append(base).append(weather).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof CityWeather) == false) {
            return false;
        }
        CityWeather rhs = ((CityWeather) other);
        return new EqualsBuilder().append(dt, rhs.dt).append(clouds, rhs.clouds).append(wind, rhs.wind).append(visibility, rhs.visibility).append(sys, rhs.sys).append(main, rhs.main).append(id, rhs.id).append(coord, rhs.coord).append(cod, rhs.cod).append(name, rhs.name).append(base, rhs.base).append(weather, rhs.weather).isEquals();
    }

}
