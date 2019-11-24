package takeItems

import org.springframework.cloud.contract.spec.Contract

final UUID orderUid = UUID.fromString("37bb4049-1d1e-449f-8ada-5422f8886231")

Contract.make({
    description 'Items is empty (count = 0)'
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
                message: 'Items [' +
                        $(fromRequest().body('$.itemsUid[0]')).serverValue + ',' +
                        $(fromRequest().body('$.itemsUid[1]')).serverValue +
                        '] is empty (available count = 0)'
        )
        headers {
            contentType(applicationJsonUtf8())
        }
    }
})
