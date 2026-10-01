package com.example.data.local

import com.example.data.model.AttendanceEntity
import com.example.data.model.BookEntity
import com.example.data.model.NoticeEntity
import com.example.data.model.ResultEntity
import com.example.data.model.StudentEntity
import com.example.data.model.UpdateLogEntity

object InitialDataProvider {

    val madrasaClasses = listOf(
        "দাওরায়ে হাদীস",
        "মেশকাত জামাত",
        "জালালাইন জামাত",
        "শরহে বেকায়া",
        "কাফিয়া জামাত",
        "হেদায়াতুন্নাহব",
        "নাহবেমীর",
        "মিজান জামাত",
        "হিফজুল কুরআন",
        "ইবতিদায়ী / নুরানি"
    )

    val examTerms = listOf(
        "১ম সাময়িক (সানাবিয়া)",
        "অর্ধ-বার্ষিক (নিসফী)",
        "বার্ষিক ফাইনাল পরীক্ষা"
    )

    suspend fun populateInitialData(dao: MadrasaDao) {
        // ১. ছাত্র তালিকা
        val students = listOf(
            // মিজান জামাত
            StudentEntity(roll = 101, name = "মুহাম্মাদ আব্দুল্লাহ", className = "মিজান জামাত", phone = "01711000101", fatherName = "মাওলানা আব্দুর রহিম", guardianPhone = "01711000101"),
            StudentEntity(roll = 102, name = "মুহাম্মাদ সালমান ফারসি", className = "মিজান জামাত", phone = "01711000102", fatherName = "হাফেজ মোস্তফা কামাল", guardianPhone = "01711000102"),
            StudentEntity(roll = 103, name = "আবু বকর সিদ্দিক", className = "মিজান জামাত", phone = "01711000103", fatherName = "মুহিব্বুল্লাহ চৌধুরী", guardianPhone = "01711000103"),
            StudentEntity(roll = 104, name = "উমর ফারুক", className = "মিজান জামাত", phone = "01711000104", fatherName = "কারী আব্দুল হক", guardianPhone = "01711000104"),
            StudentEntity(roll = 105, name = "উসমান গনি", className = "মিজান জামাত", phone = "01711000105", fatherName = "মুফতি নূর হোসেন", guardianPhone = "01711000105"),

            // নাহবেমীর
            StudentEntity(roll = 201, name = "আলী হাসান", className = "নাহবেমীর", phone = "01811000201", fatherName = "মাওলানা জসিম উদ্দিন", guardianPhone = "01811000201"),
            StudentEntity(roll = 202, name = "মাহমুদুল হাসান", className = "নাহবেমীর", phone = "01811000202", fatherName = "হাফেজ সিরাজুল ইসলাম", guardianPhone = "01811000202"),
            StudentEntity(roll = 203, name = "আনাস ইবনে মালেক", className = "নাহবেমীর", phone = "01811000203", fatherName = "মাস্টার রফিকুল ইসলাম", guardianPhone = "01811000203"),

            // হেদায়াতুন্নাহব
            StudentEntity(roll = 301, name = "তলহা জুবায়ের", className = "হেদায়াতুন্নাহব", phone = "01911000301", fatherName = "মাওলানা সাজিদুর রহমান", guardianPhone = "01911000301"),
            StudentEntity(roll = 302, name = "জুবায়ের আহমেদ", className = "হেদায়াতুন্নাহব", phone = "01911000302", fatherName = "মোঃ রমিজ উদ্দিন", guardianPhone = "01911000302"),

            // হিফজুল কুরআন
            StudentEntity(roll = 401, name = "হাফেজ নূরুল ইসলাম", className = "হিফজুল কুরআন", phone = "01611000401", fatherName = "মাওলানা শামসুল আলম", guardianPhone = "01611000401"),
            StudentEntity(roll = 402, name = "হাফেজ আশরাফ আলী", className = "হিফজুল কুরআন", phone = "01611000402", fatherName = "হাফেজ আজিজুল হক", guardianPhone = "01611000402"),
            StudentEntity(roll = 403, name = "হাফেজ তামিম ইকবাল", className = "হিফজুল কুরআন", phone = "01611000403", fatherName = "মোঃ শফিকুল ইসলাম", guardianPhone = "01611000403"),

            // দাওরায়ে হাদীস
            StudentEntity(roll = 501, name = "মাওলানা মোসাদ্দেক বিল্লাহ", className = "দাওরায়ে হাদীস", phone = "01511000501", fatherName = "মুফতি আমীনুল হক", guardianPhone = "01511000501"),
            StudentEntity(roll = 502, name = "মাওলানা হাবিবুর রহমান", className = "দাওরায়ে হাদীস", phone = "01511000502", fatherName = "মাওলানা মিজানুর রহমান", guardianPhone = "01511000502")
        )
        dao.insertStudents(students)

        // ২. কিতাব ও বিষয়সমূহ (দরসি সিলেবাস)
        val books = listOf(
            // দাওরায়ে হাদীস
            BookEntity(className = "দাওরায়ে হাদীস", bookName = "সহীহ বুখারী শরীফ (১ম ও ২য় খণ্ড)", subject = "হাদীস শাস্ত্র", author = "ইমাম মুহাম্মাদ বিন ইসমাঈল বুখারী (রহ.)", totalMarks = 200, description = "সর্বশ্রেষ্ঠ বিশুদ্ধ হাদীস গ্রন্থ, কিতাবুল ঈমান থেকে শেষ পর্যন্ত।"),
            BookEntity(className = "দাওরায়ে হাদীস", bookName = "সহীহ মুসলিম শরীফ (১ম ও ২য় খণ্ড)", subject = "হাদীস শাস্ত্র", author = "ইমাম মুসলিম ইবনুল হাজ্জাজ কুশায়রী (রহ.)", totalMarks = 200, description = "মুকাদ্দামাসহ পূর্ণাঙ্গ সহীহ মুসলিম অধ্যয়ন।"),
            BookEntity(className = "দাওরায়ে হাদীস", bookName = "জামে আত-তিরমিযী", subject = "হাদীস শাস্ত্র", author = "ইমাম আবু ঈসা তিরমিযী (রহ.)", totalMarks = 100, description = "ফিকহুল হাদীস ও আহকাম সংক্রান্ত গুরুত্বপূর্ণ সুনান গ্রন্থ।"),
            BookEntity(className = "দাওরায়ে হাদীস", bookName = "সুনানে আবু দাউদ", subject = "হাদীস শাস্ত্র", author = "ইমাম আবু দাউদ আস-সিজিস্তানী (রহ.)", totalMarks = 100, description = "আহকামের হাদীস সম্বলিত মৌলিক কিতাব।"),
            BookEntity(className = "দাওরায়ে হাদীস", bookName = "সুনানে নাসায়ী ও ইবনে মাজাহ", subject = "হাদীস শাস্ত্র", author = "ইমাম নাসায়ী ও ইমাম ইবনে মাজাহ (রহ.)", totalMarks = 100, description = "সিহাহ সিত্তাহ এর অন্যতম গুরুত্বপূর্ণ দুই কিতাব।"),
            BookEntity(className = "দাওরায়ে হাদীস", bookName = "শরহু মাআনিল আসার (তহাবী শরীফ)", subject = "ফিকহুল হাদীস", author = "ইমাম আবু জাফর আহমদ বিন মুহাম্মাদ তহাবী (রহ.)", totalMarks = 100, description = "হানাফী মাযহাবের হাদীসভিত্তিক দলিলের মূল গ্রন্থ।"),
            BookEntity(className = "দাওরায়ে হাদীস", bookName = "শামায়েলে তিরমিযী", subject = "সীরাত ও আখলাক", author = "ইমাম তিরমিযী (রহ.)", totalMarks = 50, description = "রাসূলুল্লাহ (সা.)-এর সুমহান চারিত্রিক গুণাবলি ও বৈশিষ্ট্য।"),

            // মেশকাত জামাত
            BookEntity(className = "মেশকাত জামাত", bookName = "মেশকাতুল মাসাবীহ", subject = "হাদীস শাস্ত্র", author = "ওয়ালীউদ্দিন মুহাম্মাদ তাবরিযী (রহ.)", totalMarks = 200, description = "হাদীসের সমৃদ্ধ সংকলন ও সনদ পরিচিতি।"),
            BookEntity(className = "মেশকাত জামাত", bookName = "তাফসীরে বায়যাবী", subject = "তাফসীর", author = "কাজী বায়যাবী (রহ.)", totalMarks = 100, description = "বালাগাত ও ফিকহ সমৃদ্ধ মর্যাদাপূর্ণ তাফসীর।"),
            BookEntity(className = "মেশকাত জামাত", bookName = "নুখবাতুল ফিকার", subject = "উসুলুল হাদীস", author = "হাফেজ ইবনে হাজার আসকালানী (রহ.)", totalMarks = 100, description = "হাদীস শাস্ত্রের পরিভাষা ও মূলনীতি।"),
            BookEntity(className = "মেশকাত জামাত", bookName = "শরহে আকাইদে নসফী", subject = "ইলমুল কালাম ও আকাইদ", author = "আল্লামা সাদউদ্দিন তাফতাযানী (রহ.)", totalMarks = 100, description = "আহলে সুন্নাত ওয়াল জামাআতের বিশুদ্ধ আকীদা।"),

            // জালালাইন জামাত
            BookEntity(className = "জালালাইন জামাত", bookName = "তাফসীরে জালালাইন", subject = "তাফসীরুল কুরআন", author = "জালালুদ্দীন মহল্লী ও জালালুদ্দীন সুয়ূতী (রহ.)", totalMarks = 200, description = "সংক্ষিপ্ত ও গভীর অর্থবোধক নির্ভরযোগ্য তাফসীর।"),
            BookEntity(className = "জালালাইন জামাত", bookName = "আল-হেদায়া (৩য় খণ্ড)", subject = "ফিকহ", author = "আল্লামা বুরহানুদ্দীন আল-মারগীনানী (রহ.)", totalMarks = 100, description = "মুয়ামালাত ও লেনদেন সংক্রান্ত মৌলিক ফিকহ গ্রন্থ।"),
            BookEntity(className = "জালালাইন জামাত", bookName = "মুখতাসারুল মা'আনী", subject = "বালাগাত", author = "আল্লামা সাদউদ্দিন তাফতাযানী (রহ.)", totalMarks = 100, description = "আরবি অলংকারশাস্ত্রের বিস্তারিত বিশ্লেষণ।"),
            BookEntity(className = "জালালাইন জামাত", bookName = "মাকামাতে হারিরী", subject = "আরবি সাহিত্য", author = "আবু মুহাম্মাদ আল-কাসিম আল-হারিরী (রহ.)", totalMarks = 100, description = "উচ্চাঙ্গের আরবি গদ্য ও ভাষার ছন্দ।"),

            // শরহে বেকায়া
            BookEntity(className = "শরহে বেকায়া", bookName = "শরহে বেকায়া (১ম ও ২য় খণ্ড)", subject = "ফিকহ", author = "উবায়দুল্লাহ ইবনে মাসউদ আল-মাহবুবী (রহ.)", totalMarks = 200, description = "ইবাদাত ও পরিবার সংক্রান্ত হানাফী ফিকহ।"),
            BookEntity(className = "শরহে বেকায়া", bookName = "আল-হেদায়া (১ম খণ্ড)", subject = "ফিকহ", author = "আল্লামা মারগীনানী (রহ.)", totalMarks = 100, description = "তাহারাত ও সালাত বিষয়ক গভীর দলীলভিত্তিক ফিকহ।"),
            BookEntity(className = "শরহে বেকায়া", bookName = "নুরুল আনওয়ার", subject = "উসুলুল ফিকহ", author = "মোল্লা জীয়ন (রহ.)", totalMarks = 100, description = "ইসলামী আইনশাস্ত্রের উৎস ও প্রয়োগবিধি।"),

            // কাফিয়া জামাত
            BookEntity(className = "কাফিয়া জামাত", bookName = "আল-কাফিয়া", subject = "নাহব", author = "ইবনুল হাজিব (রহ.)", totalMarks = 100, description = "আরবি ব্যাকরণের জটিল তত্ত্ব ও নিয়মাবলি।"),
            BookEntity(className = "কাফিয়া জামাত", bookName = "শরহে জামী", subject = "নাহব", author = "মোল্লা আব্দুর রহমান জামী (রহ.)", totalMarks = 100, description = "কাফিয়ার সবচেয়ে প্রামাণ্য ও বিস্তারিত ব্যাখ্যা।"),
            BookEntity(className = "কাফিয়া জামাত", bookName = "উসুলুশ শাশী", subject = "উসুলুল ফিকহ", author = "ইমাম নিযामुদ্দীন আশ-শাশী (রহ.)", totalMarks = 100, description = "উসুলুল ফিকহের সহজ ও জনপ্রিয় প্রবেশিকা।"),

            // হেদায়াতুন্নাহব
            BookEntity(className = "হেদায়াতুন্নাহব", bookName = "হেদায়াতুন্নাহব", subject = "নাহব", author = "সিরাজুদ্দীন উসমান আল-চিস্তী (রহ.)", totalMarks = 100, description = "আরবি ব্যাকরণের কালজয়ী গ্রন্থ।"),
            BookEntity(className = "হেদায়াতুন্নাহব", bookName = "আল-ফিকহুল মুয়াসসার", subject = "ফিকহ", author = "মুফতি শফীকুর রহমান নদভী (রহ.)", totalMarks = 100, description = "সহজ ও প্রাঞ্জল আরবি ভাষায় ফিকহ শিক্ষা।"),
            BookEntity(className = "হেদায়াতুন্নাহব", bookName = "ইলমুস সীগাহ", subject = "সরফ", author = "মুফতি এনায়েত আহমদ কাকোরভী (রহ.)", totalMarks = 100, description = "শব্দ রূপান্তর ও সীগাহ চেনার নিয়ামাবলি।"),

            // নাহবেমীর
            BookEntity(className = "নাহবেমীর", bookName = "নাহবেমীর", subject = "নাহব", author = "মীর সাইয়্যেদ শরীফ জুরজানী (রহ.)", totalMarks = 100, description = "আরবি বাক্য গঠনের নিয়ম ও এরকামের মূল কিতাব।"),
            BookEntity(className = "নাহবেমীর", bookName = "মারাহুল আরওয়াহ", subject = "সরফ", author = "আহমদ বিন আলী বিন মাসউদ (রহ.)", totalMarks = 100, description = "আরবি ক্রিয়ার গর্দান ও ইলমুস সরফ।"),
            BookEntity(className = "নাহবেমীর", bookName = "তাইসীরুল মুবতাদী", subject = "ফারসী ভাষা", author = "মৌলভী ফরিদউদ্দীন (রহ.)", totalMarks = 50, description = "মাদ্রাসার দরসি ঐতিহ্য রক্ষায় ফারসি ব্যাকরণ।"),

            // মিজান জামাত
            BookEntity(className = "মিজান জামাত", bookName = "মিজানুস সরফ ও মুনশাইব", subject = "সরফ", author = "হামিদুল্লাহ ইবনে আমানুল্লাহ (রহ.)", totalMarks = 100, description = "আরবি শব্দ গঠন ও রূপান্তরের প্রাথমিক বর্ণমালা।"),
            BookEntity(className = "মিজান জামাত", bookName = "ফারসী কি পহলী ও পান্দেনামা", subject = "ফারসী ও আদব", author = "শেখ সাদী শেরাজী (রহ.)", totalMarks = 100, description = "চরিত্র গঠনমূলক কবিতা ও ফারসি শেখা।"),
            BookEntity(className = "মিজান জামাত", bookName = "তাইসীরুল কুরআন ও কিরাআত", subject = "কুরআন ও তাজবীদ", author = "কারী আব্দুল বারী (রহ.)", totalMarks = 100, description = "সহীহ তিলাওয়াত ও মাখরাজ অনুধাবন।"),

            // হিফজুল কুরআন
            BookEntity(className = "হিফজুল কুরআন", bookName = "পবিত্র কুরআনুল কারীম (৩০ পারা)", subject = "হিফজ", author = "আল্লাহ তায়ালার কালাম", totalMarks = 300, description = "দৈনিক ছবক, সাত ছবক ও আমুখা পদ্ধতিতে পূর্ণ হিফজ।"),
            BookEntity(className = "হিফজুল কুরআন", bookName = "তাজবীদুল কুরআনুল কারীম", subject = "তাজবীদ", author = "মুফতি কাফায়েতুল্লাহ (রহ.)", totalMarks = 100, description = "মাখরাজ, সিফাত, গুন্নাহ ও ওয়াকফের কাওয়ায়েদ।"),
            BookEntity(className = "হিফজুল কুরআন", bookName = "মাসনুন দুআ ও প্রয়োজনীয় মাসআলা", subject = "আখলাক ও ফিকহ", author = "মাওলানা আব্দুল মতিন", totalMarks = 50, description = "দৈনন্দিন জীবনের মাসনুন আমল ও নামাজের মাসআলা।"),

            // ইবতিদায়ী / নুরানি
            BookEntity(className = "ইবতিদায়ী / নুরানি", bookName = "নুরানি কায়দা ও আমপারা", subject = "কুরআন শিক্ষা", author = "আলহাজ্ব ক্বারী বেলায়াত হুসাইন (রহ.)", totalMarks = 100, description = "অক্ষর চেনা, হরকত, তানভীন ও পবিত্র আমপারা তিলাওয়াত।"),
            BookEntity(className = "ইবতিদায়ী / নুরানি", bookName = "দীনিয়াত ও ইসলাম শিক্ষা", subject = "আকাইদ ও মাসআলা", author = "মাওলানা মুফতি আজিজুল হক", totalMarks = 100, description = "ঈমানিয়াত, কালিমা, ওজু ও নামাজের নিয়ম।"),
            BookEntity(className = "ইবতিদায়ী / নুরানি", bookName = "সহজ বাংলা ও অংক শিক্ষা", subject = "সাধারণ শিক্ষা", author = "এনসিটিবি কারিকুলাম সমন্বিত", totalMarks = 100, description = "বাংলা বর্ণমালা, নামতা ও প্রাথমিক হিসাব।")
        )
        dao.insertBooks(books)

        // ৩. নোটিশ বোর্ড
        val notices = listOf(
            NoticeEntity(
                title = "পবিত্র মাহে রমজান ও ঈদুল ফিতরের দীর্ঘ বার্ষিক ছুটি সংক্রান্ত",
                content = "মাদ্রাসার সম্মানিত সকল উস্তাদ, অভিভাবক ও প্রিয় ছাত্রদের সদয় অবগতির জন্য জানানো যাচ্ছে যে, আগামী ২৫শে শা'বান থেকে শুরু হয়ে পবিত্র মাহে রমজান ও ঈদুল ফিতর উপলক্ষে মাদ্রাসা বন্ধ থাকবে। আগামী ৮ই শাওয়াল রোজ শনিবার যথারীতি ক্লাস চালু হবে। সকল ছাত্রকে যথাসময়ে উপস্থিত হওয়ার নির্দেশ দেওয়া হলো।",
                date = "২০২৬-১০-০১",
                category = "ছুটি",
                publishedBy = "মুহতামিম, মাজহারুল উলুম মাদ্রাসা",
                isPinned = true
            ),
            NoticeEntity(
                title = "আসন্ন ১ম সাময়িক (সানাবিয়া) পরীক্ষার সময়সূচি ও প্রবেশপত্র বিতরণ",
                content = "আগামী ১০ই অক্টোবর থেকে সকল জামাতের ১ম সাময়িক পরীক্ষা অনুষ্ঠিত হবে। পরীক্ষা সকাল ৯:০০ টা থেকে ১২:০০ টা পর্যন্ত চলবে। যেসব ছাত্রের মাসিক বেতন ও খোরাকি বকেয়া রয়েছে, আগামী ৫ই অক্টোবরের মধ্যে হিসাব শাখায় পরিশোধ করে প্রবেশপত্র গ্রহণ করার নির্দেশ দেওয়া যাচ্ছে।",
                date = "২০২৬-০৯-২৮",
                category = "পরীক্ষা",
                publishedBy = "শিক্ষা সচিব ও প্রধান শিক্ষক",
                isPinned = true
            ),
            NoticeEntity(
                title = "আগামী জুমাবার বাদ জুমা অভিভাবক সম্মেলন ও তালীমি বয়ান",
                content = "ছাত্রদের পড়ালেখার সার্বিক অগ্রগতি ও আমলি মেহনত পর্যালোচনার জন্য মাদ্রাসার অডিটোরিয়ামে এক বিশেষ অভিভাবক সমাবেশ অনুষ্ঠিত হবে। সকল অভিভাবকবৃন্দকে সময়মতো উপস্থিত থেকে দোয়া ও পরামর্শে শরিক হওয়ার উদাত্ত আহ্বান জানানো যাচ্ছে।",
                date = "২০২৬-০৯-২৫",
                category = "জরুরি",
                publishedBy = "মুহতামিম সাহেব",
                isPinned = false
            ),
            NoticeEntity(
                title = "ছাত্রাবাসে তাহাজ্জুদ ও পাঁচ ওয়াক্ত জামাতে শতভাগ উপস্থিতি নির্দেশিকা",
                content = "মাদ্রাসার সকল আবাসিক ছাত্রের জন্য নিয়মিত তাহাজ্জুদ নামাজ ও ফজরের আজানের ১৫ মিনিট পূর্বে মসজিদে হাজির থাকা বাধ্যতামূলক। কোনো ছাত্র জামাত তরক বা বিলম্ব করলে শৃঙ্খলা কমিটির কাছে জবাবদিহি করতে হবে।",
                date = "২০২৬-০৯-২০",
                category = "সাধারণ",
                publishedBy = "নাজেমে দারুল ইকামা (হল সুপার)",
                isPinned = false
            )
        )
        dao.insertNotices(notices)

        // ৪. ফলাফল (Results)
        val results = listOf(
            ResultEntity(
                studentRoll = 101,
                studentName = "মুহাম্মাদ আব্দুল্লাহ",
                className = "মিজান জামাত",
                examTerm = "১ম সাময়িক (সানাবিয়া)",
                quranMarks = 95,
                hadithMarks = 88,
                fiqhMarks = 92,
                nahwMarks = 90,
                sarfMarks = 96,
                generalMarks = 89,
                totalMarks = 550,
                percentage = 91.6,
                grade = "মুমতাজ (A+)",
                position = 1,
                remarks = "অত্যন্ত মেহনতী ও উজ্জ্বল ভবিষ্যৎ"
            ),
            ResultEntity(
                studentRoll = 102,
                studentName = "মুহাম্মাদ সালমান ফারসি",
                className = "মিজান জামাত",
                examTerm = "১ম সাময়িক (সানাবিয়া)",
                quranMarks = 90,
                hadithMarks = 85,
                fiqhMarks = 84,
                nahwMarks = 86,
                sarfMarks = 88,
                generalMarks = 82,
                totalMarks = 515,
                percentage = 85.8,
                grade = "মুমতাজ (A+)",
                position = 2,
                remarks = "মাশাআল্লাহ খুবই উত্তম"
            ),
            ResultEntity(
                studentRoll = 103,
                studentName = "আবু বকর সিদ্দিক",
                className = "মিজান জামাত",
                examTerm = "১ম সাময়িক (সানাবিয়া)",
                quranMarks = 84,
                hadithMarks = 78,
                fiqhMarks = 80,
                nahwMarks = 75,
                sarfMarks = 82,
                generalMarks = 76,
                totalMarks = 475,
                percentage = 79.1,
                grade = "জায়্যিদ জিদ্দান (A)",
                position = 3,
                remarks = "নাহবে আরেকটু মেহনত প্রয়োজন"
            ),
            ResultEntity(
                studentRoll = 104,
                studentName = "উমর ফারুক",
                className = "মিজান জামাত",
                examTerm = "১ম সাময়িক (সানাবিয়া)",
                quranMarks = 75,
                hadithMarks = 70,
                fiqhMarks = 72,
                nahwMarks = 68,
                sarfMarks = 74,
                generalMarks = 71,
                totalMarks = 430,
                percentage = 71.6,
                grade = "জায়্যিদ জিদ্দান (A)",
                position = 4,
                remarks = "ভালো, আরও নিয়মিত হতে হবে"
            ),
            ResultEntity(
                studentRoll = 401,
                studentName = "হাফেজ নূরুল ইসলাম",
                className = "হিফজুল কুরআন",
                examTerm = "১ম সাময়িক (সানাবিয়া)",
                quranMarks = 98,
                hadithMarks = 92,
                fiqhMarks = 90,
                nahwMarks = 88,
                sarfMarks = 90,
                generalMarks = 88,
                totalMarks = 546,
                percentage = 91.0,
                grade = "মুমতাজ (A+)",
                position = 1,
                remarks = "মাখরাজ ও হুসনে সওত চমৎকার"
            )
        )
        dao.insertResults(results)

        // ৫. আজকের হাজিরা
        val todayAttendances = listOf(
            AttendanceEntity(studentRoll = 101, studentName = "মুহাম্মাদ আব্দুল্লাহ", className = "মিজান জামাত", date = "২০২৬-১০-০১", status = "উপস্থিত", remarks = "সকাল ক্লাসে নিয়মিত"),
            AttendanceEntity(studentRoll = 102, studentName = "মুহাম্মাদ সালমান ফারসি", className = "মিজান জামাত", date = "২০২৬-১০-০১", status = "উপস্থিত", remarks = ""),
            AttendanceEntity(studentRoll = 103, studentName = "আবু বকর সিদ্দিক", className = "মিজান জামাত", date = "২০২৬-১০-০১", status = "ছুটি", remarks = "পারিবারিক ওযর"),
            AttendanceEntity(studentRoll = 104, studentName = "উমর ফারুক", className = "মিজান জামাত", date = "২০২৬-১০-০১", status = "উপস্থিত", remarks = ""),
            AttendanceEntity(studentRoll = 105, studentName = "উসমান গনি", className = "মিজান জামাত", date = "২০২৬-১০-০১", status = "অনুপস্থিত", remarks = "অবহিত ছাড়া অনুপস্থিত"),

            AttendanceEntity(studentRoll = 201, studentName = "আলী হাসান", className = "নাহবেমীর", date = "২০২৬-১০-০১", status = "উপস্থিত", remarks = ""),
            AttendanceEntity(studentRoll = 202, studentName = "মাহমুদুল হাসান", className = "নাহবেমীর", date = "২০২৬-১০-০১", status = "উপস্থিত", remarks = ""),
            AttendanceEntity(studentRoll = 203, studentName = "আনাস ইবনে মালেক", className = "নাহবেমীর", date = "২০২৬-১০-০১", status = "বিলম্ব", remarks = "১০ মিনিট পর ক্লাসে প্রবেশ")
        )
        dao.insertAttendances(todayAttendances)

        // ৬. আপডেট লগ (শুধুমাত্র অ্যাডমিন ও নির্মাতার জন্য)
        val updateLogs = listOf(
            UpdateLogEntity(
                version = "v1.2.0 (সর্বশেষ আপডেট)",
                title = "AppSheet স্টাইল আর্কিটেকচার ও নিরাপত্তা হালনাগাদ",
                description = "মাজহারুল উলুম মাদ্রাসার ডেটাবেস সিস্টেমকে অপটিমাইজ করা হয়েছে। নতুন ছাত্র লগইন, শিক্ষক লগইন ও প্রধান শিক্ষক ভ্যালিডেশন যোগ করা হয়েছে। গোপন ইমেইল অথেন্টিকেশন চালু।",
                timestamp = "২০২৬-১০-০১ ১১:৩০ ঘটিকা",
                updatedBy = "jubayerhasang85@gmail.com (অ্যাপ নির্মাতা)"
            ),
            UpdateLogEntity(
                version = "v1.1.0",
                title = "ফলাফল ট্র্যাকার ও গ্রেডিং সিস্টেম সংযোগ",
                description = "মাদ্রাসার ঐতিহ্যবাহী রেজাল্ট স্কেল (মুমতাজ, জায়্যিদ জিদ্দান, জায়্যিদ, মাকবুল ও রাসিব) যোগ করা হয়েছে। প্রতিটি বিষয়ের নম্বর হিসাব অটোমেশন সম্পন্ন।",
                timestamp = "২০২৬-০৯-২৫ ১৬:০০ ঘটিকা",
                updatedBy = "সিস্টেম আর্কিটেক্ট"
            ),
            UpdateLogEntity(
                version = "v1.0.0",
                title = "প্রাথমিক সিস্টেম স্থাপন ও ডেটাবেস তৈরি",
                description = "মাজহারুল উলুম মাদ্রাসার ড্যাশবোর্ড, হাজিরা ট্র্যাকিং, নোটিশ বোর্ড ও পূর্ণাঙ্গ দরসি কিতাব তালিকা ডাটাবেজ ইন্টিগ্রেশন সম্পন্ন।",
                timestamp = "২০২৬-০৯-১০ ১০:১৫ ঘটিকা",
                updatedBy = "jubayerhasang85@gmail.com"
            )
        )
        dao.insertUpdateLogs(updateLogs)
    }
}
