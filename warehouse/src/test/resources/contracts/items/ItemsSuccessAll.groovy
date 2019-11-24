package items

import org.springframework.cloud.contract.spec.Contract

Contract.make({
    description 'Return all available items'
    request {
        method GET()
        url '/api/v1/items'
    }
    response {
        status OK()
        body(
                page: 0,
                totalSize: 6,
                pageSize: 6,
                items: [
                        [
                                count: 1,
                                name: 'Lego Technic 42090',
                                itemUid: anyUuid()
                        ],
                        [
                                count: 2,
                                name: 'Lego Technic 42091',
                                itemUid: anyUuid()
                        ],
                        [
                                count: 3,
                                name: 'Lego Technic 42092',
                                itemUid: anyUuid()
                        ],
                        [
                                count: 4,
                                name: 'Lego Technic 42093',
                                itemUid: anyUuid()
                        ],
                        [
                                count: 5,
                                name: 'Lego Technic 42094',
                                itemUid: anyUuid()
                        ],
                        [
                                count: 6,
                                name: 'Lego Technic 42095',
                                itemUid: anyUuid()
                        ]

                ]
        )
        headers {
            contentType(applicationJsonUtf8())
        }
    }
})
