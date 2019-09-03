package takeItem

import org.springframework.cloud.contract.spec.Contract

final UUID orderUid = UUID.fromString("1a1f775c-4f31-4256-bec1-c3d4e9bf1b52")
final UUID itemUid1 = UUID.randomUUID();
final UUID itemUid2 = UUID.randomUUID();

Contract.make({
    description 'Take items (create OrderItem and decrement available items count)'
    request {
        method POST()
        url "/api/v1/items/take"
        body {
            itemsUid: [itemUid1, itemUid2]
        }
        headers {
            contentType(applicationJsonUtf8())
        }
    }
    response {
        status OK()
        body(
                orderUid: $(orderUid),
                state: 'READY_FOR_DELIVERY',
                items: [
                        [
                                itemUid: $(anyUuid()),
                                name   : $(regex('\\S{10}'))
                        ],
                        [
                                itemUid: $(anyUuid()),
                                name   : $(regex('\\S{10}'))
                        ]
                ]
        )
        headers {
            contentType(applicationJsonUtf8())
        }
    }
})
