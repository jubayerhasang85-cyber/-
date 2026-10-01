package com.example.data.model

/**
 * Data model representing a school announcement / notice.
 * Stores announcement details including title, date, content, category, publisher, and priority.
 */
data class SchoolAnnouncement(
    val id: Long = 0,
    val title: String,
    val date: String,
    val content: String,
    val category: String = "সাধারণ",
    val publishedBy: String = "মাদ্রাসার প্রশাসন",
    val isPinned: Boolean = false,
    val priority: AnnouncementPriority = AnnouncementPriority.NORMAL
) {
    /**
     * Short excerpt of the announcement text for compact card displays.
     */
    val excerpt: String
        get() = if (content.length > 90) content.take(87) + "..." else content

    /**
     * Returns true if the announcement contains valid required fields.
     */
    val isValid: Boolean
        get() = title.isNotBlank() && content.isNotBlank() && date.isNotBlank()
}

/**
 * Priority levels for school announcements.
 */
enum class AnnouncementPriority(val label: String) {
    LOW("সাধারণ"),
    NORMAL("স্বাভাবিক"),
    HIGH("গুরুত্বপূর্ণ"),
    URGENT("জরুরি")
}

/**
 * Extension mapper: converts [NoticeEntity] database record to [SchoolAnnouncement] data model.
 */
fun NoticeEntity.toSchoolAnnouncement(): SchoolAnnouncement {
    val priority = when (category) {
        "জরুরি" -> AnnouncementPriority.URGENT
        "পরীক্ষা" -> AnnouncementPriority.HIGH
        else -> AnnouncementPriority.NORMAL
    }
    return SchoolAnnouncement(
        id = this.id,
        title = this.title,
        date = this.date,
        content = this.content,
        category = this.category,
        publishedBy = this.publishedBy,
        isPinned = this.isPinned,
        priority = priority
    )
}

/**
 * Extension mapper: converts [SchoolAnnouncement] data model to [NoticeEntity] database record.
 */
fun SchoolAnnouncement.toNoticeEntity(): NoticeEntity {
    return NoticeEntity(
        id = this.id,
        title = this.title,
        content = this.content,
        date = this.date,
        category = this.category,
        publishedBy = this.publishedBy,
        isPinned = this.isPinned
    )
}
