package checkout

import org.springframework.cloud.contract.spec.Contract

final UUID orderUid = UUID.fromString("36856fc6-d6ec-47cb-bbee-d20e78299eb9")

Contract.make({
    description 'OrderItem not found'
    request {
        method POST()
        url "/api/v1/items/${orderUid}/checkout"
    }
    response {
        status NOT_FOUND()
        body(
                message: "OrderItem '" + $(fromRequest().path(3)).serverValue + "' not found"
        )
        headers {
            contentType(applicationJsonUtf8())
        }
    }
})
