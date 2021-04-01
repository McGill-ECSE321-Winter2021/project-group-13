import Vue from 'vue'
import Router from 'vue-router'
import SignIn from "@/components/SignIn"
import CustomerHome from "@/components/CustomerHome"
import TechnicianHome from "@/components/TechnicianHome"
import AdministratorHome from "@/components/AdministratorHome"
import AvailableAppointments from "@/components/AvailableAppointments"
import BookAppointmentConfirmation from "@/components/BookAppointmentConfirmation"
import PayInvoice from "@/components/PayInvoice"
import AccountCreation from "@/components/AccountCreation"
import AdminUserManagement from "@/components/AdminUserManagement"
import AdminNavbar from "@/components/AdminNavbar"
import CustomerNavbar from "@/components/CustomerNavbar"
import AdminTechnicianSchedule from "@/components/AdminTechnicianSchedule"

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
      path: '/PayInvoice',
      name: 'PayInvoice',
      component: PayInvoice
    },

    {
      path: '/AdminNav',
      name: 'AdminNavbar',
      component: AdminNavbar
    },

      {
      path: '/CustomerNavbar',
      name: 'CustomerNavbar',
      component: CustomerNavbar
    },

    {
      path: '/AccountCreation',
      name: 'AccountCreation',
      component: AccountCreation
    },
    
    {
      path: '/AdminUserManagement',
      name: 'AdminUserManagement',
      component: AdminUserManagement
    },
    
    {
      path: '/AdminTechnicianSchedule',
      name: 'AdminTechnicianSchedule',
      component: AdminTechnicianSchedule
    },

        {
      path: '/AdminTechnicianSchedule',
      name: 'AdminTechnicianSchedule',
      component: AdminTechnicianSchedule
    },
  ]
})
