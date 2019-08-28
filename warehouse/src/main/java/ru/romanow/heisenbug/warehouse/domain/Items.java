package ru.romanow.heisenbug.warehouse.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "items",
        schema = "warehouse",
        indexes = {
                @Index(name = "idx_items_uid", columnList = "uid", unique = true),
                @Index(name = "idx_items_name", columnList = "name", unique = true)
        })
public class Items {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uid", nullable = false, unique = true, updatable = false)
    private UUID uid;

    @Column(name = "name", length = 1024, nullable = false, unique = true)
    private String name;

    @Column(name = "count")
    private Integer count;

    @ManyToMany(mappedBy = "item")
    private List<OrderItems> orderItems;

    public Integer decrementCount() {
        return --count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Items items = (Items) o;
        return Objects.equal(name, items.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("uid", uid)
                .add("name", name)
                .add("count", count)
                .toString();
    }
}
