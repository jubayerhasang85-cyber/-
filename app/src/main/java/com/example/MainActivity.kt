package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Grade
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.UserRole
import com.example.ui.components.MadrasaTopBar
import com.example.ui.screens.AttendanceScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.LoginScreen
import com.example.ui.screens.NoticeBoardScreen
import com.example.ui.screens.ResultsScreen
import com.example.ui.screens.SyllabusScreen
import com.example.ui.screens.UpdateLogScreen
import com.example.ui.theme.MadrasaGold
import com.example.ui.theme.MadrasaGreenDark
import com.example.ui.theme.MadrasaGreenPrimary
import com.example.ui.theme.MadrasaSurfaceWhite
import com.example.ui.theme.MazharulUloomTheme
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextOnGreen
import com.example.viewmodel.AppScreen
import com.example.viewmodel.MadrasaViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MadrasaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MazharulUloomTheme {
                MainMadrasaApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MainMadrasaApp(viewModel: MadrasaViewModel) {
    val session by viewModel.currentSession.collectAsStateWithLifecycle()
    val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
    val loginError by viewModel.loginError.collectAsStateWithLifecycle()

    val students by viewModel.allStudents.collectAsStateWithLifecycle()
    val notices by viewModel.allNotices.collectAsStateWithLifecycle()
    val books by viewModel.allBooks.collectAsStateWithLifecycle()
    val updateLogs by viewModel.updateLogs.collectAsStateWithLifecycle()
    val classAttendances by viewModel.currentClassAttendances.collectAsStateWithLifecycle()
    val todayAllAttendances by viewModel.todayAllAttendances.collectAsStateWithLifecycle()
    val results by viewModel.currentClassResults.collectAsStateWithLifecycle()

    val selectedClass by viewModel.selectedClass.collectAsStateWithLifecycle()
    val selectedDate by viewModel.selectedDate.collectAsStateWithLifecycle()
    val selectedExamTerm by viewModel.selectedExamTerm.collectAsStateWithLifecycle()

    var showLoginScreen by remember { mutableStateOf(false) }

    // BackHandler: sub-screen থেকে হোমে বা লগইন স্ক্রিন থেকে ব্যাক করার জন্য
    if (showLoginScreen) {
        BackHandler {
            showLoginScreen = false
        }
    } else if (currentScreen != AppScreen.DASHBOARD) {
        BackHandler {
            viewModel.navigateTo(AppScreen.DASHBOARD)
        }
    }

    if (showLoginScreen) {
        LoginScreen(
            errorMessage = loginError,
            onStudentLogin = { phone, cls, roll ->
                val success = viewModel.loginStudent(phone, cls, roll)
                if (success) showLoginScreen = false
            },
            onTeacherLogin = { phone, ilhaq, pin ->
                val success = viewModel.loginTeacher(phone, ilhaq, pin)
                if (success) showLoginScreen = false
            },
            onPrincipalLogin = { phone, pin ->
                val success = viewModel.loginPrincipal(phone, pin)
                if (success) showLoginScreen = false
            },
            onAdminLogin = { secretEmail ->
                val success = viewModel.loginAdmin(secretEmail)
                if (success) showLoginScreen = false
            },
            onContinueAsGuest = {
                showLoginScreen = false
            }
        )
    } else {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                MadrasaTopBar(
                    currentScreen = currentScreen,
                    session = session,
                    onNavigateBack = { viewModel.navigateTo(AppScreen.DASHBOARD) },
                    onLogoutClick = {
                        viewModel.logout()
                        showLoginScreen = true
                    },
                    onLoginClick = { showLoginScreen = true }
                )
            },
            bottomBar = {
                NavigationBar(
                    containerColor = MadrasaSurfaceWhite,
                    contentColor = MadrasaGreenPrimary,
                    tonalElevation = 8.dp,
                    modifier = Modifier
                        .height(64.dp)
                        .testTag("madrasa_bottom_nav")
                ) {
                    NavigationBarItem(
                        selected = currentScreen == AppScreen.DASHBOARD,
                        onClick = { viewModel.navigateTo(AppScreen.DASHBOARD) },
                        icon = { Icon(Icons.Default.Dashboard, contentDescription = "ড্যাশবোর্ড") },
                        label = { Text("হোম", fontSize = 10.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = TextOnGreen,
                            selectedTextColor = MadrasaGreenPrimary,
                            indicatorColor = MadrasaGreenPrimary,
                            unselectedIconColor = TextMuted,
                            unselectedTextColor = TextMuted
                        ),
                        modifier = Modifier.testTag("nav_item_dashboard")
                    )

                    NavigationBarItem(
                        selected = currentScreen == AppScreen.ATTENDANCE,
                        onClick = { viewModel.navigateTo(AppScreen.ATTENDANCE) },
                        icon = { Icon(Icons.Default.CalendarMonth, contentDescription = "হাজিরা") },
                        label = { Text("হাজিরা", fontSize = 10.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = TextOnGreen,
                            selectedTextColor = MadrasaGreenPrimary,
                            indicatorColor = MadrasaGreenPrimary,
                            unselectedIconColor = TextMuted,
                            unselectedTextColor = TextMuted
                        ),
                        modifier = Modifier.testTag("nav_item_attendance")
                    )

                    NavigationBarItem(
                        selected = currentScreen == AppScreen.RESULTS,
                        onClick = { viewModel.navigateTo(AppScreen.RESULTS) },
                        icon = { Icon(Icons.Default.Grade, contentDescription = "ফলাফল") },
                        label = { Text("ফলাফল", fontSize = 10.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = TextOnGreen,
                            selectedTextColor = MadrasaGreenPrimary,
                            indicatorColor = MadrasaGreenPrimary,
                            unselectedIconColor = TextMuted,
                            unselectedTextColor = TextMuted
                        ),
                        modifier = Modifier.testTag("nav_item_results")
                    )

                    NavigationBarItem(
                        selected = currentScreen == AppScreen.NOTICES,
                        onClick = { viewModel.navigateTo(AppScreen.NOTICES) },
                        icon = { Icon(Icons.Default.Notifications, contentDescription = "নোটিশ") },
                        label = { Text("নোটিশ", fontSize = 10.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = TextOnGreen,
                            selectedTextColor = MadrasaGreenPrimary,
                            indicatorColor = MadrasaGreenPrimary,
                            unselectedIconColor = TextMuted,
                            unselectedTextColor = TextMuted
                        ),
                        modifier = Modifier.testTag("nav_item_notices")
                    )

                    NavigationBarItem(
                        selected = currentScreen == AppScreen.SYLLABUS,
                        onClick = { viewModel.navigateTo(AppScreen.SYLLABUS) },
                        icon = { Icon(Icons.Default.MenuBook, contentDescription = "কিতাবসমূহ") },
                        label = { Text("কিতাব", fontSize = 10.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = TextOnGreen,
                            selectedTextColor = MadrasaGreenPrimary,
                            indicatorColor = MadrasaGreenPrimary,
                            unselectedIconColor = TextMuted,
                            unselectedTextColor = TextMuted
                        ),
                        modifier = Modifier.testTag("nav_item_syllabus")
                    )

                    // ১০. আপডেট লগ: শুধুমাত্র প্রশাসকের জন্য দৃশ্যমান
                    if (session?.role == UserRole.ADMIN) {
                        NavigationBarItem(
                            selected = currentScreen == AppScreen.UPDATE_LOG,
                            onClick = { viewModel.navigateTo(AppScreen.UPDATE_LOG) },
                            icon = { Icon(Icons.Default.Shield, contentDescription = "আপডেট লগ") },
                            label = { Text("অ্যাডমিন", fontSize = 10.sp) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = TextOnGreen,
                                selectedTextColor = Color(0xFF073B24),
                                indicatorColor = Color(0xFF073B24),
                                unselectedIconColor = MadrasaGold,
                                unselectedTextColor = TextMuted
                            ),
                            modifier = Modifier.testTag("nav_item_update_log")
                        )
                    }
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when (currentScreen) {
                    AppScreen.DASHBOARD -> {
                        DashboardScreen(
                            session = session,
                            students = students,
                            notices = notices,
                            todayAttendances = todayAllAttendances,
                            onNavigate = { viewModel.navigateTo(it) },
                            onLoginClick = { showLoginScreen = true }
                        )
                    }
                    AppScreen.ATTENDANCE -> {
                        AttendanceScreen(
                            session = session,
                            selectedClass = selectedClass,
                            selectedDate = selectedDate,
                            students = students.filter { it.className == selectedClass },
                            attendances = classAttendances,
                            onSelectClass = { viewModel.setSelectedClass(it) },
                            onSetStatus = { student, status ->
                                viewModel.setAttendanceStatus(student, status)
                            },
                            onMarkAllPresent = {
                                viewModel.markAllPresentForSelectedClass()
                            }
                        )
                    }
                    AppScreen.RESULTS -> {
                        ResultsScreen(
                            session = session,
                            selectedClass = selectedClass,
                            selectedExamTerm = selectedExamTerm,
                            results = results,
                            students = students,
                            onSelectClass = { viewModel.setSelectedClass(it) },
                            onSelectExamTerm = { viewModel.setSelectedExamTerm(it) },
                            onSaveResult = { student, examTerm, q, h, f, n, s, g ->
                                viewModel.saveStudentResult(student, examTerm, q, h, f, n, s, g)
                            }
                        )
                    }
                    AppScreen.NOTICES -> {
                        NoticeBoardScreen(
                            session = session,
                            notices = notices,
                            onPublishNotice = { title, content, cat, isPinned ->
                                viewModel.publishNotice(title, content, cat, isPinned)
                            },
                            onDeleteNotice = { notice ->
                                viewModel.deleteNotice(notice)
                            }
                        )
                    }
                    AppScreen.SYLLABUS -> {
                        SyllabusScreen(
                            books = books,
                            selectedClass = selectedClass,
                            onSelectClass = { viewModel.setSelectedClass(it) }
                        )
                    }
                    AppScreen.UPDATE_LOG -> {
                        UpdateLogScreen(
                            session = session,
                            updateLogs = updateLogs,
                            onAddLog = { title, desc, ver ->
                                viewModel.addUpdateLogEntry(title, desc, ver)
                            }
                        )
                    }
                }
            }
        }
    }
}
