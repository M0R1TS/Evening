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
        //Инициализируем экземпляр App, через который будем получать доступ к остальным переменным
        instance = this
        //Создаем компонент
        dagger = DaggerAppComponent.builder()
            .remoteModule(RemoteModule())
            .databaseModule(DatabaseModule())
            .domainModule(DomainModule(this))
            .build()
    }

    companion object {
        //Здесь статически хранится ссылка на экземпляр App
        lateinit var instance: App
            //Приватный сеттер, чтобы нельзя было в эту переменную присвоить что-либо другое
            private set
    }
}