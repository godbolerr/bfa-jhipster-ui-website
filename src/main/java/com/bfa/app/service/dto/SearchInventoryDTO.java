package com.bfa.app.service.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the SearchInventory entity.
 */
public class SearchInventoryDTO implements Serializable {

    private Long id;

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

        SearchInventoryDTO searchInventoryDTO = (SearchInventoryDTO) o;

        if ( ! Objects.equals(id, searchInventoryDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SearchInventoryDTO{" +
            "id=" + id +
            ", count='" + count + "'" +
            '}';
    }
}
