package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material.icons.filled.AddAlert
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Grade
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.local.InitialDataProvider
import com.example.data.model.AttendanceEntity
import com.example.data.model.NoticeEntity
import com.example.data.model.StudentEntity
import com.example.data.model.UserRole
import com.example.data.model.UserSession
import com.example.data.model.toSchoolAnnouncement
import com.example.ui.components.CompactNoticeBoardSection
import com.example.ui.components.NoticeCategoryBadge
import com.example.ui.theme.MadrasaCardBorder
import com.example.ui.theme.MadrasaGold
import com.example.ui.theme.MadrasaGoldDark
import com.example.ui.theme.MadrasaGreenDark
import com.example.ui.theme.MadrasaGreenLight
import com.example.ui.theme.MadrasaGreenPrimary
import com.example.ui.theme.MadrasaSurfaceWhite
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextOnGreen
import com.example.viewmodel.AppScreen

@Composable
fun DashboardScreen(
    session: UserSession?,
    students: List<StudentEntity>,
    notices: List<NoticeEntity>,
    todayAttendances: List<AttendanceEntity>,
    onNavigate: (AppScreen) -> Unit,
    onLoginClick: () -> Unit
) {
    val presentCount = todayAttendances.count { it.status == "উপস্থিত" }
    val totalRecorded = todayAttendances.size
    val attendancePercentage = if (totalRecorded > 0) {
        ((presentCount.toDouble() / totalRecorded.toDouble()) * 100).toInt()
    } else 88

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F7F4))
            .testTag("dashboard_screen"),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        // ১. হিরো ব্যানার (সবুজ ব্যাকগ্রাউন্ডে সাদা টেক্সট)
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MadrasaGreenPrimary)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // ইমেজ ব্যানার (যদি ড্রয়েবল থাকে)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp)
                            .clip(RoundedCornerShape(12.dp))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.madrasa_hero_banner),
                            contentDescription = "মাদ্রাসা ক্যাম্পাস",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black.copy(alpha = 0.35f))
                        )
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(12.dp)
                        ) {
                            Text(
                                text = "মাজহারুল উলুম মাদ্রাসা",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "দাওরায়ে হাদীস ও হিফজুল কুরআন বিভাগ",
                                color = MadrasaGold,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // ইউজার স্বাগতম মেসেজ বা লগইন আহ্বান
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = if (session != null) "আসসালামু আলাইকুম, ${session.name}" else "আসসালামু আলাইকুম, সম্মানিত মেহমান",
                                color = TextOnGreen,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "তারিখ: ১লা অক্টোবর ২০২৬ ইং | হিজরি রবিউস সানি ১৪৪৮",
                                color = TextOnGreen.copy(alpha = 0.85f),
                                fontSize = 11.sp
                            )
                        }

                        if (session == null) {
                            Surface(
                                onClick = onLoginClick,
                                shape = RoundedCornerShape(16.dp),
                                color = MadrasaGold
                            ) {
                                Text(
                                    text = "লগইন",
                                    color = Color(0xFF1A1C19),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // ২. পরিসংখ্যান কার্ডস (সাদা ব্যাকগ্রাউন্ডে কালো টেক্সট)
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                Text(
                    text = "মাদ্রাসার একনজরে পরিসংখ্যান:",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    StatCard(
                        title = "মোট ছাত্র",
                        value = "${students.size} জন",
                        subtitle = "১০টি জামাতে",
                        icon = Icons.Default.Group,
                        accentColor = MadrasaGreenPrimary,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        title = "আজকের উপস্থিতি",
                        value = "$attendancePercentage%",
                        subtitle = "নিয়মিত ক্লাসে",
                        icon = Icons.Default.CheckCircle,
                        accentColor = Color(0xFF107C41),
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    StatCard(
                        title = "সক্রিয় নোটিশ",
                        value = "${notices.size} টি",
                        subtitle = "সর্বশেষ বিজ্ঞপ্তি",
                        icon = Icons.Default.Notifications,
                        accentColor = Color(0xFFE65100),
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        title = "দরসি জামাত",
                        value = "১০ টি",
                        subtitle = "হিফজ ও কিতাব",
                        icon = Icons.Default.School,
                        accentColor = Color(0xFF1565C0),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // ৩. প্রধান ফিচার মেনু ও কুইক অ্যাকশন (সবুজ ব্যাকগ্রাউন্ডে সাদা টেক্সট বোতাম ও কার্ড)
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
                Text(
                    text = "প্রধান সেবাসমূহ (ফিচার মেনু):",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    QuickActionCard(
                        title = "অ্যাটেন্ডেন্স ট্র্যাকিং",
                        desc = "ক্লাসভিত্তিক দৈনিক হাজিরা",
                        icon = Icons.Default.CalendarMonth,
                        buttonText = "হাজিরা দেখুন",
                        containerColor = MadrasaGreenPrimary,
                        modifier = Modifier
                            .weight(1f)
                            .testTag("action_attendance"),
                        onClick = { onNavigate(AppScreen.ATTENDANCE) }
                    )
                    QuickActionCard(
                        title = "ছাত্র ফলাফল ট্র্যাকার",
                        desc = "বিষয়ভিত্তিক নম্বর ও গ্রেড",
                        icon = Icons.Default.Grade,
                        buttonText = "ফলাফল দেখুন",
                        containerColor = MadrasaGreenMediumColor,
                        modifier = Modifier
                            .weight(1f)
                            .testTag("action_results"),
                        onClick = { onNavigate(AppScreen.RESULTS) }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    QuickActionCard(
                        title = "নোটিশ বোর্ড",
                        desc = "পরীক্ষা ও ছুটির বিজ্ঞপ্তি",
                        icon = Icons.Default.Campaign,
                        buttonText = "নোটিশ দেখুন",
                        containerColor = MadrasaGreenDark,
                        modifier = Modifier
                            .weight(1f)
                            .testTag("action_notices"),
                        onClick = { onNavigate(AppScreen.NOTICES) }
                    )
                    QuickActionCard(
                        title = "দরসি কিতাব তালিকা",
                        desc = "প্রতিটি শ্রেণির বইয়ের তালিকা",
                        icon = Icons.Default.Book,
                        buttonText = "কিতাব দেখুন",
                        containerColor = Color(0xFF0F4E34),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("action_syllabus"),
                        onClick = { onNavigate(AppScreen.SYLLABUS) }
                    )
                }

                // ১০. আপডেট লগ: শুধুমাত্র প্রশাসকের জন্য দৃশ্যমান
                if (session?.role == UserRole.ADMIN) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onNavigate(AppScreen.UPDATE_LOG) }
                            .testTag("admin_update_log_card"),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF0A3D24)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(MadrasaGold),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Code,
                                    contentDescription = null,
                                    tint = Color(0xFF1A1C19),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "আপডেট লগ (শুধুমাত্র প্রশাসক)",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MadrasaGold
                                )
                                Text(
                                    text = "সিস্টেম আর্কিটেকচার, ডেটাবেস লগ ও ভার্সন কন্ট্রোল",
                                    fontSize = 11.sp,
                                    color = Color.White.copy(alpha = 0.85f)
                                )
                            }
                            Text(
                                text = "প্রবেশ করুন →",
                                color = MadrasaGold,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        // ৪. সর্বশেষ নোটিশ প্রিভিউ (নোটিশ বোর্ড কম্পোনেন্ট)
        item {
            Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
                CompactNoticeBoardSection(
                    announcements = notices.map { it.toSchoolAnnouncement() },
                    onViewAllClick = { onNavigate(AppScreen.NOTICES) },
                    onAnnouncementClick = { onNavigate(AppScreen.NOTICES) },
                    maxItems = 3
                )
            }
        }

        // ৫. ছাত্রের ব্যক্তিগত তথ্যাবলি (যদি ছাত্র লগইন থাকে)
        if (session?.role == UserRole.STUDENT) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    colors = CardDefaults.cardColors(containerColor = MadrasaSurfaceWhite),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "আপনার ছাত্র প্রোফাইল:",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MadrasaGreenPrimary
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = "নাম: ${session.name}", fontSize = 13.sp, color = TextDark)
                        Text(text = "জামাত: ${session.studentClass}", fontSize = 13.sp, color = TextDark)
                        Text(text = "রোল নম্বর: ${session.studentRoll}", fontSize = 13.sp, color = TextDark)
                        Text(text = "অভিভাবক ফোন: ${session.identifier}", fontSize = 13.sp, color = TextMuted)
                    }
                }
            }
        }
    }
}

val MadrasaGreenMediumColor = Color(0xFF146C43)

@Composable
fun StatCard(
    title: String,
    value: String,
    subtitle: String,
    icon: ImageVector,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MadrasaSurfaceWhite),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(accentColor.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = title,
                    fontSize = 11.sp,
                    color = TextMuted
                )
                Text(
                    text = value,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
                Text(
                    text = subtitle,
                    fontSize = 10.sp,
                    color = TextMuted
                )
            }
        }
    }
}

@Composable
fun QuickActionCard(
    title: String,
    desc: String,
    icon: ImageVector,
    buttonText: String,
    containerColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = containerColor),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MadrasaGold,
                modifier = Modifier.size(26.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextOnGreen
            )
            Text(
                text = desc,
                fontSize = 11.sp,
                color = TextOnGreen.copy(alpha = 0.85f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(10.dp))
            Surface(
                color = Color.White.copy(alpha = 0.2f),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    text = buttonText,
                    color = TextOnGreen,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                )
            }
        }
    }
}
