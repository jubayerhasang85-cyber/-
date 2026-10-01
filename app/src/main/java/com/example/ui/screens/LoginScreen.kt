package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Mosque
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.InitialDataProvider
import com.example.data.model.UserRole
import com.example.ui.theme.MadrasaCardBorder
import com.example.ui.theme.MadrasaGold
import com.example.ui.theme.MadrasaGreenDark
import com.example.ui.theme.MadrasaGreenLight
import com.example.ui.theme.MadrasaGreenPrimary
import com.example.ui.theme.MadrasaSurfaceWhite
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextOnGreen

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun LoginScreen(
    errorMessage: String?,
    onStudentLogin: (phone: String, className: String, roll: String) -> Unit,
    onTeacherLogin: (phone: String, ilhaqNo: String, pin: String) -> Unit,
    onPrincipalLogin: (phone: String, pin: String) -> Unit,
    onAdminLogin: (secretEmail: String) -> Unit,
    onContinueAsGuest: () -> Unit
) {
    var selectedRoleIndex by remember { mutableIntStateOf(0) }
    val roles = listOf("ছাত্র", "সহকারী শিক্ষক", "প্রধান শিক্ষক", "অ্যাপ নির্মাতা")

    // ছাত্র ফর্ম ফিল্ডস
    var studentPhone by remember { mutableStateOf("01711000101") }
    var studentClass by remember { mutableStateOf("মিজান জামাত") }
    var studentRoll by remember { mutableStateOf("101") }
    var classDropdownExpanded by remember { mutableStateOf(false) }

    // সহকারী শিক্ষক ফর্ম ফিল্ডস
    var teacherPhone by remember { mutableStateOf("01712000001") }
    var teacherIlhaq by remember { mutableStateOf("ILH-1042") }
    var teacherPin by remember { mutableStateOf("1234") }

    // প্রধান শিক্ষক ফর্ম ফিল্ডস
    var principalPhone by remember { mutableStateOf("01712345678") }
    var principalPin by remember { mutableStateOf("7860") }

    // অ্যাপ নির্মাতা ফর্ম ফিল্ডস
    var adminEmail by remember { mutableStateOf("jubayerhasang85@gmail.com") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F7F4))
            .verticalScroll(rememberScrollState())
    ) {
        // সবুজ হেডার ব্যাকগ্রাউন্ডে সাদা টেক্সট
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MadrasaGreenPrimary)
                .padding(top = 40.dp, bottom = 28.dp, start = 20.dp, end = 20.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(68.dp)
                        .clip(CircleShape)
                        .background(MadrasaGreenDark),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Mosque,
                        contentDescription = "মাদ্রাসা প্রতীক",
                        tint = MadrasaGold,
                        modifier = Modifier.size(38.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "মাজহারুল উলুম মাদ্রাসা",
                    color = TextOnGreen,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "জ্ঞানের আলোকবর্তিকা ও আদর্শ মানুষ গড়ার অনন্য প্রতিষ্ঠান",
                    color = TextOnGreen.copy(alpha = 0.9f),
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Surface(
                    color = MadrasaGreenDark,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "ব্যবহারকারী লগইন ও রোল নির্বাচন",
                        color = MadrasaGold,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 5.dp)
                    )
                }
            }
        }

        // লগইন কার্ড (সাদা ব্যাকগ্রাউন্ডে কালো টেক্সট)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .testTag("login_card"),
            colors = CardDefaults.cardColors(containerColor = MadrasaSurfaceWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text(
                    text = "আপনার পদবী নির্বাচন করুন:",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )

                Spacer(modifier = Modifier.height(10.dp))

                // ৪টি ইউজার রোলের জন্য ট্যাব
                TabRow(
                    selectedTabIndex = selectedRoleIndex,
                    containerColor = Color(0xFFEFF3EF),
                    contentColor = MadrasaGreenPrimary,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedRoleIndex]),
                            color = MadrasaGreenPrimary,
                            height = 3.dp
                        )
                    },
                    modifier = Modifier.clip(RoundedCornerShape(8.dp))
                ) {
                    roles.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedRoleIndex == index,
                            onClick = { selectedRoleIndex = index },
                            text = {
                                Text(
                                    text = title,
                                    fontSize = 12.sp,
                                    fontWeight = if (selectedRoleIndex == index) FontWeight.Bold else FontWeight.Normal,
                                    color = if (selectedRoleIndex == index) MadrasaGreenPrimary else TextMuted
                                )
                            },
                            modifier = Modifier.testTag("tab_role_$index")
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // ত্রুটি বার্তা প্রদর্শন
                if (!errorMessage.isNullOrBlank()) {
                    Surface(
                        color = Color(0xFFFFEBEE),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 14.dp)
                    ) {
                        Text(
                            text = errorMessage,
                            color = Color(0xFFC62828),
                            fontSize = 12.sp,
                            modifier = Modifier.padding(10.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                // রোল অনুযায়ী লগইন ফর্ম
                when (selectedRoleIndex) {
                    // ০: ছাত্র লগইন (ফোন নাম্বার, শ্রেণি ও রোল)
                    0 -> {
                        Text(
                            text = "ছাত্র লগইন তথ্য",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = MadrasaGreenPrimary
                        )
                        Text(
                            text = "আপনার পিতা/অভিভাবকের ফোন নাম্বার, শ্রেণি ও রোল নম্বর দিয়ে প্রবেশ করুন।",
                            fontSize = 12.sp,
                            color = TextMuted,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )

                        OutlinedTextField(
                            value = studentPhone,
                            onValueChange = { studentPhone = it },
                            label = { Text("মোবাইল ফোন নাম্বার") },
                            leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = MadrasaGreenPrimary) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("student_phone_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MadrasaGreenPrimary,
                                focusedLabelColor = MadrasaGreenPrimary
                            ),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        ExposedDropdownMenuBox(
                            expanded = classDropdownExpanded,
                            onExpandedChange = { classDropdownExpanded = !classDropdownExpanded },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            OutlinedTextField(
                                value = studentClass,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("জামাত / শ্রেণি") },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = classDropdownExpanded) },
                                leadingIcon = { Icon(Icons.Default.School, contentDescription = null, tint = MadrasaGreenPrimary) },
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth()
                                    .testTag("student_class_selector"),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = MadrasaGreenPrimary,
                                    focusedLabelColor = MadrasaGreenPrimary
                                )
                            )
                            ExposedDropdownMenu(
                                expanded = classDropdownExpanded,
                                onDismissRequest = { classDropdownExpanded = false }
                            ) {
                                InitialDataProvider.madrasaClasses.forEach { cls ->
                                    DropdownMenuItem(
                                        text = { Text(cls) },
                                        onClick = {
                                            studentClass = cls
                                            classDropdownExpanded = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = studentRoll,
                            onValueChange = { studentRoll = it },
                            label = { Text("ছাত্রের রোল নম্বর (যেমন ১০১)") },
                            leadingIcon = { Icon(Icons.Default.AccountCircle, contentDescription = null, tint = MadrasaGreenPrimary) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("student_roll_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MadrasaGreenPrimary,
                                focusedLabelColor = MadrasaGreenPrimary
                            ),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        Button(
                            onClick = { onStudentLogin(studentPhone, studentClass, studentRoll) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("student_login_button"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MadrasaGreenPrimary,
                                contentColor = TextOnGreen
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("ছাত্র হিসেবে প্রবেশ করুন", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    // ১: সহকারী শিক্ষক লগইন (ফোন নাম্বার, ইলহাক নাম্বার, ও পিন)
                    1 -> {
                        Text(
                            text = "সহকারী শিক্ষক লগইন",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = MadrasaGreenPrimary
                        )
                        Text(
                            text = "মাদ্রাসা বোর্ড ইলহাক নম্বর ও গোপন পিন কোড দিয়ে লগইন করুন।",
                            fontSize = 12.sp,
                            color = TextMuted,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )

                        OutlinedTextField(
                            value = teacherPhone,
                            onValueChange = { teacherPhone = it },
                            label = { Text("শিক্ষকের ফোন নাম্বার") },
                            leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = MadrasaGreenPrimary) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("teacher_phone_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MadrasaGreenPrimary,
                                focusedLabelColor = MadrasaGreenPrimary
                            ),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = teacherIlhaq,
                            onValueChange = { teacherIlhaq = it },
                            label = { Text("ইলহাক নাম্বার (বোর্ড রেজিস্ট্রেশন আইডি)") },
                            leadingIcon = { Icon(Icons.Default.Security, contentDescription = null, tint = MadrasaGreenPrimary) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("teacher_ilhaq_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MadrasaGreenPrimary,
                                focusedLabelColor = MadrasaGreenPrimary
                            ),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = teacherPin,
                            onValueChange = { teacherPin = it },
                            label = { Text("গোপন পিন কোড (৪ সংখ্যা)") },
                            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = MadrasaGreenPrimary) },
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("teacher_pin_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MadrasaGreenPrimary,
                                focusedLabelColor = MadrasaGreenPrimary
                            ),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        Button(
                            onClick = { onTeacherLogin(teacherPhone, teacherIlhaq, teacherPin) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("teacher_login_button"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MadrasaGreenPrimary,
                                contentColor = TextOnGreen
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("শিক্ষক হিসেবে প্রবেশ করুন", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    // ২: প্রধান শিক্ষক লগইন (ফোন নাম্বার ও পিন)
                    2 -> {
                        Text(
                            text = "প্রধান শিক্ষক / মুহতামিম লগইন",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = MadrasaGreenPrimary
                        )
                        Text(
                            text = "মাদ্রাসার প্রশাসনিক প্রধানের ফোন নম্বর ও অনুমোদিত অ্যাডমিন পিন।",
                            fontSize = 12.sp,
                            color = TextMuted,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )

                        OutlinedTextField(
                            value = principalPhone,
                            onValueChange = { principalPhone = it },
                            label = { Text("মুহতামিমের ফোন নাম্বার") },
                            leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = MadrasaGreenPrimary) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("principal_phone_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MadrasaGreenPrimary,
                                focusedLabelColor = MadrasaGreenPrimary
                            ),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = principalPin,
                            onValueChange = { principalPin = it },
                            label = { Text("মুহতামিম পিন কোড") },
                            leadingIcon = { Icon(Icons.Default.Key, contentDescription = null, tint = MadrasaGreenPrimary) },
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("principal_pin_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MadrasaGreenPrimary,
                                focusedLabelColor = MadrasaGreenPrimary
                            ),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        Button(
                            onClick = { onPrincipalLogin(principalPhone, principalPin) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("principal_login_button"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MadrasaGreenPrimary,
                                contentColor = TextOnGreen
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("প্রধান শিক্ষক হিসেবে প্রবেশ করুন", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    // ৩: অ্যাপ নির্মাতার লগইন (গোপন ইমেল ঠিকানা)
                    3 -> {
                        Text(
                            text = "অ্যাপ নির্মাতার লগইন (সুপার অ্যাডমিন)",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = MadrasaGreenPrimary
                        )
                        Text(
                            text = "নির্ধারিত গোপন ইমেল ঠিকানা দিয়ে সরাসরি পূর্ণাঙ্গ অ্যাডমিন কন্ট্রোল প্যানেল ও আপডেট লগে প্রবেশ করুন।",
                            fontSize = 12.sp,
                            color = TextMuted,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )

                        OutlinedTextField(
                            value = adminEmail,
                            onValueChange = { adminEmail = it },
                            label = { Text("গোপন ইমেল ঠিকানা (Admin Secret Email)") },
                            leadingIcon = { Icon(Icons.Default.Mail, contentDescription = null, tint = MadrasaGreenPrimary) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("admin_secret_email_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MadrasaGreenPrimary,
                                focusedLabelColor = MadrasaGreenPrimary
                            ),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        Button(
                            onClick = { onAdminLogin(adminEmail) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("admin_login_button"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF0A442A),
                                contentColor = MadrasaGold
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(Icons.Default.Shield, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("নির্মাতা প্যানেলে প্রবেশ", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // কুইক ডেমো ফাস্ট-লগইন চিপস (যাতে পর্যালোচক সহজে ক্লিক করেই প্রতিটি রোল টেস্ট করতে পারেন)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            colors = CardDefaults.cardColors(containerColor = MadrasaSurfaceWhite),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "দ্রুত ডেমো পরীক্ষা (এক-ক্লিকে লগইন):",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
                Text(
                    text = "অ্যাপের সকল ফিচার দেখতে নিচের যেকোনো একটি বাটন চাপুন:",
                    fontSize = 11.sp,
                    color = TextMuted,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    DemoChip(
                        title = "ছাত্র (রোল ১০১)",
                        onClick = {
                            selectedRoleIndex = 0
                            onStudentLogin("01711000101", "মিজান জামাত", "101")
                        }
                    )
                    DemoChip(
                        title = "সহকারী শিক্ষক",
                        onClick = {
                            selectedRoleIndex = 1
                            onTeacherLogin("01712000001", "ILH-1042", "1234")
                        }
                    )
                    DemoChip(
                        title = "প্রধান শিক্ষক",
                        onClick = {
                            selectedRoleIndex = 2
                            onPrincipalLogin("01712345678", "7860")
                        }
                    )
                    DemoChip(
                        title = "অ্যাপ নির্মাতা (অ্যাডমিন)",
                        highlight = true,
                        onClick = {
                            selectedRoleIndex = 3
                            onAdminLogin("jubayerhasang85@gmail.com")
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // অতিথি হিসেবে ড্যাশবোর্ড দেখা
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            TextButton(
                onClick = onContinueAsGuest,
                modifier = Modifier.testTag("continue_as_guest_button")
            ) {
                Text(
                    text = "লগইন না করে অতিথি হিসেবে ড্যাশবোর্ড দেখুন →",
                    color = MadrasaGreenPrimary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun DemoChip(
    title: String,
    highlight: Boolean = false,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        color = if (highlight) MadrasaGreenPrimary else Color(0xFFEFF3EF),
        border = if (highlight) null else androidx.compose.foundation.BorderStroke(1.dp, MadrasaCardBorder)
    ) {
        Text(
            text = title,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = if (highlight) TextOnGreen else TextDark,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}
