package takeItems

import org.springframework.cloud.contract.spec.Contract

final UUID orderUid = UUID.fromString("36856fc6-d6ec-47cb-bbee-d20e78299eb9")

Contract.make({
    description 'Requested items not found'
    request {
        method POST()
        url "/api/v1/items/${orderUid}/take"
        body(
                itemsUid: [$(anyUuid()), $(anyUuid())]
        )
        headers {
            contentType(applicationJsonUtf8())
        }
    }
    response {
        status NOT_FOUND()
        body(
                message: 'Not all items [' +
                        $(fromRequest().body('$.itemsUid[0]')).serverValue + ',' +
                        $(fromRequest().body('$.itemsUid[1]')).serverValue + '] found'
        )
        headers {
            contentType(applicationJsonUtf8())
        }
    }
})
