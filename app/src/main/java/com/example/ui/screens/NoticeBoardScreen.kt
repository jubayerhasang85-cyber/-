package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.NoticeEntity
import com.example.data.model.UserRole
import com.example.data.model.UserSession
import com.example.data.model.toNoticeEntity
import com.example.data.model.toSchoolAnnouncement
import com.example.ui.components.NoticeBoardComponent
import com.example.ui.theme.MadrasaGreenPrimary
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextOnGreen

@Composable
fun NoticeBoardScreen(
    session: UserSession?,
    notices: List<NoticeEntity>,
    onPublishNotice: (title: String, content: String, category: String, isPinned: Boolean) -> Unit,
    onDeleteNotice: (NoticeEntity) -> Unit
) {
    var showCreateDialog by remember { mutableStateOf(false) }

    val canPublish = session?.role in listOf(UserRole.TEACHER, UserRole.PRINCIPAL, UserRole.ADMIN)
    val canManage = session?.role in listOf(UserRole.PRINCIPAL, UserRole.ADMIN)

    val announcements = remember(notices) {
        notices.map { it.toSchoolAnnouncement() }
    }

    Scaffold(
        floatingActionButton = {
            if (canPublish) {
                FloatingActionButton(
                    onClick = { showCreateDialog = true },
                    containerColor = MadrasaGreenPrimary,
                    contentColor = TextOnGreen,
                    modifier = Modifier.testTag("publish_notice_fab")
                ) {
                    Icon(Icons.Default.Add, contentDescription = "নতুন নোটিশ প্রকাশ")
                }
            }
        }
    ) { padding ->
        NoticeBoardComponent(
            announcements = announcements,
            canManage = canManage,
            onDeleteAnnouncement = { announcement ->
                onDeleteNotice(announcement.toNoticeEntity())
            },
            onAddAnnouncementClick = {
                if (canPublish) showCreateDialog = true
            },
            showHeaderBanner = true,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF4F7F4))
                .testTag("notice_board_screen")
        )
    }

    // নতুন নোটিশ প্রকাশ ডায়লগ
    if (showCreateDialog) {
        CreateNoticeDialog(
            onDismiss = { showCreateDialog = false },
            onPublish = { title, content, cat, pinned ->
                onPublishNotice(title, content, cat, pinned)
                showCreateDialog = false
            }
        )
    }
}

@Composable
fun CreateNoticeDialog(
    onDismiss: () -> Unit,
    onPublish: (title: String, content: String, category: String, isPinned: Boolean) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("জরুরি") }
    var isPinned by remember { mutableStateOf(false) }

    val categories = listOf("জরুরি", "পরীক্ষা", "ছুটি", "সাধারণ")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "নতুন নোটিশ প্রকাশ",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MadrasaGreenPrimary
            )
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("নোটিশের শিরোনাম") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("notice_title_input"),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "ক্যাটাগরি নির্বাচন করুন:", fontSize = 12.sp, color = TextDark)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    categories.forEach { cat ->
                        val isSelected = cat == category
                        Surface(
                            onClick = { category = cat },
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) MadrasaGreenPrimary else Color(0xFFEFF3EF)
                        ) {
                            Text(
                                text = cat,
                                fontSize = 11.sp,
                                color = if (isSelected) TextOnGreen else TextDark,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("নোটিশের মূল বক্তব্য") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp)
                        .testTag("notice_content_input"),
                    maxLines = 5
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = isPinned,
                        onCheckedChange = { isPinned = it }
                    )
                    Text(text = "নোটিশ বোর্ডে সবার উপরে পিন করুন", fontSize = 12.sp, color = TextDark)
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank() && content.isNotBlank()) {
                        onPublish(title, content, category, isPinned)
                    }
                },
                enabled = title.isNotBlank() && content.isNotBlank(),
                colors = ButtonDefaults.buttonColors(containerColor = MadrasaGreenPrimary)
            ) {
                Text("প্রকাশ করুন")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("বাতিল")
            }
        }
    )
}
