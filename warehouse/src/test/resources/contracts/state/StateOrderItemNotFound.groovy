package state

import org.springframework.cloud.contract.spec.Contract
import static java.util.UUID.randomUUID

final UUID uid = randomUUID()

Contract.make({
    description 'Return '
    request {
        method GET()
        urlPath("/api/v1/items/${uid}/state")
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