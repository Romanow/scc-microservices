package checkout

import org.springframework.cloud.contract.spec.Contract

final UUID orderUid = UUID.fromString("1a1f775c-4f31-4256-bec1-c3d4e9bf1b52")

Contract.make({
    description 'Checkout OrderItem (change state to READY_FOR_DELIVERY)'
    request {
        method POST()
        url "/api/v1/items/${orderUid}/checkout"
    }
    response {
        status OK()
        body(
                orderUid: $(orderUid),
                state: 'READY_FOR_DELIVERY',
                items: [
                        [
                                itemUid: $(anyUuid()),
                                name   : $(regex('\\S{10}'))
                        ],
                        [
                                itemUid: $(anyUuid()),
                                name   : $(regex('\\S{10}'))
                        ]
                ]
        )
        headers {
            contentType(applicationJsonUtf8())
        }
    }
})
