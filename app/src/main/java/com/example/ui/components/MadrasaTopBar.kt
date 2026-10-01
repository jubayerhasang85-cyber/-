package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Mosque
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.UserRole
import com.example.data.model.UserSession
import com.example.ui.theme.MadrasaGold
import com.example.ui.theme.MadrasaGreenDark
import com.example.ui.theme.MadrasaGreenPrimary
import com.example.ui.theme.TextOnGreen
import com.example.viewmodel.AppScreen

@Composable
fun MadrasaTopBar(
    currentScreen: AppScreen,
    session: UserSession?,
    onNavigateBack: () -> Unit,
    onLogoutClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("madrasa_top_bar"),
        color = MadrasaGreenPrimary,
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (currentScreen != AppScreen.DASHBOARD) {
                    IconButton(
                        onClick = onNavigateBack,
                        modifier = Modifier.testTag("back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "ফিরে যান",
                            tint = TextOnGreen
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(MadrasaGreenDark),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Mosque,
                            contentDescription = "মাদ্রাসা লোগো",
                            tint = MadrasaGold,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "মাজহারুল উলুম মাদ্রাসা",
                        color = TextOnGreen,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = currentScreen.banglaTitle,
                        color = TextOnGreen.copy(alpha = 0.9f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                if (session != null) {
                    // ইউজার রোল ব্যাজ
                    Surface(
                        color = when (session.role) {
                            UserRole.STUDENT -> Color(0xFF1E88E5)
                            UserRole.TEACHER -> Color(0xFF43A047)
                            UserRole.PRINCIPAL -> Color(0xFF8E24AA)
                            UserRole.ADMIN -> MadrasaGold
                        },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.padding(end = 4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = when (session.role) {
                                    UserRole.STUDENT -> Icons.Default.School
                                    UserRole.ADMIN -> Icons.Default.Shield
                                    else -> Icons.Default.AccountCircle
                                },
                                contentDescription = null,
                                tint = if (session.role == UserRole.ADMIN) Color(0xFF1A1C19) else Color.White,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = session.role.banglaTitle,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (session.role == UserRole.ADMIN) Color(0xFF1A1C19) else Color.White
                            )
                        }
                    }

                    IconButton(
                        onClick = onLogoutClick,
                        modifier = Modifier.testTag("logout_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "লগআউট",
                            tint = TextOnGreen
                        )
                    }
                } else {
                    Surface(
                        onClick = onLoginClick,
                        shape = RoundedCornerShape(16.dp),
                        color = MadrasaGreenDark,
                        modifier = Modifier.testTag("login_action_chip")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = "লগইন",
                                tint = MadrasaGold,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "লগইন করুন",
                                color = TextOnGreen,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}
