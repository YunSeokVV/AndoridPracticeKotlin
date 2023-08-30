package com.example.samplekotlin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.samplekotlin.vo.Plant
import com.orhanobut.logger.Logger

//AppDatabase 클래스다.
    @Database(entities = [Plant::class], version = 1)
    abstract class PlantDatabase : RoomDatabase() {
    // PlantDB에 접근하기 위한 Dao이다.
    abstract fun plantDao(): PlantDao

    //멀티스레드 환경에서 RoomDB를 사용하는게 아니라면 RoomDB객체를 싱글톤 패턴으로 만들어 줘야 한다.
    // companion은 자바로 치면 static의 역할을 해준다.
    companion object {
        private var instance: PlantDatabase? = null

            fun getInstance(context : Context): PlantDatabase? {
                if(instance == null ){
                    //다른 메소드가 접근하는 것을 허용하지 않게끔 lock 시켜주고 하나의 스레드만 접근할 수 있게끔 해준다.
                    synchronized(PlantDatabase::class){
                        instance = Room.databaseBuilder(
                            context.applicationContext, PlantDatabase::class.java, "plant_database").build()
                    }
                }

                return instance
            }


    }


}