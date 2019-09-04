package takeItems

import org.springframework.cloud.contract.spec.Contract

final UUID orderUid = UUID.randomUUID()
final UUID itemUid1 = UUID.randomUUID()
final UUID itemUid2 = UUID.randomUUID()

Contract.make({
    description 'Take items (create OrderItem and decrement available items count)'
    request {
        method POST()
        url "/api/v1/items/${orderUid}/take"
        body(
            itemsUid: [itemUid1, itemUid2]
        )
        headers {
            contentType(applicationJsonUtf8())
        }
    }
    response {
        status CREATED()
        body(
                orderUid: $(orderUid),
                state: 'CREATED',
                items: [
                        [
                                itemUid: $(itemUid1),
                                name   : $(regex('\\S{10}'))
                        ],
                        [
                                itemUid: $(itemUid2),
                                name   : $(regex('\\S{10}'))
                        ]
                ]
        )
        headers {
            contentType(applicationJsonUtf8())
        }
    }
})
