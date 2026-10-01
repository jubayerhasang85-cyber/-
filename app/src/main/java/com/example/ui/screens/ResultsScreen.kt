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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Grade
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.InitialDataProvider
import com.example.data.model.ResultEntity
import com.example.data.model.StudentEntity
import com.example.data.model.UserRole
import com.example.data.model.UserSession
import com.example.ui.components.GradeBadge
import com.example.ui.theme.MadrasaCardBorder
import com.example.ui.theme.MadrasaGold
import com.example.ui.theme.MadrasaGoldDark
import com.example.ui.theme.MadrasaGreenDark
import com.example.ui.theme.MadrasaGreenPrimary
import com.example.ui.theme.MadrasaSurfaceWhite
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextOnGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultsScreen(
    session: UserSession?,
    selectedClass: String,
    selectedExamTerm: String,
    results: List<ResultEntity>,
    students: List<StudentEntity>,
    onSelectClass: (String) -> Unit,
    onSelectExamTerm: (String) -> Unit,
    onSaveResult: (StudentEntity, examTerm: String, quran: Int, hadith: Int, fiqh: Int, nahw: Int, sarf: Int, general: Int) -> Unit
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val canEdit = session?.role in listOf(UserRole.TEACHER, UserRole.PRINCIPAL, UserRole.ADMIN)

    val filteredResults = results.filter {
        (it.className == selectedClass || selectedClass.isBlank()) &&
                (searchQuery.isBlank() || it.studentName.contains(searchQuery, true) || it.studentRoll.toString().contains(searchQuery))
    }

    Scaffold(
        floatingActionButton = {
            if (canEdit) {
                FloatingActionButton(
                    onClick = { showAddDialog = true },
                    containerColor = MadrasaGreenPrimary,
                    contentColor = TextOnGreen,
                    modifier = Modifier.testTag("add_result_fab")
                ) {
                    Icon(Icons.Default.Add, contentDescription = "নতুন ফলাফল যুক্ত করুন")
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF4F7F4))
                .testTag("results_screen")
        ) {
            // সবুজ সাব-হেডার (সবুজ ব্যাকগ্রাউন্ডে সাদা টেক্সট)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MadrasaGreenPrimary)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Column {
                    Text(
                        text = "ছাত্র ফলাফল ট্র্যাকার ও নম্বরপত্র",
                        color = TextOnGreen,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // পরীক্ষা টার্ম সিলেক্টর
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(InitialDataProvider.examTerms) { term ->
                            val isSelected = term == selectedExamTerm
                            Surface(
                                onClick = { onSelectExamTerm(term) },
                                shape = RoundedCornerShape(16.dp),
                                color = if (isSelected) MadrasaGold else MadrasaGreenDark,
                                modifier = Modifier.testTag("term_chip_$term")
                            ) {
                                Text(
                                    text = term,
                                    color = if (isSelected) Color(0xFF1A1C19) else TextOnGreen,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // জামাত সিলেক্টর
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(InitialDataProvider.madrasaClasses) { cls ->
                            val isSelected = cls == selectedClass
                            Surface(
                                onClick = { onSelectClass(cls) },
                                shape = RoundedCornerShape(14.dp),
                                color = if (isSelected) Color.White else Color(0xFF0F4E34),
                                modifier = Modifier.testTag("results_class_chip_$cls")
                            ) {
                                Text(
                                    text = cls,
                                    color = if (isSelected) MadrasaGreenPrimary else TextOnGreen,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                )
                            }
                        }
                    }
                }
            }

            // সার্চ বার
            Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("ফলাফল খুঁজতে ছাত্রের নাম বা রোল লিখুন...", fontSize = 13.sp) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = MadrasaGreenPrimary) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("result_search_input"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MadrasaGreenPrimary,
                        unfocusedContainerColor = MadrasaSurfaceWhite,
                        focusedContainerColor = MadrasaSurfaceWhite
                    ),
                    shape = RoundedCornerShape(10.dp)
                )
            }

            // ফলাফল তালিকা (সাদা ব্যাকগ্রাউন্ডে কালো টেক্সট)
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (filteredResults.isEmpty()) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MadrasaSurfaceWhite),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Grade,
                                    contentDescription = null,
                                    tint = TextMuted,
                                    modifier = Modifier.size(36.dp)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "$selectedClass - $selectedExamTerm এ এখনও কোনো ফলাফল এন্ট্রি করা হয়নি।",
                                    textAlign = TextAlign.Center,
                                    color = TextMuted,
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }
                } else {
                    items(filteredResults) { result ->
                        ResultCard(result = result)
                    }
                }
                item { Spacer(modifier = Modifier.height(72.dp)) }
            }
        }
    }

    // নতুন ফলাফল যুক্ত করার ডায়লগ
    if (showAddDialog) {
        AddResultDialog(
            selectedClass = selectedClass,
            selectedExamTerm = selectedExamTerm,
            students = students,
            onDismiss = { showAddDialog = false },
            onSave = { student, quran, hadith, fiqh, nahw, sarf, general ->
                onSaveResult(student, selectedExamTerm, quran, hadith, fiqh, nahw, sarf, general)
                showAddDialog = false
            }
        )
    }
}

@Composable
fun ResultCard(result: ResultEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("result_card_${result.studentRoll}"),
        colors = CardDefaults.cardColors(containerColor = MadrasaSurfaceWhite),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // হেডার
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
                            .background(Color(0xFFE8F5E9)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${result.studentRoll}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = MadrasaGreenPrimary
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = result.studentName,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = TextDark
                        )
                        Text(
                            text = "${result.className} | ${result.examTerm}",
                            fontSize = 11.sp,
                            color = TextMuted
                        )
                    }
                }

                GradeBadge(grade = result.grade)
            }

            Spacer(modifier = Modifier.height(12.dp))
            Divider(color = Color(0xFFF0F0F0))
            Spacer(modifier = Modifier.height(10.dp))

            // বিষয়ভিত্তিক প্রাপ্ত নম্বর
            Text(
                text = "বিষয়ভিত্তিক নম্বরপত্র (পূর্ণমান প্রতি বিষয়ে ১০০):",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextDark,
                modifier = Modifier.padding(bottom = 6.dp)
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                SubjectMarkItem("কুরআন/হিফজ", "${result.quranMarks}")
                SubjectMarkItem("হাদীস শরীফ", "${result.hadithMarks}")
                SubjectMarkItem("ফিকহ ও উসুল", "${result.fiqhMarks}")
            }
            Spacer(modifier = Modifier.height(6.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                SubjectMarkItem("নাহব শাস্ত্র", "${result.nahwMarks}")
                SubjectMarkItem("সরফ শাস্ত্র", "${result.sarfMarks}")
                SubjectMarkItem("সাধারণ/বাংলা", "${result.generalMarks}")
            }

            Spacer(modifier = Modifier.height(12.dp))

            // ফলাফল সামারি বার
            Surface(
                color = Color(0xFFF7FAF7),
                shape = RoundedCornerShape(8.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, MadrasaCardBorder)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "মোট নম্বর: ${result.totalMarks} / ৬০০",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MadrasaGreenPrimary
                        )
                        Text(
                            text = "শতকরা হার: ${result.percentage}%",
                            fontSize = 11.sp,
                            color = TextDark
                        )
                    }

                    Surface(
                        color = Color(0xFFFFF8E1),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "মন্তব্য: ${result.remarks}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = MadrasaGoldDark,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SubjectMarkItem(subject: String, marks: String) {
    Column(horizontalAlignment = Alignment.Start) {
        Text(text = subject, fontSize = 11.sp, color = TextMuted)
        Text(text = "$marks নম্বর", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextDark)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddResultDialog(
    selectedClass: String,
    selectedExamTerm: String,
    students: List<StudentEntity>,
    onDismiss: () -> Unit,
    onSave: (StudentEntity, quran: Int, hadith: Int, fiqh: Int, nahw: Int, sarf: Int, general: Int) -> Unit
) {
    var selectedStudentIndex by remember { mutableStateOf(0) }
    var quran by remember { mutableStateOf("85") }
    var hadith by remember { mutableStateOf("80") }
    var fiqh by remember { mutableStateOf("82") }
    var nahw by remember { mutableStateOf("78") }
    var sarf by remember { mutableStateOf("84") }
    var general by remember { mutableStateOf("75") }

    val classStudents = students.filter { it.className == selectedClass }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "নতুন ফলাফল এন্ট্রি ($selectedClass)",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MadrasaGreenPrimary
            )
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                if (classStudents.isEmpty()) {
                    Text(text = "এই জামাতে কোনো ছাত্র নেই।", color = TextMuted)
                } else {
                    Text(text = "ছাত্র নির্বাচন করুন:", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextDark)
                    val student = classStudents.getOrElse(selectedStudentIndex) { classStudents.first() }
                    Text(text = "রোল: ${student.roll} - ${student.name}", fontSize = 13.sp, color = MadrasaGreenPrimary, fontWeight = FontWeight.Bold)

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = quran,
                            onValueChange = { quran = it },
                            label = { Text("কুরআন") },
                            modifier = Modifier.weight(1f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                        OutlinedTextField(
                            value = hadith,
                            onValueChange = { hadith = it },
                            label = { Text("হাদীস") },
                            modifier = Modifier.weight(1f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = fiqh,
                            onValueChange = { fiqh = it },
                            label = { Text("ফিকহ") },
                            modifier = Modifier.weight(1f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                        OutlinedTextField(
                            value = nahw,
                            onValueChange = { nahw = it },
                            label = { Text("নাহব") },
                            modifier = Modifier.weight(1f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = sarf,
                            onValueChange = { sarf = it },
                            label = { Text("সরফ") },
                            modifier = Modifier.weight(1f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                        OutlinedTextField(
                            value = general,
                            onValueChange = { general = it },
                            label = { Text("সাধারণ") },
                            modifier = Modifier.weight(1f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }
                }
            }
        },
        confirmButton = {
            if (classStudents.isNotEmpty()) {
                Button(
                    onClick = {
                        val student = classStudents.getOrElse(selectedStudentIndex) { classStudents.first() }
                        onSave(
                            student,
                            quran.toIntOrNull() ?: 0,
                            hadith.toIntOrNull() ?: 0,
                            fiqh.toIntOrNull() ?: 0,
                            nahw.toIntOrNull() ?: 0,
                            sarf.toIntOrNull() ?: 0,
                            general.toIntOrNull() ?: 0
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MadrasaGreenPrimary)
                ) {
                    Text("সংরক্ষণ করুন")
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("বাতিল")
            }
        }
    )
}
