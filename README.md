# Java PDF Özgeçmiş (CV) Üretici Uygulaması

Bu proje, **Nesne Yönelimli Programlama (Object-Oriented Programming - OOP)** prensipleri temel alınarak Java programlama dili ile geliştirilmiş dinamik bir PDF özgeçmiş (CV) oluşturucu uygulamasıdır. 

Uygulama, herhangi bir grafiksel arayüze (GUI) ihtiyaç duymadan; kişisel bilgileri, profil fotoğrafını, eğitim geçmişini, teknik yetkinlikleri ve 3 adet kurgusal iş deneyimini nesneler halinde modelleyerek **iText 5** kütüphanesi aracılığıyla profesyonel bir A4 PDF belgesine (`Ozgecmis_CV.pdf`) dönüştürür.

---

## Mimari Tasarım ve Prensipler

Proje geliştirilirken tüm işlemlerin `main` metodu içerisine yığıldığı spagetti kod yapısından kaçınılmış; **Tek Sorumluluk Prensibi (Single Responsibility Principle - SRP)** ve **Sorumlulukların Ayrılması (Separation of Concerns - SoC)** ilkeleri uygulanmıştır:

* **Kapsülleme (Encapsulation):** Tüm veri modellerindeki sınıf içi değişkenler `private` erişim düzenleyicisi ile dış dünyadan gizlenmiş (Data Hiding), verilere erişim yalnızca `public` yapıcı metotlar (Constructor) ve `Getter` metotları üzerinden sağlanmıştır. Nesne değişmezliğini (Immutability) korumak amacıyla `Setter` metotlarına yer verilmemiştir.
* **Bileşim (Composition - "Has-A" İlişkisi):** Kalıtım karmaşıklığı yerine bileşim tercih edilmiştir. Ana veri taşıyıcı olan `ResumeData` sınıfı; bünyesinde `PersonalInfo`, `List<WorkExperience>` ve `List<Education>` nesnelerini barındırır.
* **Soyutlama (Abstraction):** PDF oluşturma algoritması `Main` sınıfından tamamen izole edilerek `ResumePdfGenerator` sınıfına taşınmıştır. Alt bölüm oluşturucu yardımcı metotlar (`addHeaderAndPhoto`, `addWorkExperiences` vb.) `private` tanımlanmış, dış dünyaya yalnızca `public void createPdf(...)` metodu açılmıştır.

---

## Kullanılan Sınıflar, Nesneler ve Kullanım Nedenleri

### 1. Veri Modeli Sınıfları (`com.ozgecmis.model`)
* **`PersonalInfo`:** Özgeçmiş sahibinin ad-soyad, unvan, e-posta, telefon, adres ve profil fotoğrafının dosya yolu (`photoPath`) bilgilerini bir arada tutmak için kullanılmıştır.
* **`WorkExperience`:** Ödev yönergesinde istenen **3 adet kurgusal iş deneyimini** modellemek amacıyla oluşturulmuştur. Şirket adı, pozisyon, çalışma dönemi ve iş açıklaması verilerini kapsüller. Çalışma zamanında (runtime) bu sınıftan 3 ayrı nesne örneği (instance) üretilir.
* **`Education`:** Adayın üniversite, bölüm, mezuniyet yılı ve genel not ortalaması (GANO) bilgilerini temsil eden nesnedir.
* **`ResumeData`:** Özgeçmişi oluşturan tüm alt nesneleri ve yetenek listesini tek bir çatı altında toplayarak servis katmanına tek bir parametre olarak iletmek (Data Transfer Object yaklaşımı) için kullanılmıştır.

### 2. İşlem / Servis Sınıfı (`com.ozgecmis.service`)
* **`ResumePdfGenerator`:** Ödevin *"PDF özgeçmişin direkt main içinde değil ayrı bir sınıfta yapılan işlem olarak kodlanması"* şartını yerine getiren ana motor sınıfıdır. Kendisine parametre olarak gelen `ResumeData` nesnesini okur, sayfa tasarımını oluşturur ve PDF çıktısını üretir.

### 3. Üçüncü Parti Kütüphane Nesneleri (`iText 5`)
* **`Document` ve `PdfWriter`:** Bellekte A4 boyutunda mantıksal bir PDF belgesi oluşturmak ve bu belgeyi `FileOutputStream` aracılığıyla fiziksel dosyaya yazmak için kullanılmıştır.
* **`PdfPTable` ve `PdfPCell`:** Özgeçmişin üst kısmında profil fotoğrafını sola, kişisel bilgileri sağa kayma olmadan hizalamak ve iş deneyimlerini düzenli bir ızgara (grid) yapısında sunmak için kullanılmıştır.
* **`Image`:** Profil fotoğrafını (`profile.jpg`) diskten okuyup ölçeklendirerek (`scaleToFit`) PDF içerisine yerleştirmek için kullanılmıştır.
* **`BaseFont`, `Font` ve `Paragraph`:** `CP1254` karakter kodlaması ile Türkçe karakter (`ğ, ü, ş, ı, ö, ç`) sorununu çözmek ve başlık/metin hiyerarşisini biçimlendirmek için kullanılmıştır.

---

## Proje Dizin Yapısı

```text
java-pdf-cv-generator/
├── pom.xml
├── README.md
├── profile.jpg
└── src/
    └── main/
        └── java/
            └── com/
                └── ozgecmis/
                    ├── Main.java
                    ├── model/
                    │   ├── PersonalInfo.java
                    │   ├── WorkExperience.java
                    │   ├── Education.java
                    │   └── ResumeData.java
                    └── service/
                        └── ResumePdfGenerator.java
```

---

## Kurulum ve Çalıştırma (.jar Derleme Dahil)

Projeyi bilgisayarınızda derlemek ve çalıştırmak için **Java JDK (8/11/17/21)** ve **Maven** kurulu olmalıdır.

1. Repoyu bilgisayarınıza klonlayın:
   ```bash
   git clone [https://github.com/kamou3d/NESNE-FINAL-1.git](https://github.com/kamou3d/NESNE-FINAL-1.git)
   cd java-pdf-cv-generator
   ```

2. Projeyi çalıştırılabilir tekil bir `.jar` (Fat JAR) dosyası olarak paketleyin:
   ```bash
   mvn clean package
   ```

3. Oluşan `.jar` dosyasını çalıştırın:
   ```bash
   java -jar target/java-pdf-cv-generator-1.0-SNAPSHOT.jar
   ```

4. Uygulama çalıştığında proje ana dizininde **`Ozgecmis_CV.pdf`** dosyası otomatik olarak oluşturulacaktır. *(Eğer dizinde `profile.jpg` bulunmuyorsa, uygulama hata vermeden otomatik olarak temsili bir profil fotoğrafı üretip PDF'e ekler).*
