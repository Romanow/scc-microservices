package takeItems

import org.springframework.cloud.contract.spec.Contract

final UUID orderUid = UUID.fromString("1a1f775c-4f31-4256-bec1-c3d4e9bf1b52")

Contract.make({
    description 'Take items (create OrderItem and decrement available items count)'
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
        status CREATED()
        body(
                orderUid: orderUid,
                state: 'CREATED',
                items: [
                        [
                                itemUid: $(fromRequest().body('$.itemsUid[0]')).serverValue,
                                name   : $(regex('\\S{10}'))
                        ],
                        [
                                itemUid: $(fromRequest().body('$.itemsUid[1]')).serverValue,
                                name   : $(regex('\\S{10}'))
                        ]
                ]
        )
        headers {
            contentType(applicationJsonUtf8())
        }
    }
})
