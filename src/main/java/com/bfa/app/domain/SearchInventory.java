package com.bfa.app.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A SearchInventory.
 */
@Entity
@Table(name = "search_inventory")
public class SearchInventory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "count")
    private Integer count;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public SearchInventory count(Integer count) {
        this.count = count;
        return this;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SearchInventory searchInventory = (SearchInventory) o;
        if (searchInventory.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, searchInventory.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SearchInventory{" +
            "id=" + id +
            ", count='" + count + "'" +
            '}';
    }
}
