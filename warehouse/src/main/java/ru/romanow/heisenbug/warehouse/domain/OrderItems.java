package ru.romanow.heisenbug.warehouse.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.romanow.heisenbug.warehouse.domain.enums.OrderState;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "order_items",
        schema = "warehouse",
        indexes = @Index(name = "idx_order_items_order_uid", columnList = "order_uid", unique = true))
public class OrderItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_uid", nullable = false, unique = true)
    private UUID orderUid;

    @ManyToMany
    @JoinTable(name = "card_items",
            joinColumns = @JoinColumn(name = "order_item_id", foreignKey = @ForeignKey(name = "fk_card_items_order_item_id")),
            inverseJoinColumns = @JoinColumn(name = "items_id", foreignKey = @ForeignKey(name = "fk_card_items_items_id")),
            indexes = {
                    @Index(name = "idx_card_items_order_item_id", columnList = "order_item_id"),
                    @Index(name = "idx_card_items_items_id", columnList = "items_id")
            })
    private List<Items> items;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private OrderState state;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItems that = (OrderItems) o;
        return Objects.equal(orderUid, that.orderUid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(orderUid);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("state", state)
                .add("orderUid", orderUid)
                .toString();
    }
}
