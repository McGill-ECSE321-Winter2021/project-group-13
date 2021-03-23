import Vue from 'vue'
import Router from 'vue-router'
import SignIn from "@/components/SignIn"
import CustomerHome from "@/components/CustomerHome"
import TechnicianHome from "@/components/TechnicianHome"
import AdministratorHome from "@/components/AdministratorHome"
import AvailableAppointments from "@/components/AvailableAppointments"
import BookAppointmentConfirmation from "@/components/BookAppointmentConfirmation"
import ViewAllTechnicians from "@/components/ViewAllTechnicians"
import ViewAllCustomer from "@/components/ViewAllCustomer"
import ViewAllAppointments from "@/components/ViewAllAppointments"
import PayInvoice from "@/components/PayInvoice"

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'SignIn',
      component: SignIn
    } ,
    {
      path: '/CustomerHome',
      name: 'CustomerHome',
      component: CustomerHome
    } ,
    {
      path: '/TechnicianHome',
      name: 'TechnicianHome',
      component: TechnicianHome
    } ,
    {
      path: '/AdministratorHome',
      name: 'AdministratorHome',
      component: AdministratorHome
    } , 
    {
      path: '/AvailableAppointments',
      name: 'AvailableAppointments',
      component: AvailableAppointments
    }, 
    {
      path: '/BookAppointmentConfirmation',
      name: 'BookAppointmentConfirmation',
      component: BookAppointmentConfirmation
    }, 
    {
      path: '/ViewAllTechnicians',
      name: 'ViewAllTechnicians',
      component: ViewAllTechnicians
    }
    , 
    {
      path: '/ViewAllCustomer',
      name: 'ViewAllCustomer',
      component: ViewAllCustomer
    } , 
    {
      path: '/ViewAllAppointments',
      name: 'ViewAllAppointments',
      component: ViewAllAppointments
    }
    , 
    {
      path: '/PayInvoice',
      name: 'PayInvoice',
      component: PayInvoice
    }
    
    
  ]
})
