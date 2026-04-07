Daily Nutrition Android Application (B4A)
แอปพลิเคชันบน Android สำหรับบันทึกและคำนวณสารอาหารรายวันตามมาตรฐานโภชนาการ (DRI) โดยวิเคราะห์จากข้อมูลส่วนบุคคลของผู้ใช้งาน
🚀 Key Features (ฟีเจอร์หลัก)
Personalized Calculation: คำนวณสารอาหารที่ต้องการต่อวันอัตโนมัติ โดยอ้างอิงจาก เพศ และ ช่วงอายุ
Dynamic SQL Query: ใช้ Logic การดึงข้อมูลแบบเงื่อนไข (Relational Database) เพื่อหาค่า DRI ที่ถูกต้องจาก Table ใน SQLite
Meal Management: ระบบจัดการเมนูอาหาร (Manage Menu) และวัตถุดิบ (Material) พร้อมระบบบันทึกมื้ออาหารในแต่ละวัน
Data Persistence: จัดเก็บข้อมูลแบบ Offline ภายในตัวเครื่องด้วย SQLite Database
🛠️ Tech Stack (เทคโนโลยีที่ใช้)
Language: B4A (Basic4android)
Database: SQLite (Relational Design)
Library: SQL, StringUtils
Logic: Conditional Querying, Data Validation, Activity Life Cycle Management
📂 Project Structure (โครงสร้างโปรเจกต์)
projectfood.b4a: Main Logic และ Source Code หลักของโปรแกรม
food.db: ไฟล์ฐานข้อมูล SQLite ที่ออกแบบ Schema สำหรับเก็บข้อมูลอาหารและค่า DRI
Files/*.bal: ไฟล์ Layout สำหรับหน้าจอ Interface ต่างๆ ของแอปพลิเคชัน
