# Phone Numbers

Backend REST service that autocompletes the phone numbers. Implemnted REST endpoint (request and response specified bellow) that accepts `query` query parameter that contains whole or partial phone number (partial in a sense that it starts from beginning of phone number). The query parameter can specify phone number in any format ie all numbers, numbers separated by `-` or by space. The response contains up to 10 phone numbers (including persons name) that matches whole or partially with respect to given query. The phone numbers returned are as in dataset. The phone numbers are returned in order of _importance_, ie the most specific match would end up first. The endpoint is performant enough that supports hundreds of users calling API simultaneously.

The following is a specification of REST endpoint:

Request:

GET /api/v1/phone-numbers/autocomplete?query=00392750

Response:

200 OK

{

"content": [{

"name": "John Doe",

"phoneNumber": "00392-750"

}, {

"name": "John Wayne",

"phoneNumber": "00392-7501"

}, {

"name": "Bruce Wayne",

"phoneNumber": "00392-750 14"

}, {

"name": "Lil' John",

"phoneNumber": "00392-750 14 19"

}, {

"name": "John Little",

"phoneNumber": "00392-750 14 41"

}, {

"name": "Jane Doe",

"phoneNumber": "00392-750 14 69 4"

}

]

}

How to start:

1. Clone repository
2. Run Project

It will show message in console that server is running on port 8000.
