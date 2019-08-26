# Название: Consumer Driven Development в мире Java

1. Проблемы интеграционного тестирования. (время ~10 минут).

У нас есть много микросервисов и при изменении поведения в одном мы хотим гарантировать, что не нарушается функциональность других. Два подхода:
    1. Развертывание микросервисов:
        1. требуется много ресурсов для прогона и в один момент времени возможен только один сценарий;
        2. на поднятие всех сервисов требуется много времени;
        3. очень сложно разбираться в ошибках.
    2. Мокировать внешние сервисы:
        1. при изменении протокола или поведения мы не можем быть уверены, что никто не отвалится.


2. Contract driven development (CDD) (время ~10 минут +/- 5  минут, возможно здесь придется остановиться подробнее).
    1. Мы создаем описание нашего контракта на Producer, по этому контракту генерируются unit-тесты на контроллеры. Тем самым мы гарантируем, что на Producer контроллеры обрабатывают именно такое API.
    2. После этого описание заливается в nexus (или другое хранилище артефактов, возможно локальное) или в git.
    3. На стороне Consumer поднимается легковесный сервер wiremock, для него указывается файл json, предоставленный Producer, в котором описаны запросы и ответы.


3. Посмотрим, как это выглядит в действии: (20 минут). Пример.

Три сервиса: склад (warehouse), заказы (orders), доставка (delivery).
    1. Warehouse публикует контракт:
        - Получить информацию о всех вещах на складе
          GET /items?page=<page>&size=<size>
        - Зарезервировать заказ для пользователя
          POST /items/take (body: { itemUids: [] })
        - Получить информацию о вещи
          GET /items/{itemUid}/state
        - Выписать вещь со склада для доставки
          POST /items/{itemUid}/checkout
    2. Остальные сервисы на своей стороне реализуют этот контракт.
    3. На сервисах Orders и Delivery реализуем контракт и пишем тесты.
    4. Это все публикуем в gitlab и настраиваем зависимые сборки. Чтобы при изменении  в сервисе Warehouse контракт публиковался и вызывалась переборка и прогон тестов на других сервисах
    5. Изменяем название полей, поведение методов, добавляем или удаляем поля в запрос и ответ. Любуемся что тесты на других сервисах упали.


4. Есть еще один очень насущный вопрос в мире микросервисов – как бы отдать пользователю актуальную удобную документацию. Все, конечно, знают про OpenAPI, но  часто требуют наличие какой-то бумажной или офлайн-документации. Для этого существует rest-docs. Описание API генерируется по тестам на API, более того, во время работы проверяется, что все поля в запросе и ответе описаны в документированном описании.
Получается, что при описании в restdocs мы имеем практически описание контракта. (время 5 минут)

Пример модуля, где фигурирует описание restdocs, показать как по нему сгенерировать groovy dsl контракт. (время 10 минут)

Общее время 55 минут.