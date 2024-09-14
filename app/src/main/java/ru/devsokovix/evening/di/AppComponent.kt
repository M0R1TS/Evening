package ru.devsokovix.evening.di

import dagger.Component
import ru.devsokovix.evening.di.modules.DatabaseModule
import ru.devsokovix.evening.di.modules.DomainModule
import ru.devsokovix.evening.viewmodel.HomeFragmentViewModel
import ru.devsokovix.evening.viewmodel.SettingsFragmentViewModel
import ru.devsokovix.remote_module.RemoteProvider
import javax.inject.Singleton

@Singleton
@Component(
    //Внедряем все модули, нужные для этого компонента
    dependencies = [RemoteProvider::class],
    modules = [
        DatabaseModule::class,
        DomainModule::class
    ]
)
interface AppComponent {
    //метод для того, чтобы появилась возможность внедрять зависимости в HomeFragmentViewModel
    fun inject(homeFragmentViewModel: HomeFragmentViewModel)
    //метод для того, чтобы появилась возможность внедрять зависимости в SettingsFragmentViewModel
    fun inject(settingsFragmentViewModel: SettingsFragmentViewModel)
}