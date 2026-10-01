package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EventNote
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.InitialDataProvider
import com.example.data.model.AttendanceEntity
import com.example.data.model.StudentEntity
import com.example.data.model.UserRole
import com.example.data.model.UserSession
import com.example.ui.components.AttendanceBadge
import com.example.ui.theme.MadrasaCardBorder
import com.example.ui.theme.MadrasaGold
import com.example.ui.theme.MadrasaGreenDark
import com.example.ui.theme.MadrasaGreenPrimary
import com.example.ui.theme.MadrasaSurfaceWhite
import com.example.ui.theme.StatusAbsent
import com.example.ui.theme.StatusLate
import com.example.ui.theme.StatusLeave
import com.example.ui.theme.StatusPresent
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextOnGreen

@Composable
fun AttendanceScreen(
    session: UserSession?,
    selectedClass: String,
    selectedDate: String,
    students: List<StudentEntity>,
    attendances: List<AttendanceEntity>,
    onSelectClass: (String) -> Unit,
    onSetStatus: (StudentEntity, String) -> Unit,
    onMarkAllPresent: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val canEdit = session?.role in listOf(UserRole.TEACHER, UserRole.PRINCIPAL, UserRole.ADMIN)

    // বর্তমান ক্লাসের ছাত্র এবং তাদের স্ট্যাটাস ম্যাপিং
    val filteredStudents = students.filter {
        searchQuery.isBlank() ||
                it.name.contains(searchQuery, ignoreCase = true) ||
                it.roll.toString().contains(searchQuery)
    }

    val presentCount = attendances.count { it.status == "উপস্থিত" }
    val absentCount = attendances.count { it.status == "অনুপস্থিত" }
    val leaveCount = attendances.count { it.status == "ছুটি" }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F7F4))
            .testTag("attendance_screen")
    ) {
        // সবুজ সাব-হেডার (সবুজ ব্যাকগ্রাউন্ডে সাদা টেক্সট)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MadrasaGreenPrimary)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "দৈনিক হাজিরা তালিকা (AppSheet স্টাইল)",
                            color = TextOnGreen,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "তারিখ: $selectedDate ইং",
                            color = TextOnGreen.copy(alpha = 0.85f),
                            fontSize = 12.sp
                        )
                    }

                    if (canEdit) {
                        Button(
                            onClick = onMarkAllPresent,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MadrasaGreenDark,
                                contentColor = MadrasaGold
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.testTag("mark_all_present_button")
                        ) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("সবাই উপস্থিত", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // শ্রেণি নির্বাচন চিপস
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(InitialDataProvider.madrasaClasses) { cls ->
                        val isSelected = cls == selectedClass
                        Surface(
                            onClick = { onSelectClass(cls) },
                            shape = RoundedCornerShape(16.dp),
                            color = if (isSelected) MadrasaGold else MadrasaGreenDark,
                            modifier = Modifier.testTag("class_chip_$cls")
                        ) {
                            Text(
                                text = cls,
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

        // উপস্থিতি পরিসংখ্যান বার (সাদা ব্যাকগ্রাউন্ডে কালো টেক্সট)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = MadrasaSurfaceWhite),
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AttendanceStatItem(label = "মোট ছাত্র", count = "${students.size}", color = TextDark)
                AttendanceStatItem(label = "উপস্থিত", count = "$presentCount", color = StatusPresent)
                AttendanceStatItem(label = "অনুপস্থিত", count = "$absentCount", color = StatusAbsent)
                AttendanceStatItem(label = "ছুটি", count = "$leaveCount", color = StatusLeave)
            }
        }

        // অনুসন্ধান ও ফিল্টার ফিল্ড
        Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("ছাত্রের নাম বা রোল নম্বর দিয়ে খুঁজুন...", fontSize = 13.sp) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = MadrasaGreenPrimary) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("attendance_search_input"),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MadrasaGreenPrimary,
                    unfocusedContainerColor = MadrasaSurfaceWhite,
                    focusedContainerColor = MadrasaSurfaceWhite
                ),
                shape = RoundedCornerShape(10.dp)
            )
        }

        // হাজিরা রেকর্ড টেবিল / লিস্ট
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (filteredStudents.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MadrasaSurfaceWhite),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "এই জামাতে কোনো ছাত্রের তথ্য পাওয়া যায়নি অথবা অনুসন্ধানের ফলাফলের সাথে মেলেনি।",
                            modifier = Modifier.padding(24.dp),
                            textAlign = TextAlign.Center,
                            color = TextMuted,
                            fontSize = 13.sp
                        )
                    }
                }
            } else {
                items(filteredStudents) { student ->
                    val attendance = attendances.find { it.studentRoll == student.roll }
                    val currentStatus = attendance?.status ?: "অনির্ধারিত"

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("attendance_item_${student.roll}"),
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
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(34.dp)
                                            .clip(CircleShape)
                                            .background(Color(0xFFE8F5E9)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "${student.roll}",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp,
                                            color = MadrasaGreenPrimary
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(
                                            text = student.name,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp,
                                            color = TextDark
                                        )
                                        Text(
                                            text = "পিতা: ${student.fatherName}",
                                            fontSize = 11.sp,
                                            color = TextMuted
                                        )
                                    }
                                }

                                AttendanceBadge(status = currentStatus)
                            }

                            if (canEdit) {
                                Spacer(modifier = Modifier.height(10.dp))
                                Divider(color = Color(0xFFF0F0F0))
                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    StatusActionButton(
                                        title = "উপস্থিত",
                                        isSelected = currentStatus == "উপস্থিত",
                                        color = StatusPresent,
                                        modifier = Modifier.weight(1f),
                                        onClick = { onSetStatus(student, "উপস্থিত") }
                                    )
                                    StatusActionButton(
                                        title = "অনুপস্থিত",
                                        isSelected = currentStatus == "অনুপস্থিত",
                                        color = StatusAbsent,
                                        modifier = Modifier.weight(1f),
                                        onClick = { onSetStatus(student, "অনুপস্থিত") }
                                    )
                                    StatusActionButton(
                                        title = "ছুটি",
                                        isSelected = currentStatus == "ছুটি",
                                        color = StatusLeave,
                                        modifier = Modifier.weight(1f),
                                        onClick = { onSetStatus(student, "ছুটি") }
                                    )
                                    StatusActionButton(
                                        title = "বিলম্ব",
                                        isSelected = currentStatus == "বিলম্ব",
                                        color = StatusLate,
                                        modifier = Modifier.weight(1f),
                                        onClick = { onSetStatus(student, "বিলম্ব") }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AttendanceStatItem(label: String, count: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = count, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = color)
        Text(text = label, fontSize = 11.sp, color = TextMuted)
    }
}

@Composable
private fun StatusActionButton(
    title: String,
    isSelected: Boolean,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(6.dp),
        color = if (isSelected) color else Color(0xFFF5F5F5),
        border = if (isSelected) null else androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE0E0E0))
    ) {
        Text(
            text = title,
            textAlign = TextAlign.Center,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) Color.White else TextDark,
            modifier = Modifier.padding(vertical = 6.dp)
        )
    }
}
