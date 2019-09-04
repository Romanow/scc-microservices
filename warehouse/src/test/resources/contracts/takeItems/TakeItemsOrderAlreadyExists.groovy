package takeItems

import org.springframework.cloud.contract.spec.Contract

final UUID orderUid = UUID.fromString("1a1f775c-4f31-4256-bec1-c3d4e9bf1b52")
final UUID itemUid1 = UUID.randomUUID()
final UUID itemUid2 = UUID.randomUUID()

Contract.make({
    description 'Order already exists'
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
        status CONFLICT()
        body(
                message: "OrderItem '${orderUid}' already exists"
        )
        headers {
            contentType(applicationJsonUtf8())
        }
    }
})
