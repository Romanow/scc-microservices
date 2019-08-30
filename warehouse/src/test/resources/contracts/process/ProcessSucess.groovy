package process

import org.springframework.cloud.contract.spec.Contract

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
        urlPath('/api/v1/items/checkout')
        headers {
            contentType(applicationJsonUtf8())
        }
    }
    response {
        status OK()
        headers {
            contentType(applicationJsonUtf8())
        }
    }
})
