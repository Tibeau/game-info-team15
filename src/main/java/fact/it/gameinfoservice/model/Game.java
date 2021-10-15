package fact.it.gameinfoservice.model;
import javax.persistence.*;

@Entity
public class Game {
    @Column(unique=true)

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    private String name;
    private int release_year;
    private String developer;
    private int sales;

    public Game() { }
    public Game(String name,int release_year, String developer,int sales) {
        setName(name);
        setDeveloper(developer);
        setSales(sales);
        setRelease_year(release_year);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRelease_year() {
        return release_year;
    }

    public void setRelease_year(int release_year) {
        this.release_year = release_year;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }


}


