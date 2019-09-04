package takeItems

import org.springframework.cloud.contract.spec.Contract

final UUID orderUid = UUID.fromString("37bb4049-1d1e-449f-8ada-5422f8886231")
final UUID itemUid1 = UUID.randomUUID()
final UUID itemUid2 = UUID.randomUUID()

Contract.make({
    description 'Items is empty (count = 0)'
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
        status NOT_FOUND()
        body(
                message: 'Items [' + itemUid1 + ',' + itemUid2 + '] is empty (available count = 0)'
        )
        headers {
            contentType(applicationJsonUtf8())
        }
    }
})
