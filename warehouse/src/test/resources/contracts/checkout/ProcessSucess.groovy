package checkout

import org.springframework.cloud.contract.spec.Contract
import static java.util.UUID.randomUUID

final UUID uid = randomUUID()

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
        method GET()
        urlPath("/api/v1/items/${uid}/checkout")
        headers {
            contentType(applicationJsonUtf8())
        }
    }
    response {
        status OK()
        body(
                orderUid: $(uid),
                state: 'READY_FOR_DELIVERY',
                items: [
                        [
                                uid: $(anyUuid()),
                                name: $(regex('\\S{10}'))
                        ],
                        [
                                uid: $(anyUuid()),
                                name: $(regex('\\S{10}'))
                        ]
                ]
        )
        headers {
            contentType(applicationJsonUtf8())
        }
    }
})
