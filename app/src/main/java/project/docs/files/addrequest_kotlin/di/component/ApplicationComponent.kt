package project.docs.files.addrequest_kotlin.di.component


import android.app.Application
import dagger.Component
import project.docs.files.addrequest_kotlin.di.module.ApplicationModule
import javax.inject.Singleton
import dagger.BindsInstance
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import project.docs.files.addrequest_kotlin.di.ActivitiesBindingModule
import project.docs.files.addrequest_kotlin.di.App


@Singleton
@Component(
        modules = [
            AndroidSupportInjectionModule::class,
            ApplicationModule::class,
            ActivitiesBindingModule::class
        ]
)
interface ApplicationComponent: AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun create(app: Application):Builder

        fun build(): ApplicationComponent
    }
}