# API JAVA OOP | Sistem Pembayaran Subscriptions
Nama : Nyoman Tri Darma Wahyudi </br>
NIM  : 2305551052 </br>
Kelas: PBO E 

Nama : Putu Rifki Dirkayuda </br>
NIM  : 2305551068 </br>
Kelas: PBO E </br>

## Introducing
Tugas ini adalah backendAPI sederhana yang dibuat dengan bahasa JAVA dan maven, yang mana disusun untuk aplikasi Sistem Pembayaran Subscription. API digunakan untuk melakukan manipulasi data pada tiap entitas dari database dan dapat mengatur GET, POST, PUT, DELETE. Response yang diberikan oleh server API menggunakan format **JSON** dan data disimpan pada DATABASE **SQLite**. Pengujian aplikasi dilakukan pada aplikasi **Postman**.

## Struktur
Program ini memiliki 3 type kelas, yakni class untuk pertama  yakni class untuk masing-masing Entitas yang terletak pada folder **model**, class untuk keperluan API dan HTTP Server pada folder **httpserver** dan class untuk keperluan database pada folder **persistence**.

## Test in Post Man
Program pada dasarnya dapat digunakan dengan mengakses **localhost:9052** di web browser masing-masing. Namun, untuk mempermudah test, maka digunakan Postman.

### Get
Mendapatkan seluruh record item </br>
`http://localhost:9052/items`
![alt text](<img/Screenshot (249).png>)

Mendapatkan seluruh record user </br>
`http://localhost:9052/customers`
![alt text](<img/Screenshot (250).png>)

Mendapatkan record customer dengan ID customer adalah 5 </br>
`http://localhost:9052/customers/5`
![alt text](<img/Screenshot (251).png>)

Mendapatkan record item dengan ID item adalah 6 </br>
`http://localhost:9052/items/6`
![alt text](<img/Screenshot (252).png>)
 
Mendapatkan record cards dengan ID Customer adalah  </br>
`http://localhost:9052/customers/1/cards`
![alt text](<img/Screenshot (253).png>)

Mendapatkan record subscriptions dengan ID customer adalah 1 </br>
`http://localhost:9052/customers/1/subscriptions`
![alt text](<img/Screenshot (254).png>)

Mendapatkan record customer's subscription record berdasarkan ID customer adalah 1 </br>
`http://localhost:9052/customers/1/subscriptions?subscriptions_status=active`
![alt text](<img/Screenshot (255).png>)

Mendapatkan record shiping address berdasarkan ID customer adalah 1 </br>
`http://localhost:9052/customers/1/shipping_address`
![alt text](<img/Screenshot (256).png>)

Mendapatkan record semua subscription </br>
`http://localhost:9052/subscriptions`
![alt text](<img/Screenshot (257).png>)

Mendapatkan record subscription dengan current term end descending </br>
`http://localhost:9052/subscriptions?sort_by=current_term_end&sort_type=desc`
![alt text](<img/Screenshot (259).png>)

Mendapatkan record item dengan status active adalah true </br>
`http://localhost:9052/items?is_active=true`
![alt text](<img/Screenshot (261).png>)`

### Post
Menambahkan data baru pada Customers </br>
`http://localhost:9052/customers`
![alt text](<img/Screenshot (262).png>)`

Data berhasil di tambahkan, berikut merupakan data dari tabel customers 
![alt text](<img/Screenshot (263).png>)`

Menambahkan data baru pada Items </br>
`http://localhost:9052/items`
![alt text](<img/Screenshot (264).png>)`

Data berhasil di tambahkan, berikut merupakan data dari tabel items 
![alt text](<img/Screenshot (265).png>)`

Menambahkan data baru pada Subscriptions </br>
`http://localhost:9052/subscriptions`
![alt text](<img/Screenshot (267).png>)`

Data berhasil di tambahkan, berikut merupakan data dari tabel subscriptions 
![alt text](<img/Screenshot (268).png>)`

### Delete
Menghapus status is_active menjadi false pada tabel items berdasarkan ID  </br>
`http://localhost:9052/items/5`
![alt text](<img/Screenshot (274).png>)`

Data dengan status is_active berhasil di ubah menjadi false 
![alt text](<img/Screenshot (270).png>)`

Menghapus informasi kartu kredit pelanggan jika is_primary bernilai false </br>
`http://localhost:9052/customer/1/cards/6`
![alt text](<img/Screenshot (272).png>)`

Data dengan is_primary bernilai  false berhasil di hapus 
![alt text](<img/Screenshot (275).png>)`














