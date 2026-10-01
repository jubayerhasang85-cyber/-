package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.model.AttendanceEntity
import com.example.data.model.BookEntity
import com.example.data.model.NoticeEntity
import com.example.data.model.ResultEntity
import com.example.data.model.StudentEntity
import com.example.data.model.UpdateLogEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        StudentEntity::class,
        AttendanceEntity::class,
        NoticeEntity::class,
        ResultEntity::class,
        BookEntity::class,
        UpdateLogEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MadrasaDatabase : RoomDatabase() {

    abstract fun madrasaDao(): MadrasaDao

    companion object {
        @Volatile
        private var INSTANCE: MadrasaDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): MadrasaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MadrasaDatabase::class.java,
                    "mazharul_uloom_madrasa.db"
                )
                    .addCallback(MadrasaDatabaseCallback(scope))
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class MadrasaDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        InitialDataProvider.populateInitialData(database.madrasaDao())
                    }
                }
            }
        }
    }
}
