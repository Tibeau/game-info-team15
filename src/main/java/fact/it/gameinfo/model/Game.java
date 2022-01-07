package fact.it.gameinfo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "games")
public class Game {

    @Id
    private String id;

    private String name;
    private int release_year;
    private String developerName;
    private int sales;

    public Game() { }
    public Game(String name,int release_year, String developerName,int sales) {
        setName(name);
        setDeveloperName(developerName);
        setSales(sales);
        setRelease_year(release_year);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getDeveloperName() {
        return developerName;
    }

    public void setDeveloperName(String developerName) {
        this.developerName = developerName;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }


}


