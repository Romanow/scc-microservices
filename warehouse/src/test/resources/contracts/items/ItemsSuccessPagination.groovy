package items

import org.springframework.cloud.contract.spec.Contract

Contract.make({
    description 'Return available items with pagination'
    request {
        method GET()
        urlPath('/api/v1/items') {
            queryParameters {
                parameter 'page': 1
                parameter 'size': 2
            }
        }
    }
    response {
        status OK()
        body(
                page: 1,
                totalSize: 6,
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
