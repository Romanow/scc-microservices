package takeItems

import org.springframework.cloud.contract.spec.Contract

final UUID orderUid = UUID.fromString("45142058-60e6-4cde-ad13-b968180f0367");

Contract.make({
    description 'Order already exists'
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
        status CONFLICT()
        body(
                message: "OrderItem '" + $(fromRequest().path(3)).serverValue + "' already exists"
        )
        headers {
            contentType(applicationJsonUtf8())
        }
    }
})
