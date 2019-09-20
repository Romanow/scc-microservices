package state

import org.springframework.cloud.contract.spec.Contract

Contract.make({
    description 'OrderItem not found'
    request {
        method GET()
        url "/api/v1/items/${anyUuid()}/state"
    }
    response {
        status NOT_FOUND()
        body(
                message: "OrderItem '" + $(fromRequest().path(3)).clientValue + "' not found"
        )
        headers {
            contentType(applicationJsonUtf8())
        }
    }
})