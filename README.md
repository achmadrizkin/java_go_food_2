# Java Go-Mbe
This app is Go-Food Clone App, i use java and php for backend

## Features
- Admin
  - Make Coupon Code
  - Create/Update/Delete food
  - Same as user
  
- User
  - Order Food
  - Login / SignUp
  - Edit Profile
  - See All Discount Coupon
  - Use Discount Coupon
  - Share Food to another apps, etc
  - Make, and scan qr code
  
## Techstack
MVVM, CRUD API, RajaOngkir API, Live Data, Retrofit2, Google Maps, Deep Link, Glide, Shared Preference, RxJava, Scan Barcode, and make barcode, etc...

## Preview App
![flying-two-side-iphone-x-mockup-template@2x](https://user-images.githubusercontent.com/75843138/179334951-d491f69b-47fa-4f7a-be29-0e3a47cbcddd.png)

## Raja Ongkir API
https://rajaongkir.com/dokumentasi


## API Specs (Sample)
https://achmadrizkin.my.id/java_go_mbe/select_all_food.php

- Request
Header: Key = ff5867dce8cfc4c5cb441179ae07b7cb
![image](https://user-images.githubusercontent.com/75843138/179335644-3eb5b3c0-4dd0-45c0-b6c1-4b5965664add.png)


- Response



        {
            "success": 1,
            "message": "Login Berhasil",
            "user": [
                {
                    "food_name": "ashiap123",
                    "description": "ASHIAPSANTUY",
                    "image_url": "https://achmadrizkin.my.id/java_go_mbe/image/62d01b4b978fd.jpeg",
                    "harga": "15000",
                    "rating": "3.5",
                    "kd_food": "73f7741c-fe32-4aef-ba67-f837a16caf03",
                    "count_rating": "10",
                },
                {
                    "food_name": "Delicious Meal",
                    "description": "ashiap santuy marintuy",
                    "image_url": "https://achmadrizkin.my.id/java_go_mbe/image/62c91b80bfa49.jpeg",
                    "harga": "12000",
                    "rating": "3.5",
                    "kd_food": "cd0d66f9-250e-4491-8ba1-1222e41743bd",
                    "count_rating": "10",
                },
                {
                    "food_name": "Mie Kuah",
                    "description": "Mie kuah dengan campuran bumbu rahasia",
                    "image_url": "https://achmadrizkin.my.id/java_go_mbe/image/62c91be80ac6a.jpeg",
                    "harga": "30000",
                    "rating": "3.5",
                    "kd_food": "30f6f7b8-70e5-4eae-9578-b7989ed98704",
                    "count_rating": "10",
                },
                {
                    "food_name": "Mie rasa anime",
                    "description": "wibu",
                    "image_url": "https://achmadrizkin.my.id/java_go_mbe/image/62c91bb25488c.jpeg",
                    "harga": "13700",
                    "rating": "3.5",
                    "kd_food": "1f243965-8c36-478e-b500-8beb36361cec",
                    "count_rating": "10",
                }
            ]
        }
        
        
        
## Database 
- Food

![image](https://user-images.githubusercontent.com/75843138/179335798-c9ba0bd3-f0c6-4fed-bd58-802097648b9b.png)

- Promo

![image](https://user-images.githubusercontent.com/75843138/179335835-e12e7f45-4cef-4ee7-9c25-72f8a4a4e1bc.png)

- Transaksi

![image](https://user-images.githubusercontent.com/75843138/179335816-3cf1c520-0ac8-4604-8144-70df32701a27.png)

- Transaksi Dropshipper

![image](https://user-images.githubusercontent.com/75843138/179335854-9c24037d-9b6c-4dca-8458-9b86dd4ded48.png)

- User

![image](https://user-images.githubusercontent.com/75843138/179335859-dacdb09a-5d76-4b5e-8dd2-23815c726b44.png)
