package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.MadrasaGoldDark
import com.example.ui.theme.StatusAbsent
import com.example.ui.theme.StatusLate
import com.example.ui.theme.StatusLeave
import com.example.ui.theme.StatusPresent

@Composable
fun AttendanceBadge(status: String) {
    val (bgColor, textColor, borderColor) = when (status) {
        "উপস্থিত" -> Triple(Color(0xFFE8F5E9), StatusPresent, Color(0xFFA5D6A7))
        "অনুপস্থিত" -> Triple(Color(0xFFFFEBEE), StatusAbsent, Color(0xFFFFCDD2))
        "ছুটি" -> Triple(Color(0xFFFFF3E0), StatusLeave, Color(0xFFFFCC80))
        "বিলম্ব" -> Triple(Color(0xFFFFF8E1), StatusLate, Color(0xFFFFE082))
        else -> Triple(Color(0xFFF5F5F5), Color(0xFF616161), Color(0xFFE0E0E0))
    }

    Box(
        modifier = Modifier
            .background(bgColor, shape = RoundedCornerShape(12.dp))
            .border(1.dp, borderColor, shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Text(
            text = status,
            color = textColor,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun GradeBadge(grade: String) {
    val (bgColor, textColor) = when {
        grade.contains("মুমতাজ") -> Pair(Color(0xFFFEF9E7), MadrasaGoldDark)
        grade.contains("জায়্যিদ জিদ্দান") -> Pair(Color(0xFFE8F5E9), Color(0xFF1B5E20))
        grade.contains("জায়্যিদ") -> Pair(Color(0xFFE1F5FE), Color(0xFF0277BD))
        grade.contains("মাকবুল") -> Pair(Color(0xFFFFF3E0), Color(0xFFE65100))
        else -> Pair(Color(0xFFFFEBEE), Color(0xFFC62828))
    }

    Box(
        modifier = Modifier
            .background(bgColor, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = grade,
            color = textColor,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun NoticeCategoryBadge(category: String) {
    val (bgColor, textColor) = when (category) {
        "জরুরি" -> Pair(Color(0xFFFFEBEE), Color(0xFFC62828))
        "পরীক্ষা" -> Pair(Color(0xFFF3E5F5), Color(0xFF6A1B9A))
        "ছুটি" -> Pair(Color(0xFFE8F5E9), Color(0xFF2E7D32))
        else -> Pair(Color(0xFFE3F2FD), Color(0xFF1565C0))
    }

    Box(
        modifier = Modifier
            .background(bgColor, shape = RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        Text(
            text = category,
            color = textColor,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}
