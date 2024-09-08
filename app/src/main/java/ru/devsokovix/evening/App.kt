package ru.devsokovix.evening


import android.app.Application
import ru.devsokovix.evening.di.AppComponent
import ru.devsokovix.evening.di.DaggerAppComponent
import ru.devsokovix.evening.di.modules.DatabaseModule
import ru.devsokovix.evening.di.modules.DomainModule
import ru.devsokovix.evening.di.modules.RemoteModule

class App : Application() {
    lateinit var dagger: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        //Создаем компонент
        dagger = DaggerAppComponent.builder()
            .remoteModule(RemoteModule())
            .databaseModule(DatabaseModule())
            .domainModule(DomainModule(this))
            .build()
    }

    companion object {
        lateinit var instance: App
            private set
    }
}