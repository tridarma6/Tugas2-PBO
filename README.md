# API JAVA OOP | Sistem Pembayaran Subscriptions
Nama : Nyoman Tri Darma Wahyudi
NIM  : 2305551052
Kelas: PBO E

Nama : Putu Rifki Dirkayuda
NIM  : 2305551068
Kelas: PBO E

## Introducing
Tugas ini adalah backendAPI sederhana yang dibuat dengan bahasa JAVA dan maven, yang mana disusun untuk aplikasi Sistem Pembayaran Subscription. API digunakan untuk melakukan manipulasi data pada tiap entitas dari database dan dapat mengatur GET, POST, PUT, DELETE. Response yang diberikan oleh server API menggunakan format **JSON** dan data disimpan pada DATABASE **SQLite**. Pengujian aplikasi dilakukan pada aplikasi **Postman**.

## Struktur
Program ini memiliki 3 type kelas, yakni class untuk pertama  yakni class untuk masing-masing Entitas yang terletak pada folder **model**, class untuk keperluan API dan HTTP Server pada folder **httpserver** dan class untuk keperluan database pada folder **persistence**.

## Test in Post Man
Program pada dasarnya dapat digunakan dengan mengakses **localhost:9052** di web browser masing-masing. Namun, untuk mempermudah test, maka digunakan Postman.

### Get
Mendapatkan seluruh record item </br>
`http://localhost:9052/items`
![alt text](<Screenshot (249).png>)

Mendapatkan seluruh record user </br>
`http://localhost:9052/customers`
![alt text](<Screenshot (250).png>)

Mendapatkan record customer dengan ID customer adalah 5
`http://localhost:9052/customers/5`
![alt text](<Screenshot (251).png>)

Mendapatkan record item dengan ID item adalah 6
`http://localhost:9052/items/6`
![alt text](<Screenshot (252).png>)

Mendapatkan record cards dengan ID Customer adalah 
`http://localhost:9052/customers/1/cards`
![alt text](<Screenshot (253).png>)

Mendapatkan record subscriptions dengan ID customer adalah 1
`http://localhost:9052/customers/1/subscriptions`
![alt text](<Screenshot (254).png>)

Mendapatkan record customer's subscription record berdasarkan ID customer adalah 1
`http://localhost:9052/customers/1/subscriptions?subscriptions_status=active`
![alt text](<Screenshot (255).png>)

Mendapatkan record shiping address berdasarkan ID customer adalah 1
`http://localhost:9052/customers/1/shipping_address`
![alt text](<Screenshot (256).png>)

Mendapatkan record semua subscription
`http://localhost:9052/subscriptions`
![alt text](<Screenshot (257).png>)

Mendapatkan record subscription dengan current term end descending
`http://localhost:9052/subscriptions?sort_by=current_term_end&sort_type=desc`
![alt text](<Screenshot (259).png>)

Mendapatkan record item dengan status active adalah true
`http://localhost:9052/items?is_active=true`
![alt text](<Screenshot (261).png>)

