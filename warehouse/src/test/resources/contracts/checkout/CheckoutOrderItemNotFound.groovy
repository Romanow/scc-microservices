package checkout

import org.springframework.cloud.contract.spec.Contract
import static java.util.UUID.randomUUID

final UUID uid = randomUUID()

Contract.make({
    description 'OrderItem not found'
    request {
        method POST()
        url "/api/v1/items/${uid}/checkout"
        headers {
            contentType(applicationJsonUtf8())
        }
    }
    response {
        status NOT_FOUND()
        body(
                message: "OrderItem '${uid}' not found"
        )
        headers {
            contentType(applicationJsonUtf8())
        }
    }
})
