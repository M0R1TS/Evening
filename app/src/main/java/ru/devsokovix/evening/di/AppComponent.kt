package ru.devsokovix.evening.di

import dagger.Component
import ru.devsokovix.evening.di.modules.DatabaseModule
import ru.devsokovix.evening.di.modules.DomainModule
import ru.devsokovix.evening.di.modules.RemoteModule
import ru.devsokovix.evening.viewmodel.HomeFragmentViewModel
import javax.inject.Singleton

@Singleton
@Component(
    //Внедряем все модули, нужные для этого компонента
    modules = [
        RemoteModule::class,
        DatabaseModule::class,
        DomainModule::class
    ]
)

interface AppComponent {
    //метод для того, чтобы появилась внедрять зависимости в HomeFragmentViewModel
    fun inject(homeFragmentViewModel: HomeFragmentViewModel)
    //метод для того , чтобы появилась возможность внедрять зависимость в SettingFragmentViewModel
//    fun inject(settingsFragmentViewModel: SettingFragmentViewModel)
}