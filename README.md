##Eshop
Semester project BI-TJV

Author: Anton Korolov

####Database
Server uses MySQL database, named "tjvdb".
username = root
password = root

####Server
Server runs at http://localhost:8080/

####Description
Application tries to work as an e-shop. It has 4 entities: Client, Order, Product, Shipment.
Each client has unique e-mail. Each order, product and shipment have unique ids.
Client wants to put product to order and then will be created(by administrator or anyone else) shipment to the client address.
1) We cannot delete order, if exists shipment created for this order.
2) We cannot delete product, if any order has it.
There are some business restrictions, all of them are in services.


There are some http examples in folder "examples"(! There are not all possible http request. Application has much more possible http requests.)

Junit tests cover controllers, services and dao(wants to add tests covers exceptions and some other business methods).