package project.docs.files.addrequest_kotlin.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import project.docs.files.addrequest_kotlin.ui.TicketDetail.TicketDetailActivity


@Module
abstract class ActivitiesBindingModule {

    @ContributesAndroidInjector abstract fun ticketDetailActivity(): TicketDetailActivity
}