package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.DeveloperBoard
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UpdateLogEntity
import com.example.data.model.UserRole
import com.example.data.model.UserSession
import com.example.ui.theme.MadrasaCardBorder
import com.example.ui.theme.MadrasaGold
import com.example.ui.theme.MadrasaGreenDark
import com.example.ui.theme.MadrasaGreenPrimary
import com.example.ui.theme.MadrasaSurfaceWhite
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextOnGreen

@Composable
fun UpdateLogScreen(
    session: UserSession?,
    updateLogs: List<UpdateLogEntity>,
    onAddLog: (title: String, description: String, version: String) -> Unit
) {
    var showAddDialog by remember { mutableStateOf(false) }

    // ১০. আপডেট লগ: শুধুমাত্র প্রশাসকের জন্য দৃশ্যমান
    if (session?.role != UserRole.ADMIN) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF4F7F4))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFFEBEE)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                    tint = Color(0xFFC62828),
                    modifier = Modifier.size(32.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "প্রবেশাধিকার সংরক্ষিত",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "আপডেট লগ ও সিস্টেম অডিট শুধুমাত্র অ্যাপ নির্মাতা ও সিস্টেম প্রশাসকের জন্য দৃশ্যমান। অনুগ্রহ করে নির্ধারিত গোপন ইমেইল দিয়ে অ্যাডমিন লগইন করুন।",
                textAlign = TextAlign.Center,
                fontSize = 13.sp,
                color = TextMuted
            )
        }
        return
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = Color(0xFF0A442A),
                contentColor = MadrasaGold,
                modifier = Modifier.testTag("add_log_fab")
            ) {
                Icon(Icons.Default.Add, contentDescription = "নতুন আপডেট লগ যোগ করুন")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF4F7F4))
                .testTag("update_log_screen")
        ) {
            // সবুজ হেডার (সবুজ ব্যাকগ্রাউন্ডে সাদা টেক্সট)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF073B24))
                    .padding(horizontal = 16.dp, vertical = 14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(MadrasaGold.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = null,
                            tint = MadrasaGold,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "সিস্টেম আপডেট লগ ও অডিট হিস্টোরি",
                            color = MadrasaGold,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "শুধুমাত্র অ্যাডমিন/অ্যাপ নির্মাতা (${session.identifier}) এর জন্য সক্রিয়",
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 11.sp
                        )
                    }
                }
            }

            // সিস্টেম ও ডেটাবেজ স্ট্যাটাস কার্ড (সাদা ব্যাকগ্রাউন্ডে কালো টেক্সট)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = MadrasaSurfaceWhite),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Storage, contentDescription = null, tint = MadrasaGreenPrimary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "অ্যাপ ডেটাবেজ আর্কিটেকচার",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = TextDark
                            )
                        }
                        Surface(
                            color = Color(0xFFE8F5E9),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "অনলাইন ও অফলাইন সিঙ্কড",
                                color = Color(0xFF1B5E20),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Divider(color = Color(0xFFF0F0F0))
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = "• প্ল্যাটফর্ম আর্কিটেকচার: AppSheet স্টাইল ডাটাবেজ মডেলিং", fontSize = 12.sp, color = TextDark)
                    Text(text = "• ইঞ্জিন: Jetpack Compose + Room Local Persistence", fontSize = 12.sp, color = TextDark)
                    Text(text = "• মোট সক্রিয় টেবিল: ৬টি (ছাত্র, হাজিরা, নোটিশ, ফলাফল, কিতাব, আপডেট লগ)", fontSize = 12.sp, color = TextDark)
                    Text(text = "• অ্যাক্সেস নিরাপত্তা: রোল-ভিত্তিক কন্ট্রোল ও পিন অথেন্টিকেশন", fontSize = 12.sp, color = TextDark)
                }
            }

            // আপডেট লগের তালিকা
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(updateLogs) { log ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("update_log_${log.id}"),
                        colors = CardDefaults.cardColors(containerColor = MadrasaSurfaceWhite),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    color = Color(0xFFE8F5E9),
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Text(
                                        text = log.version,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MadrasaGreenPrimary,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                    )
                                }
                                Text(
                                    text = log.timestamp,
                                    fontSize = 11.sp,
                                    color = TextMuted
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = log.title,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextDark
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = log.description,
                                fontSize = 12.sp,
                                color = TextMuted,
                                lineHeight = 18.sp
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "হালনাগাদ করেছেন: ${log.updatedBy}",
                                fontSize = 11.sp,
                                color = Color(0xFF0F4E34),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                item { Spacer(modifier = Modifier.height(72.dp)) }
            }
        }
    }

    // নতুন লগ যোগ করার ডায়লগ
    if (showAddDialog) {
        AddUpdateLogDialog(
            onDismiss = { showAddDialog = false },
            onSave = { title, desc, ver ->
                onAddLog(title, desc, ver)
                showAddDialog = false
            }
        )
    }
}

@Composable
fun AddUpdateLogDialog(
    onDismiss: () -> Unit,
    onSave: (title: String, desc: String, ver: String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var version by remember { mutableStateOf("v1.3.0") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "নতুন আপডেট লগ এন্ট্রি",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MadrasaGreenPrimary
            )
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = version,
                    onValueChange = { version = it },
                    label = { Text("ভার্সন ট্যাগ (যেমন v1.3.0)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("আপডেট শিরোনাম") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = desc,
                    onValueChange = { desc = it },
                    label = { Text("বিস্তারিত বিবরণ") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    maxLines = 4
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank() && desc.isNotBlank()) {
                        onSave(title, desc, version)
                    }
                },
                enabled = title.isNotBlank() && desc.isNotBlank(),
                colors = ButtonDefaults.buttonColors(containerColor = MadrasaGreenPrimary)
            ) {
                Text("যোগ করুন")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("বাতিল")
            }
        }
    )
}
