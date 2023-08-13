import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './navbar/navbar.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { CustomerRegisterComponent } from './customer-register/customer-register.component';
import { AboutUsComponent } from './about-us/about-us.component';
import { ContactUsComponent } from './contact-us/contact-us.component';
import { ProfileComponent } from './profile/profile.component';
import { CustomerDashboardComponent } from './customer-dashboard/customer-dashboard.component';
import { ApplyPolicyComponent } from './apply-policy/apply-policy.component';
import { PolicyHistoryComponent } from './policy-history/policy-history.component';
import { AskQuestionComponent } from './ask-question/ask-question.component';
import { QuestionHistoryComponent } from './question-history/question-history.component';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { ViewCustomersComponent } from './view-customers/view-customers.component';
import { CategoriesComponent } from './categories/categories.component';
import { PoliciesComponent } from './policies/policies.component';
import { QuestionsComponent } from './questions/questions.component';
import { AddAdminComponent } from './add-admin/add-admin.component';
import { FormsModule } from '@angular/forms';

import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';
import { CookieInterceptor } from './cookie-interceptor.interceptor';
import { CustomerPoliciesComponent } from './customer-policies/customer-policies.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    SidebarComponent,
    HomeComponent,
    LoginComponent,
    CustomerRegisterComponent,
    AboutUsComponent,
    ContactUsComponent,
    ProfileComponent,
    CustomerDashboardComponent,
    ApplyPolicyComponent,
    PolicyHistoryComponent,
    AskQuestionComponent,
    QuestionHistoryComponent,
    AdminDashboardComponent,
    ViewCustomersComponent,
    CategoriesComponent,
    PoliciesComponent,
    QuestionsComponent,
    AddAdminComponent,
    CustomerPoliciesComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    FormsModule,
    HttpClientModule,
  ],
  providers: [
    CookieService,
    { provide: HTTP_INTERCEPTORS, useClass: CookieInterceptor, multi: true },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
