package takeItems

import org.springframework.cloud.contract.spec.Contract

final UUID orderUid = UUID.fromString("36856fc6-d6ec-47cb-bbee-d20e78299eb9")
final UUID itemUid1 = UUID.randomUUID()
final UUID itemUid2 = UUID.randomUUID()

Contract.make({
    description 'Requested items not found'
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
                message: 'Not all items [' + itemUid1 + ',' + itemUid2 + '] found'
        )
        headers {
            contentType(applicationJsonUtf8())
        }
    }
})
