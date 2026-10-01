package com.example

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.data.model.NoticeEntity
import com.example.data.model.SchoolAnnouncement
import com.example.data.model.toNoticeEntity
import com.example.data.model.toSchoolAnnouncement
import com.example.ui.components.CompactNoticeBoardSection
import com.example.ui.components.NoticeBoardComponent
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class NoticeBoardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun schoolAnnouncementDataModel_validationsAndMapping() {
        val announcement = SchoolAnnouncement(
            id = 1L,
            title = "বার্ষিক পরীক্ষার সময়সূচি",
            date = "২০২৬-১০-০১",
            content = "সকল ছাত্রদের জানানো যাচ্ছে যে বার্ষিক পরীক্ষা আগামী সপ্তাহে শুরু হবে।",
            category = "পরীক্ষা",
            publishedBy = "প্রধান শিক্ষক",
            isPinned = true
        )

        // Verify properties
        assertEquals("বার্ষিক পরীক্ষার সময়সূচি", announcement.title)
        assertEquals("২০২৬-১০-০১", announcement.date)
        assertTrue(announcement.isValid)
        assertTrue(announcement.isPinned)

        // Verify entity mapping
        val entity = announcement.toNoticeEntity()
        assertEquals(announcement.id, entity.id)
        assertEquals(announcement.title, entity.title)
        assertEquals(announcement.date, entity.date)
        assertEquals(announcement.content, entity.content)
        assertEquals(announcement.category, entity.category)
        assertEquals(announcement.publishedBy, entity.publishedBy)
        assertEquals(announcement.isPinned, entity.isPinned)

        // Verify reverse mapping
        val mappedBack = entity.toSchoolAnnouncement()
        assertEquals(announcement.title, mappedBack.title)
        assertEquals(announcement.date, mappedBack.date)
    }

    @Test
    fun schoolAnnouncementDataModel_invalidCheck() {
        val blankTitle = SchoolAnnouncement(
            id = 2L,
            title = "",
            date = "২০২৬-১০-০১",
            content = "কিছু তথ্য"
        )
        assertFalse(blankTitle.isValid)
    }

    @Test
    fun noticeBoardComponent_displaysAnnouncementTitlesAndDates() {
        val testAnnouncements = listOf(
            SchoolAnnouncement(
                id = 101L,
                title = "পবিত্র রমজানের ছুটি সংক্রান্ত বিজ্ঞপ্তি",
                date = "২০২৬-১০-০১",
                content = "আগামী ২৫শে শা'বান থেকে মাদ্রাসা ছুটি থাকবে।",
                category = "ছুটি",
                publishedBy = "মুহতামিম",
                isPinned = true
            ),
            SchoolAnnouncement(
                id = 102L,
                title = "অভিভাবক সমাবেশ ও আলোচনা",
                date = "২০২৬-০৯-২৮",
                content = "আসন্ন জুমাবার বাদ আসর মাদ্রাসায় সমাবেশ অনুষ্ঠিত হবে।",
                category = "জরুরি",
                publishedBy = "দফতর",
                isPinned = false
            )
        )

        composeTestRule.setContent {
            NoticeBoardComponent(
                announcements = testAnnouncements,
                showHeaderBanner = true
            )
        }

        // Verify titles are displayed
        composeTestRule.onNodeWithText("পবিত্র রমজানের ছুটি সংক্রান্ত বিজ্ঞপ্তি").assertIsDisplayed()
        composeTestRule.onNodeWithText("অভিভাবক সমাবেশ ও আলোচনা").assertIsDisplayed()

        // Verify dates are displayed
        composeTestRule.onNodeWithText("২০২৬-১০-০১").assertIsDisplayed()
        composeTestRule.onNodeWithText("২০২৬-০৯-২৮").assertIsDisplayed()
    }

    @Test
    fun compactNoticeBoardSection_displaysTitlesAndDates() {
        val announcements = listOf(
            SchoolAnnouncement(
                id = 201L,
                title = "সানাবিয়া পরীক্ষার প্রবেশপত্র",
                date = "২০২৬-১০-০৫",
                content = "হিসাব শাখা থেকে প্রবেশপত্র সংগ্রহ করুন।",
                category = "পরীক্ষা"
            )
        )

        var viewAllClicked = false

        composeTestRule.setContent {
            CompactNoticeBoardSection(
                announcements = announcements,
                onViewAllClick = { viewAllClicked = true },
                onAnnouncementClick = {}
            )
        }

        // Title and date are visible in compact view
        composeTestRule.onNodeWithText("সানাবিয়া পরীক্ষার প্রবেশপত্র").assertIsDisplayed()
        composeTestRule.onNodeWithText("২০২৬-১০-০৫").assertIsDisplayed()

        // Click action
        composeTestRule.onNodeWithTag("view_all_notices_button").performClick()
        assertTrue(viewAllClicked)
    }
}
