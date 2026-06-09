### Tasarım Sistemi ve Tipografi Altyapısı

- **Seçim:** Merkezi Jetpack Compose Material 3 Typography (Sistem Varsayılanı: Roboto)
- **Son Güncelleme Tarihi:** 09.06.2026
- **Alternatifler:** Tasarımlara özel harici fontlar (Google Fonts - Inter, Poppins vb.), Hardcoded (doğrudan ekrana yazılmış) stil tanımlamaları, Material 2 tipografi sistemi.
- **Karar Gerekçesi ve Kapsam:**
    - **Performans ve Maliyet:** Uygulamanın ilk aşamalarında harici bir font dosyası yüklemenin getireceği performans yükünden (app size ve rendering time) kaçınmak için Android'in doğal fontu olan Roboto tercih edilmiştir.
    - **Ölçeklenebilirlik (Scalability):** İleride tasarım dili değişirse tüm ekranları tek tek gezmek yerine, sadece `Type.kt` içerisindeki Material 3 slotlarını (Display, Headline, Body vb.) güncelleyerek tüm uygulamanın anında adapte olması hedeflenmiştir.
    - **Erişilebilirlik (Accessibility):** Material 3 standartları, kullanıcı cihazında metin boyutunu büyüttüğünde arayüzün kırılmadan dinamik olarak esnemesine olanak tanır.
    - **Yapay Zeka Entegrasyonu:** Tipografi kurallarının `docs/typography.md` gibi düz metin (plain text) dosyalarında tutulması, yapay zeka destekli kodlama araçlarının (Cursor, GitHub Copilot vb.) sistemin genel kurallarını anlayıp arayüz kodlarken doğru font ağırlıklarını (weight) ve boyutlarını (sp) otomatik olarak kullanmasını sağlar.