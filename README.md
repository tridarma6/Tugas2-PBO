# API JAVA OOP | Sistem Pembayaran Subscriptions

Nama: Nyoman Tri Darma Wahyudi </br>
NIM: 2305551052 </br>
Kelas: PBO E 

Nama: Putu Rifki Dirkayuda </br>
NIM: 2305551068 </br>
Kelas: PBO E </br>

## Introducing

Tugas ini adalah backend API sederhana yang dibuat dengan bahasa JAVA dan maven, yang mana disusun untuk aplikasi Sistem Pembayaran Subscription. API digunakan untuk melakukan manipulasi data pada tiap entitas dari database dan dapat mengatur GET, POST, PUT, DELETE. Response yang diberikan oleh server API menggunakan format **JSON** dan data disimpan pada DATABASE **SQLite**. Pengujian aplikasi dilakukan pada aplikasi **Postman**.

## Struktur

Program ini memiliki 3 type kelas, yakni class untuk pertama yakni class untuk masing-masing Entitas yang terletak pada folder **model**, class untuk keperluan API dan HTTP Server pada folder **httpserver** dan class untuk keperluan database pada folder **persistence**.

## Test in Postman

Program pada dasarnya dapat digunakan dengan mengakses **localhost:9052** di web browser masing-masing. Namun, untuk mempermudah test, maka digunakan Postman.

### GET

Mendapatkan seluruh record item </br>
`http://localhost:9052/items`
![Get Items](img/Screenshot%20(249).png)

Mendapatkan seluruh record user </br>
`http://localhost:9052/customers`
![Get Customers](img/Screenshot%20(250).png)

Mendapatkan record customer dengan ID customer adalah 5 </br>
`http://localhost:9052/customers/5`
![Get Customer by ID](img/Screenshot%20(251).png)

Mendapatkan record item dengan ID item adalah 6 </br>
`http://localhost:9052/items/6`
![Get Item by ID](img/Screenshot%20(252).png)

Mendapatkan record cards dengan ID Customer adalah </br>
`http://localhost:9052/customers/1/cards`
![Get Cards by Customer ID](img/Screenshot%20(253).png)

Mendapatkan record subscriptions dengan ID customer adalah 1 </br>
`http://localhost:9052/customers/1/subscriptions`
![Get Subscriptions by Customer ID](img/Screenshot%20(254).png)

Mendapatkan record customer's subscription record berdasarkan ID customer adalah 1 </br>
`http://localhost:9052/customers/1/subscriptions?subscriptions_status=active`
![Get Active Subscriptions by Customer ID](img/Screenshot%20(255).png)

Mendapatkan record shipping address berdasarkan ID customer adalah 1 </br>
`http://localhost:9052/customers/1/shipping_address`
![Get Shipping Address by Customer ID](img/Screenshot%20(256).png)

Mendapatkan record semua subscription </br>
`http://localhost:9052/subscriptions`
![Get All Subscriptions](img/Screenshot%20(257).png)

Mendapatkan record subscription dan informasi dari customer, subscription items berdasarkan subs ID adalah 2 </br>
`http://localhost:9052/subscriptions/2`
![Get All Subscriptions](img/Screenshot%20(285).png)

Mendapatkan record subscription dengan current term end descending </br>
`http://localhost:9052/subscriptions?sort_by=current_term_end&sort_type=desc`
![Get Subscriptions Sorted by Current Term End](img/Screenshot%20(259).png)

Mendapatkan record item dengan status active adalah true </br>
`http://localhost:9052/items?is_active=true`
![Get Active Items](img/Screenshot%20(261).png)

### POST

Menambahkan data baru pada Customers </br>
`http://localhost:9052/customers`
![Post Customer](img/Screenshot%20(262).png)

Data berhasil di tambahkan, berikut merupakan data dari tabel customers 
![Customer Data](img/Screenshot%20(263).png)

Menambahkan data baru pada Items </br>
`http://localhost:9052/items`
![Post Item](img/Screenshot%20(264).png)

Data berhasil di tambahkan, berikut merupakan data dari tabel items 
![Item Data](img/Screenshot%20(265).png)

Menambahkan data baru pada Subscriptions </br>
`http://localhost:9052/subscriptions`
![Post Subscription](img/Screenshot%20(267).png)

Data berhasil di tambahkan, berikut merupakan data dari tabel subscriptions 
![Subscription Data](img/Screenshot%20(268).png)

### PUT

Mengupdate data yang sudah ada pada Customer dengan ID 5 </br>
`http://localhost:9052/customers/5`
![Customer Data](img/Screenshot%20(278).png)
Berikut merupakan hasil data yang sudah di update dengan customer ID 5 </br>
![Customer Data](img/Screenshot%20(279).png)

Mengupdate data yang sudah ada pada Items dengan ID 6 </br>
`http://localhost:9052/items/6`
![Items Data](img/Screenshot%20(283).png)
Berikut merupakan hasil data yang sudah di update dengan items ID 6 </br>
![Items Data](img/Screenshot%20(284).png)

Mengupdate data yang sudah di update dengan shiping address ID 1 dengan customer ID 1 </br>
`http://localhost:9052/customers/1/shipping_address/1`
![Subscriptions](img/Screenshot%20(281).png)

### DELETE

Menghapus status is_active menjadi false pada tabel items berdasarkan ID </br>
`http://localhost:9052/items/5`
![Delete Item](img/Screenshot%20(274).png)

Data dengan status is_active berhasil di ubah menjadi false 
![Item Data Updated](img/Screenshot%20(270).png)

Menghapus informasi kartu kredit pelanggan jika is_primary bernilai false </br>
`http://localhost:9052/customer/1/cards/6`
![Delete Card](img/Screenshot%20(272).png)

Data dengan is_primary bernilai false berhasil di hapus 
![Card Data Deleted](img/Screenshot%20(275).png)

### ERROR 405 (Selain Delete, Put, Post, Get)
PATCH `http://localhost:9052`
![alt text](<img/Screenshot 2024-06-21 090547.png>)

### ERROR 404
Tidak terdapat entitas field test </br>
`http://localhost:9052/test`
![alt text](<img/Screenshot 2024-06-21 090952.png>)
