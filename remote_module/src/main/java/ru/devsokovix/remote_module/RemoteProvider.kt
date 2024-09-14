package ru.devsokovix.remote_module

interface RemoteProvider {
    fun provideRemote(): TmdbApi
}