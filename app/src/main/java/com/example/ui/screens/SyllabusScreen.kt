package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
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
import com.example.data.model.BookEntity
import com.example.ui.theme.MadrasaCardBorder
import com.example.ui.theme.MadrasaGold
import com.example.ui.theme.MadrasaGoldDark
import com.example.ui.theme.MadrasaGreenDark
import com.example.ui.theme.MadrasaGreenPrimary
import com.example.ui.theme.MadrasaSurfaceWhite
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextOnGreen

@Composable
fun SyllabusScreen(
    books: List<BookEntity>,
    selectedClass: String,
    onSelectClass: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    val filteredBooks = books.filter { book ->
        (book.className == selectedClass || selectedClass == "সকল জামাত") &&
                (searchQuery.isBlank() ||
                        book.bookName.contains(searchQuery, true) ||
                        book.subject.contains(searchQuery, true) ||
                        book.author.contains(searchQuery, true))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F7F4))
            .testTag("syllabus_screen")
    ) {
        // সবুজ হেডার (সবুজ ব্যাকগ্রাউন্ডে সাদা টেক্সট)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MadrasaGreenPrimary)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Column {
                Text(
                    text = "প্রতিটি জামাতের দরসি কিতাব ও বিষয়সমূহ",
                    color = TextOnGreen,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "দরসে নিজামি কারিকুলাম ও বোর্ড সিলেবাসের প্রামাণ্য তালিকা",
                    color = TextOnGreen.copy(alpha = 0.85f),
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                // জামাত সিলেক্টর চিপস
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(listOf("সকল জামাত") + InitialDataProvider.madrasaClasses) { cls ->
                        val isSelected = cls == selectedClass
                        Surface(
                            onClick = { onSelectClass(cls) },
                            shape = RoundedCornerShape(16.dp),
                            color = if (isSelected) MadrasaGold else MadrasaGreenDark,
                            modifier = Modifier.testTag("syllabus_class_$cls")
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

        // কিতাব অনুসন্ধান
        Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("কিতাবের নাম, লেখক বা শাস্ত্র দিয়ে খুঁজুন...", fontSize = 13.sp) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = MadrasaGreenPrimary) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("syllabus_search_input"),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MadrasaGreenPrimary,
                    unfocusedContainerColor = MadrasaSurfaceWhite,
                    focusedContainerColor = MadrasaSurfaceWhite
                ),
                shape = RoundedCornerShape(10.dp)
            )
        }

        // কিতাবের তালিকা (সাদা ব্যাকগ্রাউন্ডে কালো টেক্সট)
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "মোট কিতাব: ${filteredBooks.size} টি",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                    Text(
                        text = "শ্রেণি: $selectedClass",
                        fontSize = 12.sp,
                        color = MadrasaGreenPrimary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            if (filteredBooks.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MadrasaSurfaceWhite),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "কোনো কিতাবের বিবরণ পাওয়া যায়নি।",
                            modifier = Modifier.padding(24.dp),
                            textAlign = TextAlign.Center,
                            color = TextMuted,
                            fontSize = 13.sp
                        )
                    }
                }
            } else {
                items(filteredBooks) { book ->
                    BookItemCard(book = book)
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@Composable
fun BookItemCard(book: BookEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("book_card_${book.id}"),
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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE8F5E9)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoStories,
                            contentDescription = null,
                            tint = MadrasaGreenPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = book.bookName,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextDark
                        )
                        Text(
                            text = "শ্রেণি: ${book.className}",
                            fontSize = 11.sp,
                            color = MadrasaGreenPrimary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Surface(
                    color = Color(0xFFEFF3EF),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "${book.totalMarks} নম্বর",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MadrasaGreenPrimary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Divider(color = Color(0xFFF0F0F0))
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Book,
                        contentDescription = null,
                        tint = TextMuted,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "বিষয়: ${book.subject}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextDark
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = TextMuted,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = book.author,
                        fontSize = 12.sp,
                        color = TextMuted
                    )
                }
            }

            if (book.description.isNotBlank()) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = book.description,
                    fontSize = 12.sp,
                    color = TextMuted,
                    lineHeight = 18.sp
                )
            }
        }
    }
}
