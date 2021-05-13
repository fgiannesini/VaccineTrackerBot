import java.util.Objects;

public final class Office {

    private final long id;
    private final String city;
    private final String link;

    public Office(long id, String city, String link) {
        this.id = id;
        this.city = city;
        this.link = link;
    }

    public long getId() {
        return id;
    }

    public String getLink() {
        return link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Office office = (Office) o;
        return id == office.id && Objects.equals(city, office.city) && Objects.equals(link, office.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, city, link);
    }

    @Override
    public String toString() {
        return "Office{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", url='" + link + '\'' +
                '}';
    }
}
