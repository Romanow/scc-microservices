package items

import org.springframework.cloud.contract.spec.Contract

Contract.make({
    description('''
given:
    items in database
when:
    request items
then:
    return available items (count > 0)
    ''')
    request {
        method GET()
        urlPath('/api/v1/items') {
            queryParameters {
                parameter 'page': 2
                parameter 'size': 2
            }
        }
        headers {
            contentType(applicationJsonUtf8())
        }
    }
    response {
        status OK()
        body(
                page: 2,
                totalSize: 10,
                pageSize: 2,
                items: [
                        [
                                count: 3,
                                name: 'Lego Technic 42092',
                                itemUid: anyUuid()
                        ],
                        [
                                count: 4,
                                name: 'Lego Technic 42093',
                                itemUid: anyUuid()
                        ]
                ]
        )
        headers {
            contentType(applicationJsonUtf8())
        }
    }
})
