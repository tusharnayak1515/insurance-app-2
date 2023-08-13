import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { CustomerRegisterComponent } from './customer-register/customer-register.component';
import { AboutUsComponent } from './about-us/about-us.component';
import { ContactUsComponent } from './contact-us/contact-us.component';
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
import { ProfileComponent } from './profile/profile.component';
import { noAuthGuard } from './guards/no-auth/no-auth.guard';
import { customerGuard } from './guards/customer/customer.guard';
import { adminGuard } from './guards/admin/admin.guard';
import { CustomerPoliciesComponent } from './customer-policies/customer-policies.component';

const routes: Routes = [
  {path: "", component: HomeComponent, pathMatch:'full'},
  {path: "customer-login", component: LoginComponent, pathMatch:'full', canActivate: [noAuthGuard]},
  {path: "admin-login", component: LoginComponent, pathMatch:'full', canActivate: [noAuthGuard]},
  {path: "customer-register", component: CustomerRegisterComponent, pathMatch:'full', canActivate: [noAuthGuard]},
  {path: "about-us", component: AboutUsComponent, pathMatch:'full'},
  {path: "contact-us", component: ContactUsComponent, pathMatch:'full'},
  {path: "customer", canActivateChild: [customerGuard], children: [
    {
      path: 'dashboard', component: CustomerDashboardComponent, pathMatch: 'full'
    },
    {
      path: 'apply-policy', component: ApplyPolicyComponent, pathMatch: 'full'
    },
    {
      path: 'policy-history', component: PolicyHistoryComponent, pathMatch: 'full'
    },
    {
      path: 'ask-question', component: AskQuestionComponent, pathMatch: 'full'
    },
    {
      path: 'question-history', component: QuestionHistoryComponent, pathMatch: 'full'
    },
    {
      path: 'profile', component: ProfileComponent, pathMatch: 'full'
    }
  ]},
  {path: "admin", canActivateChild: [adminGuard], children: [
    {
      path: 'dashboard', component: AdminDashboardComponent, pathMatch: 'full'
    },
    {
      path: 'view-customers', component: ViewCustomersComponent, pathMatch: 'full'
    },
    {
      path: 'categories', component: CategoriesComponent, pathMatch: 'full'
    },
    {
      path: 'policies', component: PoliciesComponent, pathMatch: 'full'
    },
    {
      path: 'customer-policies', component: CustomerPoliciesComponent, pathMatch: 'full'
    },
    {
      path: 'questions', component: QuestionsComponent, pathMatch: 'full'
    },
    {
      path: 'add-admin', component: AddAdminComponent, pathMatch: 'full'
    },
    {
      path: 'profile', component: ProfileComponent, pathMatch: 'full'
    }
  ]},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
