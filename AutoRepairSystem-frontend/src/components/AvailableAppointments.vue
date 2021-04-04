<template>
<div id="appointments-page">
  <div class="navbarContainer">
    <CustomerNavbar/>
  </div>

  <div class="sidemenu">
    <h3>Book Appointment</h3>
    <div id="service-block">

      <div class="service-table">
      <table id="service-table">
        <thead>
        <tr>
          <th id="header-appointments">Service</th>
          <th id="header-appointments">Duration</th>
          <th id="header-appointments">Price</th>
        </tr>
        </thead>
        <tbody>
          <template v-for="service in services">
          <tr v-bind:key="service.name">
            <td><div id="servicediv"><input type="checkbox" v-bind:value="service" v-model="checkedServices"><span id="service">{{service.name}}</span></div></td>
            <td>{{service.duration}} minutes</td>
            <td>${{service.price}}</td>
          </tr>
          </template>
        </tbody>
      </table>
      </div>

      <div class="btn-group mt-2 mb-4" role="group" aria-label="actionButtons"> </div>

      <div id="totals">
      Number of services: {{checkedServices.length}} checked services<br>
      Total duration: {{getTotalServicesDuration()}} minutes<br>
      Total price: ${{getTotalServicesPrice()}}
      </div>

      <div class="datetime-block">
        <h3>Date & Time</h3>
        <br>
        <label for="appt-date"></label><span id="datetimepicker"><input type="date" style="height: 46px" @input="inputDate = getDateClean($event.target.value)"></span>
        <vue-timepicker input-width = "100px" format="HH:mm" :minute-interval="15" @input="appointmentStartTime"></vue-timepicker>
      </div>

      <div style="margin-top: 30px;">
        <button v-bind:disabled="!inputDate || !inputTime.HH || !inputTime.mm || checkedServices.length<=0" v-on:click="createAppointment()">Book Appointment</button>
      </div>
      <span v-if="errorMessage" style="color:red">Error: {{errorMessage}}</span>

      <button class="back" v-on:click="backButton" >Back</button>

    </div>
  </div>

  <div class="calendar">
    <h2>Availability Calendar</h2>
    <div id="month-header"><span id="month">{{month}} {{year}}</span></div>
    <div id="week-buttons"><button class="week-change" :disabled="!loaded" v-on:click="changeWeek(false)">Previous</button><button class="week-change" :disabled="!loaded" v-on:click="changeWeek(true)">Next  </button></div>
    <table id="appointments-table">
      <tr id="calendar-top-row">
        <td>Sun.<br>{{weekdays[0]}}</td>
        <td>Mon.<br>{{weekdays[1]}}</td>
        <td>Tue.<br>{{weekdays[2]}}</td>
        <td>Wed.<br>{{weekdays[3]}}</td>
        <td>Thu.<br>{{weekdays[4]}}</td>
        <td>Fri.<br>{{weekdays[5]}}</td>
        <td>Sat.<br>{{weekdays[6]}}</td>
      </tr>
      <tr id="available-slots" >
        <td>
          <div id="availability-block" v-for="availability in availabilities[0]">{{availability.startTime.slice(0, 5)}}<br>{{availability.endTime.slice(0, 5)}}</div>
        </td>
        <td>
          <div id="availability-block" v-for="availability in availabilities[1]">{{availability.startTime.slice(0, 5)}}<br>{{availability.endTime.slice(0, 5)}}</div>
        </td>
        <td>
          <div id="availability-block" v-for="availability in availabilities[2]">{{availability.startTime.slice(0, 5)}}<br>{{availability.endTime.slice(0, 5)}}</div>
        </td>
        <td>
          <div id="availability-block" v-for="availability in availabilities[3]">{{availability.startTime.slice(0, 5)}}<br>{{availability.endTime.slice(0, 5)}}</div>
        </td>
        <td>
          <div id="availability-block" v-for="availability in availabilities[4]">{{availability.startTime.slice(0, 5)}}<br>{{availability.endTime.slice(0, 5)}}</div>
        </td>
        <td>
          <div id="availability-block" v-for="availability in availabilities[5]">{{availability.startTime.slice(0, 5)}}<br>{{availability.endTime.slice(0, 5)}}</div>
        </td>
        <td>
          <div id="availability-block" v-for="availability in availabilities[6]">{{availability.startTime.slice(0, 5)}}<br>{{availability.endTime.slice(0, 5)}}</div>
        </td>
      </tr>
    </table>
  </div>
</div>
</template>
<style>

.sidemenu {
  /*background-color: #353A57;*/
  width: 25%;
  height: 100vh;
  float: left;
  padding: 10px;
  color: white;

  margin-top: 5px;
  margin-left: 5px;

  border-radius: 20px;

  box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);

  background: linear-gradient(-45deg, #380036, #0CBABA);
  background-size: 400% 400%;
  animation: gradient 15s ease infinite;
}

@keyframes gradient {
  0% {
    background-position: 0% 50%;
  }
  50% {
    background-position: 100% 50%;
  }
  100% {
    background-position: 0% 50%;
  }
}

.calendar {
  background-color: white;
  width: 70%;
  height: 100vh;
  float: right;
  padding: 10px;
  margin-right: 2%;
}

.service-table {
  overflow-y: auto;
  height: 30vh;
}

h2, h3{
font-family: 'Poppins', sans-serif;
}

.service-table thead th {
  position: sticky;
  top: 0;
}

#service-table {
  border-collapse: collapse;
  width: 100%;
  background-color: #EAF0F4;
  color: black;
}

#service {
  margin-left: 10px;
}

#servicediv {
  margin-top: 5px;
}

#header-appointments {
  background: white;
}

.datetime-block {
  margin-top: 30px;
  text-align: left;
  font-size: 20px;
}

.datetime-block h3 {
  text-align: center;
}

#datetimepicker {
  margin-left: 10px;
}

.back {
  position: absolute;
    bottom: 10px;
    left: 0%;
    margin-left: 10px;
}

#month-header {
  font-size: 25px;
  float: left;
}

#week-buttons {
  float: right;
  margin-bottom: 5px;
}

button.week-change {
  margin-left: 5px;
  margin-right: 5px;
  width: 100px;
  font-size: 20px;
}

#appointments-table {
  width: 100%;
  table-layout: fixed;
}

#available-slots, #available-slots td, #calendar-top-row, #calendar-top-row td, #service-table tr, #service-table td {
  border: 1px solid black;
  text-align: center;
}

/*The rows of the appointment calendar:
*/
#calendar-top-row {
  background-color: white;
  font-size: 20px;
}

#available-slots {
  height: calc(100vh - 150px);
  background-color: #EAF0F4;
}

/*Availability blocks in the appointment calendar:
*/
#availability-block {
  background-color: white;
  width: 95%;
  border-radius: 50px;
  border: 1px solid black;
  margin: 0 auto;
  padding-left: 10px;
  padding-right: 10px;
  margin-bottom: 5px;
  margin-top: 5px;
}

body {
  --overflow: hidden; /*This stops the page from having a scroll bar (might be able to remove this later)*/
}

#app {
  margin-top: 0;
}

#totals {
  text-align: left;
}

.navbarContainer {
  margin-bottom: 0px;
}

</style>

<script>
import CustomerNavbar from '@/components/CustomerNavbar'
import VueTimepicker from 'vue2-timepicker'
import 'vue2-timepicker/dist/VueTimepicker.css'

import axios from 'axios';
var config = require('../../config');

var frontendUrl = "https://" + config.build.host + ":" + config.build.port;
var backendUrl =
  "https://" + config.build.backendHost + ":" + config.build.backendPort;

  var AXIOS = axios.create({
  baseURL: backendUrl,
  //headers: { "Access-Control-Allow-Origin": frontendUrl },
});

//Variable which stores the date of the start of the displayed week (Sunday)
var date = new Date();
date.setDate(date.getDate()-date.getDay());

//This function returns the month displayed (mid-week day's month)
function getDisplayedMonth() {
  var midWeek = new Date(date);
  midWeek.setDate(midWeek.getDate()+3);
  return midWeek.toLocaleString('default', { month: 'long' });
}

//This function returns the year displayed (mid-week day's year)
function getDisplayedYear() {
  var midWeek = new Date(date);
  midWeek.setDate(midWeek.getDate()+3);
  return midWeek.getFullYear();
}

//This function returns the day of the week displayed
function getWeekdays() {
  var datesOfWeek = ["", "", "", "", "", "", ""];

  var day = new Date(date);
  datesOfWeek[0] = date.getDate();
  for (var i=1;i<7;i++) {
    day.setDate(day.getDate()+1);
    datesOfWeek[i] = day.getDate();
  }

  return datesOfWeek;
}

function CustomerDto(username) {
  this.username = username;
}

function AppointmentDto(customer, services, startTime, startDate) {
  this.customer = new CustomerDto(customer);
  this.services = services;
  this.startTime = startTime;
  this.date = startDate;
}

function formatDate(aDate) {
  var offset = aDate.getTimezoneOffset()*60000; // timezone offset in milliseconds
  var formattedDate = (new Date(aDate - offset)).toISOString().slice(0,10);
  return formattedDate;
}

export default {
  name: "AvailableAppointments",
  data () {
    return {
      errorMessage: "",
      month: "",
      year: "",
      weekdays: ["", "", "", "", "", "", ""],
      inputDate: "",
      inputTime: {HH: "", mm: ""},
      services: [],
      checkedServices: [],
      availabilities: [],
      username: this.$store.getters.getActiveUserName,
      loaded: true,
    }
  },

  components: {
    CustomerNavbar,
    VueTimepicker
  },

  created: function () {
    AXIOS.get(`/workitems`)
        .then((response) => {
          this.services = response.data;
          console.log(this.services);
        })
        .catch((e) => {
          this.errorMessage = e.response.data;
        });

    this.month = getDisplayedMonth();
    this.year = getDisplayedYear();
    this.weekdays = getWeekdays();

    this.getWeekAvailabilities();

  },


  methods: {

    //This method changes the displayed week and related information
    changeWeek: function(addDays) {
      if (addDays) date.setDate(date.getDate()+7);
      else date.setDate(date.getDate()-7)
      this.year = getDisplayedYear();
      this.month = getDisplayedMonth();
      this.weekdays = getWeekdays();
      this.getWeekAvailabilities();
    },

    //This method takes the user back to the customer home page
    backButton: function() {
      this.$router.push({name: "CustomerHome"});
    },

    getTotalServicesPrice: function() {
      var totalPrice = 0;
      for (var i=0; i<this.checkedServices.length; i++) {
        totalPrice += this.checkedServices[i].price;
      }
      return totalPrice;
    },

    getTotalServicesDuration: function() {
      var totalDuration = 0;
      for (var i=0; i<this.checkedServices.length; i++) {
        totalDuration += this.checkedServices[i].duration;
      }
      return totalDuration;
    },

    createAppointment: function () {
      var dateString = this.inputDate.toLocaleString("default", {dateStyle: "full"});
      var timeString = this.inputTime.HH + ":" + this.inputTime.mm;

      var servicesString = this.checkedServices[0].name;
      for (var i=1; i<this.checkedServices.length-1; i++) {
        servicesString += ", " + this.checkedServices[i].name;
      }

      if (this.checkedServices[this.checkedServices.length-1].name !== this.checkedServices[0].name) {
        servicesString += ", " + this.checkedServices[this.checkedServices.length-1].name + ".";
      }

      var confirmation = window.confirm("Are you sure you want to book an appointment on " + dateString + " at " + timeString + " with the following services: " + servicesString);

      if (confirmation) {
        var timeInput = this.inputTime.HH + ":" + this.inputTime.mm + ":00";
        var dateInput = formatDate(this.inputDate);
        var apptDto = new AppointmentDto(this.username, this.checkedServices, timeInput, dateInput);

        AXIOS.post(`/create/appointment/any`, apptDto)
        .then((response) => {
          window.alert("Thank you for booking an appointment with us!");
          this.errorMessage = "";
          this.getWeekAvailabilities();
        })
        .catch((e) => {
          this.errorMessage = e.response.data;
        });
      }
    },

    getWeekAvailabilities: async function() {   //Returns the availabilities for every day of the week
      this.loaded = false;
      this.availabilities = [];
      var currDate = new Date(date);

      for (var i=0; i<7; i++) {
        var dailyAvailabilities = [];
        var dateInput = formatDate(currDate);

        this.availabilities.push([]);

        await AXIOS.get(`/available/` + dateInput + `?minDuration=0`)
          .then((response) => {
            for (var x in response.data) {
              for (var y in response.data[x]) {
                this.availabilities[i].push(response.data[x][y]);
              }
            }
          })
          .catch((e) => {
            console.log(e);
            this.errorMessage = e.response.data;
          });

        currDate.setDate(currDate.getDate()+1);
      }

      this.loaded = true;
    },

    //This method converts date input field to a date object (Where we only care about the date)
    getDateClean(currDate) {
        if (currDate == "") return "";
        var selectedDate = new Date(currDate + "T00:00:00");
        return selectedDate;
    },

    //This method converts time input field to a date object (Where we only care about the time)
    getTimeClean(currTime) {
      if (currTime == "") return "";
      var selectedTime = new Date();
      var time = currTime.split(":");
      selectedTime.setHours(time[0], time[1], "00");
      return selectedTime;
    },

    appointmentStartTime(eventData) {
      this.inputTime = eventData;
    },

  }
  
}

</script>