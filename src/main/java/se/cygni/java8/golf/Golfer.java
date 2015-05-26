package se.cygni.java8.golf;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Golfer {
    private String name;
    private int hcp;
    private Gender gender;
    private Country country;
    private int money;

    public Golfer(String name, int hcp, Gender gender, Country country, int money) {
        this.name = name;
        this.hcp = hcp;
        this.gender = gender;
        this.country = country;
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHcp() {
        return hcp;
    }

    public void setHcp(int hcp) {
        this.hcp = hcp;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }


    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    @JsonIgnore
    public boolean isFemale() {
        return gender == Gender.FEMALE;
    }
    @JsonIgnore
    public boolean isSameGender(Golfer other) {
        return other.gender == this.gender;
    }
    @Override
    public String toString() {
        return name + "(" + hcp + ", " + country + ",  $" + money + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Golfer golfer = (Golfer) o;

        if (hcp != golfer.hcp) return false;
        if (money != golfer.money) return false;
        if (name != null ? !name.equals(golfer.name) : golfer.name != null) return false;
        if (gender != golfer.gender) return false;
        return country == golfer.country;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + hcp;
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + money;
        return result;
    }
}
