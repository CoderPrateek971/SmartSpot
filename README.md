# 🚗 SmartSpot – Smart Parking Management App

SmartSpot is an Android-based mobile application designed to solve urban parking problems by providing real-time parking availability, seamless booking, automated billing, and admin-based management.

---

## 📌 Table of Contents

* Introduction
* Features
* Tech Stack
* Architecture
* App Workflow
* Project Structure
* Libraries Used
* Installation
* Future Scope
* Contributors
* Conclusion
* License

---

## 📖 Introduction

SmartSpot is a smart parking solution that connects vehicle owners with available parking slots in real-time. It eliminates manual ticketing and reduces congestion by enabling users to search, book, and pay digitally.

The system supports two types of users:

* **User (Commuter)**
* **Admin (Parking Manager)**

---

## ✨ Features

### 👤 User Features

* User Registration & Login
* Search parking slots using map interface
* Select vehicle type (Car / Two-wheeler)
* Real-time booking system
* QR Code-based entry system
* Live parking timer
* Automatic billing generation
* Multiple payment options (UPI, Card, Wallet)
* View past bookings
* Customer support system

### 🛠️ Admin Features

* Admin Dashboard (Revenue, Occupancy analytics)
* Manage parking slots (Enable/Disable availability)
* Dynamic pricing management
* Peak hour surcharge toggle
* Handle user support tickets

---

## 🧰 Tech Stack

### 📱 Frontend

* Java
* XML (UI Design)
* Android Studio

### 🌐 Backend

* Node.js (REST API Server)

### 🗄️ Database

* SQL (Relational Database)

---

## 🏗️ Architecture

```
Android App (Frontend)
        ↓
   REST APIs (Node.js Backend)
        ↓
     SQL Database
```

### Key Concepts

* Separation of Concerns
* API-based Communication
* Real-time Data Synchronization
* Data Integrity via SQL

---

## 🔄 App Workflow

1. User logs in / signs up
2. Searches for parking location
3. Selects slot & vehicle type
4. Confirms booking
5. Receives QR Code
6. Starts parking (live timer)
7. Ends booking
8. Billing generated automatically
9. Payment completed
10. Booking stored in history

---

## 📁 Project Structure

```
├── api/
│   ├── ApiClient.java
│   └── ApiService.java
│
├── models/
│   ├── User.java
│   └── Booking.java
│
├── activities/
│   ├── MainActivity.java
│   ├── LoginActivity.java
│   ├── SignupActivity.java
│   ├── HomeActivity.java
│   ├── BookingActivity.java
│   ├── ConfirmationActivity.java
│   ├── BookingSuccessActivity.java
│   ├── ActiveBookingActivity.java
│   ├── PastBookingsActivity.java
│   ├── BillingActivity.java
│   ├── PaymentActivity.java
│   ├── PaymentSuccessActivity.java
│   ├── ProfileActivity.java
│   ├── EditProfileActivity.java
│   ├── AdminDashboardActivity.java
│   ├── ManageSlotsActivity.java
│   ├── PricingActivity.java
│   ├── SupportActivity.java
│   └── NavbarHelper.java
│
├── adapters/
│   ├── SlotAdapter.java
│   ├── PastBookingAdapter.java
│   └── TicketAdapter.java
│
├── res/
│   ├── layout/
│   │   ├── activity_main.xml
│   │   ├── activity_login.xml
│   │   ├── activity_signup.xml
│   │   ├── activity_home.xml
│   │   ├── bottom_navbar.xml
│   │   ├── activity_booking.xml
│   │   ├── activity_confirmation.xml
│   │   ├── activity_booking_success.xml
│   │   ├── activity_active_booking.xml
│   │   ├── activity_billing.xml
│   │   ├── activity_payment.xml
│   │   ├── activity_payment_success.xml
│   │   ├── activity_profile.xml
│   │   ├── activity_edit_profile.xml
│   │   ├── activity_past_bookings.xml
│   │   ├── activity_support.xml
│   │   ├── activity_admin_dashboard.xml
│   │   ├── activity_manage_slots.xml
│   │   ├── activity_pricing.xml
│   │   ├── item_past_booking.xml
│   │   ├── item_ticket.xml
│   │   └── slot_item.xml
│   │
│   └── values/
│       └── themes.xml
│
└── AndroidManifest.xml
```

---

## 📚 Libraries Used

### 🔹 Retrofit

* Type-safe HTTP client for Android
* Handles API requests and responses

### 🔹 Gson

* Converts JSON ↔ Java objects

### 🔹 Volley

* Used for lightweight requests (Login, Signup)

### 🔹 HttpURLConnection

* Used for manual API handling

### 🔹 RecyclerView

* Efficient list rendering (slots, bookings, tickets)

### 🔹 Material Design Components

* Modern UI components

---

## ⚙️ Installation

### Prerequisites

* Android Studio
* Java SDK
* Node.js
* SQL Database

### Steps

1. Clone the repository:

```
git clone https://github.com/your-username/smartspot.git
```

2. Open in Android Studio

3. Configure API base URL in:

```
ApiClient.java
```

4. Run on emulator or physical device

---

## 🚀 Future Scope

* Real-time GPS tracking
* AI-based parking prediction
* Payment gateway integration (Razorpay/Stripe)
* Push notifications
* Multi-city support
* Cloud deployment

---

## 👥 Contributors

* Riwan Bhati – Data History & Pricing Management
* Nischay Mehta – Booking Engine & Slot Management
* Prateek Garg – Navigation & Map Integration
* Nishtha Gupta – Billing & Support System
* Kanishk Chahar – Authentication & Profile Management

---

## 📌 Conclusion

SmartSpot provides a scalable and efficient parking management system by combining real-time data, automation, and a user-friendly interface. It reduces parking search time and improves operational efficiency.

---

## 📜 License

This project is developed for academic purposes only.
