package takeItems

import org.springframework.cloud.contract.spec.Contract

Contract.make({
    description 'Order already exists'
    request {
        method POST()
        url "/api/v1/items/${anyUuid()}/take"
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
                message: "OrderItem '" + $(fromRequest().path(3)).clientValue + "' already exists"
        )
        headers {
            contentType(applicationJsonUtf8())
        }
    }
})
