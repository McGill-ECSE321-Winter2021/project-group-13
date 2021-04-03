<template>

<html>

<head>
    <link href="https://use.fontawesome.com/releases/v5.15.3/css/all.css" rel="stylesheet">
</head>

<div>

  <div class="navbarContainer">
      <AdminNavbar/>
  </div>
  
  <div id="home">

<!--
    <div id="invoices">
      <button id="button2" @click="checkinvoices()">Check/Pay Outstanding Balances</button> 
    </div>
    -->


<div style="display: flex;">
    <div id="currentappointments" style="width: 50%;">
      <h2> Current Appointments </h2>
      <br>
      <div class="datetime-block">
        <label for="appt-date">Select a Date: </label><span id="datetimepicker"><input type="date" @input="getDateClean($event.target.value)"></span>
      </div>
      <table class="content-table">
        <thead>
          <tr>
            <th>Technician</th>
            <th>Start Time</th>
            <th>End Time</th>
            <th>Customer</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="appointment in appointments" @click="selectRow(appointment)" v-bind:key="appointment.id" :class="{'highlight': (appointment.id === selectedAppointment)}">
            <td>{{ appointment.technician.name }}</td>
            <td>{{ appointment.startTime }}</td>
            <td>{{ appointment.endTime }}</td>
            <td>{{ appointment.customer.name }}</td>
          </tr>
          <tr>
            <td></td>
            <td></td>
            <td></td>
          </tr>
        </tbody>
      </table>

          <button
            type="button" 
            class="btn-hover color2 small"
            id="delete-button"
            @click ="deleteAppointment(selectedAppointment)"><i class="fa fa-trash"></i> Delete
      </button>

    <br>
    <br>
    </div>

    <div id="currentappointments" style="flex-grow: 1;">
      <h2> Services </h2>
      <br>
      <table class="content-table">
        <thead>
          <tr>
            <th>Name</th>
            <th>Duration</th>
            <th>Price</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="service in services" @click="selectService(service)" v-bind:key="service.name" :class="{'highlight': (service.name === selectedService)}">
            <td>{{ service.name }}</td>
            <td>{{ service.duration }}</td>
            <td>{{ service.price }}</td>
          </tr>
          <tr>
            <td></td>
            <td></td>
            <td></td>
          </tr>
        </tbody>
      </table>

      <button
            type="button" 
            class="btn-hover color2 small"
            id="delete-button"
            @click ="deleteService(selectedService)"><i class="fa fa-trash"></i> Delete
      </button>

      <div>
        <h2 style="margin-top: 30px;"> Add Service </h2>

<div id="Service">

      <div class="field">
      <i class="fa fa-signature icon"></i>
        <input v-model="name" placeholder="Name">
      </div>

      <div class="field">
		<i class="fas fa-clock icon"></i>
        <input v-model="duration" placeholder="Duration">
      </div>

      <div class="field">
		<i class="fas fa-dollar-sign icon"></i>
        <input v-model="price" id="password-icon"  placeholder="Price">
      </div>

<button class="btn-hover color" @click="addService(name, duration, price)"> Add </button>

</div>

        </div>
    <br>
    <br>
    </div>

    </div>
  </div>
</div>

</html>
</template>


<style>


* {
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
}

.btn-hover.color {
    background-image: linear-gradient(to right, #25aae1, #4481eb, #04befe, #3f86ed);
}

.field{
  margin: 5px;
}

.btn-hover.color {
    background-image: linear-gradient(to right, #25aae1, #4481eb, #04befe, #3f86ed);
}


.btn-hover.color2 {
    background-image: linear-gradient(to right, #DC1C13, #EA4C46, #F07470);
}

.btn-hover {
    width: 150px;
    font-size: 16px;
    font-weight: 600;
    color: #fff;
    cursor: pointer;
    margin: 5px;
    height: 40px;
    text-align:center;
    border: none;
    background-size: 300% 100%;

    border-radius: 20px;
    moz-transition: all .4s ease-in-out;
    -o-transition: all .4s ease-in-out;
    -webkit-transition: all .4s ease-in-out;
    transition: all .4s ease-in-out;

    margin-top: 20px;
}

.small {
  width: 100px;
}

.btn-hover:hover {
    background-position: 100% 0;
    moz-transition: all .4s ease-in-out;
    -o-transition: all .4s ease-in-out;
    -webkit-transition: all .4s ease-in-out;
    transition: all .4s ease-in-out;
}

#button, #deletebutton, #button2, #buttonlogout{
  border-color: #3498db;
  color: #fff;
  box-shadow: 0 0 40px 40px #3498db inset, 0 0 0 0 #3498db;
  -webkit-transition: all 150ms ease-in-out;
  transition: all 150ms ease-in-out;
}


#home {
  background-color:white;
  width: 100%;
  height: 100vh;
  float: right;
  
}

#currentappointments{
  margin-top: 60px;
  }


#invoices{

margin-right: 1000px;

}

h2{
font-family: 'Poppins', sans-serif;
}

#button2{

align: left;
margin-right: 100px;
}

#deletebutton{
align: right;
margin-left: 1100px;
}

#deletebutton:hover, #button2:hover, #buttonlogout:hover, #button:hover {
  box-shadow: 0 0 10px 0 #3498db inset, 0 0 10px 4px #3498db;
}

.content-table {
  border-collapse: collapse;
  margin: 25px 0;
  font-size: 0.9em;
  min-width: 400px;
  border-radius: 5px 5px 0 0;
  overflow: hidden;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);

  padding-top: 100px;
  margin: auto;
  width: 30%;
}

.content-table thead tr {
  background-color: 
#013bbe;
  color: #ffffff;
  text-align: left;
  font-weight: bold;
}

.content-table th,
.content-table td {
  padding: 12px 15px;
}

.content-table tbody tr {
  border-bottom: 1px solid #dddddd;
}

.content-table tbody tr:nth-of-type(even) {
  background-color: #f3f3f3;
}

.content-table tbody tr:last-of-type {
  border-bottom: 2px solid #009879;
}

.content-table tbody tr.highlight {
  font-weight: bold;
  color: #009879;
}

.navbarContainer {
  margin-bottom: 0px;
}

.datetime-block{
  margin-bottom: 50px;
}

</style>

<script>
import AdminNavbar from '@/components/AdminNavbar'

import axios from "axios";
var config = require("../../config");
var frontendUrl = "https://" + config.build.host + ":" + config.build.port;
var backendUrl =
  "https://" + config.build.backendHost + ":" + config.build.backendPort;


  var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl },
});

export default {
  name: "SignIn",
  data () {
    return {
      amountpaying: 0,
      amountpayed:0,
      amountowed:0,
      temp:'',
      inputDate: null,
      appointments: [],
      services: [],
      selectedAppointment: null,
      selectedService: null,
    }
  },

  components: {
    AdminNavbar
  },

  created: function() {
    window.setInterval(() => {
      this.getServices()
    }, 2000)
  },

  methods: {

//actually delete account in database and then go to sign in
    deleteaccountpopup: function () {
       if (confirm('Do you want to Delete?')) {
           this.$router.push({name: "SignIn"}); //replace with delete account function and call this in that function
       } else {
           return false;
       }
    },

    getAppointments: function() {
      AXIOS.get(`/appointments/` + this.inputDate, {}, {})
          .then((response) => {
            this.appointments = response.data
          })
          
          .catch((e) => {
            var error= e.response.data;
            this.errorMessage = error;
          });
    },

    getServices: function() {
      AXIOS.get(`/workitems/`, {}, {})
          .then((response) => {
            this.services = response.data
          })
          
          .catch((e) => {
            var error= e.response.data;
            this.errorMessage = error;
          });
    },

    addService(name, duration, price){
          AXIOS.post(`/workitems/` + name + '?duration=' + duration + "&price=" + price, {}, {})
          .then((response) => {
            this.getServices();
          })
          
          .catch((e) => {
            var error= e.response.data;
            this.errorMessage = error;
            window.alert(this.errorMessage);
          });
    },

    deleteService(service){
      AXIOS.delete(`/workitem/` + service, {}, {})
          .then((response) => {
            this.getServices();
          })
          
          .catch((e) => {
            var error= e.response.data;
            this.errorMessage = error;

            window.alert(this.errorMessage);
          });
    },

    deleteAppointment(appointment){
      AXIOS.delete(`/delete/appointment/` + appointment.id, {}, {})
          .then((response) => {
            this.getAppointments();
          })
          
          .catch((e) => {
            var error= e.response.data;
            this.errorMessage = error;

            window.alert(this.errorMessage);
          });
    },


    //This method converts date input field to a date object (Where we only care about the date)
    getDateClean(currDate) {

      this.inputDate = currDate;

      //console.log(currDate);

      //if (currDate == "") return "";
      //var selectedDate = new Date(currDate + "T00:00:00");

      this.getAppointments();

      //console.log(selectedDate);

        //return selectedDate;
    },

    selectRow(appointment) {
      this.selectedAppointment = appointment.id;
    },

    selectService(service){
      this.selectedService = service.name;
    },

    signIn: function (Username, Password) {
      AXIOS.get(`/signin/?username=` + Username + `&password=` + Password, {}, {})
          .then((response) => {
            this.Username= '',
            this.Password= '',
            this.errorMessage= '',
            this.$router.push({name: this.userType.concat("Home")});
          })
          
          .catch((e) => {
            var error= e.response.data;
            this.errorMessage = error;
          });
           
    }
  }
}
  

  
</script>