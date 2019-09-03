package checkout

import org.springframework.cloud.contract.spec.Contract

final UUID orderUid = UUID.fromString("1a1f775c-4f31-4256-bec1-c3d4e9bf1b52")

Contract.make({
    description('''
given:
    created OrderItems
when:
    request checkout
then:
    successful checkout
    ''')
    request {
        method POST()
        urlPath("/api/v1/items/${orderUid}/checkout")
        headers {
            contentType(applicationJsonUtf8())
        }
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
