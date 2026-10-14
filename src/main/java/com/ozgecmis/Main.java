package com.ozgecmis;

import com.ozgecmis.model.Education;
import com.ozgecmis.model.PersonalInfo;
import com.ozgecmis.model.ResumeData;
import com.ozgecmis.model.WorkExperience;
import com.ozgecmis.service.ResumePdfGenerator;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 1. Kişisel Bilgiler Nesnesinin Oluşturulması
        PersonalInfo personalInfo = new PersonalInfo(
                "Ahmet Yılmaz",
                "Yazılım Mühendisi",
                "ahmet.yilmaz@ornekmail.com",
                "+90 (555) 123 45 67",
                "Merkez / Kırklareli, Türkiye",
                "profile.jpg"
        );

        String summary = "Nesne yönelimli programlama (OOP), veri yapıları, algoritmalar ve sistem programlama " +
                "konularında güçlü bir altyapıya sahip Yazılım Mühendisi. Ölçeklenebilir arka uç (backend) servisleri, " +
                "bellek yönetimi ve yüksek performanslı kurumsal Java uygulamaları geliştirme konusunda deneyimli.";

        // 2. Hayali 3 İş Deneyimi Nesnesinin Oluşturulması
        WorkExperience exp1 = new WorkExperience(
                "ASELSAN",
                "Kıdemli Yazılım Mühendisi",
                "2024 - 2026",
                "Görev kritik savunma sistemleri için Java ve C tabanlı yüksek performanslı veri işleme modüllerinin " +
                        "tasarlanması, çok iş parçacıklı (multi-threaded) mimarilerin geliştirilmesi ve bellek optimizasyonu."
        );

        WorkExperience exp2 = new WorkExperience(
                "HAVELSAN",
                "Arka Uç (Backend) Yazılım Geliştirici",
                "2022 - 2024",
                "Kurumsal simülasyon ve yönetim projelerinde nesne yönelimli tasarım kalıpları kullanılarak mikroservis " +
                        "mimarilerinin oluşturulması, RESTful API entegrasyonları ve ilişkisel veritabanı optimizasyonu."
        );

        WorkExperience exp3 = new WorkExperience(
                "TÜBİTAK BİLGEM",
                "Aday Mühendis / Stajyer Yazılım Geliştirici",
                "2021 - 2022",
                "Açık kaynaklı yazılım projelerinde veri yapıları, ağ (network) paket zamanlayıcıları ve birim test " +
                        "(unit test) süreçlerinin yürütülmesi; teknik dokümantasyon ve kod analizi çalışmaları."
        );

        List<WorkExperience> experiences = Arrays.asList(exp1, exp2, exp3);

        // 3. Eğitim Bilgileri Nesnesinin Oluşturulması
        Education edu = new Education(
                "Kırklareli Üniversitesi",
                "Yazılım Mühendisliği (Lisans)",
                "2021",
                "3.48 / 4.00"
        );

        List<Education> educations = Arrays.asList(edu);

        // 4. Teknik Yetenekler Listesi
        List<String> skills = Arrays.asList(
                "Java (OOP)", "C / C++", "Veri Yapıları ve Algoritmalar",
                "Linux Sistem Yönetimi", "Git & GitHub", "SQL", "Maven"
        );

        // 5. Tüm Verilerin Kompozisyon Nesnesinde (ResumeData) Birleştirilmesi
        ResumeData resumeData = new ResumeData(personalInfo, summary, experiences, educations, skills);

        // 6. Ayrı Sınıfta PDF Üretim İşleminin Tetiklenmesi
        ResumePdfGenerator pdfGenerator = new ResumePdfGenerator();
        pdfGenerator.createPdf(resumeData, "Ozgecmis_CV.pdf");
    }
}