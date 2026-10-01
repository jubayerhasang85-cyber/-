package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.SchoolAnnouncement
import com.example.ui.theme.MadrasaCardBorder
import com.example.ui.theme.MadrasaGold
import com.example.ui.theme.MadrasaGreenDark
import com.example.ui.theme.MadrasaGreenLight
import com.example.ui.theme.MadrasaGreenPrimary
import com.example.ui.theme.MadrasaSurfaceWhite
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextOnGreen

/**
 * Notice Board UI Component that displays school announcements with prominent titles and dates.
 * Supports filtering by category, text search, expandable details, and optional delete/add actions.
 */
@Composable
fun NoticeBoardComponent(
    announcements: List<SchoolAnnouncement>,
    modifier: Modifier = Modifier,
    canManage: Boolean = false,
    onAnnouncementClick: ((SchoolAnnouncement) -> Unit)? = null,
    onDeleteAnnouncement: ((SchoolAnnouncement) -> Unit)? = null,
    onAddAnnouncementClick: (() -> Unit)? = null,
    showHeaderBanner: Boolean = true
) {
    var selectedCategory by remember { mutableStateOf("সকল") }
    var searchQuery by remember { mutableStateOf("") }

    val categories = listOf("সকল", "জরুরি", "পরীক্ষা", "ছুটি", "সাধারণ")

    val filteredList = remember(announcements, selectedCategory, searchQuery) {
        announcements.filter { item ->
            val matchesCategory = (selectedCategory == "সকল" || item.category == selectedCategory)
            val matchesQuery = searchQuery.isBlank() ||
                    item.title.contains(searchQuery, ignoreCase = true) ||
                    item.content.contains(searchQuery, ignoreCase = true) ||
                    item.date.contains(searchQuery, ignoreCase = true)
            matchesCategory && matchesQuery
        }
    }

    Column(
        modifier = modifier
            .testTag("notice_board_component")
    ) {
        // ১. হেডার ব্যানার (যদি সক্রিয় থাকে)
        if (showHeaderBanner) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MadrasaGreenPrimary)
                    .padding(horizontal = 16.dp, vertical = 14.dp)
                    .testTag("notice_board_header")
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(MadrasaGold.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Campaign,
                                    contentDescription = "নোটিশ বোর্ড আইকন",
                                    tint = MadrasaGold,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "নোটিশ বোর্ড ও বিজ্ঞপ্তি",
                                    color = TextOnGreen,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "স্কুল ও মাদ্রাসার সাম্প্রতিক ঘোষণা",
                                    color = TextOnGreen.copy(alpha = 0.85f),
                                    fontSize = 11.sp
                                )
                            }
                        }

                        // মোট বিজ্ঞপ্তির সংখ্যা ব্যাজ
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MadrasaGreenDark
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.NotificationsActive,
                                    contentDescription = null,
                                    tint = MadrasaGold,
                                    modifier = Modifier.size(13.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${announcements.size}টি ঘোষণা",
                                    color = TextOnGreen,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // ক্যাটাগরি ফিল্টার চিপস
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(categories) { cat ->
                            val isSelected = cat == selectedCategory
                            val count = if (cat == "সকল") announcements.size else announcements.count { it.category == cat }
                            Surface(
                                onClick = { selectedCategory = cat },
                                shape = RoundedCornerShape(16.dp),
                                color = if (isSelected) MadrasaGold else MadrasaGreenDark,
                                modifier = Modifier.testTag("notice_category_chip_$cat")
                            ) {
                                Text(
                                    text = "$cat ($count)",
                                    color = if (isSelected) Color(0xFF1A1C19) else TextOnGreen,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // ২. সার্চ ও দ্রুত ফিল্টার ইনপুট
        Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("শিরোনাম, তারিখ বা বিষয়বস্তু দিয়ে খুঁজুন...", fontSize = 12.sp) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "খুঁজুন",
                        tint = MadrasaGreenPrimary
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(
                            onClick = { searchQuery = "" },
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "সার্চ মুছুন",
                                tint = TextMuted
                            )
                        }
                    }
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("notice_search_field"),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MadrasaGreenPrimary,
                    unfocusedContainerColor = MadrasaSurfaceWhite,
                    focusedContainerColor = MadrasaSurfaceWhite,
                    unfocusedBorderColor = MadrasaCardBorder
                ),
                shape = RoundedCornerShape(10.dp)
            )
        }

        // ৩. নোটিশের তালিকা (প্রতিটি আইটেমে শিরোনাম ও তারিখ দৃশ্যমান)
        if (filteredList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("notice_empty_state"),
                    colors = CardDefaults.cardColors(containerColor = MadrasaSurfaceWhite),
                    shape = RoundedCornerShape(12.dp),
                    border = CardDefaults.outlinedCardBorder()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = TextMuted,
                            modifier = Modifier.size(36.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "কোনো নোটিশ পাওয়া যায়নি",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextDark
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "ভিন্ন ক্যাটাগরি নির্বাচন করুন বা নতুন নোটিশ প্রকাশ করুন।",
                            fontSize = 12.sp,
                            color = TextMuted,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filteredList, key = { it.id }) { announcement ->
                    SchoolAnnouncementCard(
                        announcement = announcement,
                        canDelete = canManage,
                        onClick = { onAnnouncementClick?.invoke(announcement) },
                        onDelete = { onDeleteAnnouncement?.invoke(announcement) }
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(72.dp))
                }
            }
        }
    }
}

/**
 * Announcement Card component that clearly highlights the Title and Date of the school announcement.
 */
@Composable
fun SchoolAnnouncementCard(
    announcement: SchoolAnnouncement,
    modifier: Modifier = Modifier,
    canDelete: Boolean = false,
    onClick: (() -> Unit)? = null,
    onDelete: (() -> Unit)? = null
) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                isExpanded = !isExpanded
                onClick?.invoke()
            }
            .testTag("announcement_card_${announcement.id}"),
        colors = CardDefaults.cardColors(containerColor = MadrasaSurfaceWhite),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
                .animateContentSize(animationSpec = spring())
        ) {
            // ১. শীর্ষ সারি: তারিখ এবং ক্যাটাগরি ব্যাজ
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // তারিখ ব্যাজ (ক্যালেন্ডার আইকন সহ স্পষ্ট তারিখ)
                AnnouncementDateBadge(date = announcement.date)

                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (announcement.isPinned) {
                        Surface(
                            color = MadrasaGold.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier.padding(end = 6.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PushPin,
                                    contentDescription = "পিন করা বিজ্ঞপ্তি",
                                    tint = MadrasaGold,
                                    modifier = Modifier.size(13.dp)
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                                Text(
                                    text = "পিন করা",
                                    fontSize = 10.sp,
                                    color = Color(0xFF7A5C00),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    NoticeCategoryBadge(category = announcement.category)

                    if (canDelete && onDelete != null) {
                        Spacer(modifier = Modifier.width(6.dp))
                        IconButton(
                            onClick = onDelete,
                            modifier = Modifier
                                .size(28.dp)
                                .testTag("delete_announcement_${announcement.id}")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "বিজ্ঞপ্তি মুছুন",
                                tint = Color(0xFFC62828),
                                modifier = Modifier.size(17.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // ২. নোটিশের মূল শিরোনাম (স্পষ্ট ও বড় ফন্টে দৃশ্যমান)
            Text(
                text = announcement.title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark,
                lineHeight = 22.sp,
                modifier = Modifier.testTag("announcement_title_${announcement.id}")
            )

            Spacer(modifier = Modifier.height(6.dp))

            // ৩. নোটিশের মূল বক্তব্য
            Text(
                text = if (isExpanded) announcement.content else announcement.excerpt,
                fontSize = 13.sp,
                color = TextDark.copy(alpha = 0.85f),
                lineHeight = 20.sp,
                modifier = Modifier.testTag("announcement_content_${announcement.id}")
            )

            // যদি লেখা দীর্ঘ হয়, বিস্তারিত দেখার বোতাম
            if (announcement.content.length > 90) {
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.clickable { isExpanded = !isExpanded },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isExpanded) "সংক্ষিপ্ত করুন" else "বিস্তারিত পড়ুন...",
                        color = MadrasaGreenPrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = null,
                        tint = MadrasaGreenPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Divider(color = Color(0xFFF0F0F0))
            Spacer(modifier = Modifier.height(8.dp))

            // ৪. ফুটার: প্রেরক ও প্রতিষ্ঠানের নাম
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "প্রেরক: ${announcement.publishedBy}",
                    fontSize = 11.sp,
                    color = TextMuted,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "মাজহারুল উলুম মাদ্রাসা",
                    fontSize = 10.sp,
                    color = MadrasaGreenPrimary,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

/**
 * Prominent Date Badge displaying calendar icon and announcement release date.
 */
@Composable
fun AnnouncementDateBadge(
    date: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.testTag("announcement_date_badge"),
        shape = RoundedCornerShape(8.dp),
        color = Color(0xFFE8F5E9),
        border = CardDefaults.outlinedCardBorder().copy(width = 0.5.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.CalendarMonth,
                contentDescription = "তারিখ",
                tint = MadrasaGreenPrimary,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = date,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = MadrasaGreenPrimary
            )
        }
    }
}

/**
 * Compact preview section for displaying announcements with titles and dates on dashboards or summary views.
 */
@Composable
fun CompactNoticeBoardSection(
    announcements: List<SchoolAnnouncement>,
    onViewAllClick: () -> Unit,
    onAnnouncementClick: (SchoolAnnouncement) -> Unit,
    modifier: Modifier = Modifier,
    maxItems: Int = 3
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .testTag("compact_notice_board_section")
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Campaign,
                    contentDescription = null,
                    tint = MadrasaGreenPrimary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "নোটিশ বোর্ড ও জরুরি বিজ্ঞপ্তি",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
            }
            Text(
                text = "সকল নোটিশ →",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = MadrasaGreenPrimary,
                modifier = Modifier
                    .clickable { onViewAllClick() }
                    .testTag("view_all_notices_button")
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (announcements.isEmpty()) {
            Text(
                text = "আপাতত কোনো সক্রিয় নোটিশ নেই।",
                fontSize = 12.sp,
                color = TextMuted,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        } else {
            announcements.take(maxItems).forEach { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { onAnnouncementClick(item) }
                        .testTag("compact_notice_item_${item.id}"),
                    colors = CardDefaults.cardColors(containerColor = MadrasaSurfaceWhite),
                    shape = RoundedCornerShape(10.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            NoticeCategoryBadge(category = item.category)
                            AnnouncementDateBadge(date = item.date)
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        // শিরোনাম (Title)
                        Text(
                            text = item.title,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextDark,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        // সংক্ষিপ্ত বর্ণনা
                        Text(
                            text = item.content,
                            fontSize = 12.sp,
                            color = TextMuted,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }
            }
        }
    }
}
